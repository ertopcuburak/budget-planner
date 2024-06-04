package com.bertopcu.budget_planner.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Budget extends ResponseObject{
    private Long startTime;
    private Long endTime;
    private ArrayList<Expenditure> expenditureList;
    private ArrayList<Revenue> revenueList;

    private HashMap<String, String> customAttributes;
    private Double balance;

    public Budget(Long startTime, Long endTime, ArrayList<Expenditure> expenditureList, ArrayList<Revenue> revenueList, HashMap<String, String> customAttributes) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.expenditureList = expenditureList;
        this.revenueList = revenueList;
        this.customAttributes = customAttributes == null ? new HashMap<String, String>() : customAttributes;
        this.setBalance(revenueList, expenditureList);
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

    public ArrayList<Expenditure> getExpenditureList() {
        return expenditureList;
    }

    public void setExpenditureList(ArrayList<Expenditure> expenditureList) {
        this.expenditureList = expenditureList;
    }

    public ArrayList<Revenue> getRevenueList() {
        return revenueList;
    }

    public void setRevenueList(ArrayList<Revenue> revenueList) {
        this.revenueList = revenueList;
    }

    public HashMap<String, String> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(HashMap<String, String> customAttributes) {
        this.customAttributes = customAttributes;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(ArrayList<Revenue> revenueList, ArrayList<Expenditure> expenditureList) {
        Double totalRevenues = 0.0;
        Double totalExpenditures = 0.0;
        String defaultCurrency = "EUR";
        int revIdx = 0;
        int expIdx = 0;
        try{
            for(Revenue rev : revenueList) {
                if(revIdx == 0) defaultCurrency = rev.getCurrency();
                if(!rev.getCurrency().equals(defaultCurrency)) {
                    throw new Exception("Multiple Currencies not supported for a single balance!");
                }
                totalRevenues += rev.getRate();
            }
            for(Expenditure exp : expenditureList) {
                if(expIdx == 0) defaultCurrency = exp.getCurrency();
                if(!exp.getCurrency().equals(defaultCurrency)) {
                    throw new Exception("Multiple Currencies not supported for a single balance!");
                }
                totalExpenditures += exp.getRate();
            }
            this.balance = totalRevenues - totalExpenditures;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


    }

    @Override
    String getAttribute(String key) {
        switch(key) {
            case "startTime":
                return Long.toString(this.getStartTime());
            case "endTime":
                return Long.toString(this.getEndTime());
            case "balance":
                return  Double.toString(this.getBalance());
            default:
                return this.customAttributes.get(key);
        }
    }

    @Override
    void setCustomAttribute(String key, String value) {
        this.customAttributes.put(key, value);
    }
}
