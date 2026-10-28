package com.bertopcu.budget_planner.Model;

import java.util.List;

public class ListResponse<T> {
    private String message;
    private boolean hasErrors;
    private List<T> items;

    public ListResponse(String message, boolean hasErrors, List<T> items) {
        this.message = message;
        this.hasErrors = hasErrors;
        this.items = items;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
