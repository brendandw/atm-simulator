/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.helpers;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author brendandw
 */
public class SetComparator implements Comparator<Set<List<Integer>>> {
     
                  
    /*
        The comparator below sorts set of lists; 
     
    */
     
    @Override
    public int compare(Set<List<Integer>> set1, Set<List<Integer>> set2) {
        int res = 0;
        if(!set1.isEmpty() && !set2.isEmpty()){
            Iterator<List<Integer>> set1Iter = set1.iterator();
            Iterator<List<Integer>> set2Iter = set2.iterator();
            if(!set1.isEmpty() && !set2.isEmpty()){
                while(set1Iter.hasNext()&&set2Iter.hasNext()) {                
                    Iterator<Integer> list1Iter = set1Iter.next().iterator();
                    Iterator<Integer> list2Iter = set2Iter.next().iterator();                
                    while(list1Iter.hasNext()&&list2Iter.hasNext()) {
                        res =  list1Iter.next().compareTo(list2Iter.next());
                        if (res!=0) break;
                    }
                    
                    if (res==0)  {
                        if (!list1Iter.hasNext()) {
                            res =  -1;
                        }
                        else {
                            res = 1;
                        }
                    }
                } 
            }
            else if(!set1.isEmpty() && set2.isEmpty()){
                    res =  1;
                }
                else if(set1.isEmpty() && !set2.isEmpty()){
                    res =  -1;
                }
                else{
                    res =  0;
                }
            
        }
        return res;
    }               
}                       
