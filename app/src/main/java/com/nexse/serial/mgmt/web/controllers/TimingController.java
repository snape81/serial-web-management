package com.nexse.serial.mgmt.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

@Controller("timingResults")
public class TimingController {
    public static final String TEMPLATE_VIEW = "timingResults";

       static final Logger logWriter = LoggerFactory.getLogger(HomePageController.class);

       @RequestMapping(value = "/timingResults",  method = RequestMethod.GET)
       public String showIndex(HttpServletResponse response) {
                logWriter.debug(" HomePageController -- start ");

                logWriter.debug("ADDING CORS Access-Control-Allow-Origin: http://10.0.0.221:9090 ");

                response.setHeader("Access-Control-Allow-Origin","http://10.0.0.221:9090");

                logWriter.debug(" HomePageController -- exit to {}", TEMPLATE_VIEW);
                return TEMPLATE_VIEW;
       }
}
