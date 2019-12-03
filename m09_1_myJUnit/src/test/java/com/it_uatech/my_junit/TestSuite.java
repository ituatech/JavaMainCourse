package com.it_uatech.my_junit;

import com.it_uatech.my_junit.util.ReflectionHelperTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RunnerTest.class,
        ReflectionHelperTest.class
})
public class TestSuite {
}
