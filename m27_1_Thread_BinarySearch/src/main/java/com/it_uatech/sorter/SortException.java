package com.it_uatech.sorter;

public class SortException extends RuntimeException {
    public SortException(String message) {
        super(message);
    }

    public SortException(String message, Throwable cause) {
        super(message, cause);
    }
}
