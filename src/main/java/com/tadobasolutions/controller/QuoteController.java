package com.tadobasolutions.controller;

import com.tadobasolutions.entity.Quote;
import com.tadobasolutions.service.QuoteService;
import com.tadobasolutions.utilities.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/quote")
public class QuoteController {

    @Autowired
    private  QuoteService quoteService;

    @PostMapping("/save-quote")
    public ResponseEntity<ApiResponse<String>> saveQuote(@Valid @RequestBody Quote quote) {
        String data = quoteService.saveQuote(quote);
        ApiResponse<String> response = new ApiResponse<String>();
        response.setData(data);
        response.setMessage("Quote saved successfully!");
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setTimeStamp(LocalDate.now());
        response.setSuccess(true);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



}
