package com.tadobasolutions.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Counter {

    @Id
    private int id;
    private long visitCount;
    private LocalDateTime lastUpdated;

}

