package com.it_uatech.my_junit;

import com.it_uatech.my_junit.annotations.AfterCust;
import com.it_uatech.my_junit.annotations.BeforeCust;
import com.it_uatech.my_junit.annotations.TestCust;
import com.it_uatech.my_junit.util.ReflectionHelper;
import sun.reflect.ReflectionFactory;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

public class Runner {

    private Runner(){}

    public static void runnerStart(Class<?>... typeClass) {
        for (Class<?> type : typeClass) {
            List<Method> testMethodList = ReflectionHelper.getMethodByAnnotation(type, TestCust.class);
            List<Method> beforeMethodList = ReflectionHelper.getMethodByAnnotation(type, BeforeCust.class);
            List<Method> afterMethodList = ReflectionHelper.getMethodByAnnotation(type, AfterCust.class);

            for(Method method : testMethodList){
                Object obj = ReflectionHelper.instantiateClass(type);

                invokeMethods(obj,beforeMethodList); // invoke @Before
                invokeMethod(obj, method);           // invoke @Test
                invokeMethods(obj,afterMethodList);  // invoke @After
            }
        }
    }

    public static void runnerStart(String packageScan){
        Class<?>[] classes = ReflectionHelper.getClassesFromPackage(packageScan);
        runnerStart(classes);
    }

    static void invokeMethod(Object obj, Method method){
        ReflectionHelper.invokeMethod(obj,method);
    }

    static void invokeMethods(Object obj, List<Method> methodList){
        for(Method method : methodList) {
            invokeMethod(obj,method);
        }
    }
}

