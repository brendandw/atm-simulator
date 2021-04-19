/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.util;

import com.github.brendandw.atm.helpers.ListComparator;
import com.github.brendandw.atm.helpers.MapComparator;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

/**
 *
 * @author brendandw
 */
public class MapUtil {
    
    public static Map<Integer,Integer> sortMapAscByValueThenKey(final Map<Integer,Integer> inputMap) {
        return inputMap.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue()
                        .thenComparing(Map.Entry.comparingByKey()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    public static Map<Integer,Integer> sortMapDescByValueThenKey(final Map<Integer,Integer> inputMap) {
                return inputMap.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue()
                        .thenComparing(Map.Entry.comparingByKey()).reversed())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    public static List<Map<Integer,Integer>> sortListOfUnsortedMapsAscendingly(final List<Map<Integer,Integer>> mapList) {
        return mapList.stream().sorted(new MapComparator()).collect(toList());
    }

    /*
        @return a transformed list containing all input items supplied in the input map.
        For example, the following map entry <20,6> will yield the following result: 20,20,20,20,20,20
     */
    public static List<Integer> getFlatListFromMap(final Map<Integer,Integer> input) {
        return input.entrySet().stream()
                .flatMap(es->IntStream.rangeClosed(1,es.getValue())
                        .mapToObj(index->es.getKey()))
                .collect(toList());
    }

    /*
       @return a transformed input - 20,20,20,20,20,20 - will yield the following result <20,6>:
    */
    public static Map<Integer,Integer> getMapFromList(final List<Integer> input) {
        return input.stream()
                .collect(groupingBy(m -> m,countingInt()));
    }
    
    public static Set<List<Integer>> sortSetOfUnsortedListsAscendingly(final Set<List<Integer>> listSet) {
        return listSet.stream()
                .map(innerList->innerList.stream().sorted(Integer::compareTo).collect(toList()))
                .sorted(new ListComparator())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
    
    public static List<List<Integer>> sortListOfUnsortedListsAscendingly(final List<List<Integer>> InputList) {
        return InputList.stream()
                .map(innerList->innerList.stream().sorted(Integer::compareTo).collect(toList()))
                .sorted(new ListComparator())
                .collect(toList());
    }

    public static <T> Collector<T, ?, Integer> countingInt() {
        return reducing(0, (var0) -> 1, Integer::sum);
    }

    
    

    
    
    
}
