package com.it_uatech.api;

import com.it_uatech.app.FrontendServiceAccountMS;
import com.it_uatech.dto.AccountDTO;

import java.util.concurrent.ConcurrentMap;

public interface FrontendServiceAccount extends FrontendServiceAccountMS {

    ConcurrentMap<Integer, AccountDTO> getHttpRequestMap();

    boolean isConnected();
}
