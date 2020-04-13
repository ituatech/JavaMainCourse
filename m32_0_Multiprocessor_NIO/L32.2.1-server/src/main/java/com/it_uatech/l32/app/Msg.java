package com.it_uatech.l32.app;

/**
 * Created by tully.
 */
public abstract class Msg {
    public static final String CLASS_NAME_VARIABLE = "className";

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final String className;

    protected Msg(Class<?> klass) {
        this.className = klass.getName();
    }
}
