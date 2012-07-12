// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.intent.betbull.platform.stub.controller;

import com.intent.betbull.platform.stub.controller.MarketDetailController;
import com.intent.betbull.platform.stub.entity.MarketDetail;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

privileged aspect MarketDetailController_Roo_Controller_Json {
    
    @RequestMapping(value = "/{code}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> MarketDetailController.showJson(@PathVariable("code") String code) {
        MarketDetail marketDetail = MarketDetail.findMarketDetail(code);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (marketDetail == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(marketDetail.toJson(), headers, HttpStatus.OK);
    }
    
    @RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> MarketDetailController.listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<MarketDetail> result = MarketDetail.findAllMarketDetails();
        return new ResponseEntity<String>(MarketDetail.toJsonArray(result), headers, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> MarketDetailController.createFromJson(@RequestBody String json) {
        MarketDetail marketDetail = MarketDetail.fromJsonToMarketDetail(json);
        marketDetail.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> MarketDetailController.createFromJsonArray(@RequestBody String json) {
        for (MarketDetail marketDetail: MarketDetail.fromJsonArrayToMarketDetails(json)) {
            marketDetail.persist();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> MarketDetailController.updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        MarketDetail marketDetail = MarketDetail.fromJsonToMarketDetail(json);
        if (marketDetail.merge() == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> MarketDetailController.updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (MarketDetail marketDetail: MarketDetail.fromJsonArrayToMarketDetails(json)) {
            if (marketDetail.merge() == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{code}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> MarketDetailController.deleteFromJson(@PathVariable("code") String code) {
        MarketDetail marketDetail = MarketDetail.findMarketDetail(code);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (marketDetail == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        marketDetail.remove();
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
}
