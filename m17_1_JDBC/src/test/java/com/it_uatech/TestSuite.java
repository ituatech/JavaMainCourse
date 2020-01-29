package com.it_uatech;

import com.it_uatech.dao.ConnectionTest;
import com.it_uatech.dao.UserDataDaoImplTest;
import com.it_uatech.executor.ExecutorCreateDeleteTableTest;
import com.it_uatech.executor.ExecutorTest;
import com.it_uatech.pool.ConnectionPoolTest;
import com.it_uatech.service.DBServiceImplTest;
import com.it_uatech.utils.ReflectionHelperTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ConnectionTest.class,
        ExecutorCreateDeleteTableTest.class,
        UserDataDaoImplTest.class,
        ExecutorTest.class,
        ReflectionHelperTest.class,
        ConnectionPoolTest.class,
        DBServiceImplTest.class
})
public class TestSuite {
}
