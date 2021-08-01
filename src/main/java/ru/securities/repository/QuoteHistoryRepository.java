package ru.securities.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;
import ru.securities.model.Quote;
import ru.securities.model.QuoteHistory;

import java.util.Optional;

@Repository
public interface QuoteHistoryRepository extends JpaRepository<QuoteHistory,Long> {
    //Optional<QuoteHistory> findByIsin(String isin);
    @Query(nativeQuery = true, value = "SELECT * FROM  quote_history where isin=?1 ORDER BY date DESC LIMIT 1;")
    Optional<QuoteHistory> findTopByIsinDesc(String isin);
}
