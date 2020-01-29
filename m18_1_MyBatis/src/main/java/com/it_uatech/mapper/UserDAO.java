package com.it_uatech.mapper;

import com.it_uatech.entity.UserDataSet;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface UserDAO {

    /**
     * Black magic for execution of custom query.
     */
    String SQL = "sql";

    @Update("${" + SQL + "}")
    void execute(Map<String, String> m);

    @Insert("insert into persons (name,age) values (#{name},#{age})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int save(UserDataSet dataSet);

    @Select("SELECT * FROM persons WHERE id = #{id} limit 1")
    UserDataSet select(@Param(value = "id") long id);

    @SelectProvider(type = AgeNameSelectProvider.class, method = "findByAgeName")
    UserDataSet findByAgeName(@Param(value = "name") String name, @Param(value = "age") int age);

    @Select("SELECT * FROM persons")
    List<UserDataSet> selectAll();
}
