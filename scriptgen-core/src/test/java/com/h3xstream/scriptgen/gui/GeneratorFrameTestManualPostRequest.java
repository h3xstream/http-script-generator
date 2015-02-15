package com.h3xstream.scriptgen.gui;

import com.esotericsoftware.minlog.Log;
import com.h3xstream.scriptgen.ReissueRequestScripter;
import com.h3xstream.scriptgen.model.HttpRequestInfo;
import com.h3xstream.scriptgen.HttpRequestInfoFixtures;

public class GeneratorFrameTestManualPostRequest {

    public static void main(String[] args) {
        Log.set(Log.LEVEL_DEBUG);
        HttpRequestInfo req = HttpRequestInfoFixtures.getPostRequest();
        new ReissueRequestScripter(req).openDialogWindow();
    }
}
