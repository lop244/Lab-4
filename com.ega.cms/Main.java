package com.ega.cms;

import com.ega.cms.controller.CMSController;
import com.ega.cms.view.CMSView;

public class Main {
    public static void main(String[] args) {
        CMSController controller = new CMSController();
        CMSView view = new CMSView(controller);
        view.iniciarAplicacion();
    }
}