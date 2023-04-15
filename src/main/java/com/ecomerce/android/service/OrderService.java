package com.ecomerce.android.service;

import com.ecomerce.android.dto.OrderDTO;
import com.ecomerce.android.model.Order;

import java.util.List;

public interface OrderService {
    boolean sendmailOrder(Order order);

    boolean saveOrder(OrderDTO orderDTO);

    List<OrderDTO> getAllOrder();
}
