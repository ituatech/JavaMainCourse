package com.it_uatech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainPackageClass {

    private static final Logger logger = LoggerFactory.getLogger(MainPackageClass.class);

    public void doLogging(){

        logger.trace("trace message");
        logger.debug("debug message");
        logger.info("info message");
        logger.warn("warning message");
        logger.error("error message");
    }
}
