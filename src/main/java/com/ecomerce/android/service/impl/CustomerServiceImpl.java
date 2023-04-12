package com.ecomerce.android.service.impl;

import com.cloudinary.Cloudinary;
import com.ecomerce.android.config.uploadFile.IStorageService;
import com.ecomerce.android.dto.CustomerDTO;
import com.ecomerce.android.mapper.Mapper;
import com.ecomerce.android.model.Customer;
import com.ecomerce.android.responsitory.CustomerRepository;
import com.ecomerce.android.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    Cloudinary cloudinary;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    IStorageService storageService;

    @Autowired
    Mapper mapper;

    @Override
    public CustomerDTO getCustomerById(String name) {
        Customer customer = customerRepository.findById(name).get();
        return mapper.convertTo(customer, CustomerDTO.class);
    }
    @Override
    public String changeAvatar(String name, MultipartFile file) throws IOException {
        Boolean isUser = customerRepository.findById(name).isPresent();
        if(isUser) {
            UUID uuid = UUID.randomUUID();
            String uuString = uuid.toString();
            String filename = storageService.getStorageFilename(file, uuString);
            storageService.store(file, filename); // Luu file
            // update file
            Customer customer = customerRepository.findById(name).get();
            customer.setAvatar(filename);
            customerRepository.save(customer);
            return "Success";
        }
        else  {
            return "Failed";
        }

    }
}
