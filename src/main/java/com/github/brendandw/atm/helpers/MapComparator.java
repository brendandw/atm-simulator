/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.helpers;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

 public class MapComparator implements Comparator<Map<Integer, Integer>> {
     
     private static final String DOT_STRING = ".";
     private static final String EMPTY_STRING = ".";
                   
    /*
        The comparator below sorts a list of maps; first by value, then by key 
     
     */
     
    @Override
    public int compare(Map<Integer, Integer> m1, Map<Integer, Integer> m2) {
        Collection<Integer> values1 = m1.values();
        Collection<Integer> values2 = m2.values();
        Collection<Integer> keys1 = m1.keySet();
        Collection<Integer> keys2 = m2.keySet();
        int res = 0;
        if(!values1.isEmpty() && !values2.isEmpty()){
            Iterator<Integer> values1Iter = values1.iterator();
            Iterator<Integer> values2Iter = values2.iterator();
            while(values1Iter.hasNext()&&values2Iter.hasNext()) {
                res =  values1Iter.next().compareTo(values2Iter.next());
                if (res!=0) break;
            }
            if (res==0)  {
                if (!values1Iter.hasNext()) {
                    res =  -1;
                }
                else {
                    res = 1;
                }
            }
            
        }
        else if(!values1.isEmpty() && values2.isEmpty()){
            res =  1;
        }
        else if(values1.isEmpty() && !values2.isEmpty()){
            res =  -1;
        }
        else{
            res =  0;
        }
        
        if (res!=0) return res;
        else {
            Iterator<Integer> keys1Iter = keys2.iterator();
            Iterator<Integer> keys2Iter = keys2.iterator();
            if (!keys1.isEmpty() && !keys2.isEmpty())  {
                while(keys1Iter.hasNext()&&keys2Iter.hasNext()) {
                    res =  keys1Iter.next().compareTo(keys2Iter.next());
                    if (res!=0) {
                        break;
                    }
                }
                if (res==0)  {
                    if (!keys1Iter.hasNext()) {
                        res =  -1;
                    }
                    else {
                        res = 1;
                    }
                }
            } 
            else if(!keys1.isEmpty() && keys2.isEmpty()){
            res =  1;
            }
            else if(keys1.isEmpty() && !keys2.isEmpty()){
                res =  -1;
            }
            return 0;
        }
    }                       
}
