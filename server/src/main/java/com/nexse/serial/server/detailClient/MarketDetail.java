package com.nexse.serial.server.detailClient;

import java.util.Date;

public class MarketDetail {
    private String code;

        private String team1;

        private String team2;

        private Date eventDate;

        private float odd_result_1;

        private float odd_result_0;

        private float odd_result_2;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public float getOdd_result_1() {
        return odd_result_1;
    }

    public void setOdd_result_1(float odd_result_1) {
        this.odd_result_1 = odd_result_1;
    }

    public float getOdd_result_0() {
        return odd_result_0;
    }

    public void setOdd_result_0(float odd_result_0) {
        this.odd_result_0 = odd_result_0;
    }

    public float getOdd_result_2() {
        return odd_result_2;
    }

    public void setOdd_result_2(float odd_result_2) {
        this.odd_result_2 = odd_result_2;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MarketDetail");
        sb.append("{code='").append(code).append('\'');
        sb.append(", team1='").append(team1).append('\'');
        sb.append(", team2='").append(team2).append('\'');
        sb.append(", eventDate='").append(eventDate).append('\'');
        sb.append(", odd_result_1=").append(odd_result_1);
        sb.append(", odd_result_0=").append(odd_result_0);
        sb.append(", odd_result_2=").append(odd_result_2);
        sb.append('}');
        return sb.toString();
    }
}
