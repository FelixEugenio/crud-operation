package com.felix.exceptions;

import java.time.Instant;

public class StandarError  {
    private String message;
    private Integer status;
    private Instant timestamp;
    private String path;
    private String error;

    public StandarError(String message, Integer status, Instant timestamp, String path, String error) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
        this.path = path;
        this.error = error;
    }

    public StandarError() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
