package com.tadobasolutions.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {

    private T data;
    private String message;
    private int statusCode;
    private LocalDate timeStamp;
    private boolean success;
}
