package com.tadobasolutions.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
public class Counter {

    @Id
    private int id;

    private long visitCount;

    private LocalDateTime lastUpdated;

    public Counter() {}

    public Counter(int id, long visitCount, LocalDateTime lastUpdated) {
        this.id = id;
        this.visitCount = visitCount;
        this.lastUpdated = lastUpdated;
    }

    public int getId() {
        return id;
    }

    public long getVisitCount() {
        return visitCount;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVisitCount(long visitCount) {
        this.visitCount = visitCount;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}

