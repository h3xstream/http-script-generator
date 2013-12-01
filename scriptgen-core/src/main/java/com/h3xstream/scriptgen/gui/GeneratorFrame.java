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
    private GeneratorController controller;

    public GeneratorFrame(OPTION[] options) {

        Container cont = this.getContentPane();
        cont.setLayout(new BorderLayout());

        //Building the window one part at the time
        buildLanguageOptions(cont, options, new LanguageSelectionChange()); //North
        buildCodeSection(cont); //Center
        changeIcon();
        setTitle(ScriptGeneratorConstants.PLUGIN_NAME);

        //Resizing
        pack();
        setSize(new Dimension(800,450));

        //position
        setLocationRelativeTo(null);
        centerWindow(this);
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


    private class LanguageSelectionChange implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox combo = (JComboBox) e.getSource();
            Object obj = combo.getSelectedItem();
            //System.out.println(obj.toString());
            if(obj instanceof LanguageOption) {
                try {
                    controller.updateLanguage(GeneratorFrame.this, (LanguageOption) obj);
                } catch (Exception e1) {
                    System.out.println(e1.getMessage());
                    //FIXME: Logging needed
                }
            }
        }
    }

}
