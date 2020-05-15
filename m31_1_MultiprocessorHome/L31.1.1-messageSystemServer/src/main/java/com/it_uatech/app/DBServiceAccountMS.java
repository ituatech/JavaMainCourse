package com.it_uatech.app;

import com.it_uatech.dto.AccountDTO;
import com.it_uatech.messageSystem.Addressee;

import java.util.Optional;

public interface DBServiceAccountMS extends Addressee {

    void saveAccount(AccountDTO account);

    Optional<AccountDTO> findAccountByLogin(String login);
}
