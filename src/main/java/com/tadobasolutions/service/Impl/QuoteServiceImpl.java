package com.tadobasolutions.service.Impl;

import com.tadobasolutions.entity.Quote;
import com.tadobasolutions.repository.QuoteRepository;
import com.tadobasolutions.service.QuoteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class QuoteServiceImpl implements QuoteService {

    @Autowired
    private  QuoteRepository quoteRepository;

    @Override
    public String saveQuote(Quote quote) {
        Quote newQuote = new Quote();
        newQuote.setCustomerName(quote.getCustomerName());
        newQuote.setCustomerEmail(quote.getCustomerEmail());
        newQuote.setCustomerMobileNumber(quote.getCustomerMobileNumber());
        newQuote.setQueryDescription(quote.getQueryDescription());
        Quote savedQuote = quoteRepository.save(newQuote);
        return "Quote saved with ID: " + savedQuote.getQuoteId();
    }

    @Override
    public List<Quote> getAllQuoteList() {
        return quoteRepository.findAll();
    }

}
