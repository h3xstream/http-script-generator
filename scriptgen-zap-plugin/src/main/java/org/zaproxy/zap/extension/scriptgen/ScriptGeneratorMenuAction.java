package org.zaproxy.zap.extension.scriptgen;

import com.h3xstream.scriptgen.HttpRequestInfo;
import com.h3xstream.scriptgen.ScriptGenerator;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.view.View;
import org.zaproxy.zap.view.PopupMenuHttpMessage;

public class ScriptGeneratorMenuAction extends PopupMenuHttpMessage {

    private ScriptGeneratorMenuExtension extension;

    public ScriptGeneratorMenuAction(String label) {
        super(label);
    }

    @Override
    public void performAction(HttpMessage httpMessage) throws Exception {
        //View.getSingleton().showMessageDialog(extension.getMessage("ext.scriptgen.test"));
        HttpRequestInfo req = ZapHttpRequestMapper.buildRequestInfo(httpMessage);
        new ScriptGenerator(req).openDialogWindow();
    }

    @Override
    public boolean isEnableForInvoker(Invoker invoker) {
        return true;
    }

    public void setExtension(ScriptGeneratorMenuExtension extension) {
        this.extension = extension;
    }
}
