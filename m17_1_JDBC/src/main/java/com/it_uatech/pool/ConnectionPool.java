package com.it_uatech.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public class ConnectionPool implements ConnectionFactory{

    private Queue<MyConnection> pool = new LinkedList<>();
    private ConnectionFactory connectionFactory;

    public ConnectionPool(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void dispose() throws SQLException {
        for(MyConnection myConnection : pool){
            myConnection.superClose();
        }
        pool.clear();
    }

    @Override
    public Connection get() throws SQLException {
        if (pool.isEmpty()){
            pool.add(new MyConnection(connectionFactory.get()));
        }
        return pool.poll();
    }

    public int poolSize(){
        return pool.size();
    }


    class MyConnection extends ConnectionDecorator {

        MyConnection(Connection connection) {
            super(connection);
        }

        @Override
        public void close() throws SQLException {
            pool.add(this);
        }

        public void superClose() throws SQLException {
            super.close();
        }
    }
}
