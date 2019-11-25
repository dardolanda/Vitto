/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vittostore.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import com.mycompany.vittostore.user.User;
import com.mycompany.vittostore.generalitems.Product;

import java.util.*;

public class VittoStoreDDBBRepository {

    private Connection DDBBConnection = null;

    public VittoStoreDDBBRepository(int sequence) throws SQLException {
        try {
            Class.forName("org.h2.Driver");
            this.DDBBConnection = DriverManager.getConnection("jdbc:h2:./VittoStore", "vito", "vito");
            // /media/landa/Eva-00/Desarrollo_Vitto/VittoStore/VittoDataBase.mv.db
            if (sequence == 1) {
                JOptionPane.showMessageDialog(null, "Conexión DDBB OK!");
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VittoStoreDDBBRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() {
        return this.DDBBConnection;
    }

    public void reconectDDBB() {
        try {
            VittoStoreDDBBRepository repo = new VittoStoreDDBBRepository(1);
        } catch (SQLException ex) {
            Logger.getLogger(VittoStoreDDBBRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<User> getUsers() {
        User user;
        List<User> users = new ArrayList<>();

        if (this.DDBBConnection != null) {
            String getUsers = "SELECT * FROM USERS";

            try {
                PreparedStatement statement = this.DDBBConnection.prepareStatement(getUsers);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    String apellido = resultSet.getString("apellido");
                    String dni = resultSet.getString("dni");

                    user = new User(id, nombre, apellido, dni);

                    users.add(user);

                }

            } catch (SQLException ex) {
                Logger.getLogger(VittoStoreDDBBRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            /*
                En caso de que no se encuentre activa la conexión
             */
            this.reconectDDBB();
            return getUsers();
        }

        return users;

    }

    public User findUserByCompleteName(String name, String surname) {
        User user = null;

        if (this.DDBBConnection != null) {

            String getUser = "SELECT * FROM USERS WHERE nombre = ? AND apellido = ?";

            try {
                PreparedStatement statement = this.DDBBConnection.prepareStatement(getUser);
                statement.setString(1, name);
                statement.setString(2, surname);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    String apellido = resultSet.getString("apellido");
                    String dni = resultSet.getString("dni");

                    user = new User(id, nombre, apellido, dni);
                }

            } catch (SQLException ex) {
                Logger.getLogger(VittoStoreDDBBRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return user;

    }

    public double getPriceFromProductName(String productName) {
        double productPrice = 0;

        if (this.DDBBConnection != null) {
            String getPrice = "SELECT precio FROM product WHERE nombre = ?";
            try {
                PreparedStatement statement = this.DDBBConnection.prepareStatement(getPrice);
                statement.setString(1, productName);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    double precio = resultSet.getDouble("precio");

                    productPrice = precio;
                }

            } catch (SQLException ex) {
                Logger.getLogger(VittoStoreDDBBRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        System.out.println("(getPriceFromProducts) =>  PRODUCT PRICE --> " + productPrice);

        return productPrice;
    }

    public void insertProduct(List<Product> productList) {

        if (this.DDBBConnection != null) {
            String getPrice = "insert into table_consuming ";
            
            for (Product product : productList) {
                
                

                System.out.println("PRODUCT TO INSERT --> brand : " + product.getBrand()
                        + "amount: " + product.getAmountConsumed() + " precio: " + product.getPrice());
            }
        }

        //TODO: inserta BBDD por producto de la lista.
    }

}
