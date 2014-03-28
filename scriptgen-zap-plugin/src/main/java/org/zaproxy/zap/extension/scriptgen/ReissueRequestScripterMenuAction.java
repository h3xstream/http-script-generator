package org.zaproxy.zap.extension.scriptgen;

import com.h3xstream.scriptgen.ReissueRequestScripter;
import com.h3xstream.scriptgen.model.HttpRequestInfo;
import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.view.PopupMenuHttpMessage;

public class ReissueRequestScripterMenuAction extends PopupMenuHttpMessage {

    private ReissueRequestScripterMenuExtension extension;

    public ReissueRequestScripterMenuAction(String label) {
        super(label);
    }

    @Override
    public void performAction(HttpMessage httpMessage) throws Exception {
        //View.getSingleton().showMessageDialog(extension.getMessage("ext.scriptgen.test"));
        HttpRequestInfo req = ZapHttpRequestMapper.buildRequestInfo(httpMessage);
        new ReissueRequestScripter(req).openDialogWindow();
    }

    @Override
    public boolean isEnableForInvoker(Invoker invoker) {
        return true;
    }

    public void setExtension(ReissueRequestScripterMenuExtension extension) {
        this.extension = extension;
    }
}
