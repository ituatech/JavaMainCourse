package com.it_uatech.api.service;

import com.it_uatech.api.model.UserDataSet;
import com.it_uatech.messageSystem.Addressee;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface DBServiceUser extends Addressee {

    void init();

    long save(UserDataSet user);

    void save(List<UserDataSet> user);

    Optional<UserDataSet> findById(long id);

    Optional<UserDataSet> findByName(String name);

    String getLocalStatus();

    List<UserDataSet> findAll();

    void shutdown();
}
