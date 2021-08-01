package ru.securities.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "quote")
public class Quote {
    @Id
    @NotNull
    @Size(min = 12, max = 12)
    @Column(name = "isin")
    private String isin;

    @Column(name = "bid")
    private BigDecimal bid;
    @NotNull
    @Column(name = "ask")
    private BigDecimal ask;

    @NotNull
    @Column(name = "elv")
   private BigDecimal energyLevel;


}
