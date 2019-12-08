/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vittostore.generalitems;

public class Table {
    private int id;
    private String name;
    
    public Table(){}

    
    public Table(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getState() {
        return name;
    }

    public void setState(String state) {
        this.name = state;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    
    
    
    
    @Override
    public String toString() {
        return "Table{" + "name=" + name + '}';
    }
    
    
}
