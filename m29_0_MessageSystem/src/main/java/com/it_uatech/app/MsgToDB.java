package com.it_uatech.app;

import com.it_uatech.messageSystem.Address;
import com.it_uatech.messageSystem.Addressee;
import com.it_uatech.messageSystem.Message;

/**
 * Created by tully.
 */
public abstract class MsgToDB extends Message {
    public MsgToDB(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof DBService) {
            exec((DBService) addressee);
        }
    }

    public abstract void exec(DBService dbService);
}
