/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.interfaces;

import com.github.brendandw.atm.exceptions.TechnicalException;

import java.util.Map;

/**
 *
 * @author brendandw
 */
public interface ICashDispenser {
    
    /*
     @param availableCash The ATM model containing all of the cash currently available in the ATM
     @param requestedAmount The amount that the client requested to withdraw from the ATM
     @returns A Map containing the following:
      key: the available amount (this could be less than the requested amount
           if the ATM does not have all the required notes to reach the requested amount. In this case
           the key will be the largest possible amount that is less than the requested amount)
      value: A map with denominations as keys, and amounts as values. For example [20,4] means
             4 * $20 notes.
    */
    Map<Integer,Map<Integer,Integer>> getCashCombination (Map<Integer,Integer> availableCash, int requestedAmount) throws TechnicalException;
    
}
