package com.it_uatech;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class MyArrayListTest {
    private MyArrayList<Integer> myArrayList;
    private MyArrayList<Integer> myArrayListUnsorted;
    private int size = 0;
    private Object IndexOutOfBoundsException;

    @Before
    public void createList(){
        myArrayList = new MyArrayList<Integer>();
        myArrayListUnsorted = new MyArrayList<Integer>();
        Assert.assertEquals(10,myArrayList.getArrayLength());
        for (int i = 0; i < 10; i++){
            myArrayList.add(i);
            myArrayListUnsorted.add(10-i);
            size++;
        }
        Assert.assertEquals(size,myArrayList.size());
        Assert.assertEquals(size,myArrayList.getArrayLength());
    }

    @Test
    public void growTest(){
        myArrayList.add(10);
        Assert.assertEquals(((10>>1)+10), myArrayList.getArrayLength());
        Assert.assertArrayEquals(new Integer[]{0,1,2,3,4,5,6,7,8,9,10}, myArrayList.toArray());
    }

    @Test
    public void sortTest(){
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        };
        Assert.assertArrayEquals(new Integer[]{10,9,8,7,6,5,4,3,2,1}, myArrayListUnsorted.toArray());
        Collections.sort(myArrayListUnsorted,comparator);
        Assert.assertArrayEquals(new Integer[]{1,2,3,4,5,6,7,8,9,10}, myArrayListUnsorted.toArray());
    }

    @Test
    public void arrayCopyTest(){
        MyArrayList<Integer> myArrayList1 = new MyArrayList<Integer>();
        MyArrayList<Integer> myArrayList2 = new MyArrayList<Integer>();
        for (int i=0;i<10;i++){
            myArrayList1.add(i);
            if(i<6) {
                myArrayList2.add(10 - i);
            }
        }
        Collections.copy(myArrayList1,myArrayList2);
        Assert.assertArrayEquals(new Integer[]{10,9,8,7,6,5,6,7,8,9}, myArrayList1.toArray());
        myArrayList1.add(10);
        Assert.assertThrows(IndexOutOfBoundsException.class,()->Collections.copy(myArrayList2,myArrayList1));
        Assert.assertThrows(IndexOutOfBoundsException.class,()->myArrayList1.get(99));
        Assert.assertThrows(IndexOutOfBoundsException.class,()->myArrayList1.add(67,99));
        Assert.assertThrows(IndexOutOfBoundsException.class,()->myArrayList1.remove(99));
        Assert.assertThrows(IndexOutOfBoundsException.class,()->myArrayList1.set(99,65));
    }

    @Test
    public void addAllTest(){
        myArrayList.add(10);
        Collections.addAll(myArrayList,3,4,5,6);
        Assert.assertArrayEquals(new Integer[]{0,1,2,3,4,5,6,7,8,9,10,3,4,5,6},myArrayList.toArray());
    }

    @Test
    public void removeTest(){
        int a = myArrayList.remove(5);
        Assert.assertEquals(5,a);
        Assert.assertArrayEquals(new Integer[]{0,1,2,3,4,6,7,8,9},myArrayList.toArray());
        Assert.assertEquals(9,myArrayList.size());
    }

    @Test
    public void removeObjectTest(){
        boolean a = myArrayList.remove(new Integer(4));
        Assert.assertTrue(a);
        Assert.assertArrayEquals(new Integer[]{0,1,2,3,5,6,7,8,9},myArrayList.toArray());
        Assert.assertEquals(9,myArrayList.size());
    }

    @Test
    public void addIndexTest(){
        myArrayList.add(5,1);
        Assert.assertArrayEquals(new Integer[]{0,1,2,3,4,1,5,6,7,8,9},myArrayList.toArray());
        Assert.assertEquals(11,myArrayList.size());
    }

    @Test
    public void clearEmptyTest(){
        myArrayList.clear();
        Assert.assertTrue(myArrayList.isEmpty());
        Assert.assertTrue(0==myArrayList.size());
        Assert.assertTrue(10==myArrayList.getArrayLength());
    }

    @Test
    public void containsTest(){
        Assert.assertTrue(myArrayList.contains(6));
        Assert.assertFalse(myArrayList.contains(12));
    }

    @Test
    public void getIndexTest(){
        Assert.assertEquals(new Integer(7),myArrayList.get(7));
    }

    @Test
    public void indexOfTest(){
        Assert.assertEquals(8,myArrayList.indexOf(8));
    }

    @Test
    public void iteratorTest(){
        int i = 0;
        ListIterator<Integer> iterator1 = myArrayList.listIterator();
        while(iterator1.hasNext()){
            Assert.assertEquals(iterator1.next(),new Integer(i));
            i++;
        }
        ListIterator<Integer> iterator = myArrayList.listIterator();
        iterator.next();
        iterator.next();
        Assert.assertEquals(new Integer(1),iterator.previous());
        iterator.next();
        Assert.assertEquals(new Integer(2),iterator.next());
        iterator.remove();
        Assert.assertArrayEquals(new Integer[]{0,1,3,4,5,6,7,8,9},myArrayList.toArray());
        Assert.assertEquals(new Integer(1),iterator.previous());
        iterator.next();
        Assert.assertEquals(new Integer(3),iterator.next());
        iterator.add(2);
        Assert.assertArrayEquals(new Integer[]{0,1,3,2,4,5,6,7,8,9},myArrayList.toArray());
        iterator.previous();
        Assert.assertEquals(new Integer(2),iterator.next());
        iterator.next();
        iterator.next();
        iterator.next(); //6
        iterator.set(78);
        iterator.previous();//5
        iterator.previous();//4
        Assert.assertArrayEquals(new Integer[]{0,1,3,2,4,5,78,7,8,9},myArrayList.toArray());
        Assert.assertEquals(4,iterator.previousIndex());
        Assert.assertEquals(5,iterator.nextIndex());
        iterator.add(7);
        Assert.assertThrows(IllegalStateException.class,()->iterator.set(99));
        Assert.assertThrows(IllegalStateException.class,()->iterator.remove());
    }
}
