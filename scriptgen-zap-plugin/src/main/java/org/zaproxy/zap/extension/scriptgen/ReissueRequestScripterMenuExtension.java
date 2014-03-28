package org.zaproxy.zap.extension.scriptgen;

import com.h3xstream.scriptgen.ReissueRequestScripterConstants;
import org.parosproxy.paros.Constant;
import org.parosproxy.paros.extension.ExtensionAdaptor;
import org.parosproxy.paros.extension.ExtensionHook;

import java.util.ResourceBundle;

public class ReissueRequestScripterMenuExtension extends ExtensionAdaptor {

    private ReissueRequestScripterMenuAction menuItem;
    private ResourceBundle messages = null;


    public ReissueRequestScripterMenuExtension(String msg) {
        super(msg);
        initExtension();
    }

    public ReissueRequestScripterMenuExtension() {
        initExtension();
    }

    private void initExtension() {
        messages = ResourceBundle.getBundle(this.getClass().getPackage().getName() + ".Messages", Constant.getLocale());
    }

    @Override
    public String getAuthor() {
        return ReissueRequestScripterConstants.AUTHOR;
    }

    @Override
    public void hook(ExtensionHook extensionHook) {
        super.hook(extensionHook);

        if (getView() != null) {
            // Register our popup menu item, as long as we're not running as a daemon
            extensionHook.getHookMenu().addPopupMenuItem(getPopupMsgMenu());
        }
    }

    public String getMessage(String msgId) {
        return messages.getString(msgId);
    }

    private ReissueRequestScripterMenuAction getPopupMsgMenu() {
        if (menuItem == null) {
            menuItem = new ReissueRequestScripterMenuAction(getMessage("ext.scriptgen.menu"));
            menuItem.setExtension(this);
        }
        return menuItem;
    }
}
