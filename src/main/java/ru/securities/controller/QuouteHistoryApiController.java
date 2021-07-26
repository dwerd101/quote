package ru.securities.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.securities.model.dto.QuoteDto;
import ru.securities.service.QuoteHistoryService;
import ru.securities.service.SecuritiesService;

import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequestMapping("/qoute-history")
@RequiredArgsConstructor
public class QuouteHistoryApiController {
    private final QuoteHistoryService qouteHistoryService;

    @GetMapping("/")
    public Callable<ResponseEntity<List<QuoteDto>>> findAll() {
        return ()->ResponseEntity.ok(qouteHistoryService.findAll());
    }
}
