package ru.securities.service;

import ru.securities.model.Quote;
import ru.securities.model.dto.QuoteDto;

import java.util.List;

public interface QuoteService extends SecuritiesService<QuoteDto, Long> {
    QuoteDto findByIsin(QuoteDto isin);
    List<QuoteDto> findAllByIsin(QuoteDto isin);
}
