/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sahajsoft.assignment;

import com.sahajsoft.assignment.model.Order;
import com.sahajsoft.assignment.model.Side;
import com.sahajsoft.assignment.util.Util;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Rahul
 */
public class MainClass {

    public static void main(String[] args) throws IOException, Exception {

        while (true) {
            Broker broker = new Broker();
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter number of orders that you want to place:");
            int n = scanner.nextInt();
            for (int i = 0; i < n; i++) {
                System.out.print("Side :");
                String side = scanner.next();

                System.out.print("Comapny :");
                String comany = scanner.next();

                System.out.print("Qunatity :");
                long quantity = scanner.nextLong();
                Order stock = new Order(Util.ORDER_ID, Side.getSide(side), comany, quantity);
                broker.takeOrder(stock);
                Util.ORDER_ID++;
            }
            System.out.println("--------------------------------------");
            System.out.println("Side Company Quantity  RemaingQty Status");
            broker.placeOrder();
            System.out.println("--------------------------------------");
        }
    }

}
