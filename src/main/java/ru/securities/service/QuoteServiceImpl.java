package ru.securities.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = true)
    @Override
    public List<QuoteDto> findAll() {
        return quoteRepository.findAll()
                .stream()
                .map(quoteMapper::toDTO)
                .collect(Collectors.toList());
    }

/*    @Override
    public QuoteDto save(QuoteDto quoteDto) {
        Optional<Quote> quoteOptional = quoteRepository.findByIsin(quoteDto.getIsin());

        if(quoteOptional.isPresent()) {

        }
        return null;
    }*/

    @Transactional
    @Override
    public QuoteDto save(QuoteDto quoteDto) {
        Quote quote1;
        if (quoteDto.getBid() != null) {
            quote1 = Quote.builder()
                    .bid(quoteDto.getBid())
                    .ask(quoteDto.getAsk())
                    .isin(quoteDto.getIsin())
                    .energyLevel(quoteDto.getBid())
                    .build();
            // quote1.setEnergyLevel(quoteDto.getBid());
        } else {
            quote1 = Quote.builder()
                    .ask(quoteDto.getAsk())
                    .isin(quoteDto.getIsin())
                    .energyLevel(quoteDto.getAsk())
                    .build();
        } //quoteDto.setEnergyLevel(quoteDto.getAsk());
        return quoteMapper.toDTO(quoteRepository.save(quote1));

        //return quoteMapper.toDTO(quoteRepository.save(quoteMapper.toModel(quoteDto)));
    }

    /*   @Override
       public QuoteDto save(QuoteDto quoteDto) {
           Optional<Quote> quote = quoteRepository.findByIsin(quoteDto.getIsin());
           if (quote.isPresent()) {
               Quote quote1 = quote.get();
               if (quoteDto.getBid()==null) {

                  // quoteDto.setEnergyLevel(quote1.getAsk());
                 //  quoteDto.setEnergyLevel(quoteDto.getAsk());
                   quote1.setEnergyLevel(quoteDto.getAsk());
                   quote1.setAsk(quoteDto.getAsk());

               } else if (quote1.getEnergyLevel() == null) {

                   //quoteDto.setEnergyLevel(quoteDto.getBid());
                   quote1.setEnergyLevel(quoteDto.getAsk());
                   quote1.setAsk(quoteDto.getAsk());
                   quote1.setBid(quoteDto.getBid());

               } else if (quoteDto.getBid().compareTo(quote1.getEnergyLevel()) > 0) {

                  // quoteDto.setEnergyLevel(quoteDto.getBid());
                   quote1.setEnergyLevel(quoteDto.getBid());
                   quote1.setAsk(quoteDto.getAsk());
                   quote1.setBid(quoteDto.getBid());

               } else if (quoteDto.getAsk().compareTo(quote1.getEnergyLevel()) < 0) {

                 //  quoteDto.setEnergyLevel(quoteDto.getAsk());
                   quote1.setEnergyLevel(quoteDto.getAsk());
                   quote1.setAsk(quoteDto.getAsk());
                   quote1.setBid(quoteDto.getBid());
               }
               try {
                  // return quoteMapper.toDTO(quoteRepository.save(quoteMapper.toModel(quoteDto)));
                 return   quoteMapper.toDTO(quoteRepository.save(quote1));
               } catch (Exception e) {
                   throw new RuntimeException("Невозможно рассчитать котировку");
               }

           } else {

               Quote quote1;
               if(quoteDto.getBid()!=null) {
                   quote1 = Quote.builder()
                           .ask(quoteDto.getAsk())
                           .isin(quoteDto.getIsin())
                           .energyLevel(quoteDto.getBid())
                           .build();
                  // quote1.setEnergyLevel(quoteDto.getBid());
               } else {
                   quote1 = Quote.builder()
                           .ask(quoteDto.getAsk())
                           .isin(quoteDto.getIsin())
                           .energyLevel(quoteDto.getAsk())
                           .build();
               } //quoteDto.setEnergyLevel(quoteDto.getAsk());
               return quoteMapper.toDTO(quoteRepository.save(quote1));
           }
           //return quoteMapper.toDTO(quoteRepository.save(quoteMapper.toModel(quoteDto)));
       }*/
    @Transactional
    @Override
    public QuoteDto update(QuoteDto quoteDto) {
        Quote quote = quoteRepository.findByIsin(quoteDto.getIsin())
                .orElseThrow(()->
                        new QuoteNotUpdateException("Невозможно обновить квоту, т.к не найдена запись по isin"));

        if (quoteDto.getBid() == null) {

                // quoteDto.setEnergyLevel(quote1.getAsk());
                //  quoteDto.setEnergyLevel(quoteDto.getAsk());
                quote.setIsin(quoteDto.getIsin());
                quote.setEnergyLevel(quoteDto.getAsk());
                quote.setAsk(quoteDto.getAsk());

            } else if (quote.getEnergyLevel() == null) {

                //quoteDto.setEnergyLevel(quoteDto.getBid());
                quote.setIsin(quoteDto.getIsin());
                quote.setEnergyLevel(quoteDto.getAsk());
                quote.setAsk(quoteDto.getAsk());
                quote.setBid(quoteDto.getBid());

            } else if (quoteDto.getBid().compareTo(quote.getEnergyLevel()) > 0) {

                // quoteDto.setEnergyLevel(quoteDto.getBid());
                quote.setIsin(quoteDto.getIsin());
                quote.setEnergyLevel(quoteDto.getBid());
                quote.setAsk(quoteDto.getAsk());
                quote.setBid(quoteDto.getBid());

            } else if (quoteDto.getAsk().compareTo(quote.getEnergyLevel()) < 0) {

                //  quoteDto.setEnergyLevel(quoteDto.getAsk());
                quote.setIsin(quoteDto.getIsin());
                quote.setEnergyLevel(quoteDto.getAsk());
                quote.setAsk(quoteDto.getAsk());
                quote.setBid(quoteDto.getBid());
            }
            try {
                // return quoteMapper.toDTO(quoteRepository.save(quoteMapper.toModel(quoteDto)));
                return quoteMapper.toDTO(quoteRepository.save(quote));
            } catch (Exception e) {
                throw new QuoteNotImpossibleToCalculate("Невозможно рассчитать котировку");
            }

            // return quoteMapper.toDTO(quoteRepository.save(quote1));

     //   throw new QuoteNotUpdateException("Невозможно обновить квоту, т.к не найдена запись по isin");
        //return quoteMapper.toDTO(quoteRepository.save(quoteMapper.toModel(quoteDto)));
    }
   /* @Override
    public QuoteDto update(QuoteDto quoteDto) {
        Optional<Quote> quote = quoteRepository.findByIsin(quoteDto.getIsin());
        if (quote.isPresent()) {
            Quote quote1 = quote.get();
            if (quoteDto.getBid() == null) {

                // quoteDto.setEnergyLevel(quote1.getAsk());
                //  quoteDto.setEnergyLevel(quoteDto.getAsk());
                quote1.setEnergyLevel(quoteDto.getAsk());
                quote1.setAsk(quoteDto.getAsk());

            } else if (quote1.getEnergyLevel() == null) {

                //quoteDto.setEnergyLevel(quoteDto.getBid());
                quote1.setEnergyLevel(quoteDto.getAsk());
                quote1.setAsk(quoteDto.getAsk());
                quote1.setBid(quoteDto.getBid());

            } else if (quoteDto.getBid().compareTo(quote1.getEnergyLevel()) > 0) {

                // quoteDto.setEnergyLevel(quoteDto.getBid());
                quote1.setEnergyLevel(quoteDto.getBid());
                quote1.setAsk(quoteDto.getAsk());
                quote1.setBid(quoteDto.getBid());

            } else if (quoteDto.getAsk().compareTo(quote1.getEnergyLevel()) < 0) {

                //  quoteDto.setEnergyLevel(quoteDto.getAsk());
                quote1.setEnergyLevel(quoteDto.getAsk());
                quote1.setAsk(quoteDto.getAsk());
                quote1.setBid(quoteDto.getBid());
            }
            try {
                // return quoteMapper.toDTO(quoteRepository.save(quoteMapper.toModel(quoteDto)));
                return quoteMapper.toDTO(quoteRepository.save(quote1));
            } catch (Exception e) {
                throw new RuntimeException("Невозможно рассчитать котировку");
            }

           // return quoteMapper.toDTO(quoteRepository.save(quote1));
        }
        throw new QuoteNotUpdateException("Невозможно обновить квоту, т.к не найдена запись по isin");
        //return quoteMapper.toDTO(quoteRepository.save(quoteMapper.toModel(quoteDto)));
    }*/

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





   /* @Override
    public QuoteHistory findByIsin(String isin) {
        return quoteRepository.findByIsin(isin).orElseThrow(() -> new QuoteException("not found by isin"));
    }*/


}
