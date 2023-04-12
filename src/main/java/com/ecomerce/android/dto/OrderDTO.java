package com.ecomerce.android.dto;

import com.ecomerce.android.model.Customer;
import com.ecomerce.android.model.Lineitem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private int orderId;

    private double totalPrice;

    private List<LineitemDTO> lineitems;

    private CustomerDTO customer;
//    private List<LineitemDTO> lineitems;
}
