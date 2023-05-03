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
    private Integer codeProvince;
    private Integer codeDistrict;
    private Integer codeSubDistrict;

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "userName='" + userName + '\'' +
                ", address='" + address + '\'' +
                ", avatar='" + avatar + '\'' +
                ", fullname='" + fullname + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", codeProvince=" + codeProvince +
                ", codeDistrict=" + codeDistrict +
                ", codeSubDistrict=" + codeSubDistrict +
                '}';
    }
}
