package com.it_uatech.entities;

import com.it_uatech.annotations.Column;
import com.it_uatech.annotations.Table;

import java.util.Objects;

@Table(name = "persons")
public class UserDataSet extends DataSet {

    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;

    public UserDataSet(){}

    public UserDataSet(long id, String name, int age) {
        this(name, age);
        super.setId(id);
    }

    public UserDataSet(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public long getID(){
        return  id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDataSet)) return false;
        UserDataSet that = (UserDataSet) o;
        return age == that.age &&
                name.equals(that.name) &&
                id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, id);
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id=" + id +
                '}';
    }
}
