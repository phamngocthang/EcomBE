package com.ecomerce.android.service.impl;

import com.cloudinary.Cloudinary;
import com.ecomerce.android.config.uploadFile.IStorageService;
import com.ecomerce.android.dto.CustomerDTO;
import com.ecomerce.android.mapper.Mapper;
import com.ecomerce.android.model.Customer;
import com.ecomerce.android.model.User;
import com.ecomerce.android.responsitory.CustomerRepository;
import com.ecomerce.android.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
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
        Optional<Customer> isCustomer = customerRepository.findById(name);
        if(isCustomer.isPresent())
            return mapper.convertTo(isCustomer.get(), CustomerDTO.class);
        else {
            return null;
        }
    }

    @Override
    public Boolean changeAvatar(String name, MultipartFile file) throws Exception {
        Optional<Customer> isCustomer = customerRepository.findById(name);
        if(isCustomer.isPresent()) {
            Customer customer = isCustomer.get();
            if(customer.getAvatar() == null || customer.getAvatar().equals("")) {
                // User chưa có hình, muốn thêm hình
                UUID uuid = UUID.randomUUID();
                String uuString = uuid.toString();
                String filename = storageService.getStorageFilename(file, uuString);
                storageService.store(file, filename); // Luu file
                // update file
                customer.setAvatar(filename);
                customerRepository.save(customer);
            }
            else {
                // User đã có hình, xóa hình cũ, upload hình mới
                String oldAvatar = customer.getAvatar();
                storageService.delete(oldAvatar); // xoa hình
                UUID uuid = UUID.randomUUID();
                String uuString = uuid.toString();
                String newFilename = storageService.getStorageFilename(file, uuString);
                storageService.store(file, newFilename);
                customer.setAvatar(newFilename); // Cập nhật trường image của đối tượng Customer
                customerRepository.save(customer);
            }
            return true;
        }
        else  {
            return false;
        }

    }
    @Override
    public void updateCustomer(CustomerDTO customerDTO) {
        Customer customer = mapper.convertTo(customerDTO, Customer.class);
        String getAvatar = customerRepository.findById(customer.getUserName()).get().getAvatar();
        customer.setAvatar(getAvatar);
        customerRepository.save(customer);

    }

	@Override
	public <S extends Customer> boolean save(S entity) {
		// TODO Auto-generated method stub
		return customerRepository.save(entity) != null;
	}
}
