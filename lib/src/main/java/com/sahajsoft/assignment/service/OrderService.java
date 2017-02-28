/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sahajsoft.assignment.service;

import com.sahajsoft.assignment.model.Order;
import com.sahajsoft.assignment.model.Status;
import com.sahajsoft.assignment.util.Util;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Rahul
 */
public class OrderService {

    Long orderQuantity;

    public void placeOrder(Order order) {
        Util.getOrders().add(order);
        List<Order> orders = Util.getOrders(order.getCompanyName(),
                order.getSide(), Status.OPEN);
        orderQuantity = order.getRemainingQuantity();
        orders.stream().filter(o -> orderQuantity > 0)
                .forEach((o) -> {
                    Long remainingOrderQuantity = matchQuantity(o);
                    updateRemainingQuantityStatus(orderQuantity, order,
                            remainingOrderQuantity, o);
                });

    }

    private Long matchQuantity(Order o) {
        Long o_orderQuantity = o.getRemainingQuantity();
        if (Objects.equals(orderQuantity, o_orderQuantity)) {
            orderQuantity = 0L;
            o_orderQuantity = 0L;
        } else if (orderQuantity < o_orderQuantity) {
            o_orderQuantity -= orderQuantity;
            orderQuantity = 0L;
        } else {
            orderQuantity -= o_orderQuantity;
            o_orderQuantity = 0L;
        }
        return o_orderQuantity;
    }

    private void updateRemainingQuantityStatus(Long orderQuantity, Order order, Long o_orderQuantity, Order o) {

        order.setRemainingQuantity(orderQuantity);
        if (orderQuantity == 0) {
            order.setStatus(Status.CLOSED);
        }
        o.setRemainingQuantity(o_orderQuantity);
        if (o_orderQuantity == 0) {
            o.setStatus(Status.CLOSED);
        }

    }

    public void printOrder() {
        Util.printOrders();
    }

}
