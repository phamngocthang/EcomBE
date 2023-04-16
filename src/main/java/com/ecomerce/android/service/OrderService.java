package com.ecomerce.android.service;

import com.ecomerce.android.dto.OrderDTO;
import com.ecomerce.android.model.Order;

import java.sql.Timestamp;
import java.util.List;

public interface OrderService {
    boolean sendmailOrder(Order order, double totalPrice, Timestamp updateAt);

    boolean saveOrder(OrderDTO orderDTO);

    List<OrderDTO> getAllOrder();

    List<OrderDTO> sortOrder(boolean isASC, String attribute);

    List<OrderDTO> getOrderByUsername(String username);
}
