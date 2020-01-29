package com.it_uatech.service;

import com.it_uatech.entity.UserDataSet;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public interface DBService {

    void createTable ();

    int save(UserDataSet dataSet);

    UserDataSet select(long id);

    UserDataSet findByAgeName(String name, int age);

    void dropTable();

    List<UserDataSet> selectAll();
}
