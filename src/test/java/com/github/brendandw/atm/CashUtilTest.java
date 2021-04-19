package com.github.brendandw.atm;


import com.github.brendandw.atm.util.CashUtil;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brendandw
 */
public class CashUtilTest {

    /*
    The test below checks whether an input map is aptly resized relative to the
    target supplied. For example, If 100 is supplied as target, then 10 is the maximum amount of
    $10 notes that are applicable. Any additional $10 notes will be redundant, and unnecassarily result
    in a performance decrease.
    */
    @Test
    public void testResizeInputMap() throws Exception {

        int target = 100;
        Map inputMap = new HashMap<Integer,Integer>();
        inputMap.put(100, 10);
        inputMap.put(50, 10);
        inputMap.put(30, 10);
        inputMap.put(20, 10);
        inputMap.put(10, 10);

        Map expectedMap = new HashMap<Integer,Integer>();
        expectedMap.put(100, 1);
        expectedMap.put(50, 2);
        expectedMap.put(30, 3);
        expectedMap.put(20, 5);
        expectedMap.put(10, 10);

        Object[] parameters = new Object[]{inputMap,target};
        Map<Integer,Integer> returnMap = CashUtil.resizeInputMap(inputMap, target);
        Assert.assertEquals(returnMap,expectedMap);
    }

}
