package ru.securities.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.securities.app.AppApplication;
import ru.securities.controller.QuoteApiController;
import ru.securities.model.Quote;
import ru.securities.model.dto.QuoteDto;
import ru.securities.service.QuoteAndQuoteHistoryService;
import ru.securities.service.QuoteService;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
//@WebMvcTest(QuoteApiController.class)
//@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
//@WebMvcTest
//@ContextConfiguration(classes = {AppApplication.class})
@SpringBootTest(classes={AppApplication.class})
public class QuoteApiControllerTest {


 /*   .ISIN: RU000A0JX0J2Bid: 100.2
    Ask: 101.9
    Котировка новая, поэтому elvl -> 100.22.
    Пришла новая котировка, уже существует elvl
    ISIN: RU000A0JX0J2Bid: 100.5
    Ask: 101.9
    ТаккакBid> elvl, тоновыйelvl= 100.5 (bid)*/


    @Autowired
   private MockMvc mockMvc;


    private List<QuoteDto> quoteDtoList;

    private List<QuoteDto> singletonDtoList;

    @MockBean
   // @Autowired
   private QuoteService quoteService;

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
    void findAll_thenReturnOk() throws Exception {
        final String url  = "/quote/";
        when(quoteService.findAll()).thenReturn(quoteDtoList);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        ResponseEntity<List<QuoteDto>> responseEntity = (ResponseEntity<List<QuoteDto>>) mvcResult.getAsyncResult();
        assertEquals(quoteDtoList,responseEntity.getBody());
    }
    @Test
    void findAllByIsin_thenReturnOk() throws Exception {
        final  String url = "/quote/?isin=EN000A0JX0J2C";
        when(quoteService.findAllByIsin(quoteDtoList.get(1))).thenReturn(singletonDtoList);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
      //  verify(quoteService).findAllByIsin(quoteDtoList.get(1));
       ResponseEntity<List<QuoteDto>> responseEntity = (ResponseEntity<List<QuoteDto>>) mvcResult.getAsyncResult();
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());

    }

    @Test
    void getElv_thenReturnOk() throws Exception {
        final  String url = "/quote/elv";
        when(quoteService.findAll()).thenReturn(quoteDtoList);
        List<BigDecimal> list = quoteDtoList.stream().map(QuoteDto::getEnergyLevel).collect(Collectors.toList());
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        ResponseEntity<List<BigDecimal>> responseEntity = (ResponseEntity<List<BigDecimal>>) mvcResult.getAsyncResult();
        assertEquals(list,responseEntity.getBody());
    }

    @Test
    void getElvWithParam_thenReturnOk() throws Exception {
        final  String url = "/quote/elv?isin=EN000A0JX0JJ";
        QuoteDto quoteDto = quoteDtoList.get(1);
        when(quoteService.findAllByIsin(quoteDto)).thenReturn(singletonDtoList);
      //  set(quoteApiController,"quoteService",quoteService);
      //  List<BigDecimal> list = quoteDtoList.stream().map(QuoteDto::getEnergyLevel).collect(Collectors.toList());
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        ResponseEntity<List<BigDecimal>> responseEntity = (ResponseEntity<List<BigDecimal>>) mvcResult.getAsyncResult();
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }
/*
    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }*/


 /*   private static boolean set(Object object, String fieldName, Object fieldValue) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(object, fieldValue);
                return true;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return false;
    }*/
}
