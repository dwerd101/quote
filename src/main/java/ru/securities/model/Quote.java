package ru.securities.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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
    //@NotNull
    @Column(name = "bid")
    private BigDecimal bid;
    @NotNull
    @Column(name = "ask")
    private BigDecimal ask;
   /* @OneToOne(mappedBy = "quote", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private EnergyLevel energyLevel;*/
    @NotNull
    @Column(name = "elv")
   private BigDecimal energyLevel;
 /*   @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "quoteId")
    private Set<QuoteHistory> quoteHistorySet;
*/

}
