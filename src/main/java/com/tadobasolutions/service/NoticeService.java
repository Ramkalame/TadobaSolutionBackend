package com.tadobasolutions.service;

import com.tadobasolutions.dto.NoticeDTO;

import java.util.List;

public interface NoticeService {
    NoticeDTO createNotice(NoticeDTO dto);

    NoticeDTO updateNotice(Long id, NoticeDTO dto);

    void deleteNotice(Long id);

    NoticeDTO getNoticeById(Long id);

    List<NoticeDTO> getAllNotices();
}
