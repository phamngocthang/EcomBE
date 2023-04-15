package com.ecomerce.android.service.impl;

import com.ecomerce.android.dto.EmailDTO;
import com.ecomerce.android.dto.OrderDTO;
import com.ecomerce.android.mapper.Mapper;
import com.ecomerce.android.model.Customer;
import com.ecomerce.android.model.Lineitem;
import com.ecomerce.android.model.Order;
import com.ecomerce.android.responsitory.OrderRepository;
import com.ecomerce.android.responsitory.UserRepository;
import com.ecomerce.android.sendmail.EmailService;
import com.ecomerce.android.service.OrderService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final Mapper mapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public boolean sendmailOrder(Order order) {
        try {
            Customer customer = order.getCustomer();
            String email = userRepository.findById(customer.getUserName()).get().getEmail();
            if(email == null) {
                return false;
            }

            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String html = "<html><body><h3>Thông tin hóa đơn của khách hàng:</h3>" +
                    "<p>Tên khách hàng: " + customer.getFullname() + "</p>" +
                    "<table><thead><tr><th>Sản phẩm</th><th>Số lượng</th><th>Đơn giá</th><th>Thành tiền</th></tr></thead><tbody>";

            double subtotal = 0;
            for(Lineitem item: order.getLineitems()) {
                html += "<tr><td>" + item.getOption().getProduct().getProductName() + "</td><td>" + item.getQuantity() + "</td><td>" + item.getOption().getPrice() + "</td><td>" + item.getQuantity() * item.getOption().getPrice() + "</td></tr>";
                subtotal += item.getQuantity() * item.getOption().getPrice();
            }

            html += "</tbody></table>" +
                    "<p>Tổng tiền hóa đơn: " + order.getTotalPrice() + "</p>" +
                    "<p>Ngày đặt hàng: " + order.getUpdateAt() + "</p>" +
                    "<p>Cảm ơn quý khách đã mua hàng của chúng tôi.</p></body></html>";

            helper.setTo(email);
            helper.setSubject("Thông tin đặt hàng");
            helper.setText(html, true);

            emailSender.send(message);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean saveOrder(OrderDTO orderDTO) {
        Order order = mapper.convertTo(orderDTO, Order.class);
        Order savedOrder = orderRepository.save(order);
        if(savedOrder != null) {
            sendmailOrder(order);
            return true;
        }
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
