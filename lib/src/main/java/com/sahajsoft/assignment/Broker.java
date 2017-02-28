/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sahajsoft.assignment;

import com.sahajsoft.assignment.model.Order;
import com.sahajsoft.assignment.service.OrderService;
import com.sahajsoft.assignment.util.Util;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rahul
 */
public class Broker {

    private final List<Order> orders;
    private final OrderService service;

    public Broker() {
        orders = new ArrayList<>();
        service = new OrderService();
    }

    public void takeOrder(Order order) {
        orders.add(order);
    }

    public void placeOrder() {
        orders.stream().forEach(service::placeOrder);
        service.printOrder();
    }

    public Order getOrder(long id) {
        return Util.getOrder(id);
    }
}
