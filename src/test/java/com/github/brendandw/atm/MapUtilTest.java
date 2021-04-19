package com.github.brendandw.atm;


import com.github.brendandw.atm.util.MapUtil;

import java.util.*;

import io.swagger.models.auth.In;
import org.junit.Assert;
import org.junit.Test;
import com.github.brendandw.atm.helpers.MapComparator;

        /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brendandw
 */
public class MapUtilTest {

    //Ensure that input objects are not mutated
    @Test
    public void mapAscByValueThenKey_should_not_mutate_original_object() throws Exception {
        Map<Integer,Integer> linkedHashMap1 = new LinkedHashMap<Integer, Integer>();
        linkedHashMap1.put(20,1);//1
        linkedHashMap1.put(6,3);//1
        linkedHashMap1.put(2,4);//2
        linkedHashMap1.put(5,3);//4
        linkedHashMap1.put(8,3);//7
        linkedHashMap1.put(1,10);//9

        MapUtil.sortMapAscByValueThenKey(linkedHashMap1);

        //TODO: Just create a deep copy of map1
        Map<Integer,Integer> linkedHashMap2 = new LinkedHashMap<Integer, Integer>();
        linkedHashMap2.put(20,1);//1
        linkedHashMap2.put(6,3);//1
        linkedHashMap2.put(2,4);//2
        linkedHashMap2.put(5,3);//4
        linkedHashMap2.put(8,3);//7
        linkedHashMap2.put(1,10);//9

        //Make sure that input was not unintentionally modified
        Assert.assertEquals(linkedHashMap1.size(),linkedHashMap2.size());
        assertMapsEqual(linkedHashMap1,linkedHashMap2);
    }

    @Test
    public void sortMapAscByValueKey_test() throws Exception {
        Map<Integer,Integer> linkedHashMap = new LinkedHashMap<Integer, Integer>();
        linkedHashMap.put(20,1);//1
        linkedHashMap.put(6,3);//1
        linkedHashMap.put(2,4);//2
        linkedHashMap.put(5,3);//4
        linkedHashMap.put(8,3);//7
        linkedHashMap.put(1,10);//9
        linkedHashMap = MapUtil.sortMapAscByValueThenKey(linkedHashMap);

        int counter = 0;
        for (Map.Entry<Integer,Integer> entry: linkedHashMap.entrySet()) {
            if (counter==0) {
                Assert.assertTrue(entry.getValue().equals(1));
                Assert.assertTrue(entry.getKey().equals(20));
            }
            else if (counter==1) {
                Assert.assertTrue(entry.getValue().equals(3));
                Assert.assertTrue(entry.getKey().equals(5));
            }
            else if (counter==2) {
                Assert.assertTrue(entry.getValue().equals(3));
                Assert.assertTrue(entry.getKey().equals(6));
            }
            else if (counter==3) {
                Assert.assertTrue(entry.getValue().equals(3));
                Assert.assertTrue(entry.getKey().equals(8));
            }
            else if (counter==4) {
                Assert.assertTrue(entry.getValue().equals(4));
                Assert.assertTrue(entry.getKey().equals(2));
            }
            else if (counter==5) {
                Assert.assertTrue(entry.getValue().equals(10));
                Assert.assertTrue(entry.getKey().equals(1));
            }
            counter++;
        }
    }

    @Test
    public void sortMapDescByValueKey_test() throws Exception {
        Map<Integer,Integer> linkedHashMap = new LinkedHashMap<Integer, Integer>();
        linkedHashMap.put(20,1);
        linkedHashMap.put(6,3);
        linkedHashMap.put(2,4);
        linkedHashMap.put(5,3);
        linkedHashMap.put(8,3);
        linkedHashMap.put(1,10);
        linkedHashMap = MapUtil.sortMapDescByValueThenKey(linkedHashMap);

        int counter = 0;
        for (Map.Entry<Integer,Integer> entry: linkedHashMap.entrySet()) {
            if (counter==5) {
                Assert.assertTrue(entry.getValue().equals(1));
                Assert.assertTrue(entry.getKey().equals(20));
            }
            else if (counter==4) {
                Assert.assertTrue(entry.getValue().equals(3));
                Assert.assertTrue(entry.getKey().equals(5));
            }
            else if (counter==3) {
                Assert.assertTrue(entry.getValue().equals(3));
                Assert.assertTrue(entry.getKey().equals(6));
            }
            else if (counter==2) {
                Assert.assertTrue(entry.getValue().equals(3));
                Assert.assertTrue(entry.getKey().equals(8));
            }
            else if (counter==1) {
                Assert.assertTrue(entry.getValue().equals(4));
                Assert.assertTrue(entry.getKey().equals(2));
            }
            else if (counter==0) {
                Assert.assertTrue(entry.getValue().equals(10));
                Assert.assertTrue(entry.getKey().equals(1));
            }
            counter++;
        }
    }

    @Test
    public void sortMapDescByValueKey_should_not_mutate_original_object() throws Exception {
        Map<Integer,Integer> linkedHashMap1 = new LinkedHashMap<Integer, Integer>();
        linkedHashMap1.put(20,1);//1
        linkedHashMap1.put(6,3);//1
        linkedHashMap1.put(2,4);//2
        linkedHashMap1.put(5,3);//4
        linkedHashMap1.put(8,3);//7
        linkedHashMap1.put(1,10);//9

        MapUtil.sortMapDescByValueThenKey(linkedHashMap1);

        Map<Integer,Integer> linkedHashMap2 = new LinkedHashMap<Integer, Integer>();
        linkedHashMap2.put(20,1);//1
        linkedHashMap2.put(6,3);//1
        linkedHashMap2.put(2,4);//2
        linkedHashMap2.put(5,3);//4
        linkedHashMap2.put(8,3);//7
        linkedHashMap2.put(1,10);//9

        //Make sure that input was not unintentionally modified
        Assert.assertEquals(linkedHashMap1.size(),linkedHashMap2.size());
        assertMapsEqual(linkedHashMap1,linkedHashMap2);
    }

    /*
        List1 and List 2 contains exactly the same maps, but in a different order.
        Thus, before they are sorted, they should return false for equality;
        and after sorting should return true.
    */
    @Test
    public void mapComparator_test() throws Exception {
        Map<Integer,Integer> linkedHashMap1 = new LinkedHashMap<Integer, Integer>();
        linkedHashMap1.put(20,1);
        linkedHashMap1.put(6,3);
        linkedHashMap1.put(2,4);
        linkedHashMap1.put(5,3);

        Map<Integer,Integer> linkedHashMap2 = new LinkedHashMap<Integer, Integer>();
        linkedHashMap2.put(2,4);
        linkedHashMap2.put(5,3);
        linkedHashMap2.put(8,3);
        linkedHashMap2.put(1,10);

        //same entries as map 2, but in a different order
        Map<Integer,Integer> linkedHashMap3 = new LinkedHashMap<Integer, Integer>();
        linkedHashMap3.put(1,10);
        linkedHashMap3.put(8,3);
        linkedHashMap3.put(5,3);
        linkedHashMap3.put(2,4);



        //same entries as map 2, but in a different order
        Map<Integer,Integer> linkedHashMap4 = new LinkedHashMap<Integer, Integer>();
        linkedHashMap4.put(5,3);
        linkedHashMap4.put(2,4);
        linkedHashMap4.put(6,3);
        linkedHashMap4.put(20,1);


        List<Map<Integer,Integer>> listOfMaps1 = new ArrayList<>();
        List<Map<Integer,Integer>> listOfMaps2 = new ArrayList<>();

        listOfMaps1.add(linkedHashMap2);
        listOfMaps1.add(linkedHashMap1);
        listOfMaps1.add(linkedHashMap3);
        listOfMaps1.add(linkedHashMap4);

        listOfMaps2.add(linkedHashMap1);
        listOfMaps2.add(linkedHashMap2);
        listOfMaps2.add(linkedHashMap4);
        listOfMaps2.add(linkedHashMap3);

        Collections.sort(listOfMaps1,new MapComparator());
        Collections.sort(listOfMaps2,new MapComparator());

        assertCollectionOfMapsEqual(listOfMaps1,listOfMaps2);
    }

    @Test
    public void sortListOfMaps_should_not_mutate_input() throws Exception {
        Map<Integer,Integer> inputMap1 = new LinkedHashMap<>();
        inputMap1.put(20,1);
        inputMap1.put(6,3);
        inputMap1.put(2,4);
        inputMap1.put(5,3);

        Map<Integer,Integer> outputMap1 = new LinkedHashMap<>();
        outputMap1.put(20,1);
        outputMap1.put(6,3);
        outputMap1.put(2,4);
        outputMap1.put(5,3);

        Map<Integer,Integer> inputMap2 = new LinkedHashMap<>();
        inputMap2.put(5,3);
        inputMap2.put(2,4);
        inputMap2.put(8,3);
        inputMap2.put(1,10);

        Map<Integer,Integer> outputMap2 = new LinkedHashMap<>();
        outputMap2.put(5,3);
        outputMap2.put(2,4);
        outputMap2.put(8,3);
        outputMap2.put(1,10);

        Map<Integer,Integer> inputMap3 = new LinkedHashMap<>();
        inputMap3.put(1,10);
        inputMap3.put(8,3);
        inputMap3.put(5,3);
        inputMap3.put(2,4);

        Map<Integer,Integer> outputMap3 = new LinkedHashMap<>();
        outputMap3.put(1,10);
        outputMap3.put(8,3);
        outputMap3.put(5,3);
        outputMap3.put(2,4);


        Map<Integer,Integer> inputMap4 = new LinkedHashMap<>();
        inputMap4.put(5,3);
        inputMap4.put(2,4);
        inputMap4.put(6,3);
        inputMap4.put(20,1);

        Map<Integer,Integer> outputMap4 = new LinkedHashMap<>();
        outputMap4.put(5,3);
        outputMap4.put(2,4);
        outputMap4.put(6,3);
        outputMap4.put(20,1);


        List<Map<Integer,Integer>> inputList = new ArrayList<>();
        List<Map<Integer,Integer>> outputList = new ArrayList<>();

        inputList.add(inputMap2);
        inputList.add(inputMap1);
        inputList.add(inputMap3);
        inputList.add(inputMap4);

        outputList.add(outputMap2);
        outputList.add(outputMap1);
        outputList.add(outputMap3);
        outputList.add(outputMap4);

        Assert.assertEquals(outputList.size(),inputList.size());
        assertCollectionOfMapsEqual(inputList,outputList);
    }



    /*
    The below test compares two lists with one map each that contains the exact same entries,
    but in a different order. This should return true.
    */
    @Test
    public void list_of_maps_with_same_values_in_different_order_should_equal_true() throws Exception {
        Map<Integer,Integer> linkedHashMap1 = new LinkedHashMap<Integer, Integer>();
        linkedHashMap1.put(20,1);
        linkedHashMap1.put(6,3);
        linkedHashMap1.put(2,4);
        linkedHashMap1.put(5,3);

        //same entries as map1, but in a different order
        Map<Integer,Integer> linkedHashMap2 = new LinkedHashMap<Integer, Integer>();
        linkedHashMap2.put(5,3);
        linkedHashMap2.put(2,4);
        linkedHashMap2.put(6,3);
        linkedHashMap2.put(20,1);


        List<Map<Integer,Integer>> listOfMaps1 = new ArrayList<>();
        List<Map<Integer,Integer>> listOfMaps2 = new ArrayList<>();

        listOfMaps1.add(linkedHashMap1);
        listOfMaps2.add(linkedHashMap2);

        Assert.assertTrue(listOfMaps1.equals(listOfMaps2));
    }

    /*
    The below test compares two lists with one map each that contains the same reversed raw values.
    i.e: map1(1,2)!=map2(2,1)
    */
    @Test
    public void list_of_maps_with_tuples_reversed_should_not_equal_true() throws Exception {
        Map<Integer,Integer> linkedHashMap1 = new LinkedHashMap<Integer, Integer>();
        linkedHashMap1.put(2,1);

        //same raw values as map1, but with key, value reversed
        Map<Integer,Integer> linkedHashMap2 = new LinkedHashMap<Integer, Integer>();
        linkedHashMap2.put(1,2);


        List<Map<Integer,Integer>> listOfMaps1 = new ArrayList<>();
        List<Map<Integer,Integer>> listOfMaps2 = new ArrayList<>();

        listOfMaps1.add(linkedHashMap1);
        listOfMaps2.add(linkedHashMap2);

        Assert.assertFalse(listOfMaps1.equals(listOfMaps2));
    }

    @Test
    public void get_flat_list_from_map_test() throws Exception {
        Map<Integer,Integer> linkedHashMap = new LinkedHashMap<Integer, Integer>();
        linkedHashMap.put(20,1);//1
        linkedHashMap.put(6,3);//1
        linkedHashMap.put(2,4);//2
        linkedHashMap.put(5,3);//4
        linkedHashMap.put(8,3);//7
        linkedHashMap.put(1,10);//9
        List<Integer> outpList = MapUtil.getFlatListFromMap(linkedHashMap);
        List<Integer> expectedList = new ArrayList<>();
        expectedList.add(20);
        expectedList.add(6);
        expectedList.add(6);
        expectedList.add(6);
        expectedList.add(2);
        expectedList.add(2);
        expectedList.add(2);
        expectedList.add(2);
        expectedList.add(5);
        expectedList.add(5);
        expectedList.add(5);
        expectedList.add(8);
        expectedList.add(8);
        expectedList.add(8);
        expectedList.add(1);//1
        expectedList.add(1);//2
        expectedList.add(1);//3
        expectedList.add(1);//4
        expectedList.add(1);//5
        expectedList.add(1);//6
        expectedList.add(1);//7
        expectedList.add(1);//8
        expectedList.add(1);//9
        expectedList.add(1);//10
        Collections.sort(outpList);
        Collections.sort(expectedList);
        assertCollectionEqual(outpList,expectedList);
    }

    @Test
    public void testGetMapFromList() throws Exception {
        List<Integer> inputList = new ArrayList<>();
        inputList.add(20);
        inputList.add(6);
        inputList.add(6);
        inputList.add(6);
        inputList.add(2);
        inputList.add(2);
        inputList.add(2);
        inputList.add(2);
        inputList.add(5);
        inputList.add(5);
        inputList.add(5);
        inputList.add(8);
        inputList.add(8);
        inputList.add(8);
        inputList.add(1);//1
        inputList.add(1);//2
        inputList.add(1);//3
        inputList.add(1);//4
        inputList.add(1);//5
        inputList.add(1);//6
        inputList.add(1);//7
        inputList.add(1);//8
        inputList.add(1);//9
        inputList.add(1);//10

        Map<Integer,Integer> expectedMap = new LinkedHashMap<Integer, Integer>();
        expectedMap.put(20,1);//1
        expectedMap.put(6,3);//1
        expectedMap.put(2,4);//2
        expectedMap.put(5,3);//4
        expectedMap.put(8,3);//7
        expectedMap.put(1,10);//9

        Map<Integer,Integer> outpMap = MapUtil.getMapFromList(inputList);
        outpMap = MapUtil.sortMapAscByValueThenKey(outpMap);
        expectedMap = MapUtil.sortMapAscByValueThenKey(expectedMap);
        assertMapsEqual(outpMap, expectedMap);
    }

    @Test
    public void sortSetOfUnsortedListsAscendingly_should_not_mutate_input_object() throws Exception {
        List<Integer> inputList1 = new ArrayList<>();
        inputList1.add(3);
        inputList1.add(2);
        inputList1.add(1);

        List<Integer> inputList2 = new ArrayList<>();
        inputList2.add(4);
        inputList2.add(3);
        inputList2.add(2);

        List<Integer> inputList3 = new ArrayList<>();
        inputList3.add(4);
        inputList3.add(2);
        inputList3.add(1);

        List<Integer> inputList4 = new ArrayList<>();
        inputList4.add(2);
        inputList4.add(1);

        List<Integer> inputList5 = new ArrayList<>();
        inputList5.add(5);
        inputList5.add(4);

        List<Integer> inputList6 = new ArrayList<>();
        inputList6.add(6);

        Set<List<Integer>> inputSet = new LinkedHashSet<>();
        inputSet.add(inputList3);
        inputSet.add(inputList4);
        inputSet.add(inputList2);
        inputSet.add(inputList1);
        inputSet.add(inputList6);
        inputSet.add(inputList5);

        MapUtil.sortSetOfUnsortedListsAscendingly(inputSet);

        List<Integer> expectedList1 = new ArrayList<>();
        expectedList1.add(3);
        expectedList1.add(2);
        expectedList1.add(1);

        List<Integer> expectedList2 = new ArrayList<>();
        expectedList2.add(4);
        expectedList2.add(3);
        expectedList2.add(2);

        List<Integer> expectedList3 = new ArrayList<>();
        expectedList3.add(4);
        expectedList3.add(2);
        expectedList3.add(1);

        List<Integer> expectedList4 = new ArrayList<>();
        expectedList4.add(2);
        expectedList4.add(1);

        List<Integer> expectedList5 = new ArrayList<>();
        expectedList5.add(5);
        expectedList5.add(4);

        List<Integer> expectedList6 = new ArrayList<>();
        expectedList6.add(6);

        Set<List<Integer>> expectedSet = new LinkedHashSet<>();
        expectedSet.add(expectedList3);
        expectedSet.add(expectedList4);
        expectedSet.add(expectedList2);
        expectedSet.add(expectedList1);
        expectedSet.add(expectedList6);
        expectedSet.add(expectedList5);

        //original list should still
        assertCollectionOfListsEqual(inputSet,expectedSet);
    }


    @Test
    public void testSortSetOfUnsortedListsAscendingly() throws Exception {
        List<Integer> inputList1 = new ArrayList<>();
        inputList1.add(3);
        inputList1.add(2);
        inputList1.add(1);

        List<Integer> inputList2 = new ArrayList<>();
        inputList2.add(4);
        inputList2.add(3);
        inputList2.add(2);

        List<Integer> inputList3 = new ArrayList<>();
        inputList3.add(4);
        inputList3.add(2);
        inputList3.add(1);

        List<Integer> inputList4 = new ArrayList<>();
        inputList4.add(2);
        inputList4.add(1);

        List<Integer> inputList5 = new ArrayList<>();
        inputList5.add(5);
        inputList5.add(4);

        List<Integer> inputList6 = new ArrayList<>();
        inputList6.add(6);

        Set<List<Integer>> inputSet = new LinkedHashSet<>();
        inputSet.add(inputList3);
        inputSet.add(inputList4);
        inputSet.add(inputList2);
        inputSet.add(inputList1);
        inputSet.add(inputList6);
        inputSet.add(inputList5);

        Set<List<Integer>> outputSet = MapUtil.sortSetOfUnsortedListsAscendingly(inputSet);

        List<Integer> expectedList1 = new ArrayList<>();
        expectedList1.add(1);
        expectedList1.add(2);
        expectedList1.add(3);

        List<Integer> expectedList2 = new ArrayList<>();
        expectedList2.add(2);
        expectedList2.add(3);
        expectedList2.add(4);

        List<Integer> expectedList3 = new ArrayList<>();
        expectedList3.add(1);
        expectedList3.add(2);
        expectedList3.add(4);

        List<Integer> expectedList4 = new ArrayList<>();
        expectedList4.add(1);
        expectedList4.add(2);

        List<Integer> expectedList5 = new ArrayList<>();
        expectedList5.add(4);
        expectedList5.add(5);

        List<Integer> expectedList6 = new ArrayList<>();
        expectedList6.add(6);

        Set<List<Integer>> expectedSet = new LinkedHashSet<>();
        expectedSet.add(expectedList4);
        expectedSet.add(expectedList1);
        expectedSet.add(expectedList3);
        expectedSet.add(expectedList2);
        expectedSet.add(expectedList5);
        expectedSet.add(expectedList6);

        assertCollectionOfListsEqual(outputSet,expectedSet);


    }

    @Test
    public void testSortListOfUnsortedListsAscendingly() throws Exception {
        List<Integer> inputList1 = new ArrayList<>();
        inputList1.add(3);
        inputList1.add(2);
        inputList1.add(1);

        List<Integer> inputList2 = new ArrayList<>();
        inputList2.add(4);
        inputList2.add(3);
        inputList2.add(2);

        List<Integer> inputList3 = new ArrayList<>();
        inputList3.add(4);
        inputList3.add(2);
        inputList3.add(1);

        List<Integer> inputList4 = new ArrayList<>();
        inputList4.add(2);
        inputList4.add(1);

        List<Integer> inputList5 = new ArrayList<>();
        inputList5.add(5);
        inputList5.add(4);

        List<Integer> inputList6 = new ArrayList<>();
        inputList6.add(6);

        List<List<Integer>> inputList = new ArrayList<>();
        inputList.add(inputList3);
        inputList.add(inputList4);
        inputList.add(inputList2);
        inputList.add(inputList1);
        inputList.add(inputList6);
        inputList.add(inputList5);

        List<List<Integer>> outputList = MapUtil.sortListOfUnsortedListsAscendingly(inputList);


        List<Integer> expectedList1 = new ArrayList<>();
        expectedList1.add(1);
        expectedList1.add(2);
        expectedList1.add(3);

        List<Integer> expectedList2 = new ArrayList<>();
        expectedList2.add(2);
        expectedList2.add(3);
        expectedList2.add(4);

        List<Integer> expectedList3 = new ArrayList<>();
        expectedList3.add(1);
        expectedList3.add(2);
        expectedList3.add(4);

        List<Integer> expectedList4 = new ArrayList<>();
        expectedList4.add(1);
        expectedList4.add(2);

        List<Integer> expectedList5 = new ArrayList<>();
        expectedList5.add(4);
        expectedList5.add(5);

        List<Integer> expectedList6 = new ArrayList<>();
        expectedList6.add(6);

        List<List<Integer>> expectedList = new ArrayList<>();
        expectedList.add(expectedList4);
        expectedList.add(expectedList1);
        expectedList.add(expectedList3);
        expectedList.add(expectedList2);
        expectedList.add(expectedList5);
        expectedList.add(expectedList6);


        assertCollectionOfListsEqual(outputList,expectedList);

    }

    private void assertMapsEqual(Map<Integer,Integer> input,Map<Integer,Integer> expected) {
        Iterator<Map.Entry<Integer, Integer>> inputIter = input.entrySet().iterator();
        Iterator<Map.Entry<Integer, Integer>> expectedIter = expected.entrySet().iterator();

        while (inputIter.hasNext()) {
            Map.Entry<Integer, Integer> inputEntry = inputIter.next();
            Map.Entry<Integer, Integer> exp = expectedIter.next();
            Assert.assertEquals(exp.getKey(), inputEntry.getKey());
            Assert.assertEquals(exp.getValue(), inputEntry.getValue());
        }
    }

    private void assertCollectionOfListsEqual(Collection<List<Integer>> input,Collection<List<Integer>> expected) {
        Iterator<List<Integer>> inputIter = input.iterator();
        Iterator<List<Integer>> expectedIter = expected.iterator();

        while (inputIter.hasNext()) {
            assertCollectionEqual(inputIter.next(),expectedIter.next());

        }
    }



    private void assertCollectionEqual(Collection<Integer> input,Collection<Integer> expected) {
        Iterator<Integer> inputIter= input.iterator();
        Iterator<Integer> expectedIter= expected.iterator();

        while (inputIter.hasNext()) {
            Assert.assertEquals(expectedIter.next(),inputIter.next());
        }
    }

    private void assertCollectionOfMapsEqual(Collection<Map<Integer,Integer>> input,Collection<Map<Integer,Integer>> expected) {
        Iterator<Map<Integer,Integer>> inputIter= input.iterator();
        Iterator<Map<Integer,Integer>> expectedIter= expected.iterator();

        while (inputIter.hasNext()) {
            assertMapsEqual(inputIter.next(),expectedIter.next());
        }
    }




    }
