package com.bertopcu.budget_planner.Model;

public abstract class ResponseObject {

    public abstract String getAttribute(String key);
    public abstract void setCustomAttribute(String key, String value);
}
