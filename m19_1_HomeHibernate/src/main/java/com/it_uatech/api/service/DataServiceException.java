package com.it_uatech.api.service;

public class DataServiceException extends RuntimeException{

    public DataServiceException(Exception ex) {
        super(ex);
    }
}
