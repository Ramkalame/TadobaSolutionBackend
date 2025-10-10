package com.tadobasolutions.service;

import com.tadobasolutions.dto.HighlightDTO;

import java.util.List;

public interface HighlightService {
    HighlightDTO createHighlight(HighlightDTO dto);

    HighlightDTO updateHighlight(Long id, HighlightDTO dto);

    void deleteHighlight(Long id);

    HighlightDTO getHighlightById(Long id);

    List<HighlightDTO> getAllHighlights();
}
