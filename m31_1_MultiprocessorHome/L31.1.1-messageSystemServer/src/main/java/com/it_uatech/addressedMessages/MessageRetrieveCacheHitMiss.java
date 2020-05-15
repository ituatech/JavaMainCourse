package com.it_uatech.addressedMessages;

import com.it_uatech.app.FrontendServiceUserMS;
import com.it_uatech.app.MessageToFrontUser;
import com.it_uatech.messageSystem.Address;
import com.it_uatech.messageSystem.AddressedMessage;

public class MessageRetrieveCacheHitMiss extends MessageToFrontUser {

    private final int cacheHits;
    private final int cacheMiss;

    public MessageRetrieveCacheHitMiss(Address fromAddress, Address toAddress, int cacheHits, int cacheMiss) {
        super(fromAddress, toAddress);
        this.cacheHits= cacheHits;
        this.cacheMiss = cacheMiss;
    }

    @Override
    public AddressedMessage exec(FrontendServiceUserMS frontendService) {
        frontendService.showCacheHitMiss(cacheHits,cacheMiss);
        return null;
    }
}
