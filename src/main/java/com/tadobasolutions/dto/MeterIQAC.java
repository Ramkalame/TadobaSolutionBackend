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
public class MeterIQAC {
    private LocalDateTime dateTime;
    private String meterId;
    private String phase;
    private Double voltage;
    private Double current;
    private Double power;
    private Double energy;
    private Double frequency;
}
