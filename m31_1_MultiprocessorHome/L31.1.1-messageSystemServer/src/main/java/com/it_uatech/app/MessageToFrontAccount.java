package com.it_uatech.app;

import com.it_uatech.messageSystem.Address;
import com.it_uatech.messageSystem.AddressedMessage;
import com.it_uatech.messageSystem.Addressee;

public abstract class MessageToFrontAccount extends AddressedMessage {

    public MessageToFrontAccount(Address fromAddress, Address toAddress) {
        super(fromAddress, toAddress);
    }

    @Override
    public AddressedMessage exec(Addressee addressee) {
        if (addressee instanceof FrontendServiceAccountMS){
            return exec((FrontendServiceAccountMS) addressee);
        }else{
            throw new MessageException("An addressee is not a FrontendServiceAccount");
        }
    }

    public abstract AddressedMessage exec(FrontendServiceAccountMS frontendService);
}
