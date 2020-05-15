package com.it_uatech.app;

import com.it_uatech.messageSystem.Address;
import com.it_uatech.messageSystem.AddressedMessage;
import com.it_uatech.messageSystem.Addressee;

public abstract class MessageToFrontUser extends AddressedMessage {

    public MessageToFrontUser(Address fromAddress, Address toAddress) {
        super(fromAddress, toAddress);
    }

    @Override
    public AddressedMessage exec(Addressee addressee) {
        if (addressee instanceof FrontendServiceUserMS){
            return exec((FrontendServiceUserMS) addressee);
        }else{
            throw new MessageException("An addressee is not a FrontendServiceUser");
        }
    }

    public abstract AddressedMessage exec(FrontendServiceUserMS frontendService);
}
