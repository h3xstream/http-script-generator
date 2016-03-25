package burp;


import com.esotericsoftware.minlog.Log;
import com.h3xstream.scriptgen.ReissueRequestScripterConstants;
import com.h3xstream.scriptgen.model.HttpRequestInfo;
import com.h3xstream.scriptgen.ReissueRequestScripter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BurpExtender implements IBurpExtender, IContextMenuFactory {

    private IExtensionHelpers helpers;


    @Override
    public void registerExtenderCallbacks(final IBurpExtenderCallbacks callbacks) {
        this.helpers = callbacks.getHelpers();

        PrintWriter stdout = new PrintWriter(callbacks.getStdout(), true);
        stdout.println("== Reissue Request Scripter plugin ==");
        stdout.println("Passive scan rules to detect vulnerable Javascript libraries");
        stdout.println(" - Github : https://github.com/h3xstream/http-script-generator");
        stdout.println("");
        stdout.println("== License ==");
        stdout.println("Reissue Request Scripter Burp plugin is release under LGPL.");
        stdout.println("");

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
