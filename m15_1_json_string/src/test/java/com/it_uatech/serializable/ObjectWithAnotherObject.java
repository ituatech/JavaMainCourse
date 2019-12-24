package com.it_uatech.serializable;

import java.util.Objects;

public class ObjectWithAnotherObject {
    private int number;
    private SimpleObject simpleObject;
    private Boolean aBoolean;

    public ObjectWithAnotherObject(int number, SimpleObject simpleObject, Boolean aBoolean) {
        this.number = number;
        this.simpleObject = simpleObject;
        this.aBoolean = aBoolean;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectWithAnotherObject that = (ObjectWithAnotherObject) o;
        return number == that.number &&
                simpleObject.equals(that.simpleObject) &&
                aBoolean.equals(that.aBoolean);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, simpleObject, aBoolean);
    }
}
