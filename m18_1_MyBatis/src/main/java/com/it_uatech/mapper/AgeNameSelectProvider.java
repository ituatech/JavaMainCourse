package com.it_uatech.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class AgeNameSelectProvider {
    public String findByAgeName(@Param(value = "name") String name, @Param(value = "age") int age) {
        return new SQL() {{
            SELECT("*");
            FROM("persons");
            WHERE("name = #{name}");
            AND();
            WHERE("age = #{age}");
            LIMIT(1);
        }}.toString();
    }
}
