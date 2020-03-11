package com.it_uatech.db;

import com.it_uatech.app.FrontendService;
import com.it_uatech.app.MsgToFrontend;
import com.it_uatech.messageSystem.Address;
/**
 * Created by tully.
 */
public class MsgGetUserIdAnswer extends MsgToFrontend {
    private final String name;
    private final int id;

    public MsgGetUserIdAnswer(Address from, Address to, String name, int id) {
        super(from, to);
        this.name = name;
        this.id = id;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.addUser(id, name);
    }
}
