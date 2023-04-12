package com.ecomerce.android.config.uploadFile;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface IStorageService {
    String getStorageFilename(MultipartFile file, String id);

    void store(MultipartFile file, String storeFilename);

    Resource loadAsResource(String filename);

    Path load(String filename);

    void delete(String storeFilename) throws Exception;

    void init();
}
