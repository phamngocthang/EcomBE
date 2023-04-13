package com.ecomerce.android.controller;

import com.ecomerce.android.config.uploadFile.IStorageService;
import com.ecomerce.android.dto.CustomerDTO;
import com.ecomerce.android.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @Autowired
    IStorageService storageService;

    @GetMapping("/image/{id}")
    public ResponseEntity<Resource> getImage(@PathVariable("id") String name) {
        CustomerDTO customer = customerService.getCustomerById(name);
        String filename = customer.getAvatar();
        Resource file = storageService.loadAsResource(filename);
        String contentType = null;
        if(filename.toLowerCase().endsWith(".jpg") || filename.toLowerCase().endsWith(".jpeg")) {
            contentType = MediaType.IMAGE_JPEG_VALUE;
        }
        else if(filename.toLowerCase().endsWith(".png")){
            contentType = MediaType.IMAGE_PNG_VALUE;
        }
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, contentType).body(file);

    }

    // Change Avatar Customer - Cloud
    @PostMapping(value = "/change-avatar")
    public ResponseEntity<?> changeAvatar(@RequestParam("name") String name,
                                          @RequestParam("images") MultipartFile file) throws Exception {
        if(customerService.changeAvatar(name, file)) {
            return ResponseEntity.status(HttpStatus.OK).body("Success");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Failed");
        }
    }
}
