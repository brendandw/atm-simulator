/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.helpers;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author brendandw
 */
public class ListComparator implements Comparator<List<Integer>> {
    

    @Override
    public int compare(List<Integer>list1, List<Integer> list2) {
        
        int res = 0;
        if(!list1.isEmpty() && !list2.isEmpty()){            
            Iterator<Integer> values1Iter = list1.iterator();
            Iterator<Integer> values2Iter = list2.iterator();
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
            if (res==0)  {
                if (!values1Iter.hasNext()) {
                    res =  -1;
                }
                else {
                    res = 1;
                }
            }
            
        }
        else if(!list1.isEmpty() && list2.isEmpty()){
            res =  1;
        }
        else if(list1.isEmpty() && !list2.isEmpty()){
            res =  -1;
        }
        else{
            res =  0;
        }
        return res;
    }
                           

    
}
