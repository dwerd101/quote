package ru.securities.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.securities.app.AppApplication;
import ru.securities.controller.QuoteApiController;
import ru.securities.model.QuoteHistory;
import ru.securities.model.dto.QuoteDto;
import ru.securities.service.QuoteHistoryService;
import ru.securities.service.QuoteService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
//@WebMvcTest
//@ContextConfiguration(classes = {AppApplication.class})
@SpringBootTest(classes={AppApplication.class})
public class QuoteHistoryApiControllerTest {

    @Autowired
    private MockMvc mockMvc;


    private List<QuoteDto> quoteDtoList;

    private List<QuoteDto> singletonDtoList;

    @MockBean
    // @Autowired
    private QuoteHistoryService quoteHistoryService;

    @BeforeEach
    void init() {
        quoteDtoList = new ArrayList<>();
        singletonDtoList = new ArrayList<>();
        quoteDtoList.add(QuoteDto.builder()
                .isin("RU000A0JX0J2B")
                .ask(new BigDecimal(101.9))
                .bid(new BigDecimal(100.2))
                .energyLevel(new BigDecimal(100.2))
                .build());
        quoteDtoList.add(QuoteDto.builder()
                .isin("EN000A0JX0J2С")
                .ask(new BigDecimal(101.9))
                .bid(new BigDecimal(100.2))
                .energyLevel(new BigDecimal(101.9))
                .build());

        singletonDtoList.add(QuoteDto.builder()
                .isin("EN000A0JX0J2С")
                .ask(new BigDecimal(101.9))
                .bid(new BigDecimal(100.2))
                .build());

        //List<QuoteDto> list = new ArrayList<>();
      /*  list.add(QuoteDto.builder()
                .isin("EN000A0JX0J2С")
                .ask(new BigDecimal(101.9))
                .bid(new BigDecimal(100.2))
                .build());*/
    }

    @Test
    void findAllHistory_thenReturnCorrect() throws Exception {
        final  String url = "/qoute-history/";
        when(quoteHistoryService.findAll()).thenReturn(quoteDtoList);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        ResponseEntity<List<QuoteDto>> responseEntity = (ResponseEntity<List<QuoteDto>>) mvcResult.getAsyncResult();
        assertEquals(quoteDtoList,responseEntity.getBody());
    }


}
