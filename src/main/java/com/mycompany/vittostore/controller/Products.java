/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vittostore.controller;

import com.mycompany.vittostore.dataStore.DataStore;
import com.mycompany.vittostore.generalitems.Product;
import java.util.List;
import java.util.Map;

public interface Products {
    
    public void insertProduct(DataStore dataStore);
    
    public List<Product> getConsumingProduct(int tableId, String tableUser);
    
    public void closeTable(int tableId, String tableUser);
    
    public List<Map<Integer, String>> getOperatingTable();
    
    public String findTableUserByTableId(int tableId);
    
    public List<Product> findTableSelectedProducts(int tableId);
    
    public void payTable(int tableId, double totalToPay, double discount, String nombreMozo, String payMethod);
    
    
    
    
}
