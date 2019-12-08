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
import java.sql.Date;
import java.sql.Timestamp;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import com.mycompany.vittostore.user.User;
import com.mycompany.vittostore.generalitems.Product;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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

    public Map<String, Double> getProductFromProductName(String productName) {
        Map<String, Double> productIdPrice = new HashMap<>();
        double productPrice = 0;

        if (this.DDBBConnection != null) {
            String getPrice = "SELECT id, precio FROM product WHERE nombre = ?";
            try {
                PreparedStatement statement = this.DDBBConnection.prepareStatement(getPrice);
                statement.setString(1, productName);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    double precio = resultSet.getDouble("precio");
                    double doubleId = id;

                    productIdPrice.put("ID", doubleId);
                    productIdPrice.put("PRECIO", precio);
                    
                }

            } catch (SQLException ex) {
                Logger.getLogger(VittoStoreDDBBRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        System.out.println("(getPriceFromProducts) =>  PRODUCT PRICE --> " + productPrice);

        return productIdPrice;
    }

    public void insertProduct(List<Product> productList , int mesa , String nombreMozo ) {

        if (this.DDBBConnection != null) {
            System.out.println("ddbb inserting OPERATING_TABLE");
            StringBuffer insertOperatingTableQuery = new StringBuffer();
            insertOperatingTableQuery.append("INSERT INTO operating_table ");
            insertOperatingTableQuery.append(" (mesa, nombre_mozo, producto_id, producto_nombre, producto_cantidad, producto_precio_unitario, actividad, horario_apertura)");
            insertOperatingTableQuery.append(" VALUES (?,?,?,?,?,?,?,?)");
                        
            Calendar cal = Calendar.getInstance();                        
            
            Timestamp timeStampNow = new Timestamp(cal.getTimeInMillis());                        

            for (Product product : productList) {
                System.out.println("product to insert: (BRAND) --> " + product.getBrand());
                try {
                    PreparedStatement preparedStatement = this.DDBBConnection.prepareStatement(insertOperatingTableQuery.toString());
                    
                    preparedStatement.setInt(1, mesa);
                    preparedStatement.setString(2, nombreMozo);
                    preparedStatement.setInt(3, product.getId());
                    preparedStatement.setString(4, product.getBrand());
                    preparedStatement.setInt(5,product.getAmountConsumed());
                    preparedStatement.setDouble(6, product.getPrice());
                    preparedStatement.setBoolean(7, true);
                    preparedStatement.setTimestamp(8, timeStampNow);
                    
                    preparedStatement.execute();
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
