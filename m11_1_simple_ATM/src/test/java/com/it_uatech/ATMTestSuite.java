package com.it_uatech;

import com.it_uatech.model.BanknoteTest;
import com.it_uatech.storage.ATMStorageTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BanknoteTest.class,
        ATMStorageTest.class
})
public class ATMTestSuite {

}
