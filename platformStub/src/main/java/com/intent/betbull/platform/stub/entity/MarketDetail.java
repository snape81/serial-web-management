package com.intent.betbull.platform.stub.entity;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Id;
import java.util.Date;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class MarketDetail {

    @Id
    private String code;

    private String team1;

    private String team2;

    private Date eventDate;

    private float odd_result_1;

    private float odd_result_0;

    private float odd_result_2;
}
