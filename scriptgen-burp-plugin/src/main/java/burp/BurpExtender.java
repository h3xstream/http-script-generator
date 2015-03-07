package burp;


import com.esotericsoftware.minlog.Log;
import com.h3xstream.scriptgen.ReissueRequestScripterConstants;
import com.h3xstream.scriptgen.model.HttpRequestInfo;
import com.h3xstream.scriptgen.ReissueRequestScripter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BurpExtender implements IBurpExtender, IContextMenuFactory {

    private IBurpExtenderCallbacks callbacks;
    private IExtensionHelpers helpers;


    @Override
    public void registerExtenderCallbacks(final IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;
        this.helpers = callbacks.getHelpers();

        Log.setLogger(new Log.Logger() {
            @Override
            protected void print(String message) {
                try {
                    if (message.contains("ERROR:")) { //Not the most elegant way, but should be effective.
                        callbacks.issueAlert(message);
                    }
                    callbacks.getStdout().write(message.getBytes());
                    callbacks.getStdout().write('\n');
                } catch (IOException e) {
                    System.err.println("Error while printing the log : " + e.getMessage()); //Very unlikely
                }
            }
        });
        Log.DEBUG();

        //Register context menu
        callbacks.registerContextMenuFactory(this);

        callbacks.setExtensionName(ReissueRequestScripterConstants.PLUGIN_NAME);
    }


    @Override
    public List<JMenuItem> createMenuItems(IContextMenuInvocation invocation) {
        IHttpRequestResponse[] responsesSelected = invocation.getSelectedMessages();

        byte[] requestBytes = responsesSelected[0].getRequest();
        IRequestInfo requestInfo = helpers.analyzeRequest(responsesSelected[0].getHttpService(), requestBytes);

        List<JMenuItem> menuItems = new ArrayList<JMenuItem>();

        JMenuItem item = new JMenuItem(new GenerateScriptAction(requestInfo,requestBytes));
        menuItems.add(item);

        return menuItems;
    }

    private class GenerateScriptAction extends AbstractAction {
        private IRequestInfo requestInfo;
        private byte[] requestBytes;
        public GenerateScriptAction(IRequestInfo requestInfo,byte[] requestBytes) {
            super("Generate Script");
            this.requestInfo = requestInfo;
            this.requestBytes = requestBytes;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //JOptionPane.showMessageDialog(null, "Testing...");

            try {
                HttpRequestInfo req = BurpHttpRequestMapper.buildRequestInfo(requestInfo, requestBytes);
                new ReissueRequestScripter(req).openDialogWindow();
            }
            catch (Exception ex) {
                Log.error("Unexpected error while building the request entity."+ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}
