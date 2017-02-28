package com.sahajsoft.assignment;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.sahajsoft.assignment.model.Order;
import com.sahajsoft.assignment.model.Side;
import com.sahajsoft.assignment.model.Status;
import com.sahajsoft.assignment.util.Util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.sahajsoft.assignment.util.Util.getOrders;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 * @author Rahul
 */
public class OrderTest {

    Broker broker;

    @Before
    public void init() {
        broker = new Broker();

    }

    public OrderTest() {

    }

    @After
    public void clear(){
        Util.getOrders().clear();
    }

    @Test
    public void testOneOrder() {
        Order order = new Order(Util.ORDER_ID, Side.BUY, "ABC", 10L);
        Util.ORDER_ID++;
        this.broker.takeOrder(order);
        this.broker.placeOrder();
        assertThat(getOrders().size(), is(1));
        assertThat(broker.getOrder(order.getId()).getStatus(), is(Status.OPEN));
        assertThat(broker.getOrder(order.getId()).getRemainingQuantity(), is(10L));

    }

    @Test
    public void testSecondInput() {
        Order order = new Order(Util.ORDER_ID, Side.BUY, "ABC", 10L);
        Util.ORDER_ID++;
        Order order1 = new Order(Util.ORDER_ID, Side.SELL, "XYZ", 15L);
        Util.ORDER_ID++;
        Order order2 = new Order(Util.ORDER_ID, Side.SELL, "ABC", 13L);
        Util.ORDER_ID++;
        this.broker.takeOrder(order);
        this.broker.takeOrder(order1);
        this.broker.takeOrder(order2);
        this.broker.placeOrder();
        assertThat(getOrders().size(), is(3));
        assertThat(broker.getOrder(order.getId()).getStatus(), is(Status.CLOSED));
        assertThat(broker.getOrder(order.getId()).getRemainingQuantity(), is(0L));

        assertThat(broker.getOrder(order1.getId()).getStatus(), is(Status.OPEN));
        assertThat(broker.getOrder(order1.getId()).getRemainingQuantity(), is(15L));

        assertThat(broker.getOrder(order2.getId()).getStatus(), is(Status.OPEN));
        assertThat(broker.getOrder(order2.getId()).getRemainingQuantity(), is(3L));
    }

    @Test
    public void testThirdInput() {
        Order order = new Order(Util.ORDER_ID, Side.BUY, "ABC", 10L);
        Util.ORDER_ID++;
        Order order1 = new Order(Util.ORDER_ID, Side.SELL, "XYZ", 15L);
        Util.ORDER_ID++;
        Order order2 = new Order(Util.ORDER_ID, Side.SELL, "ABC", 13L);
        Util.ORDER_ID++;
        Order order3 = new Order(Util.ORDER_ID, Side.BUY, "XYZ", 10L);
        Util.ORDER_ID++;
        Order order4 = new Order(Util.ORDER_ID, Side.BUY, "XYZ", 8L);
        Util.ORDER_ID++;
        this.broker.takeOrder(order);
        this.broker.takeOrder(order1);
        this.broker.takeOrder(order2);
        this.broker.takeOrder(order3);
        this.broker.takeOrder(order4);
        this.broker.placeOrder();
        assertThat(getOrders().size(), is(5));

        assertThat(broker.getOrder(order.getId()).getStatus(), is(Status.CLOSED));
        assertThat(broker.getOrder(order.getId()).getRemainingQuantity(), is(0L));

        assertThat(broker.getOrder(order1.getId()).getStatus(), is(Status.CLOSED));
        assertThat(broker.getOrder(order1.getId()).getRemainingQuantity(), is(0L));

        assertThat(broker.getOrder(order2.getId()).getStatus(), is(Status.OPEN));
        assertThat(broker.getOrder(order2.getId()).getRemainingQuantity(), is(3L));

        assertThat(broker.getOrder(order3.getId()).getStatus(), is(Status.CLOSED));
        assertThat(broker.getOrder(order3.getId()).getRemainingQuantity(), is(0L));

        assertThat(broker.getOrder(order4.getId()).getStatus(), is(Status.OPEN));
        assertThat(broker.getOrder(order4.getId()).getRemainingQuantity(), is(3L));
    }
}
