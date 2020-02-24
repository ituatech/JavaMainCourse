package com.it_uatech.api.dao;

import com.it_uatech.api.model.UserDataSet;
import com.it_uatech.api.session.SessionManager;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    long save(UserDataSet user);

    void save(List<UserDataSet> user);

    Optional<UserDataSet> findById(long id);

    Optional<UserDataSet> findByName(String name);

    String getLocalStatus();

    List<UserDataSet> findAll();

    SessionManager getSessionManager();

}
