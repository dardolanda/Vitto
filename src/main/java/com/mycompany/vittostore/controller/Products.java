/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vittostore.controller;

import com.mycompany.vittostore.dataStore.CloseDateDataStore;
import com.mycompany.vittostore.dataStore.ClosePaymentDataStore;
import com.mycompany.vittostore.dataStore.DataStore;
import com.mycompany.vittostore.dataStore.PaymentDataStore;
import com.mycompany.vittostore.generalitems.Product;
import com.mycompany.vittostore.generalitems.ProductTypeEnum;
import java.util.List;
import java.util.Map;

public interface Products {
    
    public void insertProduct(DataStore dataStore);
    
    public void insertProductWithPrices(DataStore dataStore);
    
    public List<Product> getConsumingProduct(int tableId, String tableUser);
    
    public String getTableState(int tableId);
    
    public void closeTable(int tableId, String tableUser);
    
    public List<Map<Integer, String>> getOperatingTable();
    
    public String findTableUserByTableId(int tableId);
    
    public List<Product> findTableSelectedProducts(int tableId, ProductTypeEnum productTypeEnum);
    
    public void payTable(PaymentDataStore paymentDataStore);
    
    public void deleteTable(int tableId);
    
    public ClosePaymentDataStore getClosingData(CloseDateDataStore closeDateDataStore);
    
}
