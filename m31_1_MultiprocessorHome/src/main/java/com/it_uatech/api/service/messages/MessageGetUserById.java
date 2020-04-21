package com.it_uatech.api.service.messages;

import com.it_uatech.api.model.UserDataSet;
import com.it_uatech.api.service.DBServiceUser;
import com.it_uatech.messageSystem.Address;
import com.it_uatech.messageSystem.Message;
import org.eclipse.jetty.websocket.api.Session;

public class MessageGetUserById extends MessageToDB {

    private final long id;
    private final Session session;

    public MessageGetUserById(Address fromAddress, Address toAddress, long id, Session session) {
        super(fromAddress, toAddress);
        this.id = id;
        this.session = session;
    }

    @Override
    public void exec(DBServiceUser dbServiceUser) {
        UserDataSet uds = dbServiceUser.findById(id).orElse(new UserDataSet("no user found",0));
        // send message back to front
        Message msg = new MessageShowUser(getTo(),getFrom(),uds, session);
        dbServiceUser.getMessageSystem().sendMessage(msg);
    }
}
