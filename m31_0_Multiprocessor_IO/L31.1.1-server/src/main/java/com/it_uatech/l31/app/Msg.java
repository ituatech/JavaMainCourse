package com.it_uatech.l31.app;

import com.it_uatech.l31.messages.ClassName;

import java.lang.reflect.Field;

/**
 * Created by tully.
 */
public abstract class Msg {

    @ClassName
    private final String className;

    protected Msg(Class<?> klass) {
        this.className = klass.getName();
    }

    public static String getAnnotatedVariableNameToJsonParse(){
        Field[] fields = Msg.class.getDeclaredFields();
        for (Field field : fields){
            if (field.isAnnotationPresent(ClassName.class)){
                return field.getName();
            }
        }
      return "";
    }
}
