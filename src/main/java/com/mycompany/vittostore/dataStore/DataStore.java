/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vittostore.dataStore;

import com.mycompany.vittostore.generalitems.NoAlcoholDrinksEnum;

import java.util.Map;

public class DataStore {
    /**
     * Atributos propios de la mesa operativa.
    */
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
    
    
}
