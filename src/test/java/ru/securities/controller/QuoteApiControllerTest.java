package ru.securities.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.securities.app.AppApplication;
import ru.securities.model.dto.QuoteDto;
import ru.securities.service.QuoteService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
//@WebMvcTest(QuoteApiController.class)
//@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
//@WebMvcTest
//@ContextConfiguration(classes = {AppApplication.class})
@SpringBootTest(classes = {AppApplication.class})
public class QuoteApiControllerTest {



    @Autowired
    private MockMvc mockMvc;
    private List<QuoteDto> quoteDtoList;
    private List<QuoteDto> singletonDtoList;
    @MockBean
    private QuoteService quoteService;

    @BeforeEach
    void init() {
        quoteDtoList = new ArrayList<>();
        singletonDtoList = Collections.singletonList(QuoteDto.builder()
                .isin("EN000A0JX0JJ")
                .ask(new BigDecimal("101.9"))
                .bid(new BigDecimal("100.2"))
                .energyLevel(new BigDecimal("100.2"))
                .build());

        quoteDtoList.add(QuoteDto.builder()
                .isin("RU000A0JX0JJ")
                .ask(new BigDecimal("101.9"))
                .bid(new BigDecimal("100.2"))
                .energyLevel(new BigDecimal("100.2"))
                .build());

        quoteDtoList.add(QuoteDto.builder()
                .isin("EN000A0JX0JJ")
                .ask(new BigDecimal("101.9"))
                .bid(new BigDecimal("100.2"))
                .energyLevel(new BigDecimal("101.9"))
                .build());

    }


    @Test
    void findAll_thenReturnOk() throws Exception {
        final String url = "/quote/";

        when(quoteService.findAll()).thenReturn(quoteDtoList);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        ResponseEntity<List<QuoteDto>> responseEntity = (ResponseEntity<List<QuoteDto>>) mvcResult.getAsyncResult();

        assertEquals(quoteDtoList, responseEntity.getBody());
    }

    @Test
    void findAllByIsin_thenReturnOk() throws Exception {
        final String url = "/quote/?isin=EN000A0JX0J2C";
        when(quoteService.findAllByIsin(any())).thenReturn(singletonDtoList);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        ResponseEntity<List<QuoteDto>> responseEntity = (ResponseEntity<List<QuoteDto>>) mvcResult.getAsyncResult();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    void getElv_thenReturnOk() throws Exception {
        final String url = "/quote/elv";
        when(quoteService.findAll()).thenReturn(quoteDtoList);

        List<BigDecimal> elvList = quoteDtoList.stream()
                .map(QuoteDto::getEnergyLevel)
                .collect(Collectors.toList());

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        ResponseEntity<List<BigDecimal>> responseEntity = (ResponseEntity<List<BigDecimal>>) mvcResult.getAsyncResult();

        assertEquals(elvList, responseEntity.getBody());
    }

    @Test
    void getElvWithParam_thenReturnOk() throws Exception {
        final String url = "/quote/elv?isin=EN000A0JX0JJ";
        List<BigDecimal> elvList = new ArrayList<>();
        elvList.add(singletonDtoList.get(0).getEnergyLevel());

        when(quoteService.findAllByIsin(any())).thenReturn(singletonDtoList);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        ResponseEntity<List<BigDecimal>> responseEntity = (ResponseEntity<List<BigDecimal>>) mvcResult.getAsyncResult();

        assertEquals(elvList, responseEntity.getBody());
    }

}
