package com.it_uatech.api.service.messages;

import com.it_uatech.api.service.DBServiceUser;
import com.it_uatech.messageSystem.Address;
import com.it_uatech.messageSystem.Addressee;
import com.it_uatech.messageSystem.Message;

public abstract class MessageToDB extends Message {

    public MessageToDB(Address fromAddress, Address toAddress) {
        super(fromAddress, toAddress);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof DBServiceUser){
            exec((DBServiceUser) addressee);
        }else{
          throw new MessageException("An addressee is not a DBService");
        }
    }

    public abstract void exec(DBServiceUser dbServiceUser);
}
