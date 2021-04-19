/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.cashdispenser.sorting;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author brendandw
 */
public class HighestDenominationSorter implements ISortingStrategy {

     /*
        The below method returns a map which is sorted based on the denomination with the highest <strong>remaining</strong>
        occurrence. It does so in the following manner:

        Iterate through the input N times, where N equals the total amount of notes in the map.
        Upon each iteration, remove the note with the highest occurrence from the map, and add to the return array.

        @param denominations a map of Integers containing pairs of denominations and the amounts thereof
        @return an integer array sorted according to the denominations with the highest occurence first. Note that
        a "dummy" value of 0 is prepended to the array for use in the Dynamic Programming cash dispenser.
    */
     public int[] sort(final Map<Integer,Integer> denominations) {

        int totalIterations=0;
        // create copy of Map
        Map<Integer,Integer> denomsCopy = new LinkedHashMap<>(denominations);
        for (Map.Entry<Integer,Integer> entry: denomsCopy.entrySet()) {
            totalIterations+=entry.getValue();
        }

        int sortedArray[] = new int[totalIterations+1];
        sortedArray[0]=0;// add a dummy value to the array to aid the DP algorithm later on.


        for (int i=0;i<totalIterations;i++) {
            int maxItem=0;
            int maxKey=0;
            for (Map.Entry<Integer,Integer> entry: denomsCopy.entrySet()) {
                int amountOfNotes = entry.getValue();
                if (amountOfNotes>maxItem) {
                    maxItem=amountOfNotes;
                    maxKey=entry.getKey();
                }
            }
            // first item is a dummy "0". Thus, i+1
            sortedArray[i+1]=maxKey;
            //maxItem--;
            // update map to decrease the amount of the denomination we've just selected
            denomsCopy.put(maxKey, maxItem-1);
        }
        return sortedArray;

    }

}
