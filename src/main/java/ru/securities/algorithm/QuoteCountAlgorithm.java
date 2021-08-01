package ru.securities.algorithm;

import ru.securities.exception.QuoteNotImpossibleToCalculate;
import ru.securities.model.Quote;
import ru.securities.model.dto.QuoteDto;

import java.util.Optional;

public class QuoteCountAlgorithm {
    public Quote count(QuoteDto quoteDto, Optional<Quote> quote) {
        return quote.map(quoteHistory -> getQuoteIfQuoteIsNotNull(quote, quoteDto))
                .orElseGet(() -> getQuoteIfQuoteIsNull(quoteDto));
    }

    private Quote getQuoteIfQuoteIsNotNull(Optional<Quote> quote, QuoteDto quoteDto) {
        Quote quote1 = new Quote();
        quote1.setIsin(quoteDto.getIsin());
        quote1.setEnergyLevel(quote.get().getEnergyLevel());
        if (quoteDto.getBid() == null) {

            quote1.setEnergyLevel(quoteDto.getAsk());
            quote1.setAsk(quoteDto.getAsk());

        } else if (quoteDto.getBid().compareTo(quote1.getEnergyLevel()) > 0) {

            quote1.setEnergyLevel(quoteDto.getBid());
            quote1.setAsk(quoteDto.getAsk());
            quote1.setBid(quoteDto.getBid());

        } else if (quoteDto.getAsk().compareTo(quote1.getEnergyLevel()) < 0) {

            quote1.setEnergyLevel(quoteDto.getAsk());
            quote1.setAsk(quoteDto.getAsk());
            quote1.setBid(quoteDto.getBid());
        }
        else  {
            throw new QuoteNotImpossibleToCalculate("Невозможно рассчитать котировку");
        }
        return quote1;
    }

    private Quote getQuoteIfQuoteIsNull(QuoteDto quoteDto) {
        Quote quote1;
        if (quoteDto.getBid() != null) {
            quote1 = Quote.builder()
                    .ask(quoteDto.getAsk())
                    .isin(quoteDto.getIsin())
                    .bid(quoteDto.getBid())
                    .energyLevel(quoteDto.getBid())
                    .build();
        } else {
            quote1 = Quote.builder()
                    .ask(quoteDto.getAsk())
                    .isin(quoteDto.getIsin())
                    .energyLevel(quoteDto.getAsk())
                    .build();
        }
        return quote1;
    }
}
