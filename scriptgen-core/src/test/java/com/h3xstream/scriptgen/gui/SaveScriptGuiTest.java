package com.h3xstream.scriptgen.gui;

import com.h3xstream.scriptgen.HttpRequestInfoFixtures;
import com.h3xstream.scriptgen.LanguageOption;
import com.h3xstream.scriptgen.ScriptGenerator;
import com.h3xstream.scriptgen.model.HttpRequestInfo;
import org.fest.swing.fixture.FrameFixture;
import org.mockito.Matchers;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.event.KeyEvent;
import java.io.File;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class SaveScriptGuiTest {

//    FrameFixture window;
//
//    GeneratorController controller;
//    GeneratorFrame frame;
//
//    public void prepareWindow() {
//        //Spy version to do verification on calls
//        controller = spy(new GeneratorController());
//        frame = new GeneratorFrame(LanguageOption.values);
//
//        HttpRequestInfo req = HttpRequestInfoFixtures.getPostRequest();
//        ScriptGenerator scriptGen = new ScriptGenerator(req,controller,frame);
//        window = new FrameFixture(scriptGen.openDialogWindow());
//        window.show();
//    }
//
//    @BeforeClass
//    public void beforeAllTests() {
//        //The same window is reused for all tests
//        prepareWindow();
//    }
//
//
//    @AfterClass
//    public void afterClass() {
//        window.close();
//    }


}
