/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sahajsoft.assignment.model;

/**
 *
 * @author Rahul
 */
public enum Side {
    SELL, BUY;

    public static Side getSide(String side) throws Exception {
        if (side.equalsIgnoreCase("Buy")) {
            return BUY;
        } else if (side.equalsIgnoreCase("Sell")) {
            return SELL;
        } else {
            throw new Exception("No side found");
        }
    }

    public static String getSideString(Side s) {
        switch (s) {
            case BUY:
                return "Buy";
            case SELL:
                return "Sell";
        }
        return "";
    }
}
