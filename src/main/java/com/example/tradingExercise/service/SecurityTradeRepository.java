package com.example.tradingExercise.service;

import com.example.tradingExercise.entities.SecuritiesId;
import com.example.tradingExercise.entities.SecurityTrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityTradeRepository extends JpaRepository<SecurityTrade, SecuritiesId> {

}
