package com.it_uatech.my_example;

import java.io.Serializable;
import java.util.Objects;

public class SomeAnotherObject implements Serializable {
    private final int ageMy;
    private final String nameMy;

    public SomeAnotherObject(int age, String name) {
        this.ageMy = age;
        this.nameMy = name;
    }

    public int getAgeMy() {
        return ageMy;
    }

    public String getNameMy() {
        return nameMy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SomeAnotherObject that = (SomeAnotherObject) o;
        return ageMy == that.ageMy &&
                nameMy.equals(that.nameMy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ageMy, nameMy);
    }

    @Override
    public String toString() {
        return "Name: " + getNameMy() + " age: " + getAgeMy();
    }
}
