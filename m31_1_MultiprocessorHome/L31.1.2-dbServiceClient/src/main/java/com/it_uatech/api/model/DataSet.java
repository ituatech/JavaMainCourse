package com.it_uatech.api.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
class DataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    long getId() {
        return id;
    }

    void setId(long id) {
        this.id = id;
    }
}
