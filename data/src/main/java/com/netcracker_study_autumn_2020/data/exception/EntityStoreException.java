package com.netcracker_study_autumn_2020.data.exception;

public class EntityStoreException extends Exception {
    public EntityStoreException() {
    }

    public EntityStoreException(String message) {
        super(message);
    }

    public EntityStoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityStoreException(Throwable cause) {
        super(cause);
    }
}
