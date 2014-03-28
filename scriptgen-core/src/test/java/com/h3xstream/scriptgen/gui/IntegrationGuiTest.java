package com.h3xstream.scriptgen.gui;

import com.h3xstream.scriptgen.ReissueRequestScripter;
import com.h3xstream.scriptgen.model.HttpRequestInfo;
import com.h3xstream.scriptgen.HttpRequestInfoFixtures;
import com.h3xstream.scriptgen.LanguageOption;
import org.fest.swing.fixture.FrameFixture;
import org.mockito.Matchers;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.event.KeyEvent;
import java.io.File;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class IntegrationGuiTest {

    FrameFixture window;

    GeneratorController controller;
    GeneratorFrame frame;


    public void prepareWindow() {
        //Spy version to do verification on calls
        controller = spy(new GeneratorController());
        frame = new GeneratorFrame(LanguageOption.values);

        HttpRequestInfo req = HttpRequestInfoFixtures.getPostRequest();
        ReissueRequestScripter scriptGen = new ReissueRequestScripter(req,controller,frame);
        window = new FrameFixture(scriptGen.openDialogWindow());
        window.show();
    }


    @BeforeClass
    public void beforeClass() {
        prepareWindow();
    }

    @AfterClass
    public void afterClass() {
        window.close();
    }


    @Test
    public void validateLanguageOrder() {
        LanguageOption[] langOrdered = LanguageOption.values;
        langOrdered[0].getSyntax().toLowerCase().contains("python");
        langOrdered[1].getSyntax().toLowerCase().contains("ruby");
        langOrdered[2].getSyntax().toLowerCase().contains("perl");
        langOrdered[3].getSyntax().toLowerCase().contains("php");
    }

    @Test
    public void languageSelectionChange() throws Exception {

        reset(controller);//Avoid counting the initialisation to the first language (Python)

        window.comboBox(GeneratorFrame.CMB_LANGUAGE).selectItem(1);
        verify(controller,times(1)).updateLanguage(Matchers.<GeneratorFrame>any(), eq(LanguageOption.RUBY_NET_HTTP));

        window.comboBox(GeneratorFrame.CMB_LANGUAGE).selectItem(2);
        verify(controller,times(1)).updateLanguage(Matchers.<GeneratorFrame>any(), eq(LanguageOption.PERL_LWP));

        window.comboBox(GeneratorFrame.CMB_LANGUAGE).selectItem(3);
        verify(controller,times(1)).updateLanguage(Matchers.<GeneratorFrame>any(), eq(LanguageOption.PHP_CURL));

        window.comboBox(GeneratorFrame.CMB_LANGUAGE).selectItem(0);
        verify(controller,times(1)).updateLanguage(Matchers.<GeneratorFrame>any(), eq(LanguageOption.PYTHON_REQUEST));

    }


    //The save operations

    @Test
    public void testSendToCopyPaste() {

        //1. Directly jump on copy to clipboard button
        window.button(GeneratorFrame.BUTTON_COPY).click();

        verify(controller).copyToClipboard(anyString());
    }

    @Test
    public void testSendToCopyPasteAfterLanguageSelectionChange() throws Exception {

        reset(controller);//Avoid counting the initialisation to the first language (Python)

        //1. Select Perl language
        window.comboBox(GeneratorFrame.CMB_LANGUAGE).selectItem(2);
        verify(controller,times(1)).updateLanguage(Matchers.<GeneratorFrame>any(), eq(LanguageOption.PERL_LWP));

        //2. Press copy to clipboard button
        window.button(GeneratorFrame.BUTTON_COPY).click();

        verify(controller).copyToClipboard(contains("use LWP::UserAgent;"));
    }

    @Test
    public void testSaveToFile() {

        //1. Directly save the script to file
        window.button(GeneratorFrame.BUTTON_SAVE).click();
        window.fileChooser(ScriptFileChooser.FILE_CHOOSER).fileNameTextBox().enterText("somescript").pressKey(KeyEvent.VK_ENTER);

        verify(controller).fileSaveSuccess(endsWith("somescript"));

        //Cleanup
        String currentDirectory = new File(".").getAbsolutePath();
        new File(currentDirectory,"somescript").delete();
    }
}
