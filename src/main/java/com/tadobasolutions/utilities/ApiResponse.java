package com.tadobasolutions.utilities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Data
@Builder
public class ApiResponse<T> {

    private T data;
    private String message;
    private int statusCode;
    private LocalDate timeStamp;
    private boolean success;

    public ApiResponse() {
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public LocalDate getTimeStamp() {
        return timeStamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setTimeStamp(LocalDate timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ApiResponse(T data, String message, int statusCode, LocalDate timeStamp, boolean success) {
        this.data = data;
        this.message = message;
        this.statusCode = statusCode;
        this.timeStamp = timeStamp;
        this.success = success;
    }
}
