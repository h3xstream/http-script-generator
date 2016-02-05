package org.zaproxy.zap.extension.scriptgen;

import com.h3xstream.scriptgen.ReissueRequestScripter;
import com.h3xstream.scriptgen.model.HttpRequestInfo;
import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.view.PopupMenuHttpMessage;
import org.zaproxy.zap.view.popup.PopupMenuItemHttpMessageContainer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ReissueRequestScripterMenuAction extends PopupMenuItemHttpMessageContainer {

    private ReissueRequestScripterMenuExtension extension;

    public ReissueRequestScripterMenuAction(String label) {
        super(label, true); //true == the action can be done on multiple items
    }

    protected void performActions(List<HttpMessage> httpMessages) {
        try {
            List<HttpRequestInfo> req = ZapHttpRequestMapper.buildRequestInfo(httpMessages);
            new ReissueRequestScripter(req).openDialogWindow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void performAction(HttpMessage httpMessage) {
        try {
            List<HttpRequestInfo> req = ZapHttpRequestMapper.buildRequestInfo(Arrays.asList(httpMessage));
            new ReissueRequestScripter(req).openDialogWindow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void setExtension(ReissueRequestScripterMenuExtension extension) {
        this.extension = extension;
    }
}
