package ru.securities.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import ru.securities.app.AppApplication;
import ru.securities.mapper.QuoteHistoryMapper;
import ru.securities.model.dto.QuoteDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ContextConfiguration(classes = {AppApplication.class})
public class QuoteHistoryServiceImplTest {

    @Autowired
    private QuoteHistoryService quoteHistoryService;

    private List<QuoteDto> quoteDtoList;

    @Autowired
    private QuoteHistoryMapper quoteHistoryMapper;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void init() {
        quoteDtoList = new ArrayList<>();

        quoteDtoList.add(QuoteDto.builder()
                .isin("RU000A0JX0JJ")
                .ask(new BigDecimal("101.9"))
                .bid(new BigDecimal("100.2"))
                .energyLevel(new BigDecimal("100.1"))
                .build());

        quoteDtoList.add(QuoteDto.builder()
                .isin("RU000A0JX0JJ")
                .ask(new BigDecimal("101.9"))
                .energyLevel(new BigDecimal("100.1"))
                .build());

        quoteDtoList.add(QuoteDto.builder()
                .isin("RU000A0JX0JJ")
                .ask(new BigDecimal("60.9"))
               // .bid(new BigDecimal("50.2"))
                .energyLevel(new BigDecimal("60.9"))
                .build());


        quoteDtoList.add(QuoteDto.builder()
                .isin("EN100A0JX0JJ")
                .ask(new BigDecimal("101.9"))
                .bid(new BigDecimal("100.2"))
                .energyLevel(new BigDecimal("102"))
                .build());

        quoteDtoList.add(QuoteDto.builder()
                .isin("CN100A0JX0JJ")
                .ask(new BigDecimal("101.9"))
                .bid(new BigDecimal("100.2"))
                .energyLevel(new BigDecimal("103"))
                .build());
        quoteDtoList.forEach(quoteDto -> entityManager.persist(quoteHistoryMapper.toModel(quoteDto)));
        // entityManager.flush();
    }


    @Test
    void saveQuoteIfAskLowerThenElv_thenReturnOk() {
        QuoteDto quoteDto = new QuoteDto();
        quoteDto.setIsin(quoteDtoList.get(0).getIsin());
        quoteDto.setAsk(quoteDtoList.get(0).getAsk());
        quoteDto.setBid(quoteDtoList.get(0).getBid());

        entityManager.persist(quoteHistoryMapper.toModel(QuoteDto.builder()
                .isin("RU000A0JX0JJ")
                .ask(new BigDecimal("101.9"))
                .bid(new BigDecimal("100.2"))
                .energyLevel(new BigDecimal("102"))
                .build()));

        QuoteDto quoteDtoResult = quoteHistoryService.save(quoteDto);
        quoteDto.setBid(quoteDtoResult.getBid());
        quoteDto.setEnergyLevel(quoteDtoResult.getEnergyLevel());
        assertEquals(101.9, quoteDtoResult.getEnergyLevel().doubleValue());

    }

    @Test
    void saveQuoteIfBidOlderThenElv_thenReturnOk() {
        QuoteDto quoteDto = new QuoteDto();
        quoteDto.setIsin(quoteDtoList.get(0).getIsin());
        quoteDto.setAsk(quoteDtoList.get(0).getAsk());
        quoteDto.setBid(quoteDtoList.get(0).getBid());

        entityManager.persist(quoteHistoryMapper.toModel(QuoteDto.builder()
                .isin("RU000A0JX0JJ")
                .ask(new BigDecimal("101.9"))
                .bid(new BigDecimal("100.2"))
                .energyLevel(new BigDecimal("90"))
                .build()));

        QuoteDto quoteDtoResult = quoteHistoryService.save(quoteDto);
        quoteDto.setBid(quoteDtoResult.getBid());
        quoteDto.setEnergyLevel(quoteDtoResult.getEnergyLevel());
        assertEquals(100.2, quoteDtoResult.getEnergyLevel().doubleValue());

    }

    @Test
    void saveNewQuoteIfBidNotEqualNull_thenReturnOk() {
        QuoteDto quoteDto = new QuoteDto();
        quoteDto.setIsin("FR100A0JX0JJ");
        quoteDto.setAsk(quoteDtoList.get(0).getAsk());
        quoteDto.setBid(quoteDtoList.get(0).getBid());
        QuoteDto quoteDtoResult = quoteHistoryService.save(quoteDto);
        quoteDto.setBid(quoteDtoResult.getBid());
        quoteDto.setEnergyLevel(quoteDtoResult.getEnergyLevel());
        assertEquals(quoteDto, quoteDtoResult);
    }

    @Test
    void saveNewQuoteIfBidEqualNull_thenReturnOk() {
        QuoteDto quoteDto = new QuoteDto();
        quoteDto.setIsin("FR100A0JX0JJ");
        quoteDto.setAsk(quoteDtoList.get(0).getAsk());
        QuoteDto quoteDtoResult = quoteHistoryService.save(quoteDto);
        quoteDto.setEnergyLevel(quoteDtoResult.getEnergyLevel());
        assertEquals(quoteDto, quoteDtoResult);

    }

}
