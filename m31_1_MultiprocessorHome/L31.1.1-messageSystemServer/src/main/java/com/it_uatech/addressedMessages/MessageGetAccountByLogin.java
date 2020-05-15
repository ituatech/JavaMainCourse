package com.it_uatech.addressedMessages;

import com.it_uatech.app.DBServiceAccountMS;
import com.it_uatech.app.MessageToAccountDB;
import com.it_uatech.dto.AccountDTO;
import com.it_uatech.messageSystem.Address;
import com.it_uatech.messageSystem.AddressedMessage;

public class MessageGetAccountByLogin extends MessageToAccountDB {

    private final String login;
    private final Integer key;

    public MessageGetAccountByLogin (Address fromAddress, Address toAddress, String login, Integer key) {
        super(fromAddress, toAddress);
        this.login = login;
        this.key = key;
    }

    @Override
    public AddressedMessage exec(DBServiceAccountMS dbServiceAccount) {
        AccountDTO account = dbServiceAccount.findAccountByLogin(login).orElse(new AccountDTO("",""));
        // send message back to front
        return new MessageValidateAccount(getTo(),getFrom(),account,key);
    }
}
