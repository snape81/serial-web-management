package com.nexse.serial.mgmt.web.controllers;

import com.nexse.serial.mgmt.logger.Log;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller("HomePageController")
public class HomePageController {
    public static final String INDEX_TEMPLATE_VIEW = "template/index";

    @Log
    private Logger log;

    @RequestMapping(value = "/index",  method = RequestMethod.GET)
    public ModelAndView showIndex() {
             log.debug(" HomePageController -- start ");

             log.debug(" HomePageController -- exit to {}",INDEX_TEMPLATE_VIEW);
             return new ModelAndView(INDEX_TEMPLATE_VIEW);
    }


}
