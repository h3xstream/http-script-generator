package com.h3xstream.scriptgen;

import com.h3xstream.scriptgen.gui.GeneratorFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScriptGenerator {
    private HttpRequestInfo req;

    public ScriptGenerator(HttpRequestInfo req) {
        this.req = req;
    }

    public JFrame openDialogWindow() {
        JFrame frame = new GeneratorFrame(LanguageOption.values, new LanguageSelectionChange());
        frame.setVisible(true);
        return frame;
    }

    private void update(GeneratorFrame frame, LanguageOption newOption) {

        String codeGenerated = new CodeTemplateBuilder().request(req).templatePath(newOption.getTemplate()).build();

        frame.updateCode(codeGenerated,newOption.getSyntax());
    }

    private class LanguageSelectionChange implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox combo = (JComboBox) e.getSource();
            Object obj = combo.getSelectedItem();
            System.out.println(obj.toString());
        }
    }
}
