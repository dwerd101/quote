package ru.securities.service;

import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.Lifecycle;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.securities.app.AppApplication;
import ru.securities.exception.QuoteNotFoundException;
import ru.securities.mapper.QuoteMapper;
import ru.securities.model.Quote;
import ru.securities.model.dto.QuoteDto;
import ru.securities.repository.QuoteRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ContextConfiguration(classes = {AppApplication.class})
public class QuoteServiceImplTest {

    @Autowired
    private QuoteService quoteService;
    @Autowired
    private QuoteMapper quoteMapper;


    private List<QuoteDto> quoteDtoList;


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
        quoteDtoList.add(QuoteDto.builder()
                .isin("CN100A0JX0JF")
                .ask(new BigDecimal("101.9"))
                .bid(new BigDecimal("100.2"))
                .energyLevel(new BigDecimal("102.0"))
                .build());
        quoteDtoList.forEach(quoteDto -> entityManager.persist(quoteMapper.toModel(quoteDto)));
        // entityManager.flush();

    }

    @Test
        //@Transactional(readOnly = true)
    void findAll_thenReturnOk() {
       /* when(quoteRepository.findAll()).thenReturn(quoteDtoList.stream()
                .map(quoteMapper::toModel)
                .collect(Collectors.toList()));*/

        List<QuoteDto> list = quoteService.findAll();
        assertEquals(list, quoteDtoList);
    }

    @Test
    void saveQuoteDtoIfGetBidEqualNull_thenReturnOk() {
        QuoteDto quoteDto = new QuoteDto();
        quoteDto.setIsin(quoteDtoList.get(1).getIsin());
        quoteDto.setAsk(quoteDtoList.get(1).getAsk());

        // Optional<Quote> optionalQuote = quoteRepository.findByIsin(quoteDto.getIsin());

        //  when(quoteRepository.findByIsin(any())).thenReturn(optionalQuote);
        //  when(quoteRepository.save(any())).thenReturn(quoteMapper.toModel(quoteDto));
        QuoteDto quoteDtoResult = quoteService.save(quoteDto);
        quoteDto.setBid(quoteDtoResult.getBid());
        quoteDto.setEnergyLevel(quoteDtoResult.getEnergyLevel());
        //  quoteDto.
        assertEquals(101.9, quoteDtoResult.getEnergyLevel().doubleValue());
    }

    @Test
    void saveQuoteIfAskLowerThenElv_thenReturnOk() {
        QuoteDto quoteDto = new QuoteDto();
        quoteDto.setIsin(quoteDtoList.get(3).getIsin());
        quoteDto.setAsk(quoteDtoList.get(3).getAsk());
        quoteDto.setBid(quoteDtoList.get(3).getBid());

        // Optional<Quote> optionalQuote = quoteRepository.findByIsin(quoteDto.getIsin());

        //  when(quoteRepository.findByIsin(any())).thenReturn(optionalQuote);
        //  when(quoteRepository.save(any())).thenReturn(quoteMapper.toModel(quoteDto));
        QuoteDto quoteDtoResult = quoteService.save(quoteDto);
        quoteDto.setBid(quoteDtoResult.getBid());
        quoteDto.setEnergyLevel(quoteDtoResult.getEnergyLevel());
        //  quoteDto.
        assertEquals(101.9, quoteDtoResult.getEnergyLevel().doubleValue());

    }

    @Test
    void saveQuoteIfBidOlderThenElv_thenReturnOk() {
        QuoteDto quoteDto = new QuoteDto();
        quoteDto.setIsin(quoteDtoList.get(0).getIsin());
        quoteDto.setAsk(quoteDtoList.get(0).getAsk());
        quoteDto.setBid(quoteDtoList.get(0).getBid());

        // Optional<Quote> optionalQuote = quoteRepository.findByIsin(quoteDto.getIsin());

        //  when(quoteRepository.findByIsin(any())).thenReturn(optionalQuote);
        //  when(quoteRepository.save(any())).thenReturn(quoteMapper.toModel(quoteDto));
        QuoteDto quoteDtoResult = quoteService.save(quoteDto);
        quoteDto.setBid(quoteDtoResult.getBid());
        quoteDto.setEnergyLevel(quoteDtoResult.getEnergyLevel());
        //  quoteDto.
        assertEquals(100.2, quoteDtoResult.getEnergyLevel().doubleValue());

    }

    @Test
    void saveNewQuoteIfBidNotEqualNull_thenReturnOk() {
        QuoteDto quoteDto = new QuoteDto();
        quoteDto.setIsin("FR100A0JX0JJ");
        quoteDto.setAsk(quoteDtoList.get(0).getAsk());
        quoteDto.setBid(quoteDtoList.get(0).getBid());

        // Optional<Quote> optionalQuote = quoteRepository.findByIsin(quoteDto.getIsin());

        //  when(quoteRepository.findByIsin(any())).thenReturn(optionalQuote);
        //  when(quoteRepository.save(any())).thenReturn(quoteMapper.toModel(quoteDto));
        QuoteDto quoteDtoResult = quoteService.save(quoteDto);
        quoteDto.setBid(quoteDtoResult.getBid());
        quoteDto.setEnergyLevel(quoteDtoResult.getEnergyLevel());
        //  quoteDto.
        assertEquals(quoteDto, quoteDtoResult);
    }

    @Test
    void saveNewQuoteIfBidEqualNull_thenReturnOk() {
        QuoteDto quoteDto = new QuoteDto();
        quoteDto.setIsin("FR100A0JX0JJ");
        quoteDto.setAsk(quoteDtoList.get(0).getAsk());

        // Optional<Quote> optionalQuote = quoteRepository.findByIsin(quoteDto.getIsin());

        //  when(quoteRepository.findByIsin(any())).thenReturn(optionalQuote);
        //  when(quoteRepository.save(any())).thenReturn(quoteMapper.toModel(quoteDto));
        QuoteDto quoteDtoResult = quoteService.save(quoteDto);
        quoteDto.setEnergyLevel(quoteDtoResult.getEnergyLevel());
        //  quoteDto.
        assertEquals(quoteDto, quoteDtoResult);

    }

    @Test
    void findAllByIsin_thenReturnOk() {
        QuoteDto quoteDto = new QuoteDto();
        quoteDto.setIsin(quoteDtoList.get(0).getIsin());
        List<QuoteDto> quoteDtoList = quoteService.findAllByIsin(quoteDto);
        assertEquals(quoteDto.getIsin(), quoteDtoList.get(0).getIsin());

    }

    @Test
    void findAllByIsin_thenReturnFalse() {
        QuoteDto quoteDto = new QuoteDto();
        quoteDto.setIsin("FR100A0JX0JJ");
        List<QuoteDto> quoteDtoList = quoteService.findAllByIsin(quoteDto);
        assertTrue(quoteDtoList.isEmpty());

    }

    @Test
    void findByIsin_thenReturnOk() {
        QuoteDto quoteDto = new QuoteDto();
        quoteDto.setIsin(quoteDtoList.get(0).getIsin());
        QuoteDto quoteDto1 = quoteService.findByIsin(quoteDto);
        assertEquals(quoteDto.getIsin(), quoteDto1.getIsin());
    }

    @Test
    void findByIsin_thenReturnFalse() {
        QuoteDto quoteDto = new QuoteDto();
        quoteDto.setIsin("FR100A0JX0JJ");
        QuoteNotFoundException quoteNotFoundException =  assertThrows(QuoteNotFoundException.class, ()->
                quoteService.findByIsin(quoteDto));
        String expetedMessage = "not found by isin";
        String actualMessage = quoteNotFoundException.getMessage();
        assertTrue(actualMessage.contains(expetedMessage));

    }


}
