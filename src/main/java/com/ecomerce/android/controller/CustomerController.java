package com.ecomerce.android.controller;

import com.ecomerce.android.config.uploadFile.IStorageService;
import com.ecomerce.android.dto.CustomerDTO;
import com.ecomerce.android.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @Autowired
    IStorageService storageService;

    @GetMapping("/customer/image/{id}")
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
    @PostMapping(value = "/customer/change-avatar")
    public ResponseEntity<?> changeAvatar(@RequestParam("name") String name,
                                          @RequestParam("images") MultipartFile file) throws Exception {
        System.out.println("name :" + name);
        if(customerService.changeAvatar(name, file)) {
            return ResponseEntity.status(HttpStatus.OK).body("Success");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Failed");
        }
    }

    @GetMapping(value = "/customer/{name}")
    public  ResponseEntity<?> getCustomerInfor(@PathVariable("name") String name)  {
        CustomerDTO customerDTO = customerService.getCustomerById(name);
        if(customerDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed");
        }
    }
    @PostMapping(value = "/customer")
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerDTO customerDTO) {
        customerService.updateCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

}
