/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vittostore.generalitems;

public class Table {
    private String name;
    
    public Table(){}

    
    public Table(String name) {
        this.name = name;
    }

    public String getState() {
        return name;
    }

    public void setState(String state) {
        this.name = state;
    }
    
    @Override
    public String toString() {
        return "Table{" + "name=" + name + '}';
    }
    
    
}
