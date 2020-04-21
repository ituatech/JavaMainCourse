package com.it_uatech.api.service.messages;

import com.it_uatech.api.model.UserDataSet;
import com.it_uatech.api.service.FrontendService;
import com.it_uatech.messageSystem.Address;
import org.eclipse.jetty.websocket.api.Session;

public class MessageShowUser extends MessageToFront {

    private final UserDataSet uds;
    private final Session session;

    public MessageShowUser(Address fromAddress, Address toAddress, UserDataSet uds, Session session) {
        super(fromAddress, toAddress);
        this.uds = uds;
        this.session = session;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.showUser(uds,session);
    }
}
