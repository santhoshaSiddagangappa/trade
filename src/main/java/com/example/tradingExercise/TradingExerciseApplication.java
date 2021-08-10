package com.example.tradingExercise;

import com.example.tradingExercise.entities.Securities;
import com.example.tradingExercise.entities.SecurityTrade;
import com.example.tradingExercise.exceptions.InvalidCancelTradeException;
import com.example.tradingExercise.exceptions.InvalidSellTradeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TradingExerciseApplication implements CommandLineRunner {

    @Autowired
    private SecuritiesManager securitiesManager;

    public static void main(String[] args) {
        SpringApplication.run(TradingExerciseApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        processEvents();
    }


    private void processEvents() {
        System.out.println("Picking up the events : ");
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                    List<SecurityTrade> list = securitiesManager.processEvent(getInput4());
                    System.out.println("Output positions : ");
                    for (SecurityTrade trade : list) {
                        System.out.println(trade.toString());
                        List<Securities> securitiesList = securitiesManager.getOutputPosition(trade);
                        securitiesList.stream().forEach(System.out::println);
                    }
                } catch (InvalidSellTradeException e) {
                    System.out.println("" + e.getDescription());
                } catch (InvalidCancelTradeException e) {
                    System.out.println("" + e.getDescription());
                }
                }
            });
            t1.start();

    }

    private List<String> getInput1() {
        return new ArrayList() {
            {
                add("1 BUY ACC1 SEC1 100");
                add("2 BUY ACC1 SEC1 50");
            }
        };
    }

    private List<String> getInput2() {
        return new ArrayList() {
            {
                add("3 BUY ACC1 SEC1 12");
                add("4 BUY ACC1 SECXYZ 50");
                add("5 BUY ACC2 SECXYZ 33");
                add("6 BUY ACC1 SEC1 20");

            }
        };
    }

    private List<String> getInput3() {
        return new ArrayList() {
            {
                add("10 BUY ACC1 SEC1 100");
                add("11 SELL ACC1 SEC1 50");
            }
        };
    }

    private List<String> getInput4() {
        return new ArrayList() {
            {
                add("21 BUY ACC1 SEC1 100");
                add("21 CANCEL ACC1 SEC1 0");
                add("22 BUY ACC1 SEC1 5");
            }
        };
    }

}