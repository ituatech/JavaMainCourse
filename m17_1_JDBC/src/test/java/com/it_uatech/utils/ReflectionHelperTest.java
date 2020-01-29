package com.it_uatech.utils;

import com.it_uatech.annotations.Column;
import com.it_uatech.annotations.Table;
import com.it_uatech.entities.UserDataSet;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.junit.Assert.*;

public class ReflectionHelperTest {

    UserDataSet uds;

    @Before
    public void setUp(){
        uds = new UserDataSet("anna",54);
    }

    @Test
    public void shouldCheckIfAnnotationPresent() {
        assertTrue(ReflectionHelper.typeAnnotationIsPresent(uds.getClass(), Table.class));
        assertFalse(ReflectionHelper.typeAnnotationIsPresent(uds.getClass(), Column.class));
    }

    @Test
    public void shouldGetAllAnnotatedFields(){
        List<Field> fieldList = ReflectionHelper.getFieldWithAnnotation(uds.getClass(),Column.class);
        assertEquals(2,fieldList.size());
        fieldList = ReflectionHelper.getFieldWithAnnotation(uds.getClass(),Table.class);
        assertEquals(0,fieldList.size());
    }

    @Test
    public void shouldGetFieldValue() throws IllegalAccessException {
        List<Field> fieldList = ReflectionHelper.getFieldWithAnnotation(uds.getClass(),Column.class);
        String name = (String) ReflectionHelper.getFieldValue(fieldList.get(0),uds);
        int age = (int) ReflectionHelper.getFieldValue(fieldList.get(1),uds);
        assertEquals("anna",name);
        assertEquals(54,age);
    }

    @Test
    public void shouldInstantiateObject() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Object obj = ReflectionHelper.instantiate(UserDataSet.class,"de",56);
        assertNotNull(obj);
    }

    @Test
    public void shouldSetFieldValueInObject() throws NoSuchFieldException, IllegalAccessException {
        int age = 67;
        ReflectionHelper.setFieldValue(age,uds.getClass().getDeclaredField("age"),uds);
        assertEquals(age,uds.getAge());
    }
}