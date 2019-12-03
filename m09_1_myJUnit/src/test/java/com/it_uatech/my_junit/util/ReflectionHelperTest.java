package com.it_uatech.my_junit.util;

import com.it_uatech.class_for_testing.TestClassFirst;
import com.it_uatech.class_for_testing.TestClassSecond;
import com.it_uatech.my_junit.annotations.AfterCust;
import com.it_uatech.my_junit.annotations.BeforeCust;
import com.it_uatech.my_junit.annotations.TestCust;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectionHelperTest {

    @Test
    public void scanClassByAvailableAnnotation(){
        List<Method> list = new ArrayList<>();
        list = ReflectionHelper.getMethodByAnnotation(TestClassFirst.class, BeforeCust.class);
        Assert.assertEquals("setUpBefore",list.get(0).getName());
        Assert.assertTrue(list.size()==1);

        list = ReflectionHelper.getMethodByAnnotation(TestClassFirst.class, TestCust.class);
        Assert.assertEquals("test1",list.get(0).getName());
        Assert.assertEquals("test2",list.get(1).getName());
        Assert.assertEquals("test3",list.get(2).getName());
        Assert.assertTrue(list.size()==3);

        list = ReflectionHelper.getMethodByAnnotation(TestClassFirst.class, AfterCust.class);
        Assert.assertEquals("finish",list.get(0).getName());
        Assert.assertTrue(list.size()==1);
    }

    @Test
    public void instantiateObjectFromClass(){
        TestClassFirst testClassFirst = ReflectionHelper.instantiateClass(TestClassFirst.class);
        Assert.assertNotNull(testClassFirst);
    }

    @Test
    public void invocationMethodFromObject(){
        List<Method> list = new ArrayList<>();
        TestClassFirst testClassFirst = ReflectionHelper.instantiateClass(TestClassFirst.class);
        list = ReflectionHelper.getMethodByAnnotation(TestClassFirst.class, BeforeCust.class);
        ReflectionHelper.invokeMethod(testClassFirst, list.get(0));
        Assert.assertEquals("call @Before",testClassFirst.getBefore());
    }

    @Test
    public void getClassesFromPackage(){
        Class<?>[] retriveClasses = ReflectionHelper.getClassesFromPackage("com.it_uatech.class_for_testing");
        Assert.assertEquals(2,retriveClasses.length);
        Assert.assertTrue(retriveClasses[0] == TestClassFirst.class | retriveClasses[0] == TestClassFirst.class);
        Assert.assertTrue(retriveClasses[1] == TestClassSecond.class | retriveClasses[1] == TestClassSecond.class);
    }
}
