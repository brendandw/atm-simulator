/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.brendandw.atm.cashdispenser.strategy;

import com.github.brendandw.atm.cashdispenser.sorting.ISortingStrategy;
import com.github.brendandw.atm.cashdispenser.sorting.SortingFactory;
import com.github.brendandw.atm.exceptions.TechnicalException;
import com.github.brendandw.atm.interfaces.ICashDispenser;
import com.github.brendandw.atm.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author brendandw
 */
@Component
public class DPCashDispenser implements ICashDispenser {

    private static String ZERO_EXPECTED_ERROR = "Expected the first item of the input the array to be 0";
    ISortingStrategy sorter;

    @Autowired
    SortingFactory sortingFactory;

    /*
         This method accepts as input the cash available in the ATM, as well as the requested withdrawal amount.
         It subsequently determines whether it is possible to return the requested amount to the customer by constructing
         a Dynamic Programming-style matrix. If it is possible to return the requested amount, it returns the combination
         of notes that sum up to this amount, if not, it returns the first possible amount that is less than the
         requested amount, as well as the combination that sums up to this amount

         @param availableCash a map with denominations as key, and the amount of them available in the ATM, as value.
         @param requestedAmount the amount the user requested to withdraw
         @returns a map with the key that represents the total amount of cash that is returned (the target amount, or
         if it is not available, then the largest possible amount less than the target), and another map as value contains
         the denominations as key, and their amounts as value.
     */
    public  Map<Integer,Map<Integer,Integer>> getCashCombination(final Map<Integer,Integer> availableCash,
                                                                 final int requestedAmount) throws TechnicalException {
        // in the future, it may be required to prioritise different denominations based on the input.
        // for example, when cash reserves in the ATM start to run low, it would make sense to attempt to preserve the
        // lower denominations, as they can be used to make up a greater variety of sums, than higher denominations.
        // Hence the reason for adding a factory here - to programmatically define the sorting strategy.
        sorter = sortingFactory.getSorter(SortingFactory.SortingType.HIGHEST_DENOMINATION_SORTER);


        // Sort the inputs so as to preserve low-running denominations (i.e. prioritise denominations with higher
        // supply). This should not happen here as it increases the time complexity of the withdrawal method
        // TODO Move sorting of inputs to happen asynchronously after each withdrawal to improve speed of withdrawals
        int[] sortedInput = sorter.sort(availableCash);

        Map<Integer,Map<Integer,Integer>> returnMap = new HashMap<>();
        boolean [][] matrix = setupDPMatrix(sortedInput,requestedAmount);
        boolean isRequestedAmountAvailable = isCombinationPossible(matrix);
        List<Integer> returnList=getCombination(matrix, sortedInput,requestedAmount,isRequestedAmountAvailable);

        Map<Integer,Integer> combinationMap = MapUtil.getMapFromList(returnList);

        if (isRequestedAmountAvailable) {
            returnMap.put(requestedAmount, combinationMap);
        } else {
            int closestTarget = returnList.stream().mapToInt(Integer::intValue).sum();
            returnMap.put(closestTarget, combinationMap);
        }

        return returnMap;
    }

    /*
        This method constructs a Dynamic Programming-style
        matrix. The last item in the matrix returns true or false and indicates whether
        the requested amount can be dispensed, given all the cash that is available in the ATM.

        If true, the matrix will be constructed based on the first possible combination
        in relation to the order of the input array. Thus, if certain denominations are to be
        prioritised, the array should be accordingly sorted prior to passing it into this method

        If false, the highest true value in the last row of the matrix represents the first possible combination
        (based on the order of the input items) that sums up to the corresponding column value.


        @param inputDenoms an array of integers representing all notes available in the ATM. N.B. the first item in the
        array  must be 0.
        @param target the requested amount to withdraw.
        @return a two dimensional boolean array - the last item in the matrix returns
        true or false and indicates whether the requested amount can be dispensed or not.
    */
    private  boolean[][] setupDPMatrix(final int[] inputDenoms, final int target) throws TechnicalException {
        if (inputDenoms[0]!=0) {
            throw new TechnicalException(ZERO_EXPECTED_ERROR);
        }

        int items=inputDenoms.length-1;
        boolean matrix[][] = new boolean[items+1][target+1];
        // intitialise the matrix
        for (int i=0; i<=items; i++) {
            // set entire first column to true - amount of 0 can always be made by not selecting any items
            matrix[i][0] = true;
        }
        for (int j=1; j<=target; j++) {
            // set first row (bar item 0) to false - no amount, except for 0, can be made by selecting 0 items.
            matrix[0][j] = false;
        }

        for (int j=1; j<=target; j++) { // columns
            for (int i=1; i<=items; i++){ // rows
                if (inputDenoms[i]>j){ // we cannot use input[i]
                    matrix[i][j] = matrix[i-1][j];
                } else { // we can use input[i]
                    matrix[i][j] = matrix[i-1][j] || matrix[i-1][j-inputDenoms[i]];
                }
                // there is an opportunity here to break out of both loops as soon as the target column has a true value,
                // as this means we have reached our target. It would require some refactoring, but would make the
                // algorithm more performant
            }

        }
        return matrix;
    }


    /*
        this method returns true or false whether the requested amount can be returned or not, based on the value of
        last cell in the matrix

        @param matrix the pre-constructed DP matrix.
     */
    private boolean isCombinationPossible(final boolean[][] matrix) {
        int rows = matrix.length-1;
        int cols = matrix[rows].length-1;
        return matrix[rows][cols];
    }


    /*
        <p>The purpose of the below method is to determine the combination of notes
        that sum up to the requested amount (or the closest value to the requested amount).</p>

        <p>As an example, we will use as input options an integer array [1,2,3,4] and a target (requested amount) of 6.
        Given the above input options, the first possible combination that can sum up to the
        target of 6, is 1,2 and 3.</p>

        <p>As represented by the boolean matrix below:

        <pre>
            0   1   2   3   4   5   6
        0   t   f   f   f   f   f   f
        1   t   t   f   f   f   f   f
        2   t   t   t   t   f   f   f
        3   t   t   t   t   t   t   t
        4   t   t   t   t   t   t   t
        </pre></p>

        <p>It works by starting at the target (in this case [6,4]).
        <ul>
        <li>1.    If the target is true, we know that there exists within the inputDenoms array
              a combination of values that can sum up to the target (false will be explained in point 2 below).</li>
        <ul>
        <li>1.1   Check if the value directly above the target - [6,3] - is false. This value indicates whether it is
              possible to reach the target by using only values up to, and excluding, the current value.
              (1, 2, and 3 in this case).</li>
        <ul>
        <li>1.1.1 If no (true), this means that the target could be made with previous numbers,
              and we do not need to select the current item. Thus, move to the next item (3 in this case),
              and repeat all steps, starting at Step 1, until the target has been reached.</li>
        <li>1.1.2 If yes(false), this means that the target could not be made with all
              numbers prior to the current item, and we have to select the
              current item. Accordingly, we add the current item to the return list, reduce the remaining
              target by the current item, and continue on to the next item. For example,
              the value directly above [6,3] is false, thus we know that we have to select 3.
              The subsequent target would thus be [3,2]. Repeat all steps, starting at Step 1,
              until the target has been reached.</li>
        </ul>
        </ul>

        <li>2.If target is false, iteratively move one item to the left until the
        value is true - this value represents the largest number that can be made
        by summing up all inputDenoms items.</ul></p></li>
        </ul></p>

        @param  matrix the completed matrix indicating whether the target is reachable.
        @param  inputDenoms the int inputDenoms array
        @param  targetInput target sum (can be null (0) - derivable from matrix).
        @param  requestedAmountAvailable boolean value indicating whether the requested amount can be returned given
                the available denominations.
        @return a boolean value indicating whether the requested amount can be dispensed, given the cash available in the ATM.
    */
    private List<Integer> getCombination(final boolean[][] matrix, final int[] inputDenoms, final int targetInput,
                                         boolean requestedAmountAvailable) {

        // List<Integer> returnList = new ArrayList<>();
        // if supplied target is empty, then derive it from the matrix (amount of columns - 1)
        int target = targetInput == 0 ? matrix[0].length -1 : targetInput;
        // if the requested amount can be serviced, get the combination of notes to return
        if (requestedAmountAvailable) {
            return getCombinationList(matrix, inputDenoms, target);
        }
        else {
            // find the next available combination that is less than the requested amount.
            int newTarget= findHighestPossibleAmountInMatrix(matrix);
            return getCombinationList(matrix, inputDenoms, newTarget);
        }
    }

    // TODO: add test for this method
    /*
        This method returns the highest possible amount that can be returned, given the pre-constructed matrix.
        This method usually gets called when it is determined that the target amount cannot be returned, and we want to
        suggest an alternative amount to the user.

        @param matrix the pre-constructed DP matrix.
     */
    private int findHighestPossibleAmountInMatrix(final boolean[][] matrix) {
        int newTarget=0;
        int target = matrix[0].length-1;
        int totalDenonimations = matrix.length-1;
        // find the last true value in the matrix as this equals the largest amount < target amount  that can be returned
        for (int i=target; i>=1; i--) {
            if (matrix[totalDenonimations][i]){
                newTarget=i;
                break;
            }
        }
        return newTarget;
    }

    /*
        This method works backwards from the target to determine the combination of values that sum up to the target
        amount

         @param  matrix the completed DP matrix
         @param  inputDenoms the sorted list of all denominations to pick a combination from (same list, in the
         same order, that was used to construct the matrix)
         @param  target the amount requested for withdrawal.
         @returns the list of values that will be returned to the user (sum up to the target)
     */
    private List<Integer> getCombinationList(final boolean[][] matrix, final int[] inputDenoms, final int target) {
        List<Integer> returnList = new ArrayList<>();
        int items = inputDenoms.length-1;
        int j = target;
        for (int i=items; i>=1; i--) {
            if (!matrix[i-1][j]){
                returnList.add(inputDenoms[i]);
                j -= inputDenoms[i];
            }
        }
        return returnList;
    }




}


