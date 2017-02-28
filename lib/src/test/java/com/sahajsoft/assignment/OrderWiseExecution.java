/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sahajsoft.assignment;

import com.sahajsoft.assignment.model.Order;
import com.sahajsoft.assignment.model.Side;
import com.sahajsoft.assignment.model.Status;
import com.sahajsoft.assignment.util.Util;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static com.sahajsoft.assignment.util.Util.getOrders;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 * @author Rahul
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderWiseExecution {

    public Broker broker;

    @Before
    public void init() {
        broker = new Broker();

    }

    @Test
    public void firstInputTest() {
        Order order = new Order(Util.ORDER_ID, Side.BUY, "ABC", 10L);
        Util.ORDER_ID++;
        this.broker.takeOrder(order);
        this.broker.placeOrder();
        assertThat(getOrders().size(), is(1));
        assertThat(getOrders().get(0).getStatus(), is(Status.OPEN));
        assertThat(getOrders().get(0).getRemainingQuantity(), is(10L));
        System.out.println("-----------------------------------------------");
    }

    @Test
    public void secondInputTest() {
        Order order1 = new Order(Util.ORDER_ID, Side.SELL, "XYZ", 15L);
        Util.ORDER_ID++;
        Order order3 = new Order(Util.ORDER_ID, Side.SELL, "ABC", 13L);
        Util.ORDER_ID++;
        this.broker.takeOrder(order1);
        this.broker.takeOrder(order3);
        this.broker.placeOrder();
        assertThat(getOrders().size(), is(3));
        assertThat(getOrders().get(0).getStatus(), is(Status.CLOSED));
        assertThat(getOrders().get(0).getRemainingQuantity(), is(0L));

        assertThat(getOrders().get(1).getStatus(), is(Status.OPEN));
        assertThat(getOrders().get(1).getRemainingQuantity(), is(15L));

        assertThat(getOrders().get(2).getStatus(), is(Status.OPEN));
        assertThat(getOrders().get(2).getRemainingQuantity(), is(3L));
        System.out.println("---------------------------------------------");
    }

    @Test
    public void thirdInputTest() {

        Order order3 = new Order(Util.ORDER_ID, Side.BUY, "XYZ", 10L);
        Util.ORDER_ID++;
        Order order4 = new Order(Util.ORDER_ID++, Side.BUY, "XYZ", 8L);
        Util.ORDER_ID++;

        this.broker.takeOrder(order3);
        this.broker.takeOrder(order4);
        this.broker.placeOrder();
        assertThat(getOrders().size(), is(5));

        assertThat(getOrders().get(0).getStatus(), is(Status.CLOSED));
        assertThat(getOrders().get(0).getRemainingQuantity(), is(0L));

        assertThat(getOrders().get(1).getStatus(), is(Status.CLOSED));
        assertThat(getOrders().get(1).getRemainingQuantity(), is(0L));

        assertThat(getOrders().get(2).getStatus(), is(Status.OPEN));
        assertThat(getOrders().get(2).getRemainingQuantity(), is(3L));

        assertThat(getOrders().get(3).getStatus(), is(Status.CLOSED));
        assertThat(getOrders().get(3).getRemainingQuantity(), is(0L));

        assertThat(getOrders().get(4).getStatus(), is(Status.OPEN));
        assertThat(getOrders().get(4).getRemainingQuantity(), is(3L));
    }

}
