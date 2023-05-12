package com.ecomerce.android.service;

import java.util.HashMap;
import java.util.TreeMap;

public interface DashBoardService {
    HashMap<String, Double> getStatistics();

    TreeMap<Integer, Double> getBillInMonth();

    HashMap<String, Double> getTotalPriceOfBrand();
}
