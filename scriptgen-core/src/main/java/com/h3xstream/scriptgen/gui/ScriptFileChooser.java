package com.h3xstream.scriptgen.gui;

import com.esotericsoftware.minlog.Log;
import com.h3xstream.scriptgen.LanguageOption;
import com.h3xstream.scriptgen.model.HttpRequestInfo;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ScriptFileChooser {

    public static String FILE_CHOOSER = "FILE_CHOOSER";

    public void saveScriptToFile(String code, final LanguageOption lang,Component parent,HttpRequestInfo request,GeneratorController cont) {

        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setDialogTitle("Save to file");
        String currentDirectory = new File(".").getAbsolutePath();

        fileChooser.setSelectedFile(new File(currentDirectory,request.getHostname().replace('.','-')+"_http_script." + lang.getExtension()));
        fileChooser.setName(FILE_CHOOSER);

        //Extension filter
//        while(fileChooser.getChoosableFileFilters().length > 0) {
//            fileChooser.removeChoosableFileFilter(fileChooser.getChoosableFileFilters()[0]);
//        }
//        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(lang.getLanguage(), lang.getExtension()));

        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try {
                if(file.createNewFile()) {
                    OutputStream out = new FileOutputStream(file);
                    out.write(code.getBytes());
                    out.close();

                    cont.fileSaveSuccess(file.getAbsolutePath());
                }
            } catch (IOException e) {
                Log.warn("Unable to save the script file : "+e.getMessage());
                cont.fileSaveError(file.getAbsolutePath());
            }
        }
    }
}
