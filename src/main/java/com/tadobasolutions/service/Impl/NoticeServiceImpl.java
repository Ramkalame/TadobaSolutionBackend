package com.tadobasolutions.service.Impl;

import com.tadobasolutions.dto.NoticeDTO;
import com.tadobasolutions.entity.Notice;
import com.tadobasolutions.exception.ResourceNotFoundException;
import com.tadobasolutions.repository.NoticeRepository;
import com.tadobasolutions.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    private NoticeDTO mapToDTO(Notice notice) {
        return new NoticeDTO(
                notice.getId(),
                notice.getDescription(),
                notice.getCreatedAt(),
                notice.getUpdatedAt()
        );
    }

    @Override
    public NoticeDTO createNotice(NoticeDTO dto) {
        Notice notice = new Notice();
        notice.setDescription(dto.getDescription());
        return mapToDTO(noticeRepository.save(notice));
    }

    @Override
    public NoticeDTO updateNotice(Long id, NoticeDTO dto) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notice not found"));
        notice.setDescription(dto.getDescription());
        return mapToDTO(noticeRepository.save(notice));
    }

    @Override
    public void deleteNotice(Long id) {
        if (!noticeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notice not found");
        }
        noticeRepository.deleteById(id);
    }

    @Override
    public NoticeDTO getNoticeById(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notice not found"));
        return mapToDTO(notice);
    }

    @Override
    public List<NoticeDTO> getAllNotices() {
        return noticeRepository.findAllOrderedByCreatedAtDesc().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
