package ru.securities.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.securities.model.dto.QuoteDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuoteAndQuouteHistoryServiceImpl implements QuoteAndQuoteHistoryService {

    private final QuoteService quoteService;
    private final QuoteHistoryService quoteHistoryService;

    @Transactional
    @Override
    public QuoteDto save(QuoteDto quoteDto) {
        quoteHistoryService.save(quoteDto);
        quoteService.save(quoteDto);
        return quoteDto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<QuoteDto> findAll() {
        return null;
    }


    @Transactional
    @Override
    public QuoteDto update(QuoteDto quoteDto) {
       return null;
    }

    @Override
    public void remove(QuoteDto quoteDto) {

    }

    @Override
    public QuoteDto findByIsin(QuoteDto isin) {
        return null;
    }

    @Override
    public List<QuoteDto> findAllByIsin(QuoteDto isin) {
        return null;
    }
}
