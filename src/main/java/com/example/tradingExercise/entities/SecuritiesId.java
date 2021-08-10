package com.example.tradingExercise.entities;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class SecuritiesId implements Serializable {

    private String securityIdentifier;
    private String tradingAccount;

}
