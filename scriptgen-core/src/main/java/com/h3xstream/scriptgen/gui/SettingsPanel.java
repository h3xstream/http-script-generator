package com.h3xstream.scriptgen.gui;

import com.esotericsoftware.minlog.Log;
import com.h3xstream.scriptgen.model.DisplaySettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SettingsPanel extends JPanel {

    private JCheckBox chkProxy;
    private JCheckBox chkMinimalHeaders;
    private JCheckBox chkDisableSsl;
    private GeneratorController controller;
    private GeneratorFrame parent;

    public SettingsPanel(GeneratorFrame parent) {
        this.parent = parent;

        setLayout(new FlowLayout());

        UpdateCode updateCodeListener = new UpdateCode();

        chkProxy = new JCheckBox("Proxy redirection");
        chkProxy.setToolTipText("The requests will be redirected to Burp proxy");
        chkProxy.addItemListener(updateCodeListener);
        add(chkProxy);

        chkMinimalHeaders = new JCheckBox("Minimal headers");
        chkMinimalHeaders.setToolTipText("Potentially optional headers will be removed");
        chkMinimalHeaders.addItemListener(updateCodeListener);
        add(chkMinimalHeaders);

        chkDisableSsl = new JCheckBox("Disable SSL/TLS verification");
        chkDisableSsl.setToolTipText("The validation of the TLS certificate will be skipped.");
        chkDisableSsl.addItemListener(updateCodeListener);
        add(chkDisableSsl);

    }

    public void setController(GeneratorController controller) {
        this.controller = controller;
    }

    public DisplaySettings getDisplaySettings() {
        DisplaySettings settings = new DisplaySettings();
        settings.setProxy(chkProxy.isSelected());
        settings.setMinimalHeaders(chkMinimalHeaders.isSelected());
        settings.setDisableSsl(chkDisableSsl.isSelected());
        return settings;
    }



    private class UpdateCode implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            try {
                controller.updateDisplaySettings(parent, getDisplaySettings());
            } catch (Exception ex) {
                Log.error("Error occurs while updating the code: "+ex.getMessage());
            }
        }
    }
}
