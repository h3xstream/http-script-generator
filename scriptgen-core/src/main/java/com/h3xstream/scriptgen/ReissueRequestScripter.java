package com.h3xstream.scriptgen;

import com.h3xstream.scriptgen.gui.GeneratorController;
import com.h3xstream.scriptgen.gui.GeneratorFrame;
import com.h3xstream.scriptgen.model.HttpRequestInfo;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ReissueRequestScripter {
    private List<HttpRequestInfo> requests;
    private GeneratorFrame frame;

    private GeneratorController controller;

    public ReissueRequestScripter(HttpRequestInfo req) {
        requests = new ArrayList<>();
        requests.add(req);

        this.controller = new GeneratorController();
        this.frame = new GeneratorFrame(LanguageOption.values);
    }
    public ReissueRequestScripter(List<HttpRequestInfo> requests) {
        this.requests = requests;

        this.controller = new GeneratorController();
        this.frame = new GeneratorFrame(LanguageOption.values);
    }

    /**
     * Constructor intend for testing purpose.
     * @param requests
     * @param controller
     * @param frame
     */
    public ReissueRequestScripter(List<HttpRequestInfo> requests, GeneratorController controller, GeneratorFrame frame) {
        this.requests = requests;

        this.controller = controller;
        this.frame = frame;
    }

    public JFrame openDialogWindow() {
        this.controller.setHttpRequest(requests);

        this.frame.setController(controller); //Controller need to be set prior setting
        this.frame.setTitleSuffix(requests.get(0).getUrl());
        this.frame.setVisible(true);
        this.frame.updateLanguageSelection(0);
        return frame;
    }



}
