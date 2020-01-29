package com.it_uatech.dao;

import com.it_uatech.entities.DataSet;
import java.util.List;
import java.util.Optional;

public interface DBService {

    <T extends DataSet> boolean save(T user);

    <T extends DataSet> Optional<T> load(long id, Class<T> clazz);

    <T extends DataSet> List<T> loadAll(Class<T> personClass);
}
