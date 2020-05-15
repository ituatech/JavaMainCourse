package com.it_uatech.addressedMessages;

import com.it_uatech.app.FrontendServiceUserMS;
import com.it_uatech.app.MessageToFrontUser;
import com.it_uatech.dto.UserDTO;
import com.it_uatech.messageSystem.Address;
import com.it_uatech.messageSystem.AddressedMessage;

public class MessageShowUser extends MessageToFrontUser {

    private final UserDTO user;
    private final int key;

    public MessageShowUser(Address fromAddress, Address toAddress, UserDTO user, int key) {
        super(fromAddress, toAddress);
        this.user = user;
        this.key = key;
    }

    @Override
    public AddressedMessage exec(FrontendServiceUserMS frontendService) {
        frontendService.showUser(user,key);
        return null;
    }
}
