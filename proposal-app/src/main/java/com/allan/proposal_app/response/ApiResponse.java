package com.allan.proposal_app.response;

public class ApiResponse<T> {
    private final int status;
    private final String message;
    private final T data;

    public ApiResponse(ApiStatus status, T data) {
        this.status = status.getCode();
        this.message = status.getMessage();
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}