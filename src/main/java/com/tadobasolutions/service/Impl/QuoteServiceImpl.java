package com.tadobasolutions.service.Impl;

import com.tadobasolutions.entity.Quote;
import com.tadobasolutions.repository.QuoteRepository;
import com.tadobasolutions.service.QuoteService;
import lombok.AllArgsConstructor;

import java.util.List;
@AllArgsConstructor
public class QuoteServiceImpl implements QuoteService {

//    private final QuoteRepository quoteRepository;

    @Override
    public String saveQuote(Quote quote) {
        return "";
    }

    @Override
    public List<Quote> getAllQuoteList() {
        return List.of();
    }
}
