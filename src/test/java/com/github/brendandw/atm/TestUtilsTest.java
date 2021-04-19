package com.github.brendandw.atm;


import com.github.brendandw.atm.util.MapUtil;
import com.github.brendandw.atm.testutils.TestUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.github.brendandw.atm.testutils.TestUtils;
import org.junit.Assert;
import org.junit.Test;


/**
 *
 * @author brendandw
 */
public class TestUtilsTest {
    private static final String COMMA_REGULAR_EXPRESSION = "[,]";
    private static final String SEMI_COLON_REGULAR_EXPRESSION = "[;]";

    @Test
    public void testGetMapList() throws Exception {
             String[]  input = new String[]{
            "20,1;10,8","50,1;20,2;10,1","50,1;20,1;10,3","50,1;10,5","20,2;10,6","50,2","20,3;10,4","20,4;10,2","20,5","10,10"
        };

        List<Map<Integer,Integer>> result = TestUtils.getMapList(input, COMMA_REGULAR_EXPRESSION, SEMI_COLON_REGULAR_EXPRESSION);
        List<Map<Integer,Integer>> ExpectedOutputList = new ArrayList<>();
        Map<Integer,Integer> map1 = new LinkedHashMap<>();
        map1.put(20,1);
        map1.put(10,8);
        ExpectedOutputList.add(map1);

        Map<Integer,Integer> map2 = new LinkedHashMap<>();
        map2.put(50,1);
        map2.put(20,2);
        map2.put(10,1);
        ExpectedOutputList.add(map2);

        Map<Integer,Integer> map3 = new LinkedHashMap<>();
        map3.put(50,1);
        map3.put(20,1);
        map3.put(10,3);
        ExpectedOutputList.add(map3);

        Map<Integer,Integer> map4 = new LinkedHashMap<>();
        map4.put(50,1);
        map4.put(10,5);
        ExpectedOutputList.add(map4);

        Map<Integer,Integer> map5 = new LinkedHashMap<>();
        map5.put(20,2);
        map5.put(10,6);
        ExpectedOutputList.add(map5);

        Map<Integer,Integer> map6 = new LinkedHashMap<>();
        map6.put(50,2);
        ExpectedOutputList.add(map6);

        Map<Integer,Integer> map7= new LinkedHashMap<>();
        map7.put(20,3);
        map7.put(10,4);
        ExpectedOutputList.add(map7);

        Map<Integer,Integer> map8= new LinkedHashMap<>();
        map8.put(20,4);
        map8.put(10,2);
        ExpectedOutputList.add(map8);

        Map<Integer,Integer> map9= new LinkedHashMap<>();
        map9.put(20,5);
        ExpectedOutputList.add(map9);


        Map<Integer,Integer> map11= new LinkedHashMap<>();
        map11.put(10,10);
        ExpectedOutputList.add(map11);

        //sort lists before testing equality
        result = MapUtil.sortListOfUnsortedMapsAscendingly(result);
        ExpectedOutputList = MapUtil.sortListOfUnsortedMapsAscendingly(ExpectedOutputList);
        System.out.println(result.toString());
        System.out.println(ExpectedOutputList.toString());
        Assert.assertEquals(ExpectedOutputList,result);
    }

    @Test
    public void testGetListOfLists() throws Exception {
             String[]  input = new String[]{
            "20,1;10,8","50,1;20,2;10,1","50,1;20,1;10,3","50,1;10,5",
            "20,2;10,6","50,2","20,3;10,4","20,4;10,2","20,5","10,10"
        };

        List<List<Integer>> result = TestUtils.getListOfLists(input, COMMA_REGULAR_EXPRESSION, SEMI_COLON_REGULAR_EXPRESSION);
        List<List<Integer>> ExpectedOutputList = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        list1.add(20);
        list1.add(10);//1
        list1.add(10);//2
        list1.add(10);//3
        list1.add(10);//4
        list1.add(10);//5
        list1.add(10);//6
        list1.add(10);//7
        list1.add(10);//8
        Collections.sort(list1);
        ExpectedOutputList.add(list1);

        List<Integer> list2 = new ArrayList<>();
        list2.add(50);
        list2.add(20);
        list2.add(20);
        list2.add(10);
        Collections.sort(list2);
        ExpectedOutputList.add(list2);

        List<Integer> list3 = new ArrayList<>();
        list3.add(50);
        list3.add(20);
        list3.add(10);
        list3.add(10);
        list3.add(10);
        Collections.sort(list3);
        ExpectedOutputList.add(list3);

        List<Integer> list4 = new ArrayList<>();
        list4.add(50);//1
        list4.add(10);//1
        list4.add(10);//2
        list4.add(10);//3
        list4.add(10);//4
        list4.add(10);//5
        Collections.sort(list4);
        ExpectedOutputList.add(list4);

        List<Integer> list5 = new ArrayList<>();
        list5.add(10);//1
        list5.add(10);//2
        list5.add(10);//3
        list5.add(10);//4
        list5.add(10);//5
        list5.add(10);//6
        list5.add(20);//1
        list5.add(20);//2
        Collections.sort(list5);
        ExpectedOutputList.add(list5);

        List<Integer> list6 = new ArrayList<>();;
        list6.add(50);//1
        list6.add(50);//2
        Collections.sort(list6);
        ExpectedOutputList.add(list6);

        List<Integer> list7 = new ArrayList<>();
        list7.add(20);//1
        list7.add(20);//2
        list7.add(20);//3
        list7.add(10);//1
        list7.add(10);//2
        list7.add(10);//3
        list7.add(10);//4
        Collections.sort(list7);
        ExpectedOutputList.add(list7);

        List<Integer> list8 = new ArrayList<>();
        list8.add(20);//1
        list8.add(20);//2
        list8.add(20);//3
        list8.add(20);//4
        list8.add(10);//1
        list8.add(10);//2
        Collections.sort(list8);
        ExpectedOutputList.add(list8);

        List<Integer> list9 = new ArrayList<>();
        list9.add(20);//1
        list9.add(20);//2
        list9.add(20);//3
        list9.add(20);//4
        list9.add(20);//5
        Collections.sort(list9);
        ExpectedOutputList.add(list9);


        List<Integer> list10 = new ArrayList<>();
        list10.add(10);//1
        list10.add(10);//2
        list10.add(10);//3
        list10.add(10);//4
        list10.add(10);//5
        list10.add(10);//6
        list10.add(10);//7
        list10.add(10);//8
        list10.add(10);//9
        list10.add(10);//10
        Collections.sort(list10);
        ExpectedOutputList.add(list10);

        //sort lists before testing equality

        result = MapUtil.sortListOfUnsortedListsAscendingly(result);
        ExpectedOutputList = MapUtil.sortListOfUnsortedListsAscendingly(ExpectedOutputList);
        System.out.println(result.toString());
        System.out.println(ExpectedOutputList.toString());
        Assert.assertEquals(ExpectedOutputList,result);
    }

    @Test
    public void testGetIntegerList() throws Exception {
        List<Integer> outputList = TestUtils.getIntegerList("20,20,20,20,20,50,50,10,10,10,10,10,10,10,10,10,10",COMMA_REGULAR_EXPRESSION);
        List<Integer> expectedList = new ArrayList<>();
        expectedList.add(20);//1
        expectedList.add(20);//2
        expectedList.add(20);//3
        expectedList.add(20);//4
        expectedList.add(20);//5
        expectedList.add(50);//1
        expectedList.add(50);//2
        expectedList.add(10);//1
        expectedList.add(10);//2
        expectedList.add(10);//3
        expectedList.add(10);//4
        expectedList.add(10);//5
        expectedList.add(10);//6
        expectedList.add(10);//7
        expectedList.add(10);//8
        expectedList.add(10);//9
        expectedList.add(10);//10
        Collections.sort(expectedList);
        Collections.sort(outputList);
        Assert.assertEquals(expectedList, outputList);




    }




}
