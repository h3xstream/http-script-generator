package org.zaproxy.zap.extension.scriptgen;

import com.h3xstream.scriptgen.ReissueRequestScripter;
import com.h3xstream.scriptgen.model.HttpRequestInfo;
import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.view.PopupMenuHttpMessage;
import org.zaproxy.zap.view.popup.PopupMenuItemHttpMessageContainer;

import java.io.IOException;
import java.util.List;

public class ReissueRequestScripterMenuAction extends PopupMenuItemHttpMessageContainer {

    private ReissueRequestScripterMenuExtension extension;

    public ReissueRequestScripterMenuAction(String label) {
        super(label);
    }

    protected void performActions(List<HttpMessage> var1) {

    }

    @Override
    public void performAction(HttpMessage httpMessage) {
        HttpRequestInfo req = null;
        try {
            req = ZapHttpRequestMapper.buildRequestInfo(httpMessage);
            new ReissueRequestScripter(req).openDialogWindow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void setExtension(ReissueRequestScripterMenuExtension extension) {
        this.extension = extension;
    }
}
