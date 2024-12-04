package com.tadobasolutions.service;

import com.tadobasolutions.entity.Quote;
import com.tadobasolutions.repository.QuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuoteService {

    public String saveQuote(Quote quote);
    public List<Quote> getAllQuoteList();

}
