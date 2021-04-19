package com.github.brendandw.atm;


import com.github.brendandw.atm.cashdispenser.sorting.HighestDenominationSorter;
import java.util.LinkedHashMap;
import java.util.Map;

import com.github.brendandw.atm.cashdispenser.sorting.HighestDenominationSorter;
import org.junit.Assert;
import org.junit.Test;


/**
 *
 * @author brendandw
 */
public class HighestDenominationSorterTest {

    @Test
    public void testSort() throws Exception {
        HighestDenominationSorter sorter = new HighestDenominationSorter();
        // LinkedHashMap retains its insertion order, thus it is possible to predict
        // which item will be selected next
        Map<Integer,Integer> inputMap = new LinkedHashMap<>();
        inputMap.put(200, 1);
        inputMap.put(100, 2);
        inputMap.put(50, 4);
        inputMap.put(20, 7);
        // Remove one occurrence of the most frequently occurring (MFO) denomination from the map until this denomination
        // is no longer the highest occurring item in the list
        // Thus, remove 3 20s.
        // 20 and 50 now both have 4 occurrences. 50 is first in the list, so remove one 50
        // 20 is now the MFO, so remove one more 20
        // 20 and 50 now both have 3 occurrences. 50 is first in the list, so remove one 50
        // 20 is now the MFO, so remove one more 20
        // 100, 50 and 20 now each have 2 occurrences. 100 is first in the list, so remove one 100
        // 50 and 20 now each have 2 occurrences. 50 is first in the list, so remove one 50
        // 20 is now the highest occurring denomination in the map, so remove one 20
        // 200,100,50 and 20 each have 1 occurence. 200 is first in the list, so remove it.
        // 100,50 and 20 each have 1 occurence. 100 is first in the list, so remove it.
        // 50 and 20 each have 1 occurence. 50 is first in the list, so remove it.
        // 20 is the last item in the list so remove it
        // etc.
        int[] outputlist = sorter.sort(inputMap);
        int[] expectedArray = new int[]{0,20,20,20,50,20,50,20,100,50,20,200,100,50,20};
        Assert.assertArrayEquals(expectedArray, outputlist);


    }


}
