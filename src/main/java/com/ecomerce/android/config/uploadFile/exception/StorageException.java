package com.ecomerce.android.config.uploadFile.exception;

public class StorageException extends RuntimeException{
    public static final long serialVersionUID = 1L;

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Exception e) {
        super(message, e);
    }
}
