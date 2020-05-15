package com.it_uatech.addressedMessages;

import com.it_uatech.app.DBServiceUserMS;
import com.it_uatech.app.MessageToUserDB;
import com.it_uatech.messageSystem.Address;
import com.it_uatech.messageSystem.AddressedMessage;

public class MessageGetCacheHitMiss extends MessageToUserDB {

    public MessageGetCacheHitMiss(Address fromAddress, Address toAddress) {
        super(fromAddress, toAddress);
    }

    @Override
    public AddressedMessage exec(DBServiceUserMS dbServiceUser) {
        return new MessageRetrieveCacheHitMiss(getTo(),getFrom(),dbServiceUser.getCacheHitCount(),dbServiceUser.getCacheMissCount());
    }
}
