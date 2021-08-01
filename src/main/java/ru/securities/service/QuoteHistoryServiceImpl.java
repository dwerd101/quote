package ru.securities.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.securities.algorithm.QuoteHistoryCountAlgorithm;
import ru.securities.mapper.QuoteHistoryMapper;
import ru.securities.model.QuoteHistory;
import ru.securities.model.dto.QuoteDto;
import ru.securities.repository.QuoteHistoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuoteHistoryServiceImpl implements QuoteHistoryService {
    private final QuoteHistoryRepository quoteHistoryRepository;
    private final QuoteHistoryMapper quoteHistoryMapper;
    private final QuoteHistoryCountAlgorithm quoteHistoryCountAlgorithm;

    @Transactional(readOnly = true)
    @Override
    public List<QuoteDto> findAll() {
        return quoteHistoryRepository.findAll().stream()
                .map(quoteHistoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public QuoteDto save(QuoteDto quoteDto) {
        Optional<QuoteHistory> quoteHistoryOptional = quoteHistoryRepository.findTopByIsinDesc(quoteDto.getIsin());
        QuoteHistory quoteHistory = quoteHistoryCountAlgorithm.count(quoteDto, quoteHistoryOptional);
        return quoteHistoryMapper.toDTO(quoteHistoryRepository.save(quoteHistory));
    }


    @Transactional
    @Override
    public QuoteDto update(QuoteDto quoteDto) {
        return null;
    }


    @Override
    public void remove(QuoteDto quoteDto) {

    }


    @Override
    public QuoteDto findByIsin(QuoteDto isin) {
        return null;
    }

    @Override
    public List<QuoteDto> findAllByIsin(QuoteDto isin) {
        return null;
    }
}
