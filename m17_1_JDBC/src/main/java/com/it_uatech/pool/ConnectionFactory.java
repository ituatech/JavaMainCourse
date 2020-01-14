package com.it_uatech.pool;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {
    void dispose() throws SQLException;

    Connection get() throws SQLException;
}
