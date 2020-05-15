package com.it_uatech.addressedMessages;

import com.it_uatech.app.DBServiceUserMS;
import com.it_uatech.app.MessageToUserDB;
import com.it_uatech.messageSystem.Address;
import com.it_uatech.messageSystem.AddressedMessage;

public class MessageGetCacheSize extends MessageToUserDB {

    public MessageGetCacheSize(Address fromAddress, Address toAddress) {
        super(fromAddress, toAddress);
    }

    @Override
    public AddressedMessage exec(DBServiceUserMS dbServiceUser) {
        return new MessageRetrieveCacheSize(getTo(),getFrom(),dbServiceUser.getCacheSize());
    }
}
