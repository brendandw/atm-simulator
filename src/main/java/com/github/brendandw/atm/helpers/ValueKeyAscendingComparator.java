/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.helpers;

import java.util.Comparator;
import java.util.Map;

/**
 *
 * @author brendandw
 */
public class ValueKeyAscendingComparator implements Comparator{
    
    @Override
    public int compare(Object o1, Object o2) {
        int res =  ((Comparable) ((Map.Entry) (o1)).getValue())
                  .compareTo(((Map.Entry) (o2)).getValue());
        if (res != 0) {
            return res;
        }
        else
            return ((Comparable) ((Map.Entry) (o1)).getKey())
                  .compareTo(((Map.Entry) (o2)).getKey());
    }

    
}
