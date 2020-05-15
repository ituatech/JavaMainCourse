package com.it_uatech.addressedMessages;

import com.it_uatech.app.FrontendServiceAccountMS;
import com.it_uatech.app.MessageToFrontAccount;
import com.it_uatech.dto.AccountDTO;
import com.it_uatech.messageSystem.Address;
import com.it_uatech.messageSystem.AddressedMessage;

public class MessageValidateAccount extends MessageToFrontAccount {

    private final AccountDTO account;
    private final Integer key;

    public MessageValidateAccount(Address fromAddress, Address toAddress, AccountDTO account, Integer key) {
        super(fromAddress, toAddress);
        this.account = account;
        this.key = key;
    }

    @Override
    public AddressedMessage exec(FrontendServiceAccountMS frontendService) {
        frontendService.validateAccount(key,account);
        return null;
    }
}
