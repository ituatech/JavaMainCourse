package com.it_uatech.my_junit.util;

import org.reflections.Reflections;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ReflectionHelper {

    private ReflectionHelper(){}

    public static <T> List<Method> getMethodByAnnotation(Class<T> classType, Class<? extends Annotation> annotation) {
        List<Method> annotatedMethod = new ArrayList<>();
        Object obj = instantiateClass(classType);
        Method[] methods = classType.getDeclaredMethods();
        for (Method method : methods) {
            if(method.getAnnotation(annotation) != null){
                if (!method.canAccess(obj)){
                    throw new RuntimeException("Method must have public access");
                }
                if(method.getReturnType() != void.class){
                    throw new RuntimeException("Return parameter must be void");
                }
                if(method.getParameterCount() > 0){
                    throw new RuntimeException("Method must not have any parameters");
                }

                annotatedMethod.add(method);
            }
        }
        return annotatedMethod;
    }

    public static <T> T instantiateClass(Class<T> typeClass) {
        try {
            T something = typeClass.getConstructor().newInstance();
            return something;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
         throw new RuntimeException("Constructor must be public and without parameters.",e);
        }
    }

    public static void invokeMethod(Object obj, Method method) {
        try {
            method.invoke(obj);
        } catch (IllegalAccessException | InvocationTargetException e) {
           throw new RuntimeException(e);
        }
    }

    public static Class<?>[] getClassesFromPackage(String packageName){
        String packageNameFileView = packageName.replace(".","/");
        List<Class<?>> classesList = new ArrayList<>();
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File classPath = new File(classLoader.getResource("").getPath());
        File pathForFileSearching = new File(classPath,packageNameFileView);
        if(pathForFileSearching.exists()){
            File[] files = pathForFileSearching.listFiles();

            for(File file : files){
                try {
                    String fileName = file.getName().replace(".class","");
                    Class<?> clazz = classLoader.loadClass(packageName+"."+fileName);
                    classesList.add(clazz);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("class not found");
                }
            }
            Class<?>[] returnClasses = new Class[classesList.size()] ;

            return classesList.toArray(returnClasses);
        }
        throw new RuntimeException("package or files not found, pckage Name for example my.package.name");
    }
}
