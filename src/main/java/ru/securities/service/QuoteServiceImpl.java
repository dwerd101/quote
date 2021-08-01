package ru.securities.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.securities.algorithm.QuoteCountAlgorithm;
import ru.securities.exception.QuoteNotFoundException;
import ru.securities.exception.QuoteNotImpossibleToCalculate;
import ru.securities.exception.QuoteNotUpdateException;
import ru.securities.mapper.QuoteMapper;
import ru.securities.model.Quote;
import ru.securities.model.dto.QuoteDto;
import ru.securities.repository.QuoteRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {
    private final QuoteRepository quoteRepository;
    private final QuoteMapper quoteMapper;
    private final QuoteCountAlgorithm quoteCountAlgorithm;

    @Transactional(readOnly = true)
    @Override
    public List<QuoteDto> findAll() {
        return quoteRepository.findAll()
                .stream()
                .map(quoteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public QuoteDto save(QuoteDto quoteDto) {
        Optional<Quote> quote= quoteRepository.findByIsin(quoteDto.getIsin());
        return quoteMapper.toDTO(quoteRepository.save(quoteCountAlgorithm.count(quoteDto, quote)));
    }

    @Override
    public QuoteDto update(QuoteDto quoteDto) {
        return null;
    }


    @Override
    public void remove(QuoteDto quoteDto) {

    }

    @Transactional(readOnly = true)
    @Override
    public QuoteDto findByIsin(QuoteDto quoteDto) {
        return quoteMapper.toDTO(quoteRepository.findByIsin(quoteMapper.toModel(quoteDto).getIsin())
                .orElseThrow(() -> new QuoteNotFoundException("not found by isin")));
    }


    @Transactional(readOnly = true)
    @Override
    public List<QuoteDto> findAllByIsin(QuoteDto quoteDto) {
        return quoteRepository.findAllByIsin(quoteMapper.toModel(quoteDto).getIsin())
                .stream()
                .map(quoteMapper::toDTO)
                .collect(Collectors.toList());
    }


}
