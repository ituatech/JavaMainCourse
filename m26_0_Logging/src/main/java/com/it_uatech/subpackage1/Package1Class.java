package com.it_uatech.subpackage1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Package1Class {

    private static final Logger logger = LoggerFactory.getLogger(Package1Class.class);

    public void doLogging(){

        logger.trace("trace message");
        logger.debug("debug message");
        logger.info("info message");
        logger.warn("warning message");
        logger.error("error message");
    }
}
