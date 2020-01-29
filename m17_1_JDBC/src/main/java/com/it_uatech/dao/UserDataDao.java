package com.it_uatech.dao;

import com.it_uatech.entities.DataSet;
import com.it_uatech.exceptions.ORMException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDataDao extends AutoCloseable{

    <T extends DataSet> void save(T user) throws SQLException, ORMException;

    <T extends DataSet> Optional<T> loadUsingClassConstructor(long id, Class<T> clazz) throws SQLException, ORMException;

    <T extends DataSet> Optional<T> loadUsingORM(long id, Class<T> clazz) throws SQLException, ORMException;

    void deleteTables() throws SQLException;

    void createTables() throws SQLException;

    <T extends DataSet> List<T> loadAll(Class<T> personClass) throws SQLException, ORMException;
}
