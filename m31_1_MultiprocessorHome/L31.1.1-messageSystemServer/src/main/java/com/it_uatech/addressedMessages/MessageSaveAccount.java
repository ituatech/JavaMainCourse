package com.it_uatech.addressedMessages;

import com.it_uatech.app.DBServiceAccountMS;
import com.it_uatech.app.MessageToAccountDB;
import com.it_uatech.dto.AccountDTO;
import com.it_uatech.messageSystem.Address;
import com.it_uatech.messageSystem.AddressedMessage;

public class MessageSaveAccount extends MessageToAccountDB {

    private final AccountDTO account;

    public MessageSaveAccount(Address fromAddress, Address toAddress, AccountDTO account) {
        super(fromAddress, toAddress);
        this.account = account;
    }

    @Override
    public AddressedMessage exec(DBServiceAccountMS dbServiceAccount) {
        dbServiceAccount.saveAccount(account);
        return null;
    }
}
