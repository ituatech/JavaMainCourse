package com.it_uatech;

import com.it_uatech.class_for_testing.TestClassFirst;
import com.it_uatech.class_for_testing.TestClassSecond;
import com.it_uatech.my_junit.Runner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunMyJUnit {
    private static Logger logger = LoggerFactory.getLogger(RunMyJUnit.class);

    public static void main(String[] args) {
        logger.info("Start test my TestClassFirst");
        Runner.runnerStart(TestClassFirst.class);
        logger.info("---------------------------------------");
        logger.info("Start test my TestClassFirst and TestClassSecond");
        Runner.runnerStart(TestClassFirst.class, TestClassSecond.class);
        logger.info("---------------------------------------");
        logger.info("Start test my Package");
        Runner.runnerStart("com.it_uatech.class_for_testing");
        logger.info("---------------------------------------");
    }
}
