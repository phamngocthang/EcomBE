package com.ecomerce.android.service;

import com.ecomerce.android.dto.CustomerDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CustomerService {
    CustomerDTO getCustomerById(String name);

    String changeAvatar(String name, MultipartFile file) throws IOException;


}
