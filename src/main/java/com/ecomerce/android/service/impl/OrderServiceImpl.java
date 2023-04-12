package com.ecomerce.android.service.impl;

import com.ecomerce.android.dto.OrderDTO;
import com.ecomerce.android.mapper.Mapper;
import com.ecomerce.android.model.Order;
import com.ecomerce.android.responsitory.OrderRepository;
import com.ecomerce.android.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final Mapper mapper;

    @Autowired
    private OrderRepository orderRepository;
    @Override
    public boolean saveOrder(OrderDTO orderDTO) {
        return false;
    }

    @Override
    public List<OrderDTO> getAllOrder() {
//        orderRepository.findAll().stream()
//                .map(order -> mapper.convertToOrderDTO(order))
//                .collect(Collectors.toList()).forEach(System.out::println);
        return orderRepository.findAll().stream()
                .map(order -> mapper.convertTo(order, OrderDTO.class))
                .collect(Collectors.toList());

//        return orderRepository.selectAll();

    }
}
