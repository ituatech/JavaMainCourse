package com.it_uatech.api.service;

import com.it_uatech.api.model.AccountDataSet;

import java.util.Optional;

public interface DBServiceAccount {

    void save(AccountDataSet account);

    Optional<AccountDataSet> findByLogin(String login);

    void shutdown();
}
