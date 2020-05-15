package com.it_uatech.app;

import com.it_uatech.messageSystem.Address;
import com.it_uatech.messageSystem.AddressedMessage;
import com.it_uatech.messageSystem.Addressee;

public abstract class MessageToAccountDB extends AddressedMessage {

    public MessageToAccountDB(Address fromAddress, Address toAddress) {
        super(fromAddress, toAddress);
    }

    @Override
    public AddressedMessage exec(Addressee addressee) {
        if (addressee instanceof DBServiceAccountMS){
            return exec((DBServiceAccountMS) addressee);
        }else{
            throw new MessageException("An addressee is not a DBServiceAccount");
        }
    }

    public abstract AddressedMessage exec(DBServiceAccountMS dbServiceAccount);
}
