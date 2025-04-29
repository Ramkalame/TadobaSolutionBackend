package com.tadobasolutions.controller;

import com.tadobasolutions.dto.MeterIQAC;
import com.tadobasolutions.dto.MeterIQDC;
import com.tadobasolutions.service.Impl.GoogleSheetServiceImpl;
import com.tadobasolutions.utilities.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/v1/sheet-data")
@RequiredArgsConstructor
public class GoogleSheetController {

    private final GoogleSheetServiceImpl googleSheetServiceImpl;


    @GetMapping("/meter-iq/ac")
    public ResponseEntity<?> getMeterIQACData() {
        MeterIQAC responseData = googleSheetServiceImpl.getMeterIQACData();
        System.out.println(responseData);
        ApiResponse<MeterIQAC> apiResponse = ApiResponse.<MeterIQAC>builder()
                .data(responseData)
                .message("MeterIQ Pro+ AC Data Fetched Successfully")
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .success(true)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/meter-iq/dc")
    public ResponseEntity<?> getMeterIQDCData() {
        MeterIQDC responseData = googleSheetServiceImpl.getMeterIQDCData();
        ApiResponse<MeterIQDC> apiResponse = ApiResponse.<MeterIQDC>builder()
                .data(responseData)
                .message("MeterIQ Pro+ DC Data Fetched Successfully")
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .success(true)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


}
