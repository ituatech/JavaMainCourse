package com.it_uatech.api.service;

import com.it_uatech.api.dao.UserDao;
import com.it_uatech.api.model.AddressDataSet;
import com.it_uatech.api.model.PhoneDataSet;
import com.it_uatech.api.model.UserDataSet;
import com.it_uatech.hibernate_impl.HibernateUtil;
import com.it_uatech.hibernate_impl.dao.UserDaoImpl;
import com.it_uatech.hibernate_impl.sessionmanager.SessionManagerHibernate;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DBServiceUserImplTest {

    private DBServiceUserImpl serviceUser;
    private static UserDataSet uds1;
    private static UserDataSet uds2;
    private static UserDataSet uds3;

    @BeforeAll
    public static void createDataSets(){
        uds1 = new UserDataSet("Maks",25);
        AddressDataSet ads1 = new AddressDataSet();
        ads1.setStreet("street");
        uds1.setAddress(ads1);
        PhoneDataSet phone1 = new PhoneDataSet();
        phone1.setNumber("566-77-98");
        PhoneDataSet phone2 = new PhoneDataSet();
        phone2.setNumber("789-47-12");
        uds1.addPhone(phone1,phone2);

        uds2 = new UserDataSet("Anna",42);
        AddressDataSet ads2 = new AddressDataSet();
        ads2.setStreet("kievskaya");
        uds2.setAddress(ads2);
        PhoneDataSet phone12 = new PhoneDataSet();
        phone12.setNumber("854-78-74");
        PhoneDataSet phone21 = new PhoneDataSet();
        phone21.setNumber("781-72-47");
        uds2.addPhone(phone12,phone21);

        uds3 = new UserDataSet("Zorcha",3);
        AddressDataSet ads3 = new AddressDataSet();
        ads3.setStreet("akademika Pavlova");
        uds3.setAddress(ads3);
        PhoneDataSet phone13 = new PhoneDataSet();
        phone13.setNumber("855-77-77");
        uds3.addPhone(phone13);
    }

    @BeforeEach
    public void setUp(){
        SessionFactory sessionFactory = HibernateUtil.buildSessionFactory("hibernate-test.cfg", UserDataSet.class, AddressDataSet.class, PhoneDataSet.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao dao = new UserDaoImpl(sessionManager);
        serviceUser = new DBServiceUserImpl(dao);
    }

    @Test
    public void shouldSaveUserDataSetAndRetrieveStoredId(){
        UserDataSet uds = new UserDataSet("Anna",42);
        long id = serviceUser.save(uds);
        assertEquals(1,id);
    }

    @Test
    public void shouldSaveUserDataSetWithAddressDataSetAndRetrieveStoredId(){
        UserDataSet uds = new UserDataSet("Zoriana",3);
        AddressDataSet ads = new AddressDataSet();
        ads.setStreet("Irpen");
        uds.setAddress(ads);
        long id = serviceUser.save(uds);
        assertEquals(1,id);
    }

    @Test
    public void shouldSaveUserDataSetWithAddressDataSetWithPhoneDataSetAndRetrieveStoredId(){
        long id = serviceUser.save(uds1);
        assertEquals(1,id);
    }

    @Test
    public void shouldFindUserById(){
        long id1 = serviceUser.save(uds1);
        long id2 = serviceUser.save(uds2);
        long id3 = serviceUser.save(uds3);
        assertEquals(1,id1);
        assertEquals(2,id2);
        assertEquals(3,id3);

        Optional<UserDataSet> user = serviceUser.findById(id2);
        assertEquals(uds2.getName(),user.get().getName());
    }

    @Test
    public void shouldSaveListOfUsersAndRetrieveAllOfThem(){
        List<UserDataSet> userDataSetList = new ArrayList<>();
        userDataSetList.add(uds1);
        userDataSetList.add(uds2);
        userDataSetList.add(uds3);
        serviceUser.save(userDataSetList);

        List<UserDataSet> usersRetrieval = serviceUser.findAll();
        assertEquals(userDataSetList.size(),usersRetrieval.size());
    }

    @Test
    public void shouldGetLocalStatus(){
        String status = serviceUser.getLocalStatus();
        assertEquals("ACTIVE",status);
    }

    @Test
    public void shouldFindByName(){
        serviceUser.save(uds1);
        serviceUser.save(uds2);
        serviceUser.save(uds3);

        Optional<UserDataSet> user = serviceUser.findByName(uds3.getName());
        assertEquals(uds3.getName(),user.get().getName());
    }

    @Test
    public void shouldRetrieveNullIfNothingFound(){
        Optional<UserDataSet> user = serviceUser.findById(5);
        assertFalse(user.isPresent());

        Optional<UserDataSet> user1 = serviceUser.findByName("masha");
        assertFalse(user1.isPresent());
    }

    @AfterEach
    public void tearDown(){
        serviceUser.shutdown();
    }
}