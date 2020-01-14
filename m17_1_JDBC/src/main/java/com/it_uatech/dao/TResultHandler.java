package com.it_uatech.dao;

import com.it_uatech.exceptions.ORMException;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface TResultHandler<T>{
    T handle(ResultSet resultSet) throws SQLException, ORMException;
}
