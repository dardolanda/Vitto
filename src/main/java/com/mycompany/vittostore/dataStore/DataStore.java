/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vittostore.dataStore;

import com.mycompany.vittostore.generalitems.AlcoholDrinksEnum;
import com.mycompany.vittostore.generalitems.NoAlcoholDrinksEnum;
import com.mycompany.vittostore.generalitems.ProductTypeEnum;
import com.mycompany.vittostore.generalitems.SweetProductsEnum;

import java.util.Map;

public class DataStore {
    /**
     * Atributos propios de la mesa operativa.
    */
    private ProductTypeEnum productTypeEnum;
    private int mesa;
    private String nombreMozo;
    private int productoId;
    private String productoNombre;
    private int productoCantidad;
    private double productoPrecioUnitatio;
    
    /**
    * Atributos propios del consumo de la mesa en cuestión.
    */
    private Map<NoAlcoholDrinksEnum, Integer> noAlcoholDrinks;
    private Map<SweetProductsEnum, Map<Integer, Double>> sweetProducts;
    private Map<AlcoholDrinksEnum, Map<Integer, Double>> alcoholProducts;
    

    public ProductTypeEnum getProductTypeEnum() {
        return productTypeEnum;
    }

    public void setProductTypeEnum(ProductTypeEnum productTypeEnum) {
        this.productTypeEnum = productTypeEnum;
    }
    
    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    public String getNombreMozo() {
        return nombreMozo;
    }

    public void setNombreMozo(String nombreMozo) {
        this.nombreMozo = nombreMozo;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getProductoNombre() {
        return productoNombre;
    }

    public void setProductoNombre(String productoNombre) {
        this.productoNombre = productoNombre;
    }

    public int getProductoCantidad() {
        return productoCantidad;
    }

    public void setProductoCantidad(int productoCantidad) {
        this.productoCantidad = productoCantidad;
    }

    public double getProductoPrecioUnitatio() {
        return productoPrecioUnitatio;
    }

    public void setProductoPrecioUnitatio(double productoPrecioUnitatio) {
        this.productoPrecioUnitatio = productoPrecioUnitatio;
    } 

    public Map<NoAlcoholDrinksEnum, Integer> getNoAlcoholDrinks() {
        return noAlcoholDrinks;
    }

    public void setNoAlcoholDrinks(Map<NoAlcoholDrinksEnum, Integer> noAlcoholDrinks) {
        this.noAlcoholDrinks = noAlcoholDrinks;
    }

    public Map<SweetProductsEnum, Map<Integer, Double>> getSweetProducts() {
        return sweetProducts;
    }

    public void setSweetProducts(Map<SweetProductsEnum, Map<Integer, Double>> sweetProducts) {
        this.sweetProducts = sweetProducts;
    }

    public Map<AlcoholDrinksEnum, Map<Integer, Double>> getAlcoholProducts() {
        return alcoholProducts;
    }

    public void setAlcoholProducts(Map<AlcoholDrinksEnum, Map<Integer, Double>> alcoholProducts) {
        this.alcoholProducts = alcoholProducts;
    }   
    
}
