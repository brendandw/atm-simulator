/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * @author brendandw
 */

@Builder
@AllArgsConstructor
public final class Withdrawal {

    @Getter
    private Error error;
    @Getter
    private List<CashNotes> cashList;
    @Getter
    private int amount;
    @Getter
    private boolean successful;

    public Withdrawal(List<CashNotes> cashList ,Error error, boolean successful)
    {
        this.cashList = cashList;
        this.error = error;
        this.successful=successful;
    }
}
