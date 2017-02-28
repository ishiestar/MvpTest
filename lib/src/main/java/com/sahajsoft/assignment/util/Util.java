/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sahajsoft.assignment.util;

import com.sahajsoft.assignment.model.Order;
import com.sahajsoft.assignment.model.Side;
import com.sahajsoft.assignment.model.Status;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Rahul
 */
public final class Util {

    private static final List<Order> ORDERS = new ArrayList<>();
    public static long ORDER_ID = 1L;

    public static List<Order> getOrders() {
        return ORDERS;
    }

    public static List<Order> getOrders(String comapny, Side side, Status s) {
        List<Order> filterOrder = ORDERS.stream()
                .filter(order -> order.getCompanyName().equals(comapny))
                .filter(order -> order.getSide() != side)
                .filter(order -> order.getStatus() == s)
                .collect(Collectors.toList());
        return filterOrder;
    }

    public static void printOrders() {
        ORDERS.stream().forEach((order) -> {
            System.out.format("%s \t %s \t %d \t %d \t %s \n",
                    Side.getSideString(order.getSide()),
                    order.getCompanyName(), order.getQuantity(),
                    order.getRemainingQuantity(),
                    Status.getStatusString(order.getStatus()));
        });
    }

    public static Order getOrder(long id) {
        return ORDERS.stream()
                .filter(order -> order.getId() == id)
                .limit(1)
                .collect(Collectors.toList())
                .get(0);
    }

}
