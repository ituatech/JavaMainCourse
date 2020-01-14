package com.it_uatech.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ReflectionHelper {

    static Map<String,Class<?>> primitivesMap = new HashMap<>();
    static {
        primitivesMap.put("Integer", Integer.TYPE );
        primitivesMap.put("Long", Long.TYPE );
        primitivesMap.put("Double", Double.TYPE );
        primitivesMap.put("Float", Float.TYPE );
        primitivesMap.put("Boolean", Boolean.TYPE );
        primitivesMap.put("Character", Character.TYPE );
        primitivesMap.put("Byte", Byte.TYPE );
        primitivesMap.put("Void", Void.TYPE );
        primitivesMap.put("Short", Short.TYPE );
    }

    public static <A extends Annotation> boolean typeAnnotationIsPresent(Class<?> checkingClass, Class<A> annotationClass) {
        return checkingClass.isAnnotationPresent(annotationClass);
    }

    public static <A extends Annotation> List<Field> getFieldWithAnnotation(Class<?> checkingClass, Class<A> annotationClass) {
        List<Field> fieldsList = new ArrayList<>();
        Field[] fields = checkingClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(annotationClass)) {
                fieldsList.add(field);
            }
        }
        return fieldsList;
    }

    public static Object getFieldValue(Field field, Object referencedObject) throws IllegalAccessException {
        boolean accessible = field.canAccess(referencedObject);
        field.setAccessible(true);
        try {
            return field.get(referencedObject);
        } finally {
            field.setAccessible(accessible);
        }
    }

    public static void setFieldValue(Object value, Field field, Object referencedObject) throws IllegalAccessException {
        boolean accessible = field.canAccess(referencedObject);
        field.setAccessible(true);
        try {
            field.set(referencedObject,value);
        } finally {
            field.setAccessible(accessible);
        }
    }

    public static <T> T instantiate(Class<T> type, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (args.length == 0) {
            return type.getDeclaredConstructor().newInstance();
        }
        Class<?>[] classes = Arrays.asList(args).stream().map(arg -> {
            Class<?> cl;
            String str =arg.getClass().getSimpleName();
            if (primitivesMap.containsKey(str)) {
                cl = primitivesMap.get(str);
            }else{
                cl = arg.getClass();
            }
            return cl;
        }).toArray(Class<?>[]::new);
        return type.getDeclaredConstructor(classes).newInstance(args);
    }
}
