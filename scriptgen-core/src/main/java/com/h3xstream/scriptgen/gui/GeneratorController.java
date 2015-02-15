package com.h3xstream.scriptgen.gui;

import com.esotericsoftware.minlog.Log;
import com.h3xstream.scriptgen.model.DisplaySettings;
import com.h3xstream.scriptgen.model.HttpRequestInfo;
import com.h3xstream.scriptgen.LanguageOption;
import com.h3xstream.scriptgen.template.CodeTemplateBuilder;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;


public class GeneratorController {


    HttpRequestInfo request;

    LanguageOption latestLanguage;
    DisplaySettings latestDisplaySettings = new DisplaySettings();

    public void setHttpRequest(HttpRequestInfo request) {
        this.request = request;
    }


    public void updateLanguage(GeneratorFrame frame, LanguageOption newLanguage) throws Exception {
        latestLanguage = newLanguage;

        Log.debug("Updating the language to "+newLanguage.getLanguage());

        String codeGenerated = new CodeTemplateBuilder().request(request)
                .templatePath(newLanguage.getTemplate())
                .displaySettings(latestDisplaySettings).build();

        frame.updateCode(codeGenerated, newLanguage.getSyntax());
    }

    public void updateDisplaySettings(GeneratorFrame frame, DisplaySettings newDisplay) throws Exception {
        latestDisplaySettings = newDisplay;

        Log.debug("Updating the settings to "+newDisplay.toString());

        String codeGenerated = new CodeTemplateBuilder().request(request)
                .templatePath(latestLanguage.getTemplate())
                .displaySettings(newDisplay).build();

        frame.updateCode(codeGenerated, latestLanguage.getSyntax());
    }

    public void copyToClipboard(String code) {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard ();
        clip.setContents(new StringSelection(code), null);
    }

    public void saveScriptToFile(String code, final LanguageOption lang,Component parent) {
        new ScriptFileChooser().saveScriptToFile(code,lang,parent,request,this);
    }

    public void fileSaveSuccess(String fileName) {
        Log.info(String.format("Script '%s' saved with success!\n", fileName));
    }

    public void fileSaveError(String fileName) {
        Log.info(String.format("Unable to save '%s'\n", fileName));
    }
}
