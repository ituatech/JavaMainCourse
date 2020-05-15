package com.it_uatech.api.service;

import com.it_uatech.api.cache.CacheElement;
import com.it_uatech.api.cache.CacheEngine;
import com.it_uatech.api.dao.UserDao;
import com.it_uatech.api.model.UserDataSet;
import com.it_uatech.api.session.SessionManager;
import com.it_uatech.app.DBServiceUserMS;
import com.it_uatech.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class DBCachedServiceUserImpl implements DBServiceUser, DBServiceUserMS {

    private static final Logger logger = LoggerFactory.getLogger(DBCachedServiceUserImpl.class);

    private UserDao dao;
    private CacheEngine<Long,UserDataSet> cacheEngine;

    public DBCachedServiceUserImpl(UserDao dao, CacheEngine<Long, UserDataSet> cache) {
        this.dao = dao;
        this.cacheEngine = cache;
    }

    @Override
    public Optional<UserDTO> findUserById(long id) {
        Optional<UserDataSet> optional = findById(id);
        return optional.map(userDataSet -> new UserDTO(userDataSet.getName(), userDataSet.getAge()));
    }

    @Override
    public int getCacheHitCount() {
        return cacheEngine.getHitCount();
    }

    @Override
    public int getCacheMissCount() {
        return cacheEngine.getMissCount();
    }

    @Override
    public int getCacheSize() {
        return cacheEngine.getSize();
    }

    @Override
    public long save(UserDataSet user) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long userId = dao.save(user);
                sessionManager.commitSession();
                cacheEngine.put(new CacheElement<>(userId,user));
                return userId;
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DataServiceException(e);
            }
        }
    }

    @Override
    public void save(List<UserDataSet> user) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                user.forEach(person ->{
                    long personId = dao.save(person);
                    cacheEngine.put(new CacheElement<>(personId, person));
                });
                sessionManager.commitSession();
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DataServiceException(e);
            }
        }
    }

    @Override
    public Optional<UserDataSet> findById(long id) {
        logger.info("Find user with id: {}",id);
        CacheElement<Long,UserDataSet> element = cacheEngine.get(id);
        if(element != null) {
            UserDataSet uds = element.getValue();
            if (uds != null) {
                return Optional.of(uds);
            }
        }
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                return dao.findById(id);
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DataServiceException(e);
            }
        }
    }

    @Override
    public Optional<UserDataSet> findByName(String name) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<UserDataSet> optional = dao.findByName(name);
                optional.ifPresent(userDataSet -> cacheEngine.put(new CacheElement<Long, UserDataSet>(userDataSet.getId(), userDataSet)));
                return optional;
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DataServiceException(e);
            }
        }
    }

    @Override
    public String getLocalStatus() {
        return "Cache hits: " + cacheEngine.getHitCount()
                +", cache miss: " + cacheEngine.getMissCount();
    }

    @Override
    public List<UserDataSet> findAll() {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                return dao.findAll();
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DataServiceException(e);
            }
        }
    }

    @Override
    public void shutdown() {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.shutdown();
            cacheEngine.dispose();
        }
    }

}
