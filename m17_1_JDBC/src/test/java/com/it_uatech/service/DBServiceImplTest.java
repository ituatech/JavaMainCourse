package com.it_uatech.service;

import com.it_uatech.dao.DBService;
import com.it_uatech.entities.UserDataSet;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class DBServiceImplTest {

    DBService service;

    @Before
    public void setUp() {
        service = new DBServiceImpl();
    }

    @Test
    public void shouldSavePersonInDBAndReturnItBack() {
        UserDataSet uds = new UserDataSet("kesha", 67);
        boolean saveDone = service.save(uds);
        assertTrue(saveDone);

        Optional<UserDataSet> udsDB = service.load(uds.getID(), UserDataSet.class);
        assertEquals(uds, udsDB.get());
    }

    @Test
    public void loadAll() {
        Collection<UserDataSet> loadingPersonList= new ArrayList<>();
        loadingPersonList.add(new UserDataSet("kesha", 67));
        loadingPersonList.add(new UserDataSet("popka", 108));
        loadingPersonList.add(new UserDataSet("gosha", 123));

        service.save(((ArrayList<UserDataSet>)loadingPersonList).get(0));
        service.save(((ArrayList<UserDataSet>)loadingPersonList).get(1));
        service.save(((ArrayList<UserDataSet>)loadingPersonList).get(2));
        List<UserDataSet> personList = service.loadAll(UserDataSet.class);

        assertTrue(personList.containsAll(loadingPersonList));
    }
}