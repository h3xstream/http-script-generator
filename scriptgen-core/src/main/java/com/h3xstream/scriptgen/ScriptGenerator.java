package com.h3xstream.scriptgen;

import com.h3xstream.scriptgen.gui.GeneratorFrame;
import com.h3xstream.scriptgen.template.CodeTemplateBuilder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ScriptGenerator {
    private HttpRequestInfo req;
    private GeneratorFrame frame;

    public ScriptGenerator(HttpRequestInfo req) {
        this.req = req;
    }

    public JFrame openDialogWindow() {
        frame = new GeneratorFrame(LanguageOption.values, new LanguageSelectionChange());
        frame.updateLanguageSelection(0);
        frame.setTitleSuffix(req.getUrl());
        frame.setVisible(true);
        return frame;
    }

    private void update(GeneratorFrame frame, LanguageOption newOption) throws Exception {

        String codeGenerated = new CodeTemplateBuilder().request(req).templatePath(newOption.getTemplate()).build();

        frame.updateCode(codeGenerated,newOption.getSyntax());
    }

    private class LanguageSelectionChange implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox combo = (JComboBox) e.getSource();
            Object obj = combo.getSelectedItem();
            //System.out.println(obj.toString());
            if(obj instanceof LanguageOption) {
                try {
                    update(ScriptGenerator.this.frame,(LanguageOption) obj);
                } catch (Exception e1) {
                    System.out.println(e1.getMessage());
                    //FIXME: Logging needed
                }
            }
        }
    }
}
