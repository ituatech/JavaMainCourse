package com.it_uatech.messageSystem;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author tully
 */
public final class Address {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger();
    private final String id;

    public Address(){
        id = String.valueOf(ID_GENERATOR.getAndIncrement());
    }

    public Address(String id) {
        this.id = id + "-" + ID_GENERATOR.getAndIncrement();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
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
}
