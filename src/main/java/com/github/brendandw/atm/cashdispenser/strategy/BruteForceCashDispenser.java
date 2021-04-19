/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.cashdispenser.strategy;

import com.github.brendandw.atm.interfaces.ICashDispenser;
import com.github.brendandw.atm.util.CashUtil;
import com.github.brendandw.atm.util.MapUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections4.list.TreeList;
import org.springframework.stereotype.Component;

/**
 *
 * @author brendandw
 *
 *  The Brute Force dispenser will, almost always, be less performant than
 *  the Dynamic Programming version. It has the advantage, however, of returning
 *  every possible combination. Thus, it is simple to effectively prioritise certain
 *  denominations. The Dynamic Programming algorithm can also prioritise certain
 *  denominations based on the way in which the input was sorted. It is, however, not as effective
 *  as the Brute Force Algorithm.
 *
 *  Hence, it makes sense to add as a strategy for small inputs.
 */

@Component
public class BruteForceCashDispenser implements ICashDispenser{

    @Override
    public Map<Integer,Map<Integer,Integer>> getCashCombination(Map<Integer, Integer> availableCash, int requestedAmount) {
        // remove redundant items from input in order to improve algorithm's performance
        Map<Integer, Integer> combinationInput = CashUtil.resizeInputMap(availableCash, requestedAmount);
        Map<Integer,Map<Integer,Integer>> returnMap = new HashMap<Integer,Map<Integer,Integer>>();
        List<Integer> flatInputList = MapUtil.getFlatListFromMap(combinationInput);
        Collections.sort(flatInputList);

        Set<List<Integer>> matchedCombinations = new HashSet<List<Integer>>();
        Map<List<Integer>,Integer> triedCombinations = new HashMap<>();
        triedCombinations.put(new ArrayList<Integer>(), 0);

        findAllCombinations(flatInputList,new ArrayList<Integer>(),requestedAmount,triedCombinations, matchedCombinations);

        if (matchedCombinations.isEmpty()) {
            Map<Integer,Set<List<Integer>>> closestMatchCombinations  = getClosestMatch(triedCombinations, requestedAmount);
            // if the map ever returns more than one value, then an unrecoverable programmatic error has occurred
            if (closestMatchCombinations.size()==1) {
                int closestAmount=closestMatchCombinations.keySet().iterator().next();
                Set<List<Integer>> closestComboSet = closestMatchCombinations.get(closestAmount);
                returnMap.put(closestAmount, getLowestVariance(closestComboSet, availableCash));
            }
        }
        else {
            returnMap.put(requestedAmount, getLowestVariance(matchedCombinations, availableCash));
        }
        return returnMap;
    }

    /*
      This method makes use of recursion, with a bit of memoisation, to find all of the combinations that
      sum up to a target value, given a specific input list.

      @param remainingCashList an ascendingly sorted Integer list containing the cash currently available in the ATM.
             N.B. It is essential to sort the list prior to passing it in as argument. If not, combinations will be lost
             as the recursion cycle will break as soon as the target value has been exceedeed
      @param sumList an empty Integer list that will be used internally by the recursive method
      @param target the amount requested by the customer for withdrawal
      @param triedCombos a map used internally by the method to store and recall (memoise)
        the sums of particular combinations. N.B. It is critical to put into the map, a single entry with an empty list as key,
        and 0 as value. Otherwise a null check would have had to be done at the start of the method, which would
        be executed on every recursion, thus negatively impacting performance.
      @param validCombos an empty list which will ultimately contain all of the combinations that can
      be summed together to reach the requested target - if empty, it means that no combinations are possible.

    */
    private  void findAllCombinations(List<Integer> remainingCashList, List<Integer> sumList,  int target,Map<List<Integer>,Integer> triedCombos,Set<List<Integer>> validCombos) {
           Integer sum= triedCombos.get(sumList);//

            for (int i=0;i<remainingCashList.size();i++) {
                int currentItem = remainingCashList.get(i);
                // Use treelist to automatically sort inputs
                List<Integer> sumListCopy = new TreeList<>(sumList);
                sumListCopy.add(currentItem);
                // memoize next item;
                Integer sumCopy = sum+currentItem;
                triedCombos.put(sumListCopy, sumCopy);

                // only enter another recursion cycle if the sum is less than the target
                if (sumCopy<target){
                    // setup remaining cash list
                    List<Integer> newRemainingList = new TreeList<>(remainingCashList.subList(i+1, remainingCashList.size()));

                    findAllCombinations(newRemainingList,sumListCopy,target,triedCombos,validCombos);
                }
                else if (sumCopy==target) {
                    validCombos.add(sumListCopy);
                    break;
                }
                else {
                    break;
                }
            }
    }

    // returns a map of all the combinations that make up the highest value less than the target
    private Map<Integer,Set<List<Integer>>> getClosestMatch(Map<List<Integer>,Integer> triedCombos,int target) {
        int highestValue = 0;
        Set<List<Integer>> listOfCombos = new HashSet<>();
        Map<Integer,Integer> combo = new HashMap<>();
        Map<Integer,Set<List<Integer>>> returnCombination = new HashMap<>();
        for (Map.Entry<List<Integer>,Integer> entry: triedCombos.entrySet()) {
            int currentVal = entry.getValue();
            if (currentVal<=target) {
                if (currentVal>=highestValue) {
                    highestValue=currentVal;
                }
            }
        }
        for (Map.Entry<List<Integer>,Integer> entry: triedCombos.entrySet()) {
            int currentVal = entry.getValue();
            if (currentVal==highestValue) {
                listOfCombos.add(entry.getKey());
            }
        }
        returnCombination.put(highestValue, listOfCombos);
        return returnCombination;
    }

    /*
        This method returns the combination that would result in the lowest variance between the different denominations
        still available in the ATM
    */
    private  Map<Integer,Integer> getLowestVariance(Set<List<Integer>> setOfCombinationLists,Map<Integer,Integer> cashCurrentlyInAtm ) {
        Double lowestDeviation=null;
        Map<Integer,Integer> bestCombination=null;
        for (List<Integer> currentCombination: setOfCombinationLists) {
            double totalNotesLeftInAtmAfterUse = 0;
            double numberOfDenominationsInAtm = cashCurrentlyInAtm.keySet().size();
            Map<Integer,Integer> cashLeftInAtm = new HashMap<Integer,Integer>(cashCurrentlyInAtm);
            for (Integer entry: currentCombination) {
                Integer denomination = entry;
                Integer valueBeforeAdjustment = cashLeftInAtm.get(denomination);
                Integer valueAfterAdjustment = valueBeforeAdjustment-1;
                cashLeftInAtm.put(denomination, valueAfterAdjustment);
            }
            //get mean
            for (Map.Entry<Integer,Integer> entry: cashLeftInAtm.entrySet()) {
                totalNotesLeftInAtmAfterUse+=entry.getValue();
            }
            double mean = totalNotesLeftInAtmAfterUse / numberOfDenominationsInAtm;
            double totalVariance = 0;
            String totalVarianceString="";
            //calculate variance
            for (Map.Entry<Integer,Integer> entry: cashLeftInAtm.entrySet()) {
                double denominationLeftAfterUse = entry.getValue();
                double deviation = denominationLeftAfterUse-mean;
                double deviationSquared = deviation*deviation;
                totalVariance +=deviationSquared;
            }
            lowestDeviation=lowestDeviation==null?totalVariance:lowestDeviation;
            if (totalVariance<=lowestDeviation) {
                lowestDeviation=totalVariance;
                bestCombination= MapUtil.getMapFromList(currentCombination);
            }
        }
    return bestCombination;
    }





}
