package org.zaproxy.zap.extension.scriptgen;

import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.view.View;
import org.zaproxy.zap.view.PopupMenuHttpMessage;

public class ScriptGeneratorMenuAction extends PopupMenuHttpMessage {

    private ScriptGeneratorMenuExtension extension;

    public ScriptGeneratorMenuAction(String label) {
        super(label);
    }

    @Override
    public void performAction(HttpMessage paramHttpMessage) throws Exception {
        View.getSingleton().showMessageDialog(extension.getMessage("ext.scriptwriter.test"));
    }

    @Override
    public boolean isEnableForInvoker(Invoker invoker) {
        return true;
    }

    public void setExtension(ScriptGeneratorMenuExtension extension) {
        this.extension = extension;
    }
}
