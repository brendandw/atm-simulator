/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.util;

import com.github.brendandw.atm.dto.CashNotes;
import com.github.brendandw.atm.dto.CashNotes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author brendandw
 *
 * util class
 */
public abstract class CashUtil {
            


    /*
     * Resize input map to contain only the necessary amount of items, to thereby improve performance.
     *
     * Explanation: If the value of the sum of any individual denomination exceeds the withdrawal target, then all
     * items greater than the target can be discarded as it would not affect possible combinations.
     *
     * for example: If the input map contains 12*$10 and 7*$20 and the withdrawal target is $100. then the following
     * combinations are possible
     * 10*10=100; 10*8+20*1=100; 10*6+20*2=100; 10*4+20*3=100; 10*2+20*4=100; 20*5=100;
     *
     * Thus, reducing the input map to 10*$10 and 5*$20, would result in the exact same combinations
     *
     * @param inputMap - Map representing the available cash in the  ATM
     * @param target - Amount requested to withdraw
     * @return List of Map objects
     */
    public static Map<Integer,Integer>  resizeInputMap(Map<Integer,Integer> inputMap, int target) {
        return inputMap.entrySet()
                .stream()
                .collect(Collectors.toMap(e->e.getKey(),
                        e->e.getValue()>target/e.getKey()?target/e.getKey():e.getValue()));
    }
    
    public static List<CashNotes> convertMapToListOfCashNotes(Map<Integer,Integer> inputMap)  {
         return inputMap.entrySet().stream()
                 .map(e->new CashNotes(e.getKey(),e.getValue()))
                .collect(Collectors.toList());
    }
    
    public static Map<Integer,Integer> convertListOfCashNotesToMap(List<CashNotes> inputList)  {
        return inputList.stream()
                .collect(Collectors.toMap(CashNotes::getDenomination,CashNotes::getAmountOfNotes));
    }
    
    
    
    
    
    
    
 
}
