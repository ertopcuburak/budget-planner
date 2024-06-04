package com.bertopcu.budget_planner.Model;

  abstract class ResponseObject {

    abstract String getAttribute(String key);
    abstract void setCustomAttribute(String key, String value);
}
