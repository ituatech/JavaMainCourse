package com.it_uatech.entity;

public class DataSet {

    long id;

    public void setId(long id) {
        if (id > 0) this.id = id;
    }

    public long getId() {
        return id;
    }
}
