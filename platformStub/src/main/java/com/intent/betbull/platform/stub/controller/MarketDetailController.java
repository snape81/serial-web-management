package com.intent.betbull.platform.stub.controller;

import com.intent.betbull.platform.stub.entity.MarketDetail;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/marketdetails")
@Controller
@RooWebScaffold(path = "marketdetails", formBackingObject = MarketDetail.class)
@RooWebJson(jsonObject = MarketDetail.class)
public class MarketDetailController {
}
