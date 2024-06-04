package com.bertopcu.budget_planner.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.HashMap;

@Entity
public class Revenue extends ResponseObject {
    @Id
    private String title;
    private String description;
    private Double rate;
    private String currency;

    private String origin;
    private Long timestamp;
    private HashMap<String, String> customAttributes;
    private Integer monthly;

    public Revenue(String title, String description, Double rate, String currency, String origin, Long timestamp, HashMap<String, String> customAttributes, Integer monthly) {
        this.title = title;
        this.description = description;
        this.rate = rate;
        this.currency = currency;
        this.origin = origin;
        this.timestamp = timestamp;
        this.customAttributes = customAttributes == null ? new HashMap<String, String>() : customAttributes;
        this.monthly = monthly;
    }

    public Revenue() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public HashMap<String, String> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(HashMap<String, String> customAttributes) {
        this.customAttributes = customAttributes;
    }

    public Integer getMonthly() {
        return monthly;
    }

    public void setMonthly(Integer monthly) {
        this.monthly = monthly;
    }

    @Override
    String getAttribute(String key) {
        switch(key) {
            case "title":
                return this.getTitle();
            case "description":
                return this.getDescription();
            case "rate":
                return Double.toString(this.getRate());
            case "currency":
                return this.getCurrency();
            case "timestamp":
                return Long.toString(this.getTimestamp());
            case "monthly":
                return Integer.toString(this.getMonthly());
            default:
                return this.customAttributes.get(key);
        }
    }

    @Override
    void setCustomAttribute(String key, String value) {
        this.customAttributes.put(key, value);
    }
}
