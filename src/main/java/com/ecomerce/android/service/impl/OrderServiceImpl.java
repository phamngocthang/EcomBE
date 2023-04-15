package com.ecomerce.android.service.impl;

import com.ecomerce.android.dto.OrderDTO;
import com.ecomerce.android.mapper.Mapper;
import com.ecomerce.android.model.*;
import com.ecomerce.android.responsitory.OptionRepository;
import com.ecomerce.android.responsitory.OrderRepository;
import com.ecomerce.android.responsitory.ProductReponsitory;
import com.ecomerce.android.responsitory.UserRepository;
import com.ecomerce.android.service.OrderService;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
    private OptionRepository optionRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public boolean sendmailOrder(Order order, double totalPrice, Timestamp updateAt) {
        try {
            Customer customer = order.getCustomer();
            User user = userRepository.findById(customer.getUserName()).get();
            String email = user.getEmail();
            if(email == null) {
                return false;
            }

            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String html = "<html><body><h3>Thông tin hóa đơn của khách hàng:</h3>" +
                    "<p>Tên khách hàng: " + user.getCustomer().getFullname() + "</p>" +
                    "<table><thead><tr><th>Sản phẩm</th><th>Số lượng</th><th>Đơn giá</th><th>Thành tiền</th></tr></thead><tbody>";

            for(Lineitem item: order.getLineitems()) {
                Option option = optionRepository.findById(item.getOption().getOptionId()).get();
                html += "<tr><td>" + option.getProduct().getProductName() + "</td><td>" + item.getQuantity() + "</td><td>" + item.getOption().getPrice() + "</td><td>" + item.getQuantity() * item.getOption().getPrice() + "</td></tr>";
            }

            html += "</tbody></table>" +
                    "<p>Tổng tiền hóa đơn: " + totalPrice + "</p>" +
                    "<p>Ngày đặt hàng: " + updateAt + "</p>" +
                    "<p>Cảm ơn quý khách đã mua hàng của chúng tôi.</p></body></html>";

            helper.setTo(email);
            helper.setSubject("Thông tin đặt hàng");
            helper.setText(html, true);

            emailSender.send(message);
            return true;
        }
        catch (Exception e) {
            System.out.println("Error" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean saveOrder(OrderDTO orderDTO) {
        Order order = mapper.convertTo(orderDTO, Order.class);
        double totalPrice = order.getLineitems().stream()
                .mapToDouble(Lineitem -> Lineitem.getQuantity() * Lineitem.getOption().getPrice())
                .reduce(0, (subtotal, element) -> subtotal + element);
        order.setTotalPrice(totalPrice);
        Order savedOrder = orderRepository.save(order);
        if(savedOrder != null) {
            sendmailOrder(order, savedOrder.getTotalPrice(), savedOrder.getUpdateAt());
            return true;
        }
        return false;
    }

    @Override
    public List<OrderDTO> getAllOrder() {
        return orderRepository.findAll().stream()
                .map(order -> mapper.convertTo(order, OrderDTO.class))
                .collect(Collectors.toList());

    }
}
