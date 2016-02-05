package burp;


import com.esotericsoftware.minlog.Log;
import com.h3xstream.scriptgen.ReissueRequestScripterConstants;
import com.h3xstream.scriptgen.model.HttpRequestInfo;
import com.h3xstream.scriptgen.ReissueRequestScripter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

        return Arrays.asList(new JMenuItem(new GenerateScriptAction(responsesSelected)));
    }

    private class GenerateScriptAction extends AbstractAction {
        private IHttpRequestResponse[] requestSelected;

        public GenerateScriptAction(IHttpRequestResponse[] requestSelected) {
            super("Generate Script");
            this.requestSelected = requestSelected;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //JOptionPane.showMessageDialog(null, "Testing...");

            try {
                List<HttpRequestInfo> req = BurpHttpRequestMapper.buildListRequestInfo(requestSelected, helpers);
                new ReissueRequestScripter(req).openDialogWindow();
            }
            catch (Exception ex) {
                Log.error("Unexpected error while building the request entity. "+ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}
