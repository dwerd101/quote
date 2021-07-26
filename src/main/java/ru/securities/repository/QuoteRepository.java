package ru.securities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.securities.model.Quote;
import ru.securities.model.QuoteHistory;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
    Optional<Quote> findByIsin(String isin);
    List<Quote> findAllByIsin(String isin);
}
