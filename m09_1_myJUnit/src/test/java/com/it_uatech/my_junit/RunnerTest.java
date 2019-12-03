package com.it_uatech.my_junit;

import com.it_uatech.class_for_testing.TestClassFirst;
import com.it_uatech.class_for_testing.TestClassSecond;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RunnerTest {

    private List<String> list;

    @Before
    public void setUp(){
        TestClassFirst.clearList();

        list = new ArrayList<>();

        list.add("call @Before");
        list.add("call test 1");
        list.add("call @After");

        list.add("call @Before");
        list.add("call test 2");
        list.add("call @After");

        list.add("call @Before");
        list.add("call test 3");
        list.add("call @After");
    }

    @Test
    public void testPriorityByMethodCall(){
        Runner.runnerStart(TestClassFirst.class);
        List<String> testList = TestClassFirst.getList();
        Assert.assertArrayEquals(list.toArray(),testList.toArray());
        Assert.assertEquals(list.size(),testList.size());
    }

    @Test
    public void testPriorityByMethodCallWithTwoClasses(){
        Runner.runnerStart(TestClassFirst.class, TestClassSecond.class);
        List<String> testList1 = TestClassFirst.getList();
        Assert.assertArrayEquals(list.toArray(),testList1.toArray());
        Assert.assertEquals(list.size(),testList1.size());
        List<String> testList2 = TestClassFirst.getList();
        Assert.assertArrayEquals(list.toArray(),testList2.toArray());
        Assert.assertEquals(list.size(),testList2.size());
    }

    @Test
    public void testPriorityByMethodCallPackageScan(){
        Runner.runnerStart("com.it_uatech.class_for_testing");
        List<String> testList1 = TestClassFirst.getList();
        Assert.assertArrayEquals(list.toArray(),testList1.toArray());
        Assert.assertEquals(list.size(),testList1.size());
        List<String> testList2 = TestClassFirst.getList();
        Assert.assertArrayEquals(list.toArray(),testList2.toArray());
        Assert.assertEquals(list.size(),testList2.size());
    }
}
