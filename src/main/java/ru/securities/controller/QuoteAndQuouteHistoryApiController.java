package ru.securities.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.securities.model.dto.QuoteDto;
import ru.securities.service.QuoteAndQuoteHistoryService;


import javax.validation.Valid;

import java.util.concurrent.Callable;

@RestController
@RequestMapping("/quote")
@RequiredArgsConstructor
public class QuoteAndQuouteHistoryApiController {

    private final QuoteAndQuoteHistoryService quoteService;

    @PostMapping("/save")
    public Callable<ResponseEntity<QuoteDto>> save(@RequestBody @Valid QuoteDto quoteDto) {
        if(quoteDto.getBid()!=null) {
            if (quoteDto.getBid().compareTo(quoteDto.getAsk()) == 0 ||
                    quoteDto.getBid().compareTo(quoteDto.getAsk()) >0) {
                return () -> new ResponseEntity<>(quoteDto, HttpStatus.BAD_REQUEST);
            }
        }
        return () -> ResponseEntity.ok(quoteService.save(quoteDto));
    }
   /* @PutMapping("/update")
    public Callable<ResponseEntity<QuoteDto>> update(@RequestBody @Valid QuoteDto quoteDto) {
        if(quoteDto.getBid()!=null) {
            if (quoteDto.getBid().compareTo(quoteDto.getAsk()) == 0) {
                return () -> new ResponseEntity<>(quoteDto, HttpStatus.BAD_REQUEST);
            }
        }
        return () -> ResponseEntity.ok(quoteService.update(quoteDto));
    }*/


}
