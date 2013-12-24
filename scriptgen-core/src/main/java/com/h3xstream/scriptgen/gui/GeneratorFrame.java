package com.h3xstream.scriptgen.gui;

import com.h3xstream.scriptgen.LanguageOption;
import com.h3xstream.scriptgen.ScriptGeneratorConstants;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class build the main window containing the code snippet generated.
 * @param <OPTION>
 */
public class GeneratorFrame<OPTION> extends JFrame {

    //Components name refer in unit tests
    public static final String BUTTON_COPY = "BUTTON_COPY";
    public static final String BUTTON_SAVE = "BUTTON_SAVE";
    public static String CMB_LANGUAGE = "LANGUAGE_SELECTION";

    public static ImageIcon icon;
    static {
        InputStream iconStream = GeneratorFrame.class.getResourceAsStream("/com/h3xstream/scriptgen/images/script_text.png");

        try {
            icon = new ImageIcon(inputStreamToBytes(iconStream));
        } catch (IOException e) {
            System.err.println("Script icon could not be load..");
        }
    }

    private RSyntaxTextArea codeTextArea;
    private JComboBox listLanguages;
    private GeneratorController controller;

    public GeneratorFrame(OPTION[] options) {

        Container cont = this.getContentPane();
        cont.setLayout(new BorderLayout());

        //Building the window one part at the time
        buildLanguageOptions(cont, options, new LanguageSelectionChange()); //North
        buildCodeSection(cont); //Center
        buildSaveOptions(cont);
        changeIcon();
        setTitle(ScriptGeneratorConstants.PLUGIN_NAME);

        //Resizing
        pack();
        setSize(new Dimension(800,450));

        //position
        setLocationRelativeTo(null);
        centerWindow(this);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void changeIcon() {
        if(icon != null) {
            this.setIconImage(icon.getImage());
        }
    }

    private void centerWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
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

    private void buildSaveOptions(Container container) {


        JPanel buttonContainer = new JPanel(new FlowLayout());

        JButton buttonCopy = new JButton("Copy to clipboard");
        buttonCopy.setName(BUTTON_COPY);
        JButton buttonSave = new JButton("Save to file");
        buttonSave.setName(BUTTON_SAVE);
        buttonCopy.addActionListener(new CopyScriptToClipboard());
        buttonSave.addActionListener(new SaveScriptToFile());

        buttonContainer.add(buttonCopy);
        buttonContainer.add(buttonSave);

        container.add(buttonContainer,BorderLayout.SOUTH);
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

    public void setController(GeneratorController controller) {
        this.controller = controller;
    }

    private static byte[] inputStreamToBytes(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[2048];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();

        return buffer.toByteArray();
    }


    private class LanguageSelectionChange implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox combo = (JComboBox) e.getSource();
            Object langSelected = combo.getSelectedItem();
            //System.out.println(obj.toString());
            if(langSelected instanceof LanguageOption) {
                try {
                    controller.updateLanguage(GeneratorFrame.this, (LanguageOption) langSelected);
                } catch (Exception e1) {
                    System.err.println(e1.getMessage());
                    //FIXME: Logging needed
                }
            }
        }
    }


    private class CopyScriptToClipboard implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.copyToClipboard(codeTextArea.getText());
        }
    }

    private class SaveScriptToFile implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object langSelected = listLanguages.getSelectedItem();
            //System.out.println(obj.toString());
            if(langSelected instanceof LanguageOption) {
                controller.saveScriptToFile(codeTextArea.getText(),(LanguageOption) langSelected,GeneratorFrame.this);
            }
        }
    }
}
