package com.it_uatech.prepared;

import com.it_uatech.executor.PreparedExecutor;
import com.it_uatech.executor.TExecutor;
import com.it_uatech.simple.DBServiceSimple;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBServicePrepared extends DBServiceSimple {

    private static final String INSERT_INTO_USER = "insert into user (name) values(?)";

    @Override
    public void addUsers(String... names) throws SQLException {
        PreparedExecutor exec = new PreparedExecutor(getConnection());
        exec.execUpdate(INSERT_INTO_USER, statement -> {
            for (String name : names) {
                statement.setString(1, name);
                statement.execute();
            }
        });
    }

    @Override
    public List<String> getAllNames() throws SQLException {
        TExecutor executor = new TExecutor(getConnection());

        return executor.execQuery("select name from user", result -> {
            List<String> names = new ArrayList<>();

            while (!result.isLast()) {
                result.next();
                names.add(result.getObject(1).toString());
            }
            return names;
        });
    }
}
