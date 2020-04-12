package com.it_uatech.api.service.messages;

import com.it_uatech.api.service.FrontendService;
import com.it_uatech.messageSystem.Address;
import com.it_uatech.messageSystem.Addressee;
import com.it_uatech.messageSystem.Message;

public abstract class MessageToFront extends Message {

    public MessageToFront(Address fromAddress, Address toAddress) {
        super(fromAddress, toAddress);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontendService){
            exec((FrontendService) addressee);
        }else{
            throw new MessageException("An addressee is not a FrontendService");
        }
    }

    public abstract void exec(FrontendService frontendService);
}
