package com.it_uatech.app;

import com.it_uatech.dto.UserDTO;
import com.it_uatech.messageSystem.Addressee;

import java.util.Optional;

public interface DBServiceUserMS extends Addressee {

    Optional<UserDTO> findUserById(long id);

    int getCacheHitCount();

    int getCacheMissCount();

    int getCacheSize();
}