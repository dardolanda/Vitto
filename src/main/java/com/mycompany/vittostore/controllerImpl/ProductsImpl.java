/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vittostore.controllerImpl;

import com.mycompany.vittostore.controller.Products;
import com.mycompany.vittostore.dataStore.DataStore;

import com.mycompany.vittostore.generalitems.Product;
import com.mycompany.vittostore.dataStore.DataStore;
import com.mycompany.vittostore.generalitems.NoAlcoholDrinksEnum;
import com.mycompany.vittostore.database.VittoStoreDDBBRepository;
import java.util.ArrayList;

import java.util.Map;
import java.util.List;

public class ProductsImpl extends VittoConnection implements Products {

    public ProductsImpl(int sequence) {
        super(sequence);
    }

    @Override
    public void insertProduct(DataStore dataStore) {
        List<Product> productList = this.getPricesFromDataStore(dataStore);
        
        productList.forEach(product -> 
                System.out.println("product to get inserte --> " + product.getBrand())
        );
        
        vittoDDBBStore.insertProduct(productList);
    }

    private List<Product> getPricesFromDataStore(DataStore dataStore) {
        List<Product> productList = new ArrayList<>();

        if (!dataStore.getNoAlcoholDrinks().isEmpty()) {
            System.out.println("NO alcohol drinks -> (GetPricesFromDataStore)");
            for (Map.Entry<NoAlcoholDrinksEnum, Integer> entry : dataStore.getNoAlcoholDrinks().entrySet()) {
                System.out.println("key --> " + entry.getKey());
                System.out.println("value --> " + entry.getValue());

                Product product = new Product();
                product.setPrice(vittoDDBBStore.getPriceFromProductName(entry.getKey().toString()));
                product.setAmountConsumed(entry.getValue());
                product.setBrand(entry.getKey().toString());

                productList.add(product);
            }
        }
        
        
        
        // TODO: tener en cuenta que:
        //      1) Hay que hacer todas las validaciones de 
        //         dataStore para devolver una lista completa de artículos.
        //         validacion -> if (!dataStore.getNoAlcoholDrinks().isEmpty())
        //         hay que hacerlas con todos los productos:
        //         bebidas sin alcohol, sólidos, promociones, etc............
        
        //      2) Hacer new Product por cada collection que se itere
        
        
        // Por último retornar la lista con todos los productos, teniendo en 
        // cuenta que es una lista que puede tener cualquier tipo de producto.
        return productList;
    }

}
