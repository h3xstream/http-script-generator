package com.h3xstream.scriptgen.gui;

import com.h3xstream.scriptgen.HttpRequestInfo;
import com.h3xstream.scriptgen.HttpRequestInfoFixtures;
import com.h3xstream.scriptgen.ScriptGenerator;

public class GeneratorFrameTestManualPostRequest {

    public static void main(String[] args) {
        HttpRequestInfo req = HttpRequestInfoFixtures.getPostRequest();
        new ScriptGenerator(req).openDialogWindow();
    }
}
