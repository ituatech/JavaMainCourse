package com.it_uatech.api;

import com.it_uatech.messageSystem.AddressedMessage;

public interface FrontendResponseMessage {

    AddressedMessage takeAccountFrontResponse();

    AddressedMessage takeUserFrontResponse();

    boolean isConnected();
}
