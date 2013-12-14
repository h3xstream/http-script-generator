package com.h3xstream.scriptgen.gui;

import com.h3xstream.scriptgen.model.HttpRequestInfo;
import com.h3xstream.scriptgen.HttpRequestInfoFixtures;
import com.h3xstream.scriptgen.ScriptGenerator;

public class GeneratorFrameTestManualGetRequest {

    public static void main(String[] args) {
        HttpRequestInfo req = HttpRequestInfoFixtures.getGetRequest();
        new ScriptGenerator(req).openDialogWindow();
    }
}
