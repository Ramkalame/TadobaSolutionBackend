package com.tadobasolutions.controller;

import com.tadobasolutions.service.Impl.GoogleSheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/sheet-data")
@RequiredArgsConstructor
public class GoogleSheetController {

    private final GoogleSheetService googleSheetService;


    @GetMapping
    public void test() throws IOException {
        googleSheetService.getLastRowData();
    }

}
