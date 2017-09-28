package com.heepay.manage.modules.risk.entity;

import java.util.Date;
import java.util.List;

public class RiskChartRecord {

    private String day;
    private String  sucessRatio;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getSucessRatio() {
        return sucessRatio;
    }

    public void setSucessRatio(String sucessRatio) {
        this.sucessRatio = sucessRatio;
    }
}
