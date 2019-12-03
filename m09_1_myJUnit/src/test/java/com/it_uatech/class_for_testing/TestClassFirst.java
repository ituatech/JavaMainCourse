package com.it_uatech.class_for_testing;

import com.it_uatech.my_junit.annotations.AfterCust;
import com.it_uatech.my_junit.annotations.BeforeCust;
import com.it_uatech.my_junit.annotations.TestCust;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TestClassFirst {

    private static Logger logger = LoggerFactory.getLogger(TestClassFirst.class);

    private String before;
    private String test;
    private String after;

    private static List<String> list = new ArrayList<>();

    @BeforeCust
    public void setUpBefore(){
        before = "call @Before";
        list.add(before);
        logger.info(before);
    }

    @TestCust
    public void test1(){
        test = "call test 1";
        list.add(test);
        logger.info(test);
    }

    @TestCust
    public void test2(){
        test = "call test 2";
        list.add(test);
        logger.info(test);
    }

    @TestCust
    public void test3(){
        test = "call test 3";
        list.add(test);
        logger.info(test);
    }

    @AfterCust
    public void finish(){
        after = "call @After";
        list.add(after);
        logger.info(after);
    }

    public String getBefore() {
        return before;
    }

    public static List<String> getList() {
        return list;
    }

    public static void clearList() {
        list.clear();
    }
}
