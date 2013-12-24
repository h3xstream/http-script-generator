package com.h3xstream.scriptgen.gui;

import com.h3xstream.scriptgen.model.HttpRequestInfo;
import com.h3xstream.scriptgen.LanguageOption;
import com.h3xstream.scriptgen.template.CodeTemplateBuilder;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;


public class GeneratorController {


    HttpRequestInfo request;

    public void updateHttpRequest(HttpRequestInfo request) {
        this.request = request;
    }


    public void updateLanguage(GeneratorFrame frame, LanguageOption newOption) throws Exception {
        //System.out.println(newOption.getTitle());
        String codeGenerated = new CodeTemplateBuilder().request(request).templatePath(newOption.getTemplate()).build();

        frame.updateCode(codeGenerated,newOption.getSyntax());
    }

    public void copyToClipboard(String code) {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard ();
        clip.setContents(new StringSelection(code), null);
    }

    public void saveScriptToFile(String code, final LanguageOption lang,Component parent) {
        new ScriptFileChooser().saveScriptToFile(code,lang,parent,request,this);
    }

    public void fileSaveSuccess(String fileName) {
        System.out.printf("Script '%s' saved with success!\n",fileName);
    }

    public void fileSaveError(String fileName) {
        System.out.printf("Unable to save '%s'\n",fileName);
    }
}
