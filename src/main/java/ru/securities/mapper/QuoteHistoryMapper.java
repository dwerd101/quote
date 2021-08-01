package ru.securities.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import ru.securities.model.Quote;
import ru.securities.model.QuoteHistory;
import ru.securities.model.dto.QuoteDto;

import java.time.ZonedDateTime;

@Mapper(componentModel = "spring", imports = {ZonedDateTime.class})
public interface QuoteHistoryMapper extends SecuritiesMapper<QuoteHistory,QuoteDto> {
    @Mappings({
            @Mapping(target = "isin", source = "quote.isin"),
            @Mapping(target = "bid", source = "quote.bid"),
            @Mapping(target = "ask", source = "quote.ask")
    })
    QuoteDto toDTO(QuoteHistory quote);
    @Mappings({
            @Mapping(target = "isin", source = "quoteDto.isin"),
            @Mapping(target = "bid", source = "quoteDto.bid"),
            @Mapping(target = "ask", source = "quoteDto.ask"),
            @Mapping(target = "zoneDateTime", expression = "java(ZonedDateTime.now())")
    })
    QuoteHistory toModel(QuoteDto quoteDto);


}
