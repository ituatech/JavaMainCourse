package com.it_uatech.service;

import com.it_uatech.dao.DBService;
import com.it_uatech.dao.UserDataDao;
import com.it_uatech.dao.UserDataDaoImpl;
import com.it_uatech.entities.DataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DBServiceImpl implements DBService {

    private static final Logger logger = LoggerFactory.getLogger(DBServiceImpl.class);

    @Override
    public <T extends DataSet> boolean save(T user) {
        try (UserDataDao userDataDao = new UserDataDaoImpl()) {
            userDataDao.save(user);
            return true;
        } catch (Exception e) {
            logger.error("Can't save person in the table", e);
            return false;
        }
    }

    @Override
    public <T extends DataSet> Optional<T> load(long id, Class<T> clazz) {
        try (UserDataDao userDataDao = new UserDataDaoImpl()) {
            return userDataDao.loadUsingORM(id, clazz);
        } catch (Exception e) {
            logger.error("Can't load person from the table", e);
            return Optional.empty();
        }
    }

    @Override
    public <T extends DataSet> List<T> loadAll(Class<T> personClass) {
        try (UserDataDao userDataDao = new UserDataDaoImpl()) {
            return userDataDao.loadAll(personClass);
        } catch (Exception e) {
            logger.error("Can't load an list of person from the table", e);
            return new ArrayList<>();
        }
    }
}
