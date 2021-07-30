package ru.securities.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.securities.app.AppApplication;
import ru.securities.controller.QuoteApiController;
import ru.securities.model.Quote;
import ru.securities.model.QuoteHistory;
import ru.securities.model.dto.QuoteDto;
import ru.securities.service.QuoteAndQuoteHistoryService;
import ru.securities.service.QuoteHistoryService;
import ru.securities.service.QuoteService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
//@WebMvcTest
//@ContextConfiguration(classes = {AppApplication.class})
@SpringBootTest(classes={AppApplication.class})
public class QuoteAndQuoteHistorytApiControllerTest {

    @Autowired
    private MockMvc mockMvc;


    private List<QuoteDto> quoteDtoList;

    private List<QuoteDto> singletonDtoList;

    @MockBean
    private QuoteAndQuoteHistoryService quoteAndQuoteHistoryService;

    @BeforeEach
    void init() {
        quoteDtoList = new ArrayList<>();
        singletonDtoList = new ArrayList<>();
        quoteDtoList.add(QuoteDto.builder()
                .isin("RU000A0JX0JJ")
                .ask(new BigDecimal(101.9))
                .bid(new BigDecimal(100.2))
                .energyLevel(new BigDecimal(100.2))
                .build());
        quoteDtoList.add(QuoteDto.builder()
                .isin("EN000A0JX0JJ")
                .ask(new BigDecimal(101.9))
                .bid(new BigDecimal(100.2))
                .energyLevel(new BigDecimal(101.9))
                .build());

        singletonDtoList.add(QuoteDto.builder()
                .isin("EN000A0JX0JJ")
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
    void save_thenReturnCorrect() throws Exception {
        final String url = "/quote/save";
        QuoteDto quoteDto = QuoteDto.builder()
                .isin("RU000A0JX0JJ")
                .ask(new BigDecimal(101.9))
                .bid(new BigDecimal(100.2))
               // .energyLevel(new BigDecimal(100.2))
                .build();
        when(quoteAndQuoteHistoryService.save(quoteDto)).thenReturn(quoteDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(quoteDto)))
                //.andDo(print())
               // .andExpect(status().isOk())
                .andReturn();
        ResponseEntity<QuoteDto> responseEntity = (ResponseEntity<QuoteDto>) mvcResult.getAsyncResult();
        assertEquals(quoteDto, responseEntity.getBody());
    }

    @Test
    void saveNoCorrectIsin_thenNoReturnCorrect() throws Exception {
        final String url = "/quote/save";
        // размер поля 13
        QuoteDto quoteDto = QuoteDto.builder()
                .isin("RU000A0JX0JJJ")
                .ask(new BigDecimal(101.9))
                .bid(new BigDecimal(100.2))
                // .energyLevel(new BigDecimal(100.2))
                .build();
        when(quoteAndQuoteHistoryService.save(quoteDto)).thenReturn(quoteDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(quoteDto)))
                //.andDo(print())
                // .andExpect(status().isOk())
                .andReturn();
        assertNotEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void saveOlderBid_thenReturnCorrect() throws Exception {
        final String url = "/quote/save";

        QuoteDto quoteDto = QuoteDto.builder()
                .isin("RU000A0JX0JJ")
                .ask(new BigDecimal(101.9))
                .bid(new BigDecimal(100.2))
                .energyLevel(new BigDecimal(100.2))
                // .energyLevel(new BigDecimal(100.2))
                .build();
        when(quoteAndQuoteHistoryService.save(quoteDto)).thenReturn(quoteDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(quoteDto)))
                //.andDo(print())
                // .andExpect(status().isOk())
                .andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }
    @Test
    void saveLowerAsk_thenReturnCorrect() throws Exception {
        final String url = "/quote/save";

        QuoteDto quoteDto = QuoteDto.builder()
                .isin("RU000A0JX0JJ")
                .ask(new BigDecimal(101.9))
                .bid(new BigDecimal(100.2))
                .energyLevel(new BigDecimal(101.9))
                // .energyLevel(new BigDecimal(100.2))
                .build();
        when(quoteAndQuoteHistoryService.save(quoteDto)).thenReturn(quoteDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(quoteDto)))
                //.andDo(print())
                // .andExpect(status().isOk())
                .andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void saveEmptyElv_thenReturnCorrect() throws Exception {
        final String url = "/quote/save";

        QuoteDto quoteDto = QuoteDto.builder()
                .isin("RU000A0JX0JJ")
                .ask(new BigDecimal(101.9))
                .bid(new BigDecimal(100.2))
               // .energyLevel(new BigDecimal(101.9))
                // .energyLevel(new BigDecimal(100.2))
                .build();
        QuoteDto quoteDtoReturn = QuoteDto.builder()
                .isin("RU000A0JX0JJ")
                .ask(new BigDecimal(101.9))
                .bid(new BigDecimal(100.2))
                //.energyLevel(new BigDecimal(101.9))
                .energyLevel(new BigDecimal(100.2))
                .build();
        when(quoteAndQuoteHistoryService.save(quoteDto)).thenReturn(quoteDtoReturn);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(quoteDto)))
                //.andDo(print())
                // .andExpect(status().isOk())
                .andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void saveEmptyBid_thenReturnCorrect() throws Exception {
        final String url = "/quote/save";

        QuoteDto quoteDto = QuoteDto.builder()
                .isin("RU000A0JX0JJ")
                .ask(new BigDecimal(101.9))
               // .bid(new BigDecimal(100.2))
                // .energyLevel(new BigDecimal(101.9))
                // .energyLevel(new BigDecimal(100.2))
                .build();
        QuoteDto quoteDtoReturn = QuoteDto.builder()
                .isin("RU000A0JX0JJ")
                .ask(new BigDecimal(101.9))
                //.bid(new BigDecimal(100.2))
                //.energyLevel(new BigDecimal(101.9))
                .energyLevel(new BigDecimal(101.9))
                .build();
        when(quoteAndQuoteHistoryService.save(quoteDto)).thenReturn(quoteDtoReturn);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(quoteDto)))
                //.andDo(print())
                // .andExpect(status().isOk())
                .andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }


    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
