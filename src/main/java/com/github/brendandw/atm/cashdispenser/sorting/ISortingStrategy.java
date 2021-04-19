/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.cashdispenser.sorting;

import java.util.Map;

/**
 *
 * @author brendandw
 */
public interface ISortingStrategy {
    
    public int[] sort(Map<Integer,Integer> denominations);   
    
}
