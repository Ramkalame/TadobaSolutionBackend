package com.tadobasolutions.service;

import com.tadobasolutions.dto.MeterIQAC;
import com.tadobasolutions.dto.MeterIQDC;

public interface GoogleSheetService {

    public MeterIQAC getMeterIQACData();
    public MeterIQDC getMeterIQDCData();
}
