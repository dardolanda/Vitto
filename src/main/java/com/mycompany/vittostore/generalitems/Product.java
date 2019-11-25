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
    
    public Product() {}

    public Product(Long id, int amountConsumed, String brand, double price) {
        this.id = id;
        this.amountConsumed = amountConsumed;
        this.brand = brand;
        this.price = price;
    }
            

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAmountConsumed() {
        return amountConsumed;
    }

    public void setAmountConsumed(int amountConsumed) {
        this.amountConsumed = amountConsumed;
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
