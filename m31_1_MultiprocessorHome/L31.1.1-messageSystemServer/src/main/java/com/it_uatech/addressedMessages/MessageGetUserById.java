package com.it_uatech.addressedMessages;

import com.it_uatech.app.DBServiceUserMS;
import com.it_uatech.app.MessageToUserDB;
import com.it_uatech.dto.UserDTO;
import com.it_uatech.messageSystem.Address;
import com.it_uatech.messageSystem.AddressedMessage;

public class MessageGetUserById extends MessageToUserDB {

    private final long id;
    private final int key;

    public MessageGetUserById(Address fromAddress, Address toAddress, long id, int key) {
        super(fromAddress, toAddress);
        this.id = id;
        this.key = key;
    }

    @Override
    public AddressedMessage exec(DBServiceUserMS dbServiceUser) {
        UserDTO user = dbServiceUser.findUserById(id).orElse(new UserDTO("no user found",0));
        // send message back to front
        return new MessageShowUser(getTo(),getFrom(),user, key);
    }
}
