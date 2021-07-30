package ru.securities.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.securities.app.AppApplication;

import ru.securities.model.dto.QuoteDto;
import ru.securities.service.QuoteAndQuoteHistoryService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
//@WebMvcTest
//@ContextConfiguration(classes = {AppApplication.class})
@SpringBootTest(classes={AppApplication.class})
public class QuoteAndQuoteHistoryApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuoteAndQuoteHistoryService quoteAndQuoteHistoryService;

    @Test
    void save_thenReturnCorrect() throws Exception {
        final String url = "/quote/save";
        QuoteDto quoteDto = QuoteDto.builder()
                .isin("RU000A0JX0JJ")
                .ask(new BigDecimal("101.9"))
                .bid(new BigDecimal("100.2"))
                .build();

        when(quoteAndQuoteHistoryService.save(quoteDto)).thenReturn(quoteDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(quoteDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        ResponseEntity<QuoteDto> responseEntity = (ResponseEntity<QuoteDto>) mvcResult.getAsyncResult();
        assertEquals(ResponseEntity.ok(responseEntity.getBody()), mvcResult.getAsyncResult());
    }

    @Test
    void saveNoCorrectIsin_thenReturnNoCorrect() throws Exception {
        final String url = "/quote/save";
        // размер поля 13
        QuoteDto quoteDto = QuoteDto.builder()
                .isin("RU000A0JX0JJJ")
                .ask(new BigDecimal("101.9"))
                .bid(new BigDecimal("100.2"))
                .build();
        when(quoteAndQuoteHistoryService.save(quoteDto)).thenReturn(quoteDto);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(quoteDto)))
                .andDo(print())
                .andExpect(status().is(400))
                .andReturn();
        assertNotEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void saveOlderBid_thenReturnCorrect() throws Exception {
        final String url = "/quote/save";

        QuoteDto quoteDto = QuoteDto.builder()
                .isin("RU000A0JX0JJ")
                .ask(new BigDecimal("101.9"))
                .bid(new BigDecimal("100.2"))
                .energyLevel(new BigDecimal("100.2"))
                .build();
        when(quoteAndQuoteHistoryService.save(quoteDto)).thenReturn(quoteDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(quoteDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        ResponseEntity<QuoteDto> responseEntity = (ResponseEntity<QuoteDto>) mvcResult.getAsyncResult();
        assertEquals(ResponseEntity.ok(responseEntity.getBody()), mvcResult.getAsyncResult());
    }
    @Test
    void saveLowerAsk_thenReturnCorrect() throws Exception {
        final String url = "/quote/save";

        QuoteDto quoteDto = QuoteDto.builder()
                .isin("RU000A0JX0JJ")
                .ask(new BigDecimal("101.9"))
                .bid(new BigDecimal("100.2"))
                .energyLevel(new BigDecimal("101.9"))
                .build();
        when(quoteAndQuoteHistoryService.save(quoteDto)).thenReturn(quoteDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(quoteDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        ResponseEntity<QuoteDto> responseEntity = (ResponseEntity<QuoteDto>) mvcResult.getAsyncResult();
        assertEquals(ResponseEntity.ok(responseEntity.getBody()), mvcResult.getAsyncResult());
    }

    @Test
    void saveEmptyElv_thenReturnCorrect() throws Exception {
        final String url = "/quote/save";

        QuoteDto quoteDto = QuoteDto.builder()
                .isin("RU000A0JX0JJ")
                .ask(new BigDecimal("101.9"))
                .bid(new BigDecimal("100.2"))
                .build();
        QuoteDto quoteDtoReturn = QuoteDto.builder()
                .isin("RU000A0JX0JJ")
                .ask(new BigDecimal("101.9"))
                .bid(new BigDecimal("100.2"))
                .energyLevel(new BigDecimal("100.2"))
                .build();

        when(quoteAndQuoteHistoryService.save(quoteDto)).thenReturn(quoteDtoReturn);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(quoteDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        ResponseEntity<QuoteDto> responseEntity = (ResponseEntity<QuoteDto>) mvcResult.getAsyncResult();
        assertEquals(ResponseEntity.ok(responseEntity.getBody()), mvcResult.getAsyncResult());
    }

    @Test
    void saveEmptyBid_thenReturnCorrect() throws Exception {
        final String url = "/quote/save";

        QuoteDto quoteDto = QuoteDto.builder()
                .isin("RU000A0JX0JJ")
                .ask(new BigDecimal("101.9"))
                .build();

        QuoteDto quoteDtoReturn = QuoteDto.builder()
                .isin("RU000A0JX0JJ")
                .ask(new BigDecimal("101.9"))
                .energyLevel(new BigDecimal("101.9"))
                .build();

        when(quoteAndQuoteHistoryService.save(quoteDto)).thenReturn(quoteDtoReturn);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(quoteDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        ResponseEntity<QuoteDto> responseEntity = (ResponseEntity<QuoteDto>) mvcResult.getAsyncResult();
        assertEquals(ResponseEntity.ok(responseEntity.getBody()), mvcResult.getAsyncResult());
    }

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
