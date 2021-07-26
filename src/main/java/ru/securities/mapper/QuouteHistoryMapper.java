package ru.securities.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.securities.model.Quote;
import ru.securities.model.QuoteHistory;
import ru.securities.model.dto.QuoteDto;

@Mapper(componentModel = "spring")
public interface QuouteHistoryMapper extends SecuritiesMapper<QuoteHistory,QuoteDto> {
    @Mappings({
            @Mapping(target = "isin", source = "quote.isin"),
            @Mapping(target = "bid", source = "quote.bid"),
            @Mapping(target = "ask", source = "quote.ask")
    })
    QuoteDto toDTO(QuoteHistory quote);
    @Mappings({
            @Mapping(target = "isin", source = "quoteDto.isin"),
            @Mapping(target = "bid", source = "quoteDto.bid"),
            @Mapping(target = "ask", source = "quoteDto.ask")
    })
    QuoteHistory toModel(QuoteDto quoteDto);
}
