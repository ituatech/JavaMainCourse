package com.it_uatech.executor;

import com.it_uatech.dao.TResultHandler;
import com.it_uatech.exceptions.ORMException;

import java.sql.*;

public class Executor {

    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public int execUpdate(String sqlUpdate) throws SQLException {
        try (Statement stmt = getConnection().createStatement()) {
            stmt.execute(sqlUpdate);
            return stmt.getUpdateCount();
        }
    }

    public long execUpdateWithReturnGeneratedKeys(String sqlUpdate) throws SQLException {
        try (Statement stmt = getConnection().createStatement()) {
            stmt.execute(sqlUpdate, Statement.RETURN_GENERATED_KEYS);
            ResultSet result = stmt.getGeneratedKeys();
            if (result.next()) {
                return result.getLong(1);
            } else {
                return -1;
            }
        }
    }

    public void execUpdatePrepared(String sqlUpdate, ExecuteHandler handler) throws SQLException {
        try (PreparedStatement preStmt = getConnection().prepareStatement(sqlUpdate)) {
            handler.accept(preStmt);
        }
    }

    public void execUpdatePreparedWithReturnGeneratedKeys(String sqlUpdate, ExecuteHandler handler) throws SQLException {
        try (PreparedStatement preStmt = getConnection().prepareStatement(sqlUpdate, Statement.RETURN_GENERATED_KEYS)) {
            handler.accept(preStmt);
        }
    }

    public <T> T execQuery(String sqlQuery, TResultHandler handler) throws SQLException, ORMException {
        try (Statement stmt = getConnection().createStatement()) {
            stmt.executeQuery(sqlQuery);
            return (T) handler.handle(stmt.getResultSet());
        }
    }


    protected Connection getConnection() {
        return connection;
    }

    @FunctionalInterface
    interface ExecuteHandler {
        void accept(PreparedStatement preparedStatement) throws SQLException;
    }
}
