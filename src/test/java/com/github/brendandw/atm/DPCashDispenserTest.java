package com.github.brendandw.atm;


import com.github.brendandw.atm.cashdispenser.sorting.SortingFactory;
import com.github.brendandw.atm.cashdispenser.strategy.DPCashDispenser;
import com.github.brendandw.atm.config.TestConfig;
import com.github.brendandw.atm.exceptions.TechnicalException;
import com.github.brendandw.atm.testutils.TestUtils;
import com.github.brendandw.atm.util.MapUtil;
import com.github.brendandw.atm.Application;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.brendandw.atm.cashdispenser.strategy.DPCashDispenser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author brendandw
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SortingFactory.class,DPCashDispenser.class})
public class DPCashDispenserTest {

    private static final String COMMA_REGULAR_EXPRESSION = "[,]";
    Method setupDPMatrixMethod;
    Method getCombinationMethod;
    Method isCombinationPossibleMethod;


    @Autowired
    DPCashDispenser dpcd;



    @Before
    public void setup() throws Exception {
            // use reflection to test private methods
            setupDPMatrixMethod = DPCashDispenser.class.getDeclaredMethod("setupDPMatrix",
            new Class[] { int[].class, int.class });
            setupDPMatrixMethod.setAccessible(true);

            getCombinationMethod = DPCashDispenser.class.getDeclaredMethod("getCombination",
            new Class[] { boolean[][].class, int[].class, int.class, boolean.class});
            getCombinationMethod.setAccessible(true);

        isCombinationPossibleMethod = DPCashDispenser.class.getDeclaredMethod("isCombinationPossible",
                new Class[] { boolean[][].class});
        isCombinationPossibleMethod.setAccessible(true);
    }

    @Test
    public void testSetupDPMatrix() throws Exception {

        // Test1
        // Given an input of 10,10,10,20, it is possible to make the target of 50

        int[] input = new int[]{0,10,10,10,20};
        int target = 50;
        Object[] parameters = new Object[]{input,target};
        boolean[][] matrix = (boolean[][])setupDPMatrixMethod.invoke(dpcd, parameters);
        boolean combinationPossible = matrix[input.length-1][target];
        Assert.assertTrue(combinationPossible);

        // Test2
        // change the target of the above test to 60.
        // Clearly it is not possible to make the target given the input values
        // Thus, the last item should be false
        // The highest possible value that can be made is 50
        // Thus ,matrix[input.length-1][50] should be the highest "True" value
        target = 60;
        parameters = new Object[]{input,target};
        matrix = (boolean[][])setupDPMatrixMethod.invoke(dpcd, parameters);
        combinationPossible = matrix[input.length-1][target];
        Assert.assertFalse(combinationPossible);
        Assert.assertFalse(matrix[input.length-1][59]);
        Assert.assertFalse(matrix[input.length-1][57]);
        Assert.assertFalse(matrix[input.length-1][55]);
        Assert.assertFalse(matrix[input.length-1][53]);
        Assert.assertFalse(matrix[input.length-1][51]);
        Assert.assertTrue(matrix[input.length-1][50]);

        // Test 3
        // Given the below input, a combination of 100 is possible
        input = new int[]{0,10,10,10,20,50,100};
        target = 100;
        parameters = new Object[]{input,target};
        matrix = (boolean[][])setupDPMatrixMethod.invoke(dpcd, parameters);
        combinationPossible = matrix[input.length-1][target];
        Assert.assertTrue(combinationPossible);

        // Test 4
        // change the target of the above test to 105
        // it is not possible to reach the target - 100
        // is the highest sum less than the target of 105.
        target = 105;
        parameters = new Object[]{input,target};
        matrix = (boolean[][])setupDPMatrixMethod.invoke(dpcd, parameters);
        combinationPossible = matrix[input.length-1][target];
        Assert.assertFalse(combinationPossible);
        Assert.assertFalse(matrix[input.length-1][104]);
        Assert.assertFalse(matrix[input.length-1][103]);
        Assert.assertFalse(matrix[input.length-1][102]);
        Assert.assertFalse(matrix[input.length-1][101]);
        Assert.assertTrue(matrix[input.length-1][100]);
    }

    @Test
    public void testIsCombinationPossible() throws Exception{
        // Test 1
        // Given the input below, and a target of 100,
        // A combination is possible

        int[] input = new int[]{0,10,10,10,10,20,20,20,50,100};
        int target = 100;
        Object[] parameters = new Object[]{input,target};
        boolean[][] matrix = (boolean[][])setupDPMatrixMethod.invoke(dpcd, parameters);
        parameters = new Object[]{matrix};
        boolean combinationPossible = (boolean)isCombinationPossibleMethod.invoke(dpcd, parameters);
        Assert.assertTrue(combinationPossible);


        // Test 2
        // same input as above, but in a different order combination ill obviously possible
        input = new int[]{0,100,10,10,10,10,20,20,20,50};
        target = 100;
        parameters = new Object[]{input,target};
        matrix = (boolean[][])setupDPMatrixMethod.invoke(dpcd, parameters);

        parameters = new Object[]{matrix};
        combinationPossible = (boolean)isCombinationPossibleMethod.invoke(dpcd, parameters);
        Assert.assertTrue(combinationPossible);


        // Test 3
        // same input as above, but in a different order. Combination still obviously possible
        input = new int[]{0,50,100,10,10,10,10,20,20,20};
        target = 100;
        parameters = new Object[]{input,target};
        matrix = (boolean[][])setupDPMatrixMethod.invoke(dpcd, parameters);

        parameters = new Object[]{matrix};
        combinationPossible = (boolean)isCombinationPossibleMethod.invoke(dpcd, parameters);
        Assert.assertTrue(combinationPossible);


        // Test 4
        // same input as above, but in a different order. Combination still obviously possible
        input = new int[]{0,50,10,20,20,20,100,10,10,10};
        target = 100;
        parameters = new Object[]{input,target};
        matrix = (boolean[][])setupDPMatrixMethod.invoke(dpcd, parameters);

        parameters = new Object[]{matrix};
        combinationPossible = (boolean)isCombinationPossibleMethod.invoke(dpcd, parameters);
        Assert.assertTrue(combinationPossible);


        // Test 5
        // Given the input below, and a target of 200, a combination is possible
        input = new int[]{0,50,10,10,10,10,10,10,10,10,100,50,20,20,20,100,10,10};
        target =200;
        parameters = new Object[]{input,target};
        matrix = (boolean[][])setupDPMatrixMethod.invoke(dpcd, parameters);

        parameters = new Object[]{matrix};
        combinationPossible = (boolean)isCombinationPossibleMethod.invoke(dpcd, parameters);
        Assert.assertTrue(combinationPossible);


        // Test 6
        // Given the input below, and a target of 9, a combination is possible
        input = new int[]{0,1,10,11,12,13,14,8};
        target =9;
        parameters = new Object[]{input,target};
        matrix = (boolean[][])setupDPMatrixMethod.invoke(dpcd, parameters);

        parameters = new Object[]{matrix};
        combinationPossible = (boolean)isCombinationPossibleMethod.invoke(dpcd, parameters);
        Assert.assertTrue(combinationPossible);


        // Test 7
        // Given the input below, and a target of 204, no combination can reach the target
        input = new int[]{0,50,10,10,10,10,10,10,10};
        target = 104;
        parameters = new Object[]{input,target};
        matrix = (boolean[][])setupDPMatrixMethod.invoke(dpcd, parameters);

        parameters = new Object[]{matrix};
        combinationPossible = (boolean)isCombinationPossibleMethod.invoke(dpcd, parameters);
        Assert.assertFalse(combinationPossible);


        // Test 8
        // Given the input below, and a target of 4000, a combination is possible.
        input = new int[]{0,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,
                10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,
                10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,
                10,10,10,10,10,10,10,10,10,10,10,10,20,20,20,20,20,20,20,20,20,20,
                20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,
                20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,50,50,50,50,50,50,50,50,50,50,
                50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,100,100,100,100,100,
                100,100,100,100};
        target = 4000;
        parameters = new Object[]{input,target};
        matrix = (boolean[][])setupDPMatrixMethod.invoke(dpcd, parameters);

        parameters = new Object[]{matrix};
        combinationPossible = (boolean)isCombinationPossibleMethod.invoke(dpcd, parameters);
        Assert.assertTrue(combinationPossible);

        // Test 9
        // Given the input below, and a target of 4005, a combination is NOT possible.
        input = new int[]{0,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,
                10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,
                10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,
                10,10,10,10,10,10,10,10,10,10,10,10,20,20,20,20,20,20,20,20,20,20,
                20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,
                20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,50,50,50,50,50,50,50,50,50,50,
                50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,100,100,100,100,100,
                100,100,100,100};
        target = 4005;
        parameters = new Object[]{input,target};
        matrix = (boolean[][])setupDPMatrixMethod.invoke(dpcd, parameters);

        parameters = new Object[]{matrix};
        combinationPossible = (boolean)isCombinationPossibleMethod.invoke(dpcd, parameters);
        Assert.assertFalse(combinationPossible);
    }


    @Test
    public void testGetCombination() throws Exception {
        // Test 1
        // Given the input below, and a target of 100,
        // the first possible combination to reach the target is:
        // 10,10,10,10,20,20,20

        int[] input = new int[]{0,10,10,10,10,20,20,20,50,100};
        int target = 100;
        Object[] parameters = new Object[]{input,target};
        boolean[][] matrix = (boolean[][])setupDPMatrixMethod.invoke(dpcd, parameters);
        List<Integer> combination = new ArrayList<>();
        parameters = new Object[]{matrix,input,target,true};

        List<Integer> returnedCombination = (List<Integer>)getCombinationMethod.invoke(dpcd, parameters);
        List<Integer> expectedList = TestUtils.getIntegerList("10,10,10,10,20,20,20", COMMA_REGULAR_EXPRESSION);
        Collections.sort(expectedList);
        Collections.sort(combination);
        //Assert.assertTrue(combinationPossible);
        Assert.assertTrue(expectedList.size() == returnedCombination.size()
                && expectedList.containsAll(returnedCombination) && returnedCombination.containsAll(expectedList));

        // Test 2
        // same input as above, but in a different order
        // first combination to reach the target of 100 is the single value 100
        input = new int[]{0,100,10,10,10,10,20,20,20,50};
        target = 100;
        parameters = new Object[]{input,target};
        matrix = (boolean[][])setupDPMatrixMethod.invoke(dpcd, parameters);
        combination = new ArrayList<>();
        parameters = new Object[]{matrix,input,target, true};
        returnedCombination = (List<Integer> )getCombinationMethod.invoke(dpcd, parameters);
        expectedList = com.github.brendandw.atm.testutils.TestUtils.getIntegerList("100", COMMA_REGULAR_EXPRESSION);
        Assert.assertTrue(expectedList.size() == returnedCombination.size()
                && expectedList.containsAll(returnedCombination) && returnedCombination.containsAll(expectedList));

        // Test 3
        // same input as above, but in a different order
        // first combination to reach the target of 100 is the single value 100
        input = new int[]{0,50,100,10,10,10,10,20,20,20};
        target = 100;
        parameters = new Object[]{input,target};
        matrix = (boolean[][])setupDPMatrixMethod.invoke(dpcd, parameters);
        combination = new ArrayList<>();
        parameters = new Object[]{matrix,input,target,true};
        returnedCombination = (List<Integer> )getCombinationMethod.invoke(dpcd, parameters);
        expectedList = com.github.brendandw.atm.testutils.TestUtils.getIntegerList("100", COMMA_REGULAR_EXPRESSION);
        Assert.assertTrue(expectedList.size() == returnedCombination.size()
                && expectedList.containsAll(returnedCombination) && returnedCombination.containsAll(expectedList));

        // Test 4
        // same input as above, but in a different order
        // first combination to reach the target of 100 is 50,10,20,20
        input = new int[]{0,50,10,20,20,20,100,10,10,10};
        target = 100;
        parameters = new Object[]{input,target};
        matrix = (boolean[][])setupDPMatrixMethod.invoke(dpcd, parameters);
        combination = new ArrayList<>();
        parameters = new Object[]{matrix,input,target, true};
        returnedCombination = (List<Integer> )getCombinationMethod.invoke(dpcd, parameters);
        expectedList = com.github.brendandw.atm.testutils.TestUtils.getIntegerList("50,10,20,20,", COMMA_REGULAR_EXPRESSION);
        Collections.sort(expectedList);
        Collections.sort(combination);
        // Assert.assertTrue(combinationPossible);
        Assert.assertTrue(expectedList.size() == returnedCombination.size()
                && expectedList.containsAll(returnedCombination) && returnedCombination.containsAll(expectedList));

        // Test 5
        // Given the input below, and a target of 200,
        // the first possible combination to reach the target is:
        // 10,10,10,10,10,50,100
        input = new int[]{0,50,10,10,10,10,10,10,10,10,100,50,20,20,20,100,10,10};
        target =200;
        parameters = new Object[]{input,target};
        matrix = (boolean[][])setupDPMatrixMethod.invoke(dpcd, parameters);
        combination = new ArrayList<>();
        parameters = new Object[]{matrix,input,target,true};
        returnedCombination = (List<Integer> )getCombinationMethod.invoke(dpcd, parameters);
        expectedList = com.github.brendandw.atm.testutils.TestUtils.getIntegerList("10,10,10,10,10,50,100", COMMA_REGULAR_EXPRESSION);
        Collections.sort(expectedList);
        Collections.sort(combination);
        Assert.assertTrue(expectedList.size() == returnedCombination.size()
                && expectedList.containsAll(returnedCombination) && returnedCombination.containsAll(expectedList));

        // Test 6
        // Given the input below, and a target of 9,
        // the only possible combination to reach the target is:
        // 1,8
        input = new int[]{0,1,10,11,12,13,14,8};
        target =9;
        parameters = new Object[]{input,target};
        matrix = (boolean[][])setupDPMatrixMethod.invoke(dpcd, parameters);
        combination = new ArrayList<>();
        parameters = new Object[]{matrix,input,target,true};
        returnedCombination = (List<Integer> )getCombinationMethod.invoke(dpcd, parameters);
        expectedList = com.github.brendandw.atm.testutils.TestUtils.getIntegerList("1,8", COMMA_REGULAR_EXPRESSION);
        Collections.sort(expectedList);
        Collections.sort(combination);
        Assert.assertTrue(expectedList.size() == returnedCombination.size()
                && expectedList.containsAll(returnedCombination) && returnedCombination.containsAll(expectedList));

        // Test 7
        // Given the input below, and a target of 204, no combination can reach the target
        // the highest possible combination that is less than the target is:
        // 50,10,10,10,10,10 which equals 100
        input = new int[]{0,50,10,10,10,10,10,10,10};
        target = 104;
        parameters = new Object[]{input,target};
        matrix = (boolean[][])setupDPMatrixMethod.invoke(dpcd, parameters);
        combination = new ArrayList<>();
        parameters = new Object[]{matrix, input, target, false};
        returnedCombination = (List<Integer> )getCombinationMethod.invoke(dpcd, parameters);
        expectedList = TestUtils.getIntegerList("10,10,10,10,10,50", COMMA_REGULAR_EXPRESSION);
        Collections.sort(expectedList);
        Collections.sort(combination);
        Assert.assertTrue(expectedList.size() == returnedCombination.size()
                && expectedList.containsAll(returnedCombination) && returnedCombination.containsAll(expectedList));
    }

    /*
     The method that is tested below combines the private methods tested above
      in the following way:

    */
    @Test
    public  void testWithdrawCash() throws Exception{

        Map<Integer,Integer> inputMap = new HashMap<Integer,Integer>();
        int target = 100;
        inputMap.put(20, 8);
        inputMap.put(10, 2);
        inputMap.put(50, 2);


        Map<Integer,Map<Integer,Integer>> bestCombo = dpcd.getCashCombination(inputMap, target);
        Map<Integer,Integer> expextedCombo = new HashMap<>();
        expextedCombo.put(20, 5);
        int key = bestCombo.keySet().iterator().next();
        Assert.assertEquals(key,target);
        List<Integer> returnList =  MapUtil.getFlatListFromMap(bestCombo.get(target));
        int sum = returnList.stream().mapToInt(Integer::intValue).sum();
        Assert.assertEquals(key,sum);

        inputMap = new HashMap<Integer,Integer>();
        target = 200;
        inputMap.put(100, 2);
        inputMap.put(200, 2);
        inputMap.put(50, 4);


        bestCombo = dpcd.getCashCombination(inputMap, target);
        expextedCombo = new HashMap<>();
        expextedCombo.put(20, 5);
        key = bestCombo.keySet().iterator().next();
        Assert.assertEquals(key,target);
        returnList =  MapUtil.getFlatListFromMap(bestCombo.get(target));
         sum = returnList.stream().mapToInt(Integer::intValue).sum();
        Assert.assertEquals(key,sum);

        inputMap = new HashMap<Integer,Integer>();
        target = 110;
        inputMap.put(10, 10);
        inputMap.put(200, 4);
        inputMap.put(5, 2);
        bestCombo = dpcd.getCashCombination(inputMap, target);
        expextedCombo = new HashMap<>();
        expextedCombo.put(20, 5);
        key = bestCombo.keySet().iterator().next();
        Assert.assertEquals(key,target);
        returnList =  MapUtil.getFlatListFromMap(bestCombo.get(target));
         sum = returnList.stream().mapToInt(Integer::intValue).sum();
        Assert.assertEquals(key,sum);

        inputMap = new HashMap<Integer,Integer>();
        target = 102;
        int closestTarget = 100;
        inputMap.put(10, 10);
        inputMap.put(200, 4);
        inputMap.put(5, 2);
        bestCombo = dpcd.getCashCombination(inputMap, target);
        key = bestCombo.keySet().iterator().next();
        Assert.assertEquals(key,closestTarget);
        returnList =  MapUtil.getFlatListFromMap(bestCombo.get(closestTarget));
        sum = returnList.stream().mapToInt(Integer::intValue).sum();
        Assert.assertEquals(key,sum);

    }



    /*
        Visual representation of matrix
        Useful for debugging and testing purposes
    */
    private  void printMatrix(boolean[][] matrix, int[] input, int target) {
        int items = input.length-1;
        System.out.printf("%1$4s", " ");
        for (int i=0; i<=target; i++) {
            System.out.printf("%1$4s", i);
        }
        System.out.println();

        for (int i=0; i<=items; i++) {
                System.out.printf("%1$4s", input[i]);
            for (int j=0; j<=target; j++) {
                System.out.printf("%1$4s", Boolean.toString(matrix[i][j]).substring(0, 1));
            }
            System.out.println();
        }
    }


}
