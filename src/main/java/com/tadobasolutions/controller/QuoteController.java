package com.tadobasolutions.controller;

import com.tadobasolutions.entity.Quote;
import com.tadobasolutions.service.QuoteService;
import com.tadobasolutions.utilities.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/quotes")
@CrossOrigin(origins = {"https://www.tadobasolutions.com", "https://tadobasolutions.com","http://localhost:4200"})
public class QuoteController {

    @Autowired
    private  QuoteService quoteService;

    @GetMapping("/test")
    public String test(){
        return "Test done successfully";
    }


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

    @GetMapping
    public ResponseEntity<ApiResponse<List<Quote>>> getAllQuoteList(){
        List<Quote> data = quoteService.getAllQuoteList();
        ApiResponse<List<Quote>> response = new ApiResponse<List<Quote>>();
        response.setData(data);
        response.setMessage("Quote list fetched successfully!");
        response.setStatusCode(HttpStatus.OK.value());
        response.setTimeStamp(LocalDate.now());
        response.setSuccess(true);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get-visit-count")
    public ResponseEntity<?> getVisitCount(){
        Long data = quoteService.getVisitCount();

        ApiResponse<Long> response = new ApiResponse<Long>();
        response.setData(data);
        response.setMessage("Visit Count Fetched Successfully");
        response.setStatusCode(HttpStatus.OK.value());
        response.setTimeStamp(LocalDate.now());
        response.setSuccess(true);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("update-visit-count")
    public ResponseEntity<?> updateVisitCount(){
         quoteService.updateVisitCount();
        ApiResponse<String> response = new ApiResponse<String>();
        response.setData("Visit Count Updated");
        response.setMessage("Visit Count Updated successfully.");
        response.setStatusCode(HttpStatus.OK.value());
        response.setTimeStamp(LocalDate.now());
        response.setSuccess(true);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }




}
