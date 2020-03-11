package com.it_uatech.app;

import com.it_uatech.messageSystem.Addressee;

/**
 * Created by tully.
 */
public interface DBService extends Addressee {
    void init();

    int getUserId(String name);
}
