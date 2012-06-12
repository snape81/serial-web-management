package com.nexse.serial.mgmt.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

@Controller("HomePageController")
public class HomePageController {
    public static final String INDEX_TEMPLATE_VIEW = "testMarksenseCards";

    static final Logger logWriter = LoggerFactory.getLogger(HomePageController.class);

    @RequestMapping(value = "/markSenseCards",  method = RequestMethod.GET)
    public String showIndex(HttpServletResponse response) {
             logWriter.debug(" HomePageController -- start ");

             logWriter.debug("ADDING CORS Access-Control-Allow-Origin: http://localhost:9090 ");

             response.setHeader("Access-Control-Allow-Origin","http://localhost:9090");

             logWriter.debug(" HomePageController -- exit to {}",INDEX_TEMPLATE_VIEW);
             return INDEX_TEMPLATE_VIEW;
    }


}
