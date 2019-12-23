package com.mycompany.vittostore.dataStore;

import com.mycompany.vittostore.generalitems.PaymentMethodsEnum;

public class PaymentDataStore {
    
    /**
     * Atributos propios del pago.
    */
    private int tableId;
    private double total;
    private double discount;
    private String nombreMozo;
    private PaymentMethodsEnum paymentMethod;
    
    /**
     * Atributos propios de la información del cliente, 
     * teniendo en cuenta si el pago no se hizo en efectivo.
    */
    private String nombreCliente;
    private String apellidoCliente;
    private String DNICliente;
    private String telefonoCliente;
    
    /**
     * Constructores:
     * Tenemos 3 tipos, para tener en cuenta en cada ocación.
     * 
     * Para pago en efectivo solo se necesitan los datos del pago, mientras que 
     * para un pago que no se haya hecho en efectivo, se tiene que almacenar
     * los datos del cliente, ya que el pago se realizó con algun tipo de 
     * targeta.
     * 
     * Dejamos un constructor vacío, en caso de que no sean directos los datos
     * requeridos.
     */
    public PaymentDataStore(){}

    public PaymentDataStore(int tableId, double total, double discount, String nombreMozo, PaymentMethodsEnum paymentMethod) {
        this.tableId = tableId;
        this.total = total;
        this.discount = discount;
        this.nombreMozo = nombreMozo;
        this.paymentMethod = paymentMethod;
    }

    public PaymentDataStore(int tableId, double total, double discount, String nombreMozo, PaymentMethodsEnum paymentMethod, String nombreCliente, String apellidoCliente, String DNICliente, String telefonoCliente) {
        this.tableId = tableId;
        this.total = total;
        this.discount = discount;
        this.nombreMozo = nombreMozo;
        this.paymentMethod = paymentMethod;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.DNICliente = DNICliente;
        this.telefonoCliente = telefonoCliente;
    }
    

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getNombreMozo() {
        return nombreMozo;
    }

    public void setNombreMozo(String nombreMozo) {
        this.nombreMozo = nombreMozo;
    }

    public PaymentMethodsEnum getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodsEnum paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getDNICliente() {
        return DNICliente;
    }

    public void setDNICliente(String DNICliente) {
        this.DNICliente = DNICliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }
    
}
