package org.zaproxy.zap.extension.scriptgen;

import com.h3xstream.scriptgen.ScriptGeneratorConstants;
import org.parosproxy.paros.Constant;
import org.parosproxy.paros.extension.ExtensionAdaptor;
import org.parosproxy.paros.extension.ExtensionHook;

import java.util.ResourceBundle;

public class ScriptGeneratorMenuExtension extends ExtensionAdaptor {

    private ScriptGeneratorMenuAction menuItem;
    private ResourceBundle messages = null;


    public ScriptGeneratorMenuExtension(String msg) {
        super(msg);
        initExtension();
    }

    public ScriptGeneratorMenuExtension() {
        initExtension();
    }

    private void initExtension() {
        messages = ResourceBundle.getBundle(this.getClass().getPackage().getName() + ".Messages", Constant.getLocale());
    }

    @Override
    public String getAuthor() {
        return ScriptGeneratorConstants.AUTHOR;
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

    private ScriptGeneratorMenuAction getPopupMsgMenu() {
        if (menuItem == null) {
            menuItem = new ScriptGeneratorMenuAction(getMessage("ext.scriptwriter.menu"));
            menuItem.setExtension(this);
        }
        return menuItem;
    }
}
