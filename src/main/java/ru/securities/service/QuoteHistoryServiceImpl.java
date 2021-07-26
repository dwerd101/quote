package ru.securities.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.securities.exception.QuoteNotFoundException;
import ru.securities.exception.QuoteNotImpossibleToCalculate;
import ru.securities.mapper.QuouteHistoryMapper;
import ru.securities.model.Quote;
import ru.securities.model.QuoteHistory;
import ru.securities.model.dto.QuoteDto;
import ru.securities.repository.QuouteHistoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuoteHistoryServiceImpl implements QuoteHistoryService {
    private final QuouteHistoryRepository quouteHistoryRepository;
    private final QuouteHistoryMapper quouteHistoryMapper;

    @Transactional(readOnly = true)
    @Override
    public List<QuoteDto> findAll() {
        return quouteHistoryRepository.findAll().stream()
                .map(quouteHistoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public QuoteDto save(QuoteDto quoteDto) {
        QuoteHistory quote1;
        if (quoteDto.getBid() != null) {
            quote1 = QuoteHistory.builder()
                    .bid(quoteDto.getBid())
                    .ask(quoteDto.getAsk())
                    .isin(quoteDto.getIsin())
                    .energyLevel(quoteDto.getBid())
                    .build();
            // quote1.setEnergyLevel(quoteDto.getBid());
        } else {
            quote1 = QuoteHistory.builder()
                    .ask(quoteDto.getAsk())
                    .isin(quoteDto.getIsin())
                    .energyLevel(quoteDto.getAsk())
                    .build();
        }
        return quouteHistoryMapper.toDTO(quouteHistoryRepository.save(quote1));
    }

    @Transactional
    @Override
    public QuoteDto update(QuoteDto quoteDto) {
       QuoteHistory quoteHistory = quouteHistoryRepository.findByIsin(quoteDto.getIsin())
                .orElseThrow(() -> new QuoteNotFoundException("not found by isin"));

        if (quoteDto.getBid() == null) {

            // quoteDto.setEnergyLevel(quote1.getAsk());
            //  quoteDto.setEnergyLevel(quoteDto.getAsk());
            quoteHistory.setIsin(quoteDto.getIsin());
            quoteHistory.setEnergyLevel(quoteDto.getAsk());
            quoteHistory.setAsk(quoteDto.getAsk());

        } else if (quoteHistory.getEnergyLevel() == null) {

            //quoteDto.setEnergyLevel(quoteDto.getBid());
            quoteHistory.setIsin(quoteDto.getIsin());
            quoteHistory.setEnergyLevel(quoteDto.getAsk());
            quoteHistory.setAsk(quoteDto.getAsk());
            quoteHistory.setBid(quoteDto.getBid());

        } else if (quoteDto.getBid().compareTo(quoteHistory.getEnergyLevel()) > 0) {

            // quoteDto.setEnergyLevel(quoteDto.getBid());
            quoteHistory.setIsin(quoteDto.getIsin());
            quoteHistory.setEnergyLevel(quoteDto.getBid());
            quoteHistory.setAsk(quoteDto.getAsk());
            quoteHistory.setBid(quoteDto.getBid());

        } else if (quoteDto.getAsk().compareTo(quoteHistory.getEnergyLevel()) < 0) {

            //  quoteDto.setEnergyLevel(quoteDto.getAsk());
            quoteHistory.setIsin(quoteDto.getIsin());
            quoteHistory.setEnergyLevel(quoteDto.getAsk());
            quoteHistory.setAsk(quoteDto.getAsk());
            quoteHistory.setBid(quoteDto.getBid());
        }
        try {

            return quouteHistoryMapper.toDTO(quouteHistoryRepository.save(quoteHistory));
        } catch (Exception e) {
            throw new QuoteNotImpossibleToCalculate("Невозможно рассчитать котировку");
        }
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
