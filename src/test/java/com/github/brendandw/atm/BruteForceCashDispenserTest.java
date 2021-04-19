package com.github.brendandw.atm;


import com.github.brendandw.atm.cashdispenser.strategy.BruteForceCashDispenser;
import com.github.brendandw.atm.config.TestConfig;
import com.github.brendandw.atm.helpers.ListComparator;
import com.github.brendandw.atm.helpers.MapComparator;
import com.github.brendandw.atm.util.MapUtil;
import com.github.brendandw.atm.testutils.TestUtils;
import com.github.brendandw.atm.Application;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.brendandw.atm.cashdispenser.strategy.BruteForceCashDispenser;
import com.github.brendandw.atm.helpers.ListComparator;
import com.github.brendandw.atm.helpers.MapComparator;
import com.github.brendandw.atm.testutils.TestUtils;
import org.apache.commons.collections4.list.TreeList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author brendandw
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class BruteForceCashDispenserTest {

    Method findAllCombinationsMethod;
    Method getLowestVarianceMethod;

    private static final String COMMA_REGULAR_EXPRESSION = "[,]";
    private static final String SEMI_COLON_REGULAR_EXPRESSION = "[;]";
    private MapComparator mapComparator = new MapComparator();

    @Before
    public void setup() throws Exception {
           // use reflection to test private methods
           findAllCombinationsMethod = BruteForceCashDispenser.class.getDeclaredMethod("findAllCombinations",
            new Class[]{
                List.class,List.class,int.class,Map.class,Set.class
            });

            findAllCombinationsMethod.setAccessible(true);
            getLowestVarianceMethod = BruteForceCashDispenser.class.getDeclaredMethod("getLowestVariance",
            new Class[]{
                Set.class,Map.class
            });
            getLowestVarianceMethod.setAccessible(true);
    }





    @Test
    public void TestFindAllCombinations() throws Exception {

        //Test 1 ---------------------------------------------------------------
        //with 1,2,3,4,5 as input and 6 as target, the below combinations are possible:
        // - 1+2+3 = 6
        // - 1+5 = 6
        // - 2+4 = 6
        BruteForceCashDispenser BFCD = new BruteForceCashDispenser();
        String[] expectedResultInput = new String[]{"1,1;2,1;3,1","1,1;5,1","2,1;4,1"};

        Set<List<Integer>> expectedResult =  TestUtils.getSetOfLists(expectedResultInput, COMMA_REGULAR_EXPRESSION, SEMI_COLON_REGULAR_EXPRESSION);

        List<Integer> cashList = TestUtils.getIntegerList("1,2,3,4,5",COMMA_REGULAR_EXPRESSION);
        Collections.sort(cashList);
        Map<List<Integer>,Integer> triedCombinations = new HashMap<>();
        triedCombinations.put(new ArrayList<Integer>(), 0);
        Set<List<Integer>> validCombinations = new TreeSet<List<Integer>>(new ListComparator());

        Object[] parameters = new Object[]{cashList,(new TreeList<Integer>()),6, triedCombinations, validCombinations};
        findAllCombinationsMethod.invoke(BFCD, parameters);

        Assert.assertEquals(MapUtil.sortSetOfUnsortedListsAscendingly(validCombinations).toString(),MapUtil.sortSetOfUnsortedListsAscendingly(expectedResult).toString());

        //Test 2 ---------------------------------------------------------------
        //with 1,2 as input and 6 as target, no combinations are possible.
        //3 should be set as the closest possible combination
        validCombinations = new TreeSet<>();
        expectedResultInput = new String[]{};
        expectedResult = TestUtils.getSetOfLists(expectedResultInput, COMMA_REGULAR_EXPRESSION, SEMI_COLON_REGULAR_EXPRESSION);
        cashList = TestUtils.getIntegerList("1,2",COMMA_REGULAR_EXPRESSION);
        Collections.sort(cashList);
        triedCombinations = new HashMap<>();
        triedCombinations.put(new ArrayList<Integer>(), 0);
        validCombinations = new HashSet<>();

        parameters = new Object[]{cashList,new TreeList<Integer>(),6, triedCombinations, validCombinations};
        findAllCombinationsMethod.invoke(BFCD, parameters);
        Assert.assertEquals(MapUtil.sortSetOfUnsortedListsAscendingly(validCombinations).toString(),MapUtil.sortSetOfUnsortedListsAscendingly(expectedResult).toString());

        //---------------------------------------------------------------------------

        //Test 3 ---------------------------------------------------------------
        //with empty input and 6 as target, no combinations are possible:
        validCombinations = new TreeSet<>();
        expectedResultInput = new String[]{};
        expectedResult = TestUtils.getSetOfLists(expectedResultInput, COMMA_REGULAR_EXPRESSION, SEMI_COLON_REGULAR_EXPRESSION);
        cashList = new ArrayList<>();
        Collections.sort(cashList);
        triedCombinations = new HashMap<>();
        triedCombinations.put(new ArrayList<Integer>(), 0);
        validCombinations = new HashSet<>();
        parameters = new Object[]{cashList,new TreeList<Integer>(),6, triedCombinations, validCombinations};
        findAllCombinationsMethod.invoke(BFCD, parameters);
        Assert.assertEquals(validCombinations,expectedResult);
        //---------------------------------------------------------------------------

        //Test 4 ---------------------------------------------------------------
        //with 20,20,20,20,20 as input and 100 as target, the below combinations are possible:
        // - 20+20+20+20+20 = 100
        validCombinations = new TreeSet<>();
        expectedResultInput = new String[]{"20,5"};
        expectedResult = TestUtils.getSetOfLists(expectedResultInput, COMMA_REGULAR_EXPRESSION, SEMI_COLON_REGULAR_EXPRESSION);
        cashList = TestUtils.getIntegerList("20,20,20,20,20",COMMA_REGULAR_EXPRESSION);
        Collections.sort(cashList);
        triedCombinations = new HashMap<>();
        triedCombinations.put(new ArrayList<Integer>(), 0);
        validCombinations = new HashSet<>();
        parameters = new Object[]{cashList,new TreeList<Integer>(),100, triedCombinations, validCombinations};
        findAllCombinationsMethod.invoke(BFCD, parameters);
        Assert.assertEquals(MapUtil.sortSetOfUnsortedListsAscendingly(validCombinations).toString(),MapUtil.sortSetOfUnsortedListsAscendingly(expectedResult).toString());
        //---------------------------------------------------------------------------

        //Test 5 ---------------------------------------------------------------
        //with 20,20,20,20,20,50,10 as input and 100 as target, the below combinations are possible:
        // - 20+20+20+20+20 = 100
        // - 20+20+10+50 = 100
        expectedResultInput = new String[]{"20,5","20,2;10,1;50,1"};
        expectedResult = TestUtils.getSetOfLists(expectedResultInput, COMMA_REGULAR_EXPRESSION, SEMI_COLON_REGULAR_EXPRESSION);
        cashList = TestUtils.getIntegerList("20,20,20,20,20,50,10",COMMA_REGULAR_EXPRESSION);
        Collections.sort(cashList);
        triedCombinations = new HashMap<>();
        triedCombinations.put(new ArrayList<Integer>(), 0);
        validCombinations = new HashSet<>();
        parameters = new Object[]{cashList,new TreeList<Integer>(),100, triedCombinations, validCombinations};
        findAllCombinationsMethod.invoke(BFCD, parameters);
        Assert.assertEquals(MapUtil.sortSetOfUnsortedListsAscendingly(validCombinations).toString(),MapUtil.sortSetOfUnsortedListsAscendingly(expectedResult).toString());
        //---------------------------------------------------------------------------


        //Test 6 ---------------------------------------------------------------
        //with 20,20,20,20,20,50,50,10,10,10,10,10,10,10,10,10,10 as input and 100 as target, the below combinations are possible:

        // - 20+20+20+20+20 = 100-
        // - 20+20+20+20+10+10 = 100
        // - 20+20+20+10+10+10+10 = 100
        // - 20+20+10+10+10+10+10+10 = 100-
        // - 50+20+10+10+10-
        // - 20+20+10+50 = 100-
        // - 50 + 50 = 100-
        // - 20+10+10+10+10+10+10+10+10 = 100-
        // - 50+10+10+10+10+10 = 100-
        // - 10+10+10+10+10+10+10+10+10+10=100-
        expectedResultInput = new String[]{
            "20,1;10,8","50,1;20,2;10,1","50,1;20,1;10,3","50,1;10,5","20,2;10,6","50,2","20,3;10,4","20,4;10,2","20,5","10,10"
        };

        expectedResult = TestUtils.getSetOfLists(expectedResultInput, COMMA_REGULAR_EXPRESSION, SEMI_COLON_REGULAR_EXPRESSION);
        cashList = TestUtils.getIntegerList("20,20,20,20,20,50,50,10,10,10,10,10,10,10,10,10,10",COMMA_REGULAR_EXPRESSION);
        Collections.sort(cashList);
        Collections.sort(cashList);
        triedCombinations = new HashMap<>();
        triedCombinations.put(new ArrayList<Integer>(), 0);
        validCombinations = new HashSet<>();
        parameters = new Object[]{cashList,new TreeList<Integer>(),100, triedCombinations, validCombinations};
        findAllCombinationsMethod.invoke(BFCD, parameters);
        System.out.println(MapUtil.sortSetOfUnsortedListsAscendingly(validCombinations).toString());
        System.out.println(MapUtil.sortSetOfUnsortedListsAscendingly(expectedResult).toString());
        Assert.assertEquals(MapUtil.sortSetOfUnsortedListsAscendingly(validCombinations).toString(),MapUtil.sortSetOfUnsortedListsAscendingly(expectedResult).toString());


        // Test 7 ---------------------------------------------------------------
        // with [20,2],[10,5] as input and 100 as target, no combinations are possible.
        // 90 should be set as the closest match.
        validCombinations = new TreeSet<>();
        expectedResultInput = new String[]{};

        expectedResult = TestUtils.getSetOfLists(expectedResultInput, COMMA_REGULAR_EXPRESSION, SEMI_COLON_REGULAR_EXPRESSION);
        cashList = TestUtils.getIntegerList("20,20,10,10,10,10,10",COMMA_REGULAR_EXPRESSION);
        Collections.sort(cashList);
        triedCombinations = new HashMap<>();
        triedCombinations.put(new ArrayList<Integer>(), 0);
        validCombinations = new HashSet<>();
        parameters = new Object[]{cashList,new TreeList<Integer>(),100, triedCombinations, validCombinations};
        findAllCombinationsMethod.invoke(BFCD, parameters);
        Assert.assertEquals(validCombinations,expectedResult);

    }

    @Test
    public  void testGetLowestVariance() throws Exception{
        /*
         Denominations available in atm
        */
        BruteForceCashDispenser bfcd = new BruteForceCashDispenser();
        Map<Integer,Integer> cashCurrentlyInAtm = new HashMap<>();
        cashCurrentlyInAtm.put(10, 10);
        cashCurrentlyInAtm.put(20, 5);
        cashCurrentlyInAtm.put(50, 2);
        cashCurrentlyInAtm.put(100, 1);

        //find the best combination from the below list of combinations
        String[] combinationString = new String[]{
            "20,1;10,8","50,1;20,2;10,1","50,1;20,1;10,3","50,1;10,5","20,2;10,6","50,2","20,3;10,4","20,4;10,2","20,5","10,10"
            };

        /*
            Given the above data, the algorithm is expected to produce the below results.
            Combination :{20=1, 10=8}       cash left in ATM: {50=2, 100=1, 20=4, 10=2} Mean: 2.25  Variance = (-0.25^2)+(-1.25^2)+(1.75^2) +(-0.25^2) = 4.75
            Combination :{20=2, 10=6}       cash left in ATM: {50=2, 100=1, 20=3, 10=4} Mean: 2.5   Variance = (-0.5^2) +(-1.5^2) +(0.5^2)  +(1.5^2)   = 5.0
            Combination :{10=10}            cash left in ATM: {50=2, 100=1, 20=5, 10=0} Mean: 2.0   Variance = (0.0^2)  +(-1.0^2) +(3.0^2)  +(-2.0^2)  = 14.0
            Combination :{20=3, 10=4}       cash left in ATM: {50=2, 100=1, 20=2, 10=6} Mean: 2.75  Variance = (-0.75^2)+(-1.75^2)+(-0.75^2)+(3.25^2)  = 14.75
            Combination :{50=1, 10=5}       cash left in ATM: {50=1, 100=1, 20=5, 10=5} Mean: 3.0   Variance = (-2.0^2) +(-2.0^2) +(2.0^2)  +(2.0^2)   = 16.0
            Combination :{50=1, 20=1, 10=3} cash left in ATM: {50=1, 100=1, 20=4, 10=7} Mean: 3.25  Variance = (-2.25^2)+(-2.25^2)+(0.75^2) +(3.75^2)  = 24.75
            Combination :{20=4, 10=2}       cash left in ATM: {50=2, 100=1, 20=1, 10=8} Mean: 3.0   Variance = (-1.0^2) +(-2.0^2) +(-2.0^2) +(5.0^2)   = 34.0
            Combination :{50=1, 20=2, 10=1} cash left in ATM: {50=1, 100=1, 20=3, 10=9} Mean: 3.5   Variance = (-2.5^2) +(-2.5^2) +(-0.5^2) +(5.5^2)   = 43.0
            Combination :{50=2}             cash left in ATM: {50=0, 100=1, 20=5, 10=10}Mean: 4.0   Variance = (-4.0^2) +(-3.0^2) +(1.0^2)  +(6.0^2)   = 62.0
            Combination :{20=5}             cash left in ATM: {50=2, 100=1, 20=0, 10=10}Mean: 3.25  Variance = (-1.25^2)+(-2.25^2)+(-3.25^2)+(6.75^2)  = 62.75

            Combination: [20,1][10,8] should thus be the most appropriate, and should be returned.
        */

        Map<Integer,Integer> expectedOutput = new HashMap<>();
        expectedOutput.put(20, 1);
        expectedOutput.put(10, 8);


        Set<List<Integer>> combinationsInputSet = TestUtils.getSetOfLists(combinationString, COMMA_REGULAR_EXPRESSION, SEMI_COLON_REGULAR_EXPRESSION);
        Object[] parameters = new Object[]{combinationsInputSet,cashCurrentlyInAtm};
        Map<Integer,Integer> returnedResult = (Map<Integer,Integer>)getLowestVarianceMethod.invoke(bfcd, parameters);
        Assert.assertEquals(expectedOutput,returnedResult);

        /*
            Denominations available in atm
        */
        cashCurrentlyInAtm = new HashMap<>();
        cashCurrentlyInAtm.put(10, 10);
        cashCurrentlyInAtm.put(20, 5);

        //find the best combination from the below list of combinations
        combinationString = new String[]{
            "20,2;10,1","20,1;10,2"
            };

        /*
        Given the above data, the algorithm is expected to produce the below results.
        Combination :{20=2, 10=1}       cash left in ATM: {20=3, 10=9} Mean: 6  Variance = (-3^2)+(3^2) = 18
        Combination :{20=1, 10=2}       cash left in ATM: {20=4, 10=8} Mean: 6  Variance = (-2^2) +(2^2)= 8

        Combination: [20,1][10,8] should thus be the most appropriate, and should be returned.
        */

        expectedOutput = new HashMap<>();
        expectedOutput.put(20, 1);
        expectedOutput.put(10, 2);


        combinationsInputSet = TestUtils.getSetOfLists(combinationString, COMMA_REGULAR_EXPRESSION, SEMI_COLON_REGULAR_EXPRESSION);
        parameters = new Object[]{combinationsInputSet,cashCurrentlyInAtm};
        returnedResult = (Map<Integer,Integer>)getLowestVarianceMethod.invoke(bfcd, parameters);
        Assert.assertEquals(expectedOutput,returnedResult);

        /*
         Denominations available in atm
        */
        cashCurrentlyInAtm = new HashMap<>();
        cashCurrentlyInAtm.put(10, 10);
        cashCurrentlyInAtm.put(20, 5);
        cashCurrentlyInAtm.put(50, 10);
        cashCurrentlyInAtm.put(100,1);

        //find the best combination from the below list of combinations
        combinationString = new String[]{
            "100,1"
            };

        /*

        //Only one combination is supplied as input and should thus be returned

        Combination: [20,1][10,8] should thus be the most appropriate, and should be returned.
        */

        expectedOutput = new HashMap<>();
        expectedOutput.put(100, 1);


        combinationsInputSet = TestUtils.getSetOfLists(combinationString, COMMA_REGULAR_EXPRESSION, SEMI_COLON_REGULAR_EXPRESSION);
        parameters = new Object[]{combinationsInputSet,cashCurrentlyInAtm};
        returnedResult = (Map<Integer,Integer>)getLowestVarianceMethod.invoke(bfcd, parameters);
        Assert.assertEquals(expectedOutput,returnedResult);
    }

    /*
     The method that is tested below combines the two private methods tested above in the following way:
     1. Find all the possible combinations that could add up to the supplied target
     2. With all of the combinations found during step 1 above, return the most appropriate combination.
        (Most appropriate in this case means, the combination that would result in the lowest total
         variance in the cash remaining in the ATM)
    */
    @Test
    public  void testWithdrawCash() throws Exception{

        Map<Integer,Integer> inputMap = new HashMap<Integer,Integer>();
        inputMap.put(20, 8);
        inputMap.put(10, 4);
        inputMap.put(50, 2);
        BruteForceCashDispenser bfcd = new BruteForceCashDispenser();
        //with the input above, and 100 as target, the following combinations are possible
        //1. [50,2]
        //2. [50,1][20,2][10,1]
        //3. [50,1][20,1][10,3]
        //4. [20,5]
        //5. [20,4][10,2]
        //6. [20,3][10,4]
        //which would in turn result in the following total variance within the remaining denominations
        //1. 32.0
        //2. 12.666666666666668
        //3. 24.0
        //4. 2.0
        //5. 12.666666666666668
        //6. 12.666666666666668
        //thus, option 4 (20,5) is the best combination
        Map<Integer,Map<Integer,Integer>> bestCombo = bfcd.getCashCombination(inputMap, 100);
        Map<Integer,Integer> expextedCombo = new HashMap<>();
        expextedCombo.put(20, 5);
        int key = bestCombo.keySet().iterator().next();
        Assert.assertEquals(key,100);
        Assert.assertEquals(bestCombo.get(100),expextedCombo);

        //Increasing the number of 50s in the ATM should produce exactly the same combinations, but
        //different variances
        inputMap.clear();
        inputMap.put(20, 8);
        inputMap.put(10, 4);
        inputMap.put(50, 50);
        //with such a high amount of 50s available, the best combination would obviously be 50*2
        bestCombo = bfcd.getCashCombination(inputMap, 100);
        expextedCombo = new HashMap<>();
        expextedCombo.put(50, 2);
        key = bestCombo.keySet().iterator().next();
        Assert.assertEquals(key,100);
        Assert.assertEquals(bestCombo.get(100),expextedCombo);

        inputMap = new HashMap<Integer,Integer>();
        inputMap.put(20, 8);
        inputMap.put(10, 4);
        inputMap.put(50, 2);
        //Given the input above, and 105 as target, it is clear that the target cannot be reached.
        //The closest sum that can be made is (as per the test case directly preceeding this one)
        //100.
        //Given that the input is the same as in the previous test case, and the closest target
        //equals the previous target, the output should be exactly the same.
        //test case, the
        //1. [50,2]
        //2. [50,1][20,2][10,1]
        //3. [50,1][20,1][10,3]
        //4. [20,5]
        //5. [20,4][10,2]
        //6. [20,3][10,4]
        //which would in turn result in the following total variance within the remaining denominations
        //1. 32.0
        //2. 12.666666666666668
        //3. 24.0
        //4. 2.0
        //5. 12.666666666666668
        //6. 12.666666666666668
        //thus, option 4 (20,5) is the best combination
        bestCombo = bfcd.getCashCombination(inputMap, 105);
        expextedCombo = new HashMap<>();
        expextedCombo.put(20, 5);
        key = bestCombo.keySet().iterator().next();
        Assert.assertEquals(key,100);
        Assert.assertEquals(bestCombo.get(100),expextedCombo);
    }
}
