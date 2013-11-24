package com.h3xstream.scriptgen.gui;

import com.h3xstream.scriptgen.ScriptGeneratorConstants;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class GeneratorFrame<OPTION> extends JFrame {

    public static String CMB_LANGUAGE = "LANGUAGE_SELECTION";

    public static ImageIcon icon;
    static {
        InputStream iconStream = GeneratorFrame.class.getResourceAsStream("/com/h3xstream/scriptgen/images/script_text.png");

        try {
            icon = new ImageIcon(inputStreamtToBytes(iconStream));
        } catch (IOException e) {
            System.err.println("Script icon could not be load..");
        }
    }

    private RSyntaxTextArea codeTextArea;
    private JComboBox listLanguages;

    public GeneratorFrame(OPTION[] options,ActionListener languageChangeListener) {

        Container cont = this.getContentPane();
        cont.setLayout(new BorderLayout());

        buildLanguageOptions(cont, options, languageChangeListener);
        buildCodeSection(cont);
        changeIcon();

        setTitle(ScriptGeneratorConstants.PLUGIN_NAME);

        pack();
        setSize(new Dimension(800,450));

        setLocationRelativeTo(null);
    }

    private void changeIcon() {
        if(icon != null) {
            this.setIconImage(icon.getImage());
        }
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

    /**
     * Force the selection of a language
     * @param selectedIndex
     */
    public void updateLanguageSelection(int selectedIndex) {
        listLanguages.setSelectedIndex(selectedIndex);
    }

    public void setTitleSuffix(String info) {
        setTitle(ScriptGeneratorConstants.PLUGIN_NAME+" - "+info);
    }

    private static byte[] inputStreamtToBytes(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[2048];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();

        return buffer.toByteArray();
    }

}
