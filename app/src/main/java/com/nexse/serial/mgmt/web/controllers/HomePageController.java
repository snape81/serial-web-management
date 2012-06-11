package com.nexse.serial.mgmt.web.controllers;

import com.nexse.serial.mgmt.logger.Log;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller("HomePageController")
public class HomePageController {
    public static final String INDEX_TEMPLATE_VIEW = "testMarksenseCards";

    @Log
    private Logger log;

    @RequestMapping(value = "/markSenseCards",  method = RequestMethod.GET)
    public ModelAndView showIndex(HttpServletResponse response) {
             log.debug(" HomePageController -- start ");

             log.debug("ADDING CORS Access-Control-Allow-Origin: http://localhost:8080 ");

             response.setHeader("Access-Control-Allow-Origin","http://localhost:8080");

             log.debug(" HomePageController -- exit to {}",INDEX_TEMPLATE_VIEW);
             return new ModelAndView(INDEX_TEMPLATE_VIEW);
    }


}
