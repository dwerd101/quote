package ru.securities.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "quote_history")
public class QuoteHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
 /*   @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "qoute_id", referencedColumnName = "isin")
    private Quote quoteId;*/
    @NotNull
    @Size(min = 12, max = 12)
    @Column(name = "isin")
    private String isin;
   // @NotNull
    @Column(name = "bid")
    private BigDecimal bid;
    @NotNull
    @Column(name = "ask")
    private BigDecimal ask;
    @NotNull
    @Column(name = "elv")
    private BigDecimal energyLevel;


}
