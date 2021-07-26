package ru.securities.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.securities.model.Quote;
import ru.securities.model.dto.QuoteDto;

@Mapper(componentModel = "spring")
//@Mapper
public interface QuoteMapper extends SecuritiesMapper<Quote, QuoteDto> {

    @Mappings({
            @Mapping(target = "isin", source = "quote.isin"),
            @Mapping(target = "bid", source = "quote.bid"),
            @Mapping(target = "ask", source = "quote.ask")
    })
    QuoteDto toDTO(Quote quote);
    @Mappings({
            @Mapping(target = "isin", source = "quoteDto.isin"),
            @Mapping(target = "bid", source = "quoteDto.bid"),
            @Mapping(target = "ask", source = "quoteDto.ask")
    })
    Quote toModel(QuoteDto quoteDto);
}
