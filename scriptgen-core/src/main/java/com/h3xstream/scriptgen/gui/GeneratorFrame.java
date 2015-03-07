package com.h3xstream.scriptgen.gui;

import com.esotericsoftware.minlog.Log;
import com.h3xstream.scriptgen.LanguageOption;
import com.h3xstream.scriptgen.ReissueRequestScripterConstants;
import net.miginfocom.swing.MigLayout;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Theme;
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
    public static final String BUTTON_SETTINGS = "BUTTON_SETTINGS";
    public static String CMB_LANGUAGE = "LANGUAGE_SELECTION";

    public static ImageIcon icon;
    static {
        InputStream iconStream = GeneratorFrame.class.getResourceAsStream("/com/h3xstream/scriptgen/images/script_text.png");

        try {
            icon = new ImageIcon(inputStreamToBytes(iconStream));
        } catch (IOException e) {
            Log.warn("Script icon could not be load..");
        }
    }

    private RSyntaxTextArea codeTextArea;
    private JComboBox listLanguages;
    private GeneratorController controller;

    private JPanel panelSettings;
    private boolean displayOptions = false;
    private SettingsPanel settingsPanel;

    public GeneratorFrame(OPTION[] options) {

        Container cont = this.getContentPane();
        cont.setLayout(new BorderLayout());

        //Building the window one part at the time
        buildLanguageOptions(cont, options, new LanguageSelectionChange()); //North
        buildCodeSection(cont); //Center
        buildSaveOptions(cont);
        changeIcon();
        setTitle(ReissueRequestScripterConstants.PLUGIN_NAME);

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
        //codeTextArea.setCodeFoldingEnabled(false);
        Theme t = loadDarkTheme();
        if(t != null) //Theme was load
            t.apply(codeTextArea);
        codeTextArea.setEditable(false);

        RTextScrollPane sp = new RTextScrollPane(codeTextArea);
        container.add(sp, BorderLayout.CENTER);
    }

    private Theme loadDarkTheme() {
        try {
            InputStream in = getClass().getResourceAsStream("/com/h3xstream/scriptgen/themes/dark.xml");
            return Theme.load(in);
        }
        catch(Exception e) {
            return null;
        }
    }

    private void buildSaveOptions(Container container) {


        JPanel buttonContainer = new JPanel(new MigLayout());

        //Copy to clipboard
        JButton buttonCopy = new JButton("Copy to clipboard");
        buttonCopy.setName(BUTTON_COPY);
        buttonCopy.addActionListener(new CopyScriptToClipboard());

        //Save to file
        JButton buttonSave = new JButton("Save to file");
        buttonSave.setName(BUTTON_SAVE);
        buttonSave.addActionListener(new SaveScriptToFile());

        //Setting button
        JToggleButton buttonSettings = new JToggleButton("Settings");
        buttonSettings.setName(BUTTON_SETTINGS);
        buttonSettings.addActionListener(new ToggleSettings());

        //Settings container
        panelSettings = new JPanel();
        panelSettings.setLayout(new BorderLayout());

        //Setting content

        settingsPanel = new SettingsPanel(GeneratorFrame.this);

        buttonContainer.add(buttonCopy);
        buttonContainer.add(buttonSave);
        buttonContainer.add(buttonSettings,"wrap");
        buttonContainer.add(panelSettings,    "span, grow");

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
        setTitle(ReissueRequestScripterConstants.PLUGIN_NAME+" - "+info);
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

            if(langSelected instanceof LanguageOption) {
                try {
                    controller.updateLanguage(GeneratorFrame.this, (LanguageOption) langSelected);
                } catch (Exception e1) {
                    Log.error("Error while updating the language" + e1.getMessage());
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

            if(langSelected instanceof LanguageOption) {
                controller.saveScriptToFile(codeTextArea.getText(),(LanguageOption) langSelected,GeneratorFrame.this);
            }
        }
    }

    private class ToggleSettings implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            displayOptions = !displayOptions;
            if(displayOptions) {
                settingsPanel.setController(controller);//Sync controller
                panelSettings.add(settingsPanel,BorderLayout.CENTER);
                GeneratorFrame.this.revalidate();
            }
            else {
                panelSettings.removeAll();
                GeneratorFrame.this.revalidate();
            }
        }
    }
}
