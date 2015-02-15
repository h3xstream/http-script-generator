package com.h3xstream.scriptgen.gui;

import com.esotericsoftware.minlog.Log;
import com.h3xstream.scriptgen.model.HttpRequestInfo;
import com.h3xstream.scriptgen.HttpRequestInfoFixtures;
import com.h3xstream.scriptgen.ReissueRequestScripter;

public class GeneratorFrameTestManualGetRequest {

    public static void main(String[] args) {
        Log.set(Log.LEVEL_DEBUG);
        HttpRequestInfo req = HttpRequestInfoFixtures.getGetRequest();
        new ReissueRequestScripter(req).openDialogWindow();
    }
}
