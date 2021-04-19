/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.testutils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections4.list.TreeList;

/**
 *
 * @author brendandw
 * This class is used to simplify the construction of test objects, and will not be included in the final artefact.
 */
public class TestUtils {
    
    public static List<Map<Integer,Integer>> getMapList(String[] inputList, String comma,String semiColon) {        
        List<Map<Integer,Integer>> returnList = new ArrayList<>();
        for (int i=0;i<inputList.length;i++) {
            String comboList = inputList[i];
            comboList=comboList.replace(" ", "");
        
            Map<Integer,Integer> comboMap = new LinkedHashMap<>();
            String[] combinations =  comboList.split(semiColon);
            for (int j=0;j<combinations.length;j++) {
                String[] keyValue = combinations[j].split(comma);
                Integer key = Integer.parseInt(keyValue[0]);
                Integer val = Integer.parseInt(keyValue[1]);

                comboMap.put(key, val);
            }
            returnList.add(comboMap);
        }
        return returnList;
    }
        
    public static List<List<Integer>> getListOfLists(String[] inputList, String comma,String semiColon) {        
        List<List<Integer>> returnList = new ArrayList<>();
        for (int i=0;i<inputList.length;i++) {
            String comboList = inputList[i];
            comboList=comboList.replace(" ", "");
        
            List<Integer> innerList = new TreeList<>();
            String[] combinations =  comboList.split(semiColon);
            for (int j=0;j<combinations.length;j++) {
                String[] keyValue = combinations[j].split(comma);
                Integer key = Integer.parseInt(keyValue[0]);
                Integer val = Integer.parseInt(keyValue[1]);
                for (int k=0;k<val;k++) {
                    innerList.add(key);
                }
            }
            returnList.add(innerList);
        }
        return returnList;
    }
    
    public static Set<List<Integer>> getSetOfLists(String[] inputList, String comma,String semiColon) {        
        Set<List<Integer>> returnSet = new HashSet<>();
        for (int i=0;i<inputList.length;i++) {
            String comboList = inputList[i];
            comboList=comboList.replace(" ", "");
        
            List<Integer> innerList = new TreeList<>();
            String[] combinations =  comboList.split(semiColon);
            for (int j=0;j<combinations.length;j++) {
                String[] keyValue = combinations[j].split(comma);
                Integer key = Integer.parseInt(keyValue[0]);
                Integer val = Integer.parseInt(keyValue[1]);
                for (int k=0;k<val;k++) {
                    innerList.add(key);
                }
            }
            returnSet.add(innerList);
        }
        return returnSet;
    }
    
    public static List<Integer> getIntegerList(String list, String RESplitter)  {
        list=list.replace(" ", "");
        List<Integer> integerList = new ArrayList<>();
        String[] stringList = list.split(RESplitter);
        for (int i=0;i<stringList.length;i++) {
            integerList.add(Integer.parseInt(stringList[i]));
        }
        return integerList;
    }
    
    public static Comparator<Map<Integer, Integer>> mapComparator = 
            new Comparator<Map<Integer, Integer>>() {
                public int compare(Map<Integer, Integer> m1, Map<Integer, Integer> m2) {
                    return (new Integer(m1.hashCode())).compareTo((new Integer(m2.hashCode())));
                }
            };
    
    public static Comparator<List<Integer>> treeListComparator = 
            new Comparator<List<Integer>>() {
                public int compare(List<Integer> m1, List<Integer> m2) {
                    return (new Integer(m1.hashCode())).compareTo(new Integer(m2.hashCode()));
                }
            };
    
    

    
}


