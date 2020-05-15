package com.it_uatech.messageSystem;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Address implements Serializable {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger();
    private final String id;

    public Address(){
        id = String.valueOf(ID_GENERATOR.getAndIncrement());
    }

    public Address(ServiceName id) {
        this.id = id + "-" + ID_GENERATOR.getAndIncrement();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id.equals(address.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                '}';
    }
}
