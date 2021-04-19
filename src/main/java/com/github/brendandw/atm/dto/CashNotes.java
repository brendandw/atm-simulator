/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 *
 * @author brendandw
 */
@Builder
@AllArgsConstructor
public final class CashNotes {

    @Getter
    private int denomination;
    @Getter
    private int amountOfNotes;

}
