package ru.securities.algorithm;

import ru.securities.exception.QuoteNotImpossibleToCalculate;
import ru.securities.model.QuoteHistory;
import ru.securities.model.dto.QuoteDto;

import java.time.ZonedDateTime;
import java.util.Optional;

public class QuoteHistoryCountAlgorithm {
    public QuoteHistory count(QuoteDto quoteDto, Optional<QuoteHistory> quote) {
        return quote.map(quoteHistory -> getQuoteIfQuoteHistoryIsNotNull(quote,quoteDto))
                .orElseGet(() -> getQuoteIfQuoteHistoryIsNull(quoteDto));
    }

    private QuoteHistory getQuoteIfQuoteHistoryIsNotNull(Optional<QuoteHistory> quote,QuoteDto quoteDto) {
        QuoteHistory quote1 = new QuoteHistory();
        quote1.setIsin(quoteDto.getIsin());
        quote1.setEnergyLevel(quote.get().getEnergyLevel());
        if (quoteDto.getBid() == null) {

            quote1.setEnergyLevel(quoteDto.getAsk());
            quote1.setAsk(quoteDto.getAsk());

        }  else if (quoteDto.getBid().compareTo(quote1.getEnergyLevel()) > 0) {

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
        return QuoteHistory.builder()
                .ask(quote1.getAsk())
                .isin(quote1.getIsin())
                .bid(quote1.getBid())
                .energyLevel(quote1.getEnergyLevel())
                .zoneDateTime(ZonedDateTime.now())
                .build();
    }

    private QuoteHistory getQuoteIfQuoteHistoryIsNull(QuoteDto quoteDto) {
        QuoteHistory quote1;
        if (quoteDto.getBid() != null) {
            quote1 = QuoteHistory.builder()
                    .ask(quoteDto.getAsk())
                    .isin(quoteDto.getIsin())
                    .bid(quoteDto.getBid())
                    .energyLevel(quoteDto.getBid())
                    .build();
        } else {
            quote1 = QuoteHistory.builder()
                    .ask(quoteDto.getAsk())
                    .isin(quoteDto.getIsin())
                    .energyLevel(quoteDto.getAsk())
                    .build();
        }
        quote1.setZoneDateTime(ZonedDateTime.now());
        return quote1;
    }


    /*public QuoteHistory count(QuoteDto quoteDto, Optional<QuoteHistory> quote) {
        if (quote.isPresent()) {
            return getQuoteIfQuoteHistoryIsNotNull(quote.get(),quoteDto);
        } else {
            return getQuoteIfQuoteHistoryIsNull(quoteDto);
        }
    }*/
}
