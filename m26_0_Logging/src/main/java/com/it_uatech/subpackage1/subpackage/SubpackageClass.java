package com.it_uatech.subpackage1.subpackage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubpackageClass {

    private static final Logger logger = LoggerFactory.getLogger(SubpackageClass.class);

    public void doLogging(){

        logger.trace("trace message");
        logger.debug("debug message");
        logger.info("info message");
        logger.warn("warning message");
        logger.error("error message");
    }
}
