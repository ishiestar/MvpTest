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
public enum Status {
    OPEN, CLOSED;

    public static String getStatusString(Status s) {
        switch (s) {
            case OPEN:
                return "OPEN";
            case CLOSED:
                return "CLOSED";
            default:
                return "";
        }

    }
}
