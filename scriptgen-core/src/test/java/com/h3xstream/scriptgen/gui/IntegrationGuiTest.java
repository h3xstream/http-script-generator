package com.h3xstream.scriptgen.gui;

import com.h3xstream.scriptgen.model.HttpRequestInfo;
import com.h3xstream.scriptgen.HttpRequestInfoFixtures;
import com.h3xstream.scriptgen.LanguageOption;
import com.h3xstream.scriptgen.ScriptGenerator;
import org.fest.swing.fixture.FrameFixture;
import org.mockito.Matchers;
import org.testng.annotations.Test;

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
        ScriptGenerator scriptGen = new ScriptGenerator(req,controller,frame);
        window = new FrameFixture(scriptGen.openDialogWindow());
        window.show();
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

        prepareWindow();

        reset(controller);//Avoid counting the initialisation to the first language (Python)

        window.comboBox(GeneratorFrame.CMB_LANGUAGE).selectItem(1);
        verify(controller,times(1)).updateLanguage(Matchers.<GeneratorFrame>any(), eq(LanguageOption.RUBY_NET_HTTP));

        window.comboBox(GeneratorFrame.CMB_LANGUAGE).selectItem(2);
        verify(controller,times(1)).updateLanguage(Matchers.<GeneratorFrame>any(), eq(LanguageOption.PERL_LWP));

        window.comboBox(GeneratorFrame.CMB_LANGUAGE).selectItem(3);
        verify(controller,times(1)).updateLanguage(Matchers.<GeneratorFrame>any(), eq(LanguageOption.PERL_LWP));

        window.comboBox(GeneratorFrame.CMB_LANGUAGE).selectItem(0);
        verify(controller,times(1)).updateLanguage(Matchers.<GeneratorFrame>any(), eq(LanguageOption.PYTHON_REQUEST));

    }
}
