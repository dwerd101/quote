package ru.securities.mapper;

import javax.annotation.Generated;
import org.springframework.stereotype.Component;
import ru.securities.model.Quote;
import ru.securities.model.Quote.QuoteBuilder;
import ru.securities.model.dto.QuoteDto;
import ru.securities.model.dto.QuoteDto.QuoteDtoBuilder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-07-26T12:55:40+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 11.0.11 (AdoptOpenJDK)"
)
@Component
public class QuoteMapperImpl implements QuoteMapper {

    @Override
    public QuoteDto toDTO(Quote quote) {
        if ( quote == null ) {
            return null;
        }

        QuoteDtoBuilder quoteDto = QuoteDto.builder();

        quoteDto.isin( quote.getIsin() );
        quoteDto.bid( quote.getBid() );
        quoteDto.ask( quote.getAsk() );
        quoteDto.energyLevel( quote.getEnergyLevel() );

        return quoteDto.build();
    }

    @Override
    public Quote toModel(QuoteDto quoteDto) {
        if ( quoteDto == null ) {
            return null;
        }

        QuoteBuilder quote = Quote.builder();

        quote.isin( quoteDto.getIsin() );
        quote.bid( quoteDto.getBid() );
        quote.ask( quoteDto.getAsk() );
        quote.energyLevel( quoteDto.getEnergyLevel() );

        return quote.build();
    }
}
