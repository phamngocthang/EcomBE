package com.ecomerce.android.config.uploadFile.exception;

public class StorageFileNotFoundException extends StorageException{

    public static final long serialVersionUID = 1L;

    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Exception e) {
        super(message, e);
    }
}
