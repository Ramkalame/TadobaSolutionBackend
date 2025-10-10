package com.tadobasolutions.service.Impl;

import com.tadobasolutions.dto.HighlightDTO;
import com.tadobasolutions.entity.Highlight;
import com.tadobasolutions.exception.ResourceNotFoundException;
import com.tadobasolutions.repository.HighlightRepository;
import com.tadobasolutions.service.HighlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HighlightServiceImpl implements HighlightService {

    private final HighlightRepository highlightRepository;

    private HighlightDTO mapToDTO(Highlight highlight) {
        return new HighlightDTO(
                highlight.getId(),
                highlight.getDescription(),
                highlight.getCreatedAt(),
                highlight.getUpdatedAt()
        );
    }

    @Override
    public HighlightDTO createHighlight(HighlightDTO dto) {
        Highlight highlight = new Highlight();
        highlight.setDescription(dto.getDescription());
        return mapToDTO(highlightRepository.save(highlight));
    }

    @Override
    public HighlightDTO updateHighlight(Long id, HighlightDTO dto) {
        Highlight highlight = highlightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Highlight not found"));
        highlight.setDescription(dto.getDescription());
        return mapToDTO(highlightRepository.save(highlight));
    }

    @Override
    public void deleteHighlight(Long id) {
        if (!highlightRepository.existsById(id)) {
            throw new ResourceNotFoundException("Highlight not found");
        }
        highlightRepository.deleteById(id);
    }

    @Override
    public HighlightDTO getHighlightById(Long id) {
        Highlight highlight = highlightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Highlight not found"));
        return mapToDTO(highlight);
    }

    @Override
    public List<HighlightDTO> getAllHighlights() {
        return highlightRepository.findAllOrderedByCreatedAtDesc().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
