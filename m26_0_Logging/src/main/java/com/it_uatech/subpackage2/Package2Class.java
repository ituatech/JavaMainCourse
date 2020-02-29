package com.it_uatech.subpackage2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Package2Class {

    private static final Logger logger = LoggerFactory.getLogger(Package2Class.class);

    public void doLogging(){

        logger.trace("trace message");
        logger.debug("debug message");
        logger.info("info message");
        logger.warn("warning message");
        logger.error("error message");
    }
}
