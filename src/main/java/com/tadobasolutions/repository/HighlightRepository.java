package com.tadobasolutions.repository;

import com.tadobasolutions.entity.Highlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HighlightRepository extends JpaRepository<Highlight, Long> {

    @Query("SELECT h FROM Highlight h ORDER BY h.createdAt DESC")
    List<Highlight> findAllOrderedByCreatedAtDesc();

}
