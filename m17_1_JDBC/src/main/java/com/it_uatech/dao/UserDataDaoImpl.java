package com.it_uatech.dao;

import com.it_uatech.annotations.Column;
import com.it_uatech.annotations.Table;
import com.it_uatech.entities.DataSet;
import com.it_uatech.exceptions.ORMException;
import com.it_uatech.executor.Executor;
import com.it_uatech.utils.ReflectionHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

public class UserDataDaoImpl implements UserDataDao {

    private static final String DELETE_PERSONS = "DROP TABLE IF EXISTS persons";
    private static final String CREATE_TABLE_PERSONS = "CREATE TABLE IF NOT EXISTS persons " +
            " (id BIGINT(20) NOT NULL AUTO_INCREMENT, name VARCHAR(255) DEFAULT '', age INT(3) DEFAULT 0, " +
            " PRIMARY KEY (id) ) ENGINE = INNODB DEFAULT CHARSET = utf8;";
    private static final String SELECT_PERSON = "select * from persons where id=%d";
    private static final String SELECT_ALL_PERSONS = "select * from persons";

    private final Connection connection;

    public UserDataDaoImpl() {
        this.connection = ConnectionHelper.getMySQLConnection();
    }

    <T extends DataSet> List<Field> getORMFields(Class<T> checkClass) throws ORMException {
        if (!ReflectionHelper.typeAnnotationIsPresent(checkClass, Table.class))
            throw new ORMException("user object is not Table class");
        List<Field> fields = ReflectionHelper.getFieldWithAnnotation(checkClass, Column.class);
        if (fields.size() == 0) throw new ORMException("annotation Column not present");
        return fields;
    }

    <T extends DataSet> T instantiateDaoClass(Class<T> personClass, ResultSet resultSet) throws ORMException,SQLException{
        List<Field> fields = getORMFields(personClass);
        T tablePerson;
        try {
            tablePerson = ReflectionHelper.instantiate(personClass);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new ORMException(e);
        }
        for (Field field : fields) {
            try {
                ReflectionHelper.setFieldValue(resultSet.getObject(field.getAnnotation(Column.class).name()), field, tablePerson);
            } catch (IllegalAccessException e) {
                throw new ORMException(e);
            }
            tablePerson.setId(resultSet.getLong("id"));
        }
        return tablePerson;

        }

    <T extends DataSet> String buildInsertIntoSQLStatement(T user) throws ORMException {
        List<Field> fields = getORMFields(user.getClass());
        StringJoiner insertName = new StringJoiner(", ");
        StringJoiner insertValue = new StringJoiner("', '", "'", "'");
        for (Field field : fields) {
            try {
                insertName.add(field.getName());
                Object obj = ReflectionHelper.getFieldValue(field, user);
                if (obj != null) {
                    insertValue.add(obj.toString());
                } else {
                    insertValue.add("");
                }
            } catch (IllegalAccessException e) {
                throw new ORMException(e);
            }
        }

        StringBuilder insertUser = new StringBuilder();
        insertUser.append("insert into ");
        insertUser.append(user.getClass().getAnnotation(Table.class).name());
        insertUser.append(" (");
        insertUser.append(insertName);
        insertUser.append(") values (");
        insertUser.append(insertValue);
        insertUser.append(")");
        return insertUser.toString();
    }

    @Override
    public <T extends DataSet> void save(T user) throws ORMException, SQLException {
        Executor executor = new Executor(getConnection());
        long id = executor.execUpdateWithReturnGeneratedKeys(buildInsertIntoSQLStatement(user));
        user.setId(id);
    }

    @Override
    public <T extends DataSet> Optional<T> loadUsingClassConstructor(long id, Class<T> personClass) throws SQLException, ORMException {
        if (!ReflectionHelper.typeAnnotationIsPresent(personClass, Table.class))
            throw new ORMException("user object is not Table class");
        Executor executor = new Executor(getConnection());
        T person = executor.execQuery(String.format(SELECT_PERSON, id), resultSet -> {
            resultSet.next();
            try {
                return ReflectionHelper.instantiate(personClass, resultSet.getObject("id"), resultSet.getObject("name"), resultSet.getObject("age"));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                throw new ORMException(e);
            }
        });
        return Optional.ofNullable(person);
    }

    @Override
    public <T extends DataSet> Optional<T> loadUsingORM(long id, Class<T> personClass) throws SQLException, ORMException {
        Executor executor = new Executor(getConnection());
        T person = executor.execQuery(String.format(SELECT_PERSON, id), resultSet -> {
            resultSet.next();
            return instantiateDaoClass(personClass,resultSet);
        });
        return Optional.ofNullable(person);
    }

    @Override
    public void deleteTables() throws SQLException {
        Executor executor = new Executor(getConnection());
        executor.execUpdate(DELETE_PERSONS);
    }

    @Override
    public void createTables() throws SQLException {
        Executor executor = new Executor(getConnection());
        executor.execUpdate(CREATE_TABLE_PERSONS);
    }

    @Override
    public <T extends DataSet> List<T> loadAll(Class<T> personClass) throws ORMException, SQLException {
        Executor executor = new Executor(getConnection());
        List<T> personsList = executor.execQuery(SELECT_ALL_PERSONS, resultSet -> {
            List<T> listOfPersons = new ArrayList<>();
            while (resultSet.next()) {
                listOfPersons.add(instantiateDaoClass(personClass,resultSet));
            }
                return listOfPersons;
            });
            return personsList;
        }

        @Override
        public void close () throws SQLException {
            getConnection().close();
        }

        protected Connection getConnection () {
            return connection;
        }
    }
