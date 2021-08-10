package com.example.tradingExercise;

import com.example.tradingExercise.entities.Securities;
import com.example.tradingExercise.entities.SecurityTrade;
import com.example.tradingExercise.enums.EventEnum;
import com.example.tradingExercise.exceptions.InvalidCancelTradeException;
import com.example.tradingExercise.exceptions.InvalidSellTradeException;
import com.example.tradingExercise.service.SecuritiesRepository;
import com.example.tradingExercise.service.SecurityTradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class SecuritiesManager {

    @Autowired
    private SecuritiesRepository securitiesRepository;

    @Autowired
    private SecurityTradeRepository tradeRepository;


     synchronized List<SecurityTrade> processEvent(List<String> events) throws InvalidSellTradeException, InvalidCancelTradeException {
        for (String event: events) {
            Securities security = getSecurityBean(event);
            switch (security.getEvent())
            {
                case BUY:
                {
                    buyEvent(security);
                    break;
                }
                case SELL:
                {
                    sellEvent(security);
                    break;
                }
                case CANCEL:
                {
                    cancelEvent(security);
                    break;
                }
                default:

            }
        }
        return getSecurityTradeDetails();
    }

     List<SecurityTrade> getSecurityTradeDetails() {
         return tradeRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    synchronized  void buyEvent(Securities security)
    {
        securitiesRepository.save(security);
        SecurityTrade trade = new SecurityTrade(security);
        Optional<SecurityTrade> tempTrade = tradeRepository.findById(trade.getId());
        if(tempTrade.isPresent())
        {
            int total = tempTrade.get().getCount()+security.getCount();
            tempTrade.get().setCount(total);
            tradeRepository.save(tempTrade.get());
        }
        else{
            trade.setCount(security.getCount());
            tradeRepository.save(trade);
        }
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    synchronized  void sellEvent(Securities security) throws InvalidSellTradeException {
        securitiesRepository.save(security);
        SecurityTrade trade = new SecurityTrade(security);
        Optional<SecurityTrade> tempTrade = tradeRepository.findById(trade.getId());
        if(tempTrade.isPresent())
        {
            int total = tempTrade.get().getCount()-security.getCount();
            tempTrade.get().setCount(total);
            tradeRepository.save(tempTrade.get());
        }
        else{
            throw new InvalidSellTradeException("No Valid trade security to Sell");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    synchronized  void cancelEvent(Securities security) throws InvalidCancelTradeException {
        List<Securities> securityList = securitiesRepository.findByEventId(security.getEventId());
        if (!CollectionUtils.isEmpty(securityList)) {
            securitiesRepository.save(security);
            SecurityTrade trade = new SecurityTrade(securityList.get(0));
            Optional<SecurityTrade> tempTrade = tradeRepository.findById(trade.getId());
            if (tempTrade.isPresent()) {
                int total = tempTrade.get().getCount() - securityList.get(0).getCount();
                tempTrade.get().setCount(total);
                tradeRepository.save(tempTrade.get());
            } else {
                throw new InvalidCancelTradeException("No Valid trade security to Cancel");
            }
        }else {
            throw new InvalidCancelTradeException("No Valid trade security to Cancel");
        }
    }

    Securities getSecurityBean(String event)
    {
        String[] arr = event.split(" ");
        return new Securities(Integer.valueOf(arr[0]),arr[1],arr[2],arr[3],Integer.valueOf(arr[4]));
    }

    List<Securities> getOutputPosition(SecurityTrade trade)
    {
        return securitiesRepository.findByTradingAccountAndAndSecurityIdentifier(trade.getId().getTradingAccount(),trade.getId().getSecurityIdentifier());
    }
}
