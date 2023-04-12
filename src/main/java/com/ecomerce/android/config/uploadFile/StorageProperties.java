package com.ecomerce.android.config.uploadFile;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties("storage")
public class StorageProperties {
    private String location;
}
