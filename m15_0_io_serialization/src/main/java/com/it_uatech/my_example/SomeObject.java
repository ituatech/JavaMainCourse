package com.it_uatech.my_example;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by tully.
 */
@SuppressWarnings("WeakerAccess")
public class SomeObject implements Serializable {
    private final int age;
    private final String name;

    public SomeObject(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SomeObject that = (SomeObject) o;
        return age == that.age &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name);
    }

    @Override
    public String toString() {
        return "Name: " + getName() + " age: " + getAge();
    }
}
