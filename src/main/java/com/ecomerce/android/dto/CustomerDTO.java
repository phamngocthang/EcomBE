package com.ecomerce.android.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private String userName;

    private String address;

    private String avatar;

    private String fullname;

    private String phonenumber;
}
