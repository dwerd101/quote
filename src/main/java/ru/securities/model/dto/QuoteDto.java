package ru.securities.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteDto {
    @NotNull
    @Size(min = 12, max = 12)
    private String isin;
    //@NotNull
    private BigDecimal bid;
    @NotNull
    private BigDecimal ask;
    private BigDecimal energyLevel;

}
