package burp;


import com.h3xstream.scriptgen.ScriptGeneratorConstants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class BurpExtender implements IBurpExtender, IContextMenuFactory {

    private IBurpExtenderCallbacks callbacks;
    private IExtensionHelpers helpers;

    private GenerateScriptAction action = new GenerateScriptAction();

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
        List<JMenuItem> menuItems = new ArrayList<JMenuItem>();

        JMenuItem item = new JMenuItem(action);
        menuItems.add(item);

        return menuItems;
    }

    private class GenerateScriptAction extends AbstractAction {
        public GenerateScriptAction() {
            super("Generate Script");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Testing...");
        }
    }
}
