package com.ecomerce.android.service.impl.admin;

import com.ecomerce.android.mapper.Mapper;
import com.ecomerce.android.responsitory.OrderRepository;
import com.ecomerce.android.service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

@Service
public class DashBoardServiceImpl implements DashBoardService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private Mapper productMapper;

    @Override
    public HashMap<String, Double> getStatistics() {
        HashMap<String, Double> listStatistics = new HashMap<>();
        listStatistics.put("Total price of orders created in a month", orderRepository.getTotalPriceOrders());
        listStatistics.put("Number of users created in a month", orderRepository.countUsers());
        listStatistics.put("Number of reviews created in a month", orderRepository.countReviews());
        listStatistics.put("Number of products created in a month", orderRepository.countProducts());

        return listStatistics;
    }

    @Override
    public TreeMap<Integer, Double> getBillInMonth() {
        List<Object[]> listResult = orderRepository.getBillInMonth();
        TreeMap<Integer, Double> mapBillInMonth = new TreeMap<>();
        for (int i = 1; i <= 12; i++) {
            int check = 0; // check xem trong thang có hóa đơn hay không
            for (Object[] objects : listResult) {
                if(objects[0].toString().equals(String.valueOf(i))) {
                    mapBillInMonth.put(i, Double.valueOf(objects[1].toString()));
                    check = 1;
                }
            }
            if(check == 0) {
                mapBillInMonth.put(i, 0.0);
            }
        }
        return mapBillInMonth;
    }
    @Override
    public HashMap<String, Double> getTotalPriceOfBrand() {
        List<Object[]> listResult = orderRepository.getTotalPriceOfBrand();
        List<String> listBrandName = orderRepository.getAllBrandName();
        HashMap<String, Double> mapTotalPriceOfBrand = new HashMap<>();
        for (String brandName : listBrandName) {
            boolean check = false;
            for (Object[] objects : listResult) {
                if(brandName.equals(objects[0].toString())) {
                    mapTotalPriceOfBrand.put(objects[0].toString(), Double.valueOf(objects[1].toString()));
                    check = true;
                    break;
                }
            }
            if (!check) {
                mapTotalPriceOfBrand.put(brandName, 0.0);
            }
        }

        return mapTotalPriceOfBrand;
    }
}
