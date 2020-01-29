package com.it_uatech.service;

import com.it_uatech.entity.UserDataSet;
import com.it_uatech.mapper.UserDAO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBServiceImpl implements DBService {

    SqlSessionFactory sqlSessionFactory;

    public DBServiceImpl(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public void createTable() {
         try(SqlSession session = sqlSessionFactory.openSession(false)) {
             UserDAO dao = session.getMapper(UserDAO.class);
             Map<String, String> createMap = new HashMap<>();
             createMap.put(UserDAO.SQL, "create table if not exists persons (id bigint auto_increment, name varchar(256),age int(3), primary key (id))");
             dao.execute(createMap);
             session.commit();
         }
    }

    @Override
    public void dropTable() {
        try(SqlSession session = sqlSessionFactory.openSession(false)) {
            UserDAO dao = session.getMapper(UserDAO.class);
            Map<String, String> createMap = new HashMap<>();
            createMap.put(UserDAO.SQL, "drop table if exists persons");
            dao.execute(createMap);
            session.commit();
        }
    }

    @Override
    public int save(UserDataSet dataSet) {
        try(SqlSession session = sqlSessionFactory.openSession(false)) {
            UserDAO dao = session.getMapper(UserDAO.class);
            int rs = dao.save(dataSet);
            session.commit();
            return rs;
        }
    }

    @Override
    public UserDataSet select(long id) {
        try(SqlSession session = sqlSessionFactory.openSession(false)) {
            UserDAO dao = session.getMapper(UserDAO.class);
            UserDataSet uds = dao.select(id);
            session.commit();
            return uds;
        }
    }

    @Override
    public UserDataSet findByAgeName(String name, int age) {
        try(SqlSession session = sqlSessionFactory.openSession(false)) {
            UserDAO dao = session.getMapper(UserDAO.class);
            UserDataSet uds = dao.findByAgeName(name, age);
            session.commit();
            return uds;
        }
    }

    @Override
    public List<UserDataSet> selectAll() {
        try(SqlSession session = sqlSessionFactory.openSession(false)) {
            UserDAO dao = session.getMapper(UserDAO.class);
            List<UserDataSet> udsList = dao.selectAll();
            session.commit();
            return udsList;
        }
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
