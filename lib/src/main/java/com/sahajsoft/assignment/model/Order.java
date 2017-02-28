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
public class Order {

    private long id;
    private String companyName;
    private Long quantity;
    private Side side;
    private Status status;
    private Long remainingQuantity;

    public Order(long id, Side side, String companyName, Long quantity) {
        this.id = id;
        this.side = side;
        this.companyName = companyName;
        this.quantity = quantity;
        status = Status.OPEN;
        this.remainingQuantity = quantity;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
        setRemainingQuantity(quantity);
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(Long remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

}
