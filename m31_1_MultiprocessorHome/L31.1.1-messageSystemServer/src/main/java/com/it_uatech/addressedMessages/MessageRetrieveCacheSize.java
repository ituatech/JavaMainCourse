package com.it_uatech.addressedMessages;

import com.it_uatech.app.FrontendServiceUserMS;
import com.it_uatech.app.MessageToFrontUser;
import com.it_uatech.messageSystem.Address;
import com.it_uatech.messageSystem.AddressedMessage;

public class MessageRetrieveCacheSize extends MessageToFrontUser {

    private final int cacheSize;

    public MessageRetrieveCacheSize(Address fromAddress, Address toAddress, int cacheSize) {
        super(fromAddress, toAddress);
        this.cacheSize = cacheSize;
    }

    @Override
    public AddressedMessage exec(FrontendServiceUserMS frontendService) {
        frontendService.showCacheSize(cacheSize);
        return null;
    }
}
