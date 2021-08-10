package com.example.tradingExercise.service;

import com.example.tradingExercise.entities.Securities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SecuritiesRepository extends JpaRepository<Securities,Integer> {

    @Transactional
     List<Securities> findByEventId(int eventId);

    @Transactional
    List<Securities> findByTradingAccountAndAndSecurityIdentifier(String tradingAcc,String securityIdentifier);
}
