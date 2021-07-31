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

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
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
        Optional<QuoteHistory> quoteHistoryOptinal = quouteHistoryRepository.findTopByIsinDesc(quoteDto.getIsin());
        if (quoteHistoryOptinal.isPresent()) {
            QuoteHistory quoteHistory = quoteHistoryOptinal.get();
            if (quoteDto.getBid() == null) {

                // quoteDto.setEnergyLevel(quote1.getAsk());
                //  quoteDto.setEnergyLevel(quoteDto.getAsk());
                quoteHistory.setEnergyLevel(quoteDto.getAsk());
                quoteHistory.setAsk(quoteDto.getAsk());

            } else if (quoteDto.getBid().compareTo(quoteHistory.getEnergyLevel()) > 0) {

                // quoteDto.setEnergyLevel(quoteDto.getBid());
                quoteHistory.setEnergyLevel(quoteDto.getBid());
                quoteHistory.setAsk(quoteDto.getAsk());
                quoteHistory.setBid(quoteDto.getBid());

            } else if (quoteDto.getAsk().compareTo(quoteHistory.getEnergyLevel()) < 0) {

                //  quoteDto.setEnergyLevel(quoteDto.getAsk());
                quoteHistory.setEnergyLevel(quoteDto.getAsk());
                quoteHistory.setAsk(quoteDto.getAsk());
                quoteHistory.setBid(quoteDto.getBid());
            }
            try {
                quoteHistory.setZoneDateTime(ZonedDateTime.now());
                // return quoteMapper.toDTO(quoteRepository.save(quoteMapper.toModel(quoteDto)));
                return quouteHistoryMapper.toDTO(quouteHistoryRepository.save(quoteHistory));
            } catch (Exception e) {
                throw new RuntimeException("Невозможно рассчитать котировку");
            }
        } else {
            QuoteHistory quoteHistory;
            if (quoteDto.getBid() != null) {
                quoteHistory = QuoteHistory.builder()
                        .ask(quoteDto.getAsk())
                        .isin(quoteDto.getIsin())
                        .bid(quoteDto.getBid())
                        .energyLevel(quoteDto.getBid())
                        .build();
                // quote1.setEnergyLevel(quoteDto.getBid());
            } else {
                quoteHistory = QuoteHistory.builder()
                        .ask(quoteDto.getAsk())
                        .isin(quoteDto.getIsin())
                        .energyLevel(quoteDto.getAsk())
                        .build();
            }
            return quouteHistoryMapper.toDTO(quouteHistoryRepository.save(quoteHistory));
        }

    }

        //quoteDto.setEnergyLevel(quoteDto.getAsk());
        // return quoteMapper.toDTO(quoteRepository.save(quote1));
        // }
        //return quoteMapper.toDTO(quoteRepository.save(quoteMapper.toModel(quoteDto)));*/
      //  return null;


    @Transactional
    @Override
    public QuoteDto update(QuoteDto quoteDto) {
      /*  QuoteHistory quoteHistory = quouteHistoryRepository.findByIsin(quoteDto.getIsin())
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
        }*/
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
