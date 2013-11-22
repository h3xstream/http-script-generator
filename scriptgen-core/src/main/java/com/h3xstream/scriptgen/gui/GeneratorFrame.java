package com.h3xstream.scriptgen.gui;

import com.h3xstream.scriptgen.ScriptGeneratorConstants;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GeneratorFrame<OPTION> extends JFrame {

    public static String CMB_LANGUAGE = "LANGUAGE_SELECTION";

    private RSyntaxTextArea codeTextArea;
    private JComboBox listLanguages;

    public GeneratorFrame(OPTION[] options,ActionListener languageChangeListener) {

        Container cont = this.getContentPane();
        cont.setLayout(new BorderLayout());

        buildLanguageOptions(cont, options, languageChangeListener);
        buildCodeSection(cont);

        setTitle(ScriptGeneratorConstants.PLUGIN_NAME);

        pack();
        setSize(new Dimension(800,450));

        setLocationRelativeTo(null);
    }

    private void buildLanguageOptions(Container container, OPTION[] options,ActionListener languageChangeListener) {
        listLanguages = new JComboBox(options);
        listLanguages.setName(CMB_LANGUAGE);
        listLanguages.addActionListener(languageChangeListener);
        container.add(listLanguages,BorderLayout.NORTH);
    }

    private void buildCodeSection(Container container) {

        codeTextArea = new RSyntaxTextArea(20, 60);
        codeTextArea.setCodeFoldingEnabled(false);
        codeTextArea.setEditable(false);

        RTextScrollPane sp = new RTextScrollPane(codeTextArea);
        container.add(sp, BorderLayout.CENTER);
    }

    /**
     *
     * @param code Code to place in the text area
     * @param syntax Expecting SyntaxConstants values
     */
    public void updateCode(String code,String syntax) {

        codeTextArea.setSyntaxEditingStyle(syntax);
        codeTextArea.setText(code);
    }

    public void updateLanguageSelection(int selectedIndex) {
        listLanguages.setSelectedIndex(selectedIndex);
    }


}
