package com.tadobasolutions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeterIQDC {
    private LocalDateTime dateTime;
    private String meterId;
    private Double voltage;
    private Double current;
}
