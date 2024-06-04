package com.bertopcu.budget_planner.Model;

public class Response {
    private String message;
    private boolean hasErrors;

    private ResponseObject relevantObject;

    // Constructor
    public Response(String message, boolean hasErrors, ResponseObject relevantObject) {
        this.message = message;
        this.hasErrors = hasErrors;
        this.relevantObject = relevantObject;
    }

    // Getter
    public String getMessage() {
        return message;
    }

    // Setter
    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public ResponseObject getRelevantObject() {
        return relevantObject;
    }

    public void setRelevantObject(ResponseObject relevantObject) {
        this.relevantObject = relevantObject;
    }
}
