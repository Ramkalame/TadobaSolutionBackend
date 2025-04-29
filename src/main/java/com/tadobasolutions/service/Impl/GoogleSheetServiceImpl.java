package com.tadobasolutions.service.Impl;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.tadobasolutions.dto.MeterIQAC;
import com.tadobasolutions.dto.MeterIQDC;
import com.tadobasolutions.exception.GoogleSheetException;
import com.tadobasolutions.service.GoogleSheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static java.lang.Double.parseDouble;

@Service
@RequiredArgsConstructor
public class GoogleSheetServiceImpl implements GoogleSheetService {

    private final Sheets sheetsService;

    @Value("${google.sheet-id.meter-iq}")
    private String sheetIdMeterIQ;
    @Value("${google.sheet-range.meter-iq-ac}")
    private String sheetRangeMeterIQAC;
    @Value("${google.sheet-range.meter-iq-dc}")
    private String getSheetRangeMeterIQDC;

    private List<Object> getLatestSheetData(String sheetId, String dataRange) {
        try {
            ValueRange response = sheetsService.spreadsheets().values()
                    .get(sheetId, dataRange)
                    .execute();
            List<List<Object>> values = response.getValues();
            if (values == null || values.isEmpty()) {
                return Collections.emptyList();
            }
            return values.get(values.size() - 1);
        } catch (IOException e) {
            throw new GoogleSheetException("Failed To Load The Google Sheet");
        }
    }

    private LocalDateTime formatDateTime(Object value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");
        return LocalDateTime.parse(value.toString(), formatter);
    }

    private Double parseDouble(Object value) {
        return value == null ? null : Double.valueOf(value.toString());
    }

    private Integer parseInt(Object value) {
        return value == null ? null : Integer.valueOf(value.toString());
    }


    @Override
    public MeterIQAC getMeterIQACData() {
        List<Object> values = getLatestSheetData(sheetIdMeterIQ, sheetRangeMeterIQAC);
        return MeterIQAC.builder()
                .dateTime(formatDateTime(values.get(0)))
                .meterId(values.get(1).toString())
                .phase(values.get(2).toString())
                .voltage(parseDouble(values.get(4)))
                .current(parseDouble(values.get(5)))
                .power(parseDouble(values.get(6)))
                .energy(parseDouble(values.get(7)))
                .frequency(parseDouble(values.get(8)))
                .build();
    }

    @Override
    public MeterIQDC getMeterIQDCData() {
        List<Object> values = getLatestSheetData(sheetIdMeterIQ, getSheetRangeMeterIQDC);
        return MeterIQDC.builder()
                .dateTime(formatDateTime(values.get(0)))
                .meterId(values.get(1).toString())
                .voltage(parseDouble(values.get(2)))
                .current(parseDouble(values.get(3)))
                .build();
    }

}
