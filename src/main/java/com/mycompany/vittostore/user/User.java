/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vittostore.user;

public class User {
    
    private int ID;
    private String nombre;
    private String apellido;
    private String DNI;
    
    public User(int id , String nombre, String apellido, String dni) {
        this.ID = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.DNI = dni;
    }
    

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    @Override
    public String toString() {
        return "User{" + "ID=" + ID + ", nombre=" + nombre + ", apellido=" + apellido + ", DNI=" + DNI + '}';
    }
    
    
    
}
