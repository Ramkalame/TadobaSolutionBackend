package com.tadobasolutions.service.Impl;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoogleSheetService {

    private final Sheets sheetsService;
    private final String spreadsheetId = "1_hKCYF5WYb2KMc1O9RbwpCVg9KQG1SMFr6G12dK4Lxs"; // your sheet ID
    private final String range = "Parameter Values on the AC Side!A2:I"; // your sheet range

    public List<Object> getLastRowData() throws IOException {
        // Fetch all data from the specified range
        ValueRange response = sheetsService.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();

        List<List<Object>> values = response.getValues();

        // Check if the values exist
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
            return Collections.emptyList();  // Return an empty list if no data is found
        }

        // Reverse the list to get the latest row first
        Collections.reverse(values);

        // Get the latest row (first row in the reversed list)
        List<Object> latestRow = values.get(0);

        // Print the latest row
        System.out.println("Latest data from Google Sheets:");
        System.out.println(latestRow);

        return latestRow;
    }

}
