package ru.securities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;
import ru.securities.model.Quote;
import ru.securities.model.QuoteHistory;

import java.util.Optional;

@Repository
public interface QuouteHistoryRepository extends JpaRepository<QuoteHistory,Long> {
    Optional<QuoteHistory> findByIsin(String isin);
}
