package com.it_uatech.app;

import com.it_uatech.dto.UserDTO;
import com.it_uatech.messageSystem.Addressee;

public interface FrontendServiceUserMS extends Addressee {

    void handleRequest(Long id, int key);

    void handleRequestGetCacheSize();

    void handleRequestGetCacheHitMiss();

    void showUser(UserDTO user, int key);

    void showCacheSize(int cacheSize);

    void showCacheHitMiss(int cacheHits, int cacheMiss);
}
