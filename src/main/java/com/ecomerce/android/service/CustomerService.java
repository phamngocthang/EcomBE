package com.ecomerce.android.service;

import com.ecomerce.android.dto.CustomerDTO;
import com.ecomerce.android.model.Customer;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CustomerService {
    CustomerDTO getCustomerById(String name);

    Boolean changeAvatar(String name, MultipartFile file) throws Exception;

    public <S extends Customer> boolean save(S entity); 
    
    void updateCustomer(CustomerDTO customerDTO);
}
