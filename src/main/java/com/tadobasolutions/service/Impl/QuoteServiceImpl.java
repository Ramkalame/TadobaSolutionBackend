package com.tadobasolutions.service.Impl;

import com.tadobasolutions.entity.Counter;
import com.tadobasolutions.entity.Quote;
import com.tadobasolutions.repository.CounterRepository;
import com.tadobasolutions.repository.QuoteRepository;
import com.tadobasolutions.service.QuoteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuoteServiceImpl implements QuoteService {

    @Autowired
    private  QuoteRepository quoteRepository;
    @Autowired
    private  CounterRepository counterRepository;

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

    @Override
    public Long getVisitCount() {
        Counter existingCounter = counterRepository.findById(1)
                .orElseGet(() -> {
                    Counter counter = new Counter();
                    counter.setId(1);
                    counter.setVisitCount(1L);
                    counter.setLastUpdated(LocalDateTime.now());
                    return counterRepository.save(counter);
                });
        return existingCounter.getVisitCount();
    }

    @Override
    public void updateVisitCount() {
        Counter existingCounter = counterRepository.findById(1)
                .orElseGet(() -> {
                    Counter counter = new Counter();
                    counter.setId(1);
                    counter.setVisitCount(1L);
                    counter.setLastUpdated(LocalDateTime.now());
                    return counterRepository.save(counter);
                });

        existingCounter.setVisitCount(existingCounter.getVisitCount() + 1);
        existingCounter.setLastUpdated(LocalDateTime.now());
        counterRepository.save(existingCounter);
    }


}
