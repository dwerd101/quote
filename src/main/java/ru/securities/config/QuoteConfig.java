package ru.securities.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.securities.algorithm.QuoteCountAlgorithm;
import ru.securities.algorithm.QuoteHistoryCountAlgorithm;

@Configuration
public class QuoteConfig {

    @Bean
    public QuoteCountAlgorithm quoteCountAlgorithm() {
        return new QuoteCountAlgorithm();
    }
    @Bean
    public QuoteHistoryCountAlgorithm quoteHistoryCountAlgorithm() {
        return new QuoteHistoryCountAlgorithm();
    }
}
