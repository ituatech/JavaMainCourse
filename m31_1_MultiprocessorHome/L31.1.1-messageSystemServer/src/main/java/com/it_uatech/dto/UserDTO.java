package com.it_uatech.dto;

import java.io.Serializable;
import java.util.Objects;

public class UserDTO implements Serializable {

    private String name;
    private int age;

    public UserDTO(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return age == userDTO.age &&
                name.equals(userDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
