package com.h3xstream.scriptgen.gui;

import com.h3xstream.scriptgen.ScriptGenerator;
import org.fest.swing.fixture.FrameFixture;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.swing.*;

public class GeneratorFrameFestTest {

    FrameFixture window;

    @BeforeMethod
    public void setUpWindow() {
        JFrame frame = ScriptGenerator.openDialogWindow();
        window = new FrameFixture(frame);
        window.show();
    }

    @Test
    public void someTest() {
        window.comboBox(GeneratorFrame.CMB_LANGUAGE).selectItem(1);
    }
}
