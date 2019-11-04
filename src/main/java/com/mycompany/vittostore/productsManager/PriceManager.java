/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vittostore.productsManager;

import com.mycompany.vittostore.generalitems.Product;
import com.mycompany.vittostore.dataStore.DataStore;
import com.mycompany.vittostore.generalitems.NoAlcoholDrinksEnum;
import com.mycompany.vittostore.database.VittoStoreDDBBRepository;
import java.util.ArrayList;



import java.util.Map;
import java.util.List;


public class PriceManager {
    private Product product;
    private List<Product> productList;
    
    
    public List<Product> getPricesFromDataStore(DataStore dataStore , VittoStoreDDBBRepository vittoDDBBStore) {
        productList = new ArrayList<>();
        double price = 0;
        
        for (Map.Entry<NoAlcoholDrinksEnum, Integer> entry : dataStore.getNoAlcoholDrinks().entrySet()) {
            System.out.println("key --> " + entry.getKey());
            System.out.println("value --> " + entry.getValue());
            
            this.product.setPrice(vittoDDBBStore.getPriceFromProductName(entry.getKey().toString()));
            this.product.setAmountConsumed(entry.getValue());
            this.product.setBrand(entry.getKey().toString());
            
            productList.add(product);
        }
        
        

        
        
        
        return productList;
    }
    
}
