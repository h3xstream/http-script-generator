package burp;


import com.h3xstream.scriptgen.HttpRequestInfo;
import com.h3xstream.scriptgen.ScriptGenerator;
import com.h3xstream.scriptgen.ScriptGeneratorConstants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class BurpExtender implements IBurpExtender, IContextMenuFactory {

    private IBurpExtenderCallbacks callbacks;
    private IExtensionHelpers helpers;


    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;
        this.helpers = callbacks.getHelpers();

        //Register context menu
        callbacks.registerContextMenuFactory(this);

        callbacks.setExtensionName(ScriptGeneratorConstants.PLUGIN_NAME);
    }


    @Override
    public List<JMenuItem> createMenuItems(IContextMenuInvocation invocation) {
        IHttpRequestResponse[] responsesSelected = invocation.getSelectedMessages();

        IRequestInfo requestInfo = helpers.analyzeRequest(responsesSelected[0].getHttpService(), responsesSelected[0].getRequest());

        List<JMenuItem> menuItems = new ArrayList<JMenuItem>();

        JMenuItem item = new JMenuItem(new GenerateScriptAction(requestInfo));
        menuItems.add(item);

        return menuItems;
    }

    private class GenerateScriptAction extends AbstractAction {
        private IRequestInfo requestInfo;
        public GenerateScriptAction(IRequestInfo requestInfo) {
            super("Generate Script");
            this.requestInfo = requestInfo;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //JOptionPane.showMessageDialog(null, "Testing...");

            HttpRequestInfo req = BurpHttpRequestMapper.buildRequestInfo(requestInfo);
            new ScriptGenerator(req).openDialogWindow();
        }
    }
}
