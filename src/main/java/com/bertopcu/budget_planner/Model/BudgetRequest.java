package com.bertopcu.budget_planner.Model;

public class BudgetRequest {
    private Long startTime;
    private Long endTime;
    private String currency;

    public BudgetRequest(Long startTime, Long endTime, String currency) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.currency = currency;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
