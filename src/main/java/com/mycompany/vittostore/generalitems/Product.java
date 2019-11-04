/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vittostore.generalitems;

public class Product {
    
    private Long id;
    private int amountConsumed;
    private String brand;
    private double price;

    public int getQuantity() {
        return amountConsumed;
    }

    public void setQuantity(int quantity) {
        this.amountConsumed = quantity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
}
