package com.it_uatech.serializable;

import java.util.Objects;

public class WithNullAndTransient {
    private int number;
    private SimpleObject simpleObject;
    private  transient String str;
    Boolean aBoolean;

    public WithNullAndTransient(int number, String str, Boolean aBoolean) {
        this.number = number;
        this.str = str;
        this.aBoolean = aBoolean;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WithNullAndTransient that = (WithNullAndTransient) o;
        return number == that.number &&
                aBoolean.equals(that.aBoolean);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, aBoolean);
    }
}
