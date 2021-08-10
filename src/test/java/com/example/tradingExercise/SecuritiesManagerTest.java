package com.example.tradingExercise;

import com.example.tradingExercise.entities.SecurityTrade;
import com.example.tradingExercise.exceptions.InvalidCancelTradeException;
import com.example.tradingExercise.exceptions.InvalidSellTradeException;
import com.example.tradingExercise.service.SecuritiesRepository;
import com.example.tradingExercise.service.SecurityTradeRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class SecuritiesManagerTest {

    @InjectMocks
    private SecuritiesManager securitiesManager;

    @Mock
    private SecuritiesRepository securitiesRepository;

    @Mock
    private SecurityTradeRepository tradeRepository;

    @BeforeEach
    void setup()
    {

    }

    @Test
    public void testProcessEvents() throws InvalidSellTradeException, InvalidCancelTradeException {
        doReturn(getOutput()).when(tradeRepository).findAll();
        List<SecurityTrade> securityTradeList = securitiesManager.processEvent(getInput());
        assertEquals(3,securityTradeList.size());
        assertTrue(securityTradeList.stream().
                filter(s->(s.getId().getTradingAccount().equals("ACC1"))
                        && s.getId().getSecurityIdentifier().equals("SEC1")).findFirst().isPresent());
        assertEquals(32,securityTradeList.stream().
                filter(s->(s.getId().getTradingAccount().equals("ACC1"))
                        && s.getId().getSecurityIdentifier().equals("SEC1")).findFirst().get().getCount());
    }

    @Test(expected = InvalidSellTradeException.class)
    public void testInvalidSellTradeException() throws InvalidSellTradeException, InvalidCancelTradeException {
        List<String> list = new ArrayList() {
            {
                add("1 SELL ACC1 SEC1 100");
            }
        };
        List<SecurityTrade> securityTradeList = securitiesManager.processEvent(list);
    }


    @Test(expected = InvalidCancelTradeException.class)
    public void testInvalidCancelTradeException() throws InvalidSellTradeException, InvalidCancelTradeException {
        List<String> list = new ArrayList() {
            {
                add("1 CANCEL ACC1 SEC1 100");
            }
        };
        List<SecurityTrade> securityTradeList = securitiesManager.processEvent(list);
    }

    List<String> getInput()
    {
        return  new ArrayList() {
            {
                add("3 BUY ACC1 SEC1 12");
                add("4 BUY ACC1 SECXYZ 50");
                add("5 BUY ACC2 SECXYZ 33");
                add("6 BUY ACC1 SEC1 20");

            }
        };
    }

    List<SecurityTrade> getOutput()
    {
        return new ArrayList()
        {
            {
                add(new SecurityTrade("ACC1","SEC1",32));
                add(new SecurityTrade("ACC1","SECXYZ",50));
                add(new SecurityTrade("ACC2","SECXYZ",33));
            }
        };

    }
}
