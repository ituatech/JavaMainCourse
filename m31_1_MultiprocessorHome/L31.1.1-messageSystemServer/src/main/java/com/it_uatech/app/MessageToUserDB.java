package com.it_uatech.app;

import com.it_uatech.messageSystem.Address;
import com.it_uatech.messageSystem.AddressedMessage;
import com.it_uatech.messageSystem.Addressee;


public abstract class MessageToUserDB extends AddressedMessage {

    public MessageToUserDB(Address fromAddress, Address toAddress) {
        super(fromAddress, toAddress);
    }

    @Override
    public AddressedMessage exec(Addressee addressee) {
        if (addressee instanceof DBServiceUserMS){
            return exec((DBServiceUserMS) addressee);
        }else{
          throw new MessageException("An addressee is not a DBServiceUser");
        }
    }

    public abstract AddressedMessage exec(DBServiceUserMS dbServiceUser);
}
