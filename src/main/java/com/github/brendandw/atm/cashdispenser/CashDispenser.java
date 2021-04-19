/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.cashdispenser;

import com.github.brendandw.atm.cashdispenser.factory.CashDispenserFactory;
import com.github.brendandw.atm.exceptions.TechnicalException;
import com.github.brendandw.atm.util.CashUtil;
import java.util.Map;

import com.github.brendandw.atm.cashdispenser.factory.CashDispenserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author brendandw
 */
@Component
public class CashDispenser {


    @Autowired
    CashDispenserFactory cashDispenserFactory;

    // Maximum Number of notes in ATM before switching to the less performant brute force algorithm
    @Value("${bruteforce.cutoff}")
    private Integer BRUTE_FORCE_CUT_OFF;


    public Map<Integer,Map<Integer,Integer>> getCashCombination(Map<Integer, Integer> availableCash, int requestedAmount) throws TechnicalException {
        try {
            /*  The Brute Force dispenser will overwhelmingly be less performant than
             *  the Dynamic Programming version.
             *
             *  The brute force version, however, has the advantage of more accurately prioritising certain denominations.
             *
             *  For example, if the cash dispenser is running low on $10 notes, we want to dispense as few of these as
             *  possible, so that we can continue to service the highest amount of withdrawal requests.
             *
             *  Brute force makes this possible because it returns every possible combination, which allows us to verify
             *  the effect that every combination would have on the remaining cash in the ATM.
             *
             *  The Dynamic Programming algorithm can also prioritise certain
             *  denominations based on the way the input is sorted, but is not as effective as the
             *  brute force algorithm in this regard.
             */

            int totalItems = availableCash.values().stream().mapToInt(Integer::intValue).sum();
            if (totalItems<BRUTE_FORCE_CUT_OFF) {
                return cashDispenserFactory.getCashDispenser(CashDispenserFactory.DispenserType.BRUTE_FORCE).getCashCombination(availableCash, requestedAmount);
            } else {
                return cashDispenserFactory.getCashDispenser(CashDispenserFactory.DispenserType.DYNAMIC_PROGRAMMING).getCashCombination(availableCash, requestedAmount);
            }
        } catch(ClassNotFoundException e) {
            throw new TechnicalException("Unable to load class that provides dispensing algorithm");
        }
    }


}
