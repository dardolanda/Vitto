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
import java.sql.Timestamp;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import com.mycompany.vittostore.user.User;
import com.mycompany.vittostore.generalitems.Product;
import com.mycompany.vittostore.dialogs.GenericDialog;
import java.awt.Dimension;
import java.awt.Toolkit;

import java.util.*;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class VittoStoreDDBBRepository {

    private Connection DDBBConnection = null;
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

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

    /**
     * Tener en cuenta de hacer la búsqueda agregando también el DNI del usuario
     * operativo.
     */
    public User findUserByCompleteName(String name, String surname) {
        User user = null;

        if (this.DDBBConnection != null) {

            String getUser = "SELECT * FROM USERS WHERE nombre = ? AND apellido = ?";

            try {
                PreparedStatement statement = this.DDBBConnection.prepareStatement(getUser);
                statement.setString(1, name);
                statement.setString(2, surname);
                ResultSet resultSet = statement.executeQuery();

                /**
                 * Manejamos el resultSet.next con un if, ya que solo existe un
                 * único usuario con nombre - apellido - dni
                 */
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

                /**
                 * En este caso, manejamos el resultSet.next con un if ya que
                 * siempre existirá un único producto identificado por el
                 * nombre.
                 */
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

    public void insertProduct(List<Product> productList, int mesa, String nombreMozo) {

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
                    preparedStatement.setInt(5, product.getAmountConsumed());
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

    /**
     * Tener en cuenta que el tableUser espera el nombre del mozo con el
     * siguiente formato: Nombre_Apellido -> (letras iniciales en mayúsculas)
     */
    public List<Product> getConsumingProduct(int tableId, String tableUser) {
        List<Product> consumingProductList = new ArrayList<>();
        Product product;

        if (this.DDBBConnection != null) {
            String getConsumingProducts = "SELECT * FROM operating_table "
                    + " WHERE mesa = ? "
                    + " AND actividad = true "
                    + " AND nombre_mozo = ? ";
            try {
                PreparedStatement statement = this.DDBBConnection.prepareStatement(getConsumingProducts);
                statement.setInt(1, tableId);
                statement.setString(2, tableUser);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    product = new Product(resultSet.getInt("PRODUCTO_ID"),
                            resultSet.getInt("PRODUCTO_CANTIDAD"),
                            resultSet.getString("PRODUCTO_NOMBRE"),
                            resultSet.getDouble("PRODUCTO_PRECIO_UNITARIO"));

                    consumingProductList.add(product);

                }

            } catch (SQLException ex) {
                Logger.getLogger(VittoStoreDDBBRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return consumingProductList;
    }

    /**
     * Tener en cuenta que el nombre mozo espera el siguiente formato:
     * Nombre_Apellido -> con las letras iniciales en mayúsculas.
     */
    public void closeTable(int tableId, String tableUser) {
        if (this.DDBBConnection != null) {
            System.out.println("(closeTable): DDBB Updating OPERATING_TABLE");
            StringBuffer udateOperatingTableQuery = new StringBuffer();
            udateOperatingTableQuery.append(" UPDATE operating_table ");
            udateOperatingTableQuery.append(" SET actividad = false , ");
            udateOperatingTableQuery.append(" horario_cierre = ? ");
            udateOperatingTableQuery.append(" WHERE mesa = ? AND actividad = true AND nombre_mozo = ? ");

            Calendar cal = Calendar.getInstance();
            Timestamp timeStampNow = new Timestamp(cal.getTimeInMillis());

            try {
                PreparedStatement preparedStatement = this.DDBBConnection.prepareStatement(udateOperatingTableQuery.toString());

                preparedStatement.setTimestamp(1, timeStampNow);
                preparedStatement.setInt(2, tableId);
                preparedStatement.setString(3, tableUser);

                preparedStatement.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Integer> getOperatingTable() {
        List<Integer> operatingTableList = new ArrayList<>();
        System.out.println("Get Operating Table LIST");

        if (this.DDBBConnection != null) {

            String getOperatingTableQuery = " SELECT DISTINCT mesa FROM operating_table WHERE actividad = true ";

            try {
                PreparedStatement statement = this.DDBBConnection.prepareStatement(getOperatingTableQuery);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt("mesa");
                    operatingTableList.add(id);
                }

            } catch (SQLException ex) {
                Logger.getLogger(VittoStoreDDBBRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return operatingTableList;
    }

    public String findTableUserByTableId(int tableId) {
        String tableUser = null;

        if (this.DDBBConnection != null) {

            String getUser = "SELECT DISTINCT(nombre_mozo) "
                    + " FROM operating_table WHERE mesa = ? "
                    + " AND actividad = true";

            try {
                PreparedStatement statement = this.DDBBConnection.prepareStatement(getUser);
                statement.setInt(1, tableId);
                ResultSet resultSet = statement.executeQuery();

                /**
                 * Manejamos el resultSet.next con un if, ya que solo existe un
                 * único usuario que hace referencia a una mesa activa.
                 */
                if (resultSet.next()) {
                    tableUser = resultSet.getString("nombre_mozo");
                }

            } catch (SQLException ex) {
                Logger.getLogger(VittoStoreDDBBRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tableUser;
    }

    public List<Product> findtableSelectedProducts(int tableId) {
        Product product;
        List<Product> productList = new ArrayList<>();

        if (this.DDBBConnection != null) {
            String getConsumingProducts = "SELECT producto_nombre, producto_cantidad , producto_precio_unitario "
                    + " FROM operating_table "
                    + " WHERE mesa = ? "
                    + " AND actividad = true ";
            try {
                PreparedStatement statement = this.DDBBConnection.prepareStatement(getConsumingProducts);
                statement.setInt(1, tableId);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    product = new Product();
                    
                    product.setBrand(resultSet.getString("PRODUCTO_NOMBRE"));
                    product.setAmountConsumed(resultSet.getInt("PRODUCTO_CANTIDAD"));
                    product.setPrice(resultSet.getDouble("PRODUCTO_PRECIO_UNITARIO"));                    

                    productList.add(product);

                }

            } catch (SQLException ex) {
                Logger.getLogger(VittoStoreDDBBRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        return productList;
    }

}
