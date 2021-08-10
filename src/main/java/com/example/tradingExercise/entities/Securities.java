package com.example.tradingExercise.entities;

import com.example.tradingExercise.enums.EventEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table
@Data
public class Securities implements Serializable {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private int id;
    private int eventId;
    private EventEnum event;
    private String securityIdentifier;
    private String tradingAccount;
    private int count;

    public Securities(){};

    public Securities(int eventId, String event, String securityIdentifier, String tradingAccount, int count) {
        this.eventId = eventId;
        this.event = EventEnum.getEvent(event);
        this.securityIdentifier = securityIdentifier;
        this.tradingAccount = tradingAccount;
        this.count = count;
    }

    @Override
    public String toString() {
        return "[Id :"+eventId+ ","+event.toString()+","+tradingAccount+","+securityIdentifier+","+count+"]";
    }
}
