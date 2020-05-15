package com.it_uatech.app;

import com.it_uatech.dto.AccountDTO;
import com.it_uatech.messageSystem.Addressee;

public interface FrontendServiceAccountMS extends Addressee {

    void handleRequestRegisterNewAccount(AccountDTO account);

    void handleRequestGetAccountByLogin(String login, Integer key);

    void validateAccount(Integer key, AccountDTO account);
}
