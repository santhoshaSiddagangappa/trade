package com.example.tradingExercise.entities;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Data
public class SecurityTrade implements Serializable {

    @EmbeddedId
    private SecuritiesId id;
    private int count;

    public SecurityTrade(){
    }

    public SecurityTrade(Securities security) {
        this.id = new SecuritiesId();
        this.id.setSecurityIdentifier(security.getSecurityIdentifier());
        this.id.setTradingAccount(security.getTradingAccount());
    }

    public SecurityTrade(String trade,String security,int count) {
        this.id = new SecuritiesId();
        this.id.setSecurityIdentifier(security);
        this.id.setTradingAccount(trade);
        this.count = count;
    }

    @Override
    public String toString() {
        return  id.getTradingAccount() + " " + id.getSecurityIdentifier()+
                " " + count;
    }
}
