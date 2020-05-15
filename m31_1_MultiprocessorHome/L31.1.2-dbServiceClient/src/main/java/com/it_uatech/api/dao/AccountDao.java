package com.it_uatech.api.dao;

import com.it_uatech.api.model.AccountDataSet;
import com.it_uatech.api.session.SessionManager;

import java.util.Optional;

public interface AccountDao {

    void save(AccountDataSet account);

    Optional<AccountDataSet> findByLogin(String login);

    SessionManager getSessionManager();
}
