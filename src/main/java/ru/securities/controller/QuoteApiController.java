package ru.securities.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.securities.model.Quote;
import ru.securities.model.dto.QuoteDto;
import ru.securities.service.QuoteHistoryService;
import ru.securities.service.QuoteService;
import ru.securities.service.SecuritiesService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/quote")
@RequiredArgsConstructor
public class QuoteApiController {
    private final QuoteService quoteService;

    @GetMapping("/")
    public Callable<ResponseEntity<List<QuoteDto>>> findAll(@RequestParam(required = false) String isin) {
        if (isin != null) {
            List<QuoteDto> list = quoteService.findAllByIsin(QuoteDto.builder().isin(isin).build());
            return () -> ResponseEntity.ok(list);
        }
       else return () -> ResponseEntity.ok(quoteService.findAll());
    }

    @GetMapping("/elv")
    public Callable<ResponseEntity<List<BigDecimal>>> getElv(@RequestParam(required = false) String isin) {
        if (isin != null) return () -> ResponseEntity.ok(
                quoteService.findAllByIsin(QuoteDto.builder().isin(isin).build()).stream()
                        .map(QuoteDto::getEnergyLevel)
                        .collect(Collectors.toList()));
        else return () -> ResponseEntity.ok(quoteService.findAll().stream()
                .map(QuoteDto::getEnergyLevel)
                .collect(Collectors.toList()));
    }


}
