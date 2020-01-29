package com.it_uatech.connection;

import com.it_uatech.mapper.UserDAO;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;

public class Connection {

    public static <T extends UserDAO> SqlSessionFactory getSqlSessionFactory(DataSource dataSource, Class<T> mapper) {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration mapperRegistry = new Configuration(environment);

        mapperRegistry.addMapper(mapper);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(mapperRegistry);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                ((PooledDataSource)sqlSessionFactory.getConfiguration().getEnvironment().getDataSource()).forceCloseAll();
            }
        });

        return sqlSessionFactory;
    }

    public static DataSource getMySQLDataSource() {
        PooledDataSource ds = new PooledDataSource();

        ds.setDriver("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/users?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Kiev");
        ds.setUsername("myorm");
        ds.setPassword("password");

        return ds;
    }

    public static DataSource getH2DataSource() {
        PooledDataSource ds = new PooledDataSource();

        ds.setDriver("org.h2.Driver");
        ds.setUrl("jdbc:h2:mem:testBatis");
        ds.setUsername("myorm");
        ds.setPassword("password");
        return ds;
    }
}

