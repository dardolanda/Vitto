/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vittostore.database;

import com.mycompany.vittostore.dataStore.CloseDateDataStore;
import com.mycompany.vittostore.dataStore.ClosePaymentDataStore;
import com.mycompany.vittostore.dataStore.DataStore;
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
import com.mycompany.vittostore.generalitems.OperatingTableStateEnum;
import com.mycompany.vittostore.generalitems.SweetProductsEnum;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Statement;

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
            // ./VittoStore -> linux
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
            // this.reconectDDBB();
            // return getUsers();
            JOptionPane.showMessageDialog(null, "No se ha encontrado la conexión");
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

                    System.out.println("(getPriceFromProducts): Product ID: " + id + " PRODUCT PRICE --> " + precio);

                }

            } catch (SQLException ex) {
                Logger.getLogger(VittoStoreDDBBRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return productIdPrice;
    }

    public void insertProduct(List<Product> productList, int mesa, String nombreMozo, String productType) {

        if (this.DDBBConnection != null) {
            System.out.println("ddbb inserting OPERATING_TABLE");
            /**
             * Insert statement
             */
            StringBuffer insertOperatingTableQuery = new StringBuffer();
            insertOperatingTableQuery.append("INSERT INTO operating_table ");
            insertOperatingTableQuery.append(" (mesa, nombre_mozo, producto_id, producto_nombre, producto_cantidad, producto_precio_unitario, actividad, horario_apertura, estado, tipo_prouducto)");
            insertOperatingTableQuery.append(" VALUES (?,?,?,?,?,?,?,?,?,?)");

            /**
             * Update Statement
             */
            StringBuffer updateOperatingTableQuery = new StringBuffer();
            updateOperatingTableQuery.append(" UPDATE operating_table ");
            updateOperatingTableQuery.append(" SET producto_cantidad = ? ");
            updateOperatingTableQuery.append(" where mesa = ? ");
            updateOperatingTableQuery.append(" and tipo_prouducto = ? ");
            updateOperatingTableQuery.append(" and producto_nombre = ? ");
            updateOperatingTableQuery.append(" and actividad = ? ");
            updateOperatingTableQuery.append(" and estado = ? ");

            Calendar cal = Calendar.getInstance();
            Timestamp timeStampNow = new Timestamp(cal.getTimeInMillis());

            for (Product product : productList) {

                if (this.isProductSaved(mesa, productType, product.getBrand())) {
                    // update
                    System.out.println("product to UPDDATE : (BRAND) --> " + product.getBrand());
                    try {
                        PreparedStatement updatePreparedStatement = this.DDBBConnection.prepareStatement(updateOperatingTableQuery.toString());
                        updatePreparedStatement.setInt(1, product.getAmountConsumed());
                        updatePreparedStatement.setInt(2, mesa);
                        updatePreparedStatement.setString(3, productType);
                        updatePreparedStatement.setString(4, product.getBrand());
                        updatePreparedStatement.setBoolean(5, true);
                        updatePreparedStatement.setString(6, OperatingTableStateEnum.USO.toString());

                        updatePreparedStatement.execute();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // insert
                    System.out.println("product to insert: (BRAND) --> " + product.getBrand());

                    try (
                            PreparedStatement preparedStatement = this.DDBBConnection.prepareStatement(insertOperatingTableQuery.toString(), Statement.RETURN_GENERATED_KEYS);) {

                        preparedStatement.setInt(1, mesa);
                        preparedStatement.setString(2, nombreMozo);
                        preparedStatement.setInt(3, product.getId());
                        preparedStatement.setString(4, product.getBrand());
                        preparedStatement.setInt(5, product.getAmountConsumed());
                        preparedStatement.setDouble(6, product.getPrice());
                        preparedStatement.setBoolean(7, true);
                        preparedStatement.setTimestamp(8, timeStampNow);
                        preparedStatement.setString(9, OperatingTableStateEnum.USO.toString());
                        preparedStatement.setString(10, productType);

                        preparedStatement.execute();

                        ResultSet rs = preparedStatement.getGeneratedKeys();
                        while (rs.next()) {

                            /*
                            se obtienen los id's insertados en la BBDD.
                             */
                            System.out.println("rs -> getInt: id     = " + rs.getInt("id"));
                            System.out.println("rs -> getObject: id  = " + rs.getObject("id"));

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    private boolean isProductSaved(int mesa, String tipoProducto, String nombreProducto) {
        boolean isProductSavedBBDD = false;

        if (this.DDBBConnection != null) {

            String getProductSaved = "SELECT count(*) AS product_saved FROM operating_table "
                    + " WHERE mesa = ? "
                    + " AND tipo_prouducto = ? "
                    + " AND producto_nombre = ? "
                    + " AND actividad = ? "
                    + " AND estado = ? ";

            try {
                PreparedStatement statement = this.DDBBConnection.prepareStatement(getProductSaved);
                statement.setInt(1, mesa);
                statement.setString(2, tipoProducto);
                statement.setString(3, nombreProducto);
                statement.setBoolean(4, true);
                statement.setString(5, OperatingTableStateEnum.USO.toString());
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    isProductSavedBBDD = resultSet.getBoolean("product_saved");
                }

            } catch (SQLException ex) {
                Logger.getLogger(VittoStoreDDBBRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return isProductSavedBBDD;
    }

    public void insertProductPrices(DataStore dataStore) {
        if (this.DDBBConnection != null) {
            System.out.println("ddbb inserting (Products with price) OPERATING_TABLE");
            StringBuffer insertOperatingTableQuery = new StringBuffer();
            insertOperatingTableQuery.append("INSERT INTO operating_table ");
            insertOperatingTableQuery.append(" (mesa, nombre_mozo, producto_nombre, producto_cantidad, producto_precio_unitario, actividad, horario_apertura, estado, tipo_prouducto)");
            insertOperatingTableQuery.append(" VALUES (?,?,?,?,?,?,?,?,?)");

            /**
             * Update Statement
             */
            StringBuffer updateOperatingTableQuery = new StringBuffer();
            updateOperatingTableQuery.append(" UPDATE operating_table ");
            updateOperatingTableQuery.append(" SET producto_cantidad = ? ");
            updateOperatingTableQuery.append(" where mesa = ? ");
            updateOperatingTableQuery.append(" and tipo_prouducto = ? ");
            updateOperatingTableQuery.append(" and producto_nombre = ? ");
            updateOperatingTableQuery.append(" and actividad = ? ");
            updateOperatingTableQuery.append(" and estado = ? ");

            Calendar cal = Calendar.getInstance();
            Timestamp timeStampNow = new Timestamp(cal.getTimeInMillis());

            for (Map.Entry<String, Map<Integer, Double>> entry : dataStore.getProducts().entrySet()) {
                Object tipoDeProducto = entry.getKey();
                Map<Integer, Double> value = entry.getValue(); // notar la diferencia que se puede hacer con el value -> entrySet
                for (Map.Entry<Integer, Double> cantidadPrecio : entry.getValue().entrySet()) {
                    System.out.println("KEY --> " + tipoDeProducto.toString());
                    System.out.println("Sub entry -> number: " + cantidadPrecio.getKey());
                    System.out.println("Sub entry -> double: " + cantidadPrecio.getValue());

                    if (this.isProductSaved(dataStore.getMesa(), dataStore.getProductTypeEnum().toString(), tipoDeProducto.toString())) {
                        /**
                         * Actualiza el producto , ya que pertenece a la mesa
                         * operativa.
                         */
                        try {
                            PreparedStatement updatePreparedStatement = this.DDBBConnection.prepareStatement(updateOperatingTableQuery.toString());
                            updatePreparedStatement.setInt(1, cantidadPrecio.getKey());
                            updatePreparedStatement.setInt(2, dataStore.getMesa());
                            updatePreparedStatement.setString(3, dataStore.getProductTypeEnum().toString());
                            updatePreparedStatement.setString(4, tipoDeProducto.toString());
                            updatePreparedStatement.setBoolean(5, true);
                            updatePreparedStatement.setString(6, OperatingTableStateEnum.USO.toString());

                            updatePreparedStatement.execute();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {

                        /**
                         * Inserta en la bbdd ya que el producto no existe
                         */
                        try {

                            PreparedStatement preparedStatement = this.DDBBConnection.prepareStatement(insertOperatingTableQuery.toString());

                            preparedStatement.setInt(1, dataStore.getMesa());
                            preparedStatement.setString(2, dataStore.getNombreMozo());
                            preparedStatement.setString(3, tipoDeProducto.toString());
                            preparedStatement.setInt(4, cantidadPrecio.getKey());
                            preparedStatement.setDouble(5, cantidadPrecio.getValue());
                            preparedStatement.setBoolean(6, true);
                            preparedStatement.setTimestamp(7, timeStampNow);
                            preparedStatement.setString(8, OperatingTableStateEnum.USO.toString());
                            preparedStatement.setString(9, dataStore.getProductTypeEnum().toString());

                            preparedStatement.execute();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
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
            udateOperatingTableQuery.append(" SET horario_cierre = ? , ");
            udateOperatingTableQuery.append(" estado = ? ");
            udateOperatingTableQuery.append(" WHERE mesa = ? AND actividad = true AND nombre_mozo = ? ");

            Calendar cal = Calendar.getInstance();
            Timestamp timeStampNow = new Timestamp(cal.getTimeInMillis());

            try {
                PreparedStatement preparedStatement = this.DDBBConnection.prepareStatement(udateOperatingTableQuery.toString());

                preparedStatement.setTimestamp(1, timeStampNow);
                preparedStatement.setString(2, OperatingTableStateEnum.CERRADA.toString());
                preparedStatement.setInt(3, tableId);
                preparedStatement.setString(4, tableUser);

                preparedStatement.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Map<Integer, String>> getOperatingTable() {
        Map<Integer, String> operatingStateTableMap;
        List<Map<Integer, String>> operatingStateTableList = new ArrayList<>();
        System.out.println("Get Operating Table LIST");

        if (this.DDBBConnection != null) {

            String getOperatingTableQuery = " SELECT DISTINCT mesa, estado FROM operating_table WHERE actividad = true ";

            try {
                PreparedStatement statement = this.DDBBConnection.prepareStatement(getOperatingTableQuery);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    operatingStateTableMap = new HashMap<>();
                    operatingStateTableMap.put(resultSet.getInt("mesa"), resultSet.getString("estado"));
                    operatingStateTableList.add(operatingStateTableMap);
                }

            } catch (SQLException ex) {
                Logger.getLogger(VittoStoreDDBBRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return operatingStateTableList;
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

    public List<Product> findtableSelectedProducts(int tableId, String productType) {
        Product product;
        List<Product> productList = new ArrayList<>();

        if (this.DDBBConnection != null) {
            String getConsumingProducts = "SELECT producto_nombre, producto_cantidad , producto_precio_unitario "
                    + " FROM operating_table "
                    + " WHERE mesa = ? "
                    + " AND actividad = true "
                    + " AND tipo_prouducto = ? ";
            try {
                PreparedStatement statement = this.DDBBConnection.prepareStatement(getConsumingProducts);
                statement.setInt(1, tableId);
                statement.setString(2, productType);
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

    public void payOperatingTable(int tableId) throws Exception {
        if (this.DDBBConnection != null) {
            System.out.println("(Pay Table): DDBB Updating OPERATING_TABLE");

            StringBuffer udateOperatingTableQuery = new StringBuffer();
            udateOperatingTableQuery.append(" UPDATE operating_table ");
            udateOperatingTableQuery.append(" SET estado = ? ,");
            udateOperatingTableQuery.append(" actividad  = ? ");
            udateOperatingTableQuery.append(" WHERE mesa = ? AND actividad = true AND estado = ? ");

            try {
                PreparedStatement preparedStatement = this.DDBBConnection.prepareStatement(udateOperatingTableQuery.toString());

                preparedStatement.setString(1, OperatingTableStateEnum.PAGADA.toString());
                preparedStatement.setBoolean(2, false);
                preparedStatement.setInt(3, tableId);
                preparedStatement.setString(4, OperatingTableStateEnum.CERRADA.toString());

                preparedStatement.execute();

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    public int insertPayment(int tableId, double total, double discount, String nombreMozo, String paymentMethod) throws Exception {
        int paymentId = 0;

        if (this.DDBBConnection != null) {
            System.out.println("ddbb inserting Payments");
            StringBuffer insertOperatingTableQuery = new StringBuffer();
            insertOperatingTableQuery.append("INSERT INTO payments ");
            insertOperatingTableQuery.append(" (mesa, total, descuento_aplicado, nombre_mozo, tipo_pago, horario_pago)");
            insertOperatingTableQuery.append(" VALUES (?,?,?,?,?,?)");

            Calendar cal = Calendar.getInstance();
            Timestamp timeStampNow = new Timestamp(cal.getTimeInMillis());

            try (
                    PreparedStatement preparedStatement = this.DDBBConnection.prepareStatement(insertOperatingTableQuery.toString(), Statement.RETURN_GENERATED_KEYS);) {

                preparedStatement.setInt(1, tableId);
                preparedStatement.setDouble(2, total);
                preparedStatement.setDouble(3, discount);
                preparedStatement.setString(4, nombreMozo);
                preparedStatement.setString(5, paymentMethod);
                preparedStatement.setTimestamp(6, timeStampNow);

                preparedStatement.execute();

                ResultSet rs = preparedStatement.getGeneratedKeys();
                while (rs.next()) {
                    paymentId = rs.getInt("id");
                    System.out.println("(Payment ID BBDD -> ) rs -> getInt: id = " + rs.getInt("id"));
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }

        return paymentId;
    }

    public int insertPaymentPersonalData(int paymentId, String nombreCliente, String apellidoCliente, String DNICliente, String telefonoCliente) throws Exception {
        int paymentPersonalDataId = 0;

        if (this.DDBBConnection != null) {
            System.out.println("ddbb inserting Payments Customer Data");
            StringBuffer insertOperatingTableQuery = new StringBuffer();
            insertOperatingTableQuery.append("INSERT INTO payments_customer_data ");
            insertOperatingTableQuery.append(" (payment_id, nombre, apellido, dni, tel)");
            insertOperatingTableQuery.append(" VALUES (?,?,?,?,?)");

            try (
                    PreparedStatement preparedStatement = this.DDBBConnection.prepareStatement(insertOperatingTableQuery.toString(), Statement.RETURN_GENERATED_KEYS);) {

                preparedStatement.setInt(1, paymentId);
                preparedStatement.setString(2, nombreCliente);
                preparedStatement.setString(3, apellidoCliente);
                preparedStatement.setString(4, DNICliente);
                preparedStatement.setString(5, telefonoCliente);

                preparedStatement.execute();

                ResultSet rs = preparedStatement.getGeneratedKeys();
                while (rs.next()) {
                    paymentPersonalDataId = rs.getInt("id");
                    System.out.println("(Payment ID BBDD -> ) rs -> getInt: id = " + rs.getInt("id"));
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }

        return paymentPersonalDataId;
    }

    public void deletePaymentsRollBack(int paymentId, int paymentPersonalDataId) {
        if (this.DDBBConnection != null) {
            this.deletePaymentRollBack(paymentId);
            this.deletePaymentPersonalDataRollBack(paymentPersonalDataId);
        }
    }

    private void deletePaymentRollBack(int paymentId) {
        if (this.DDBBConnection != null) {
            System.out.println("(Delete payments roll back)");

            StringBuffer udateOperatingTableQuery = new StringBuffer();
            udateOperatingTableQuery.append(" DELETE FROM payments");
            udateOperatingTableQuery.append(" WHERE id = ? ");

            try {
                PreparedStatement preparedStatement = this.DDBBConnection.prepareStatement(udateOperatingTableQuery.toString());

                preparedStatement.setInt(1, paymentId);

                preparedStatement.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void deletePaymentPersonalDataRollBack(int paymentPersonalDataId) {
        if (this.DDBBConnection != null) {
            System.out.println("(Delete payments personal data roll back)");

            StringBuffer udateOperatingTableQuery = new StringBuffer();
            udateOperatingTableQuery.append(" DELETE FROM payments_customer_data");
            udateOperatingTableQuery.append(" WHERE id = ? ");

            try {
                PreparedStatement preparedStatement = this.DDBBConnection.prepareStatement(udateOperatingTableQuery.toString());

                preparedStatement.setInt(1, paymentPersonalDataId);

                preparedStatement.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getTableState(int tableId) {
        String tableState = "";

        if (this.DDBBConnection != null) {

            String getUser = "SELECT DISTINCT(actividad) , estado "
                    + " FROM operating_table WHERE mesa = ? "
                    + " AND actividad = true";

            try {
                PreparedStatement statement = this.DDBBConnection.prepareStatement(getUser);
                statement.setInt(1, tableId);
                ResultSet resultSet = statement.executeQuery();

                /**
                 * Manejamos el resultSet.next con un if, ya que solo existe una
                 * única mesa con estado true y con el id que la identifica.
                 */
                if (resultSet.next()) {
                    tableState = resultSet.getString("estado");
                }

            } catch (SQLException ex) {
                Logger.getLogger(VittoStoreDDBBRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return tableState;
    }

    public void deleteTable(int tableId) {
        System.out.println("(Pay Table): DDBB Updating OPERATING_TABLE");

        StringBuffer udateOperatingTableQuery = new StringBuffer();
        udateOperatingTableQuery.append(" UPDATE operating_table ");
        udateOperatingTableQuery.append(" SET estado = ? ,");
        udateOperatingTableQuery.append(" actividad  = ? ");
        udateOperatingTableQuery.append(" WHERE mesa = ? AND actividad = ? ");
        udateOperatingTableQuery.append(" AND ( estado = ?  OR estado = ? )");
        

        try {
            PreparedStatement preparedStatement = this.DDBBConnection.prepareStatement(udateOperatingTableQuery.toString());

            preparedStatement.setString(1, OperatingTableStateEnum.ELIMINADA.toString());
            preparedStatement.setBoolean(2, false);
            preparedStatement.setInt(3, tableId);
            preparedStatement.setBoolean(4, true);
            preparedStatement.setString(5, OperatingTableStateEnum.CERRADA.toString());
            preparedStatement.setString(6, OperatingTableStateEnum.USO.toString());

            preparedStatement.execute();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Se ha producido un error al eliminar la mesa Nº: " + tableId + " Reinicie el sistema.");
        }

        JOptionPane.showMessageDialog(null, "La mesa Nº: " + tableId + " Fue eliminada con éxito");

    }
    
    
    public ClosePaymentDataStore getClosingData(CloseDateDataStore closeDateDataStore) {
        
        ClosePaymentDataStore closePaymentDataStore = new ClosePaymentDataStore();
        
        if (this.DDBBConnection != null) {
            
            String getPaymentData = " SELECT (tipo_pago), SUM(total) AS total, SUM(descuento_aplicado) AS descuento "
                    + " FROM payments "
                    + " WHERE horario_pago BETWEEN ? AND ? "
                    + " GROUP BY tipo_pago";

            try {
                PreparedStatement statement = this.DDBBConnection.prepareStatement(getPaymentData);
                statement.setString(1, closeDateDataStore.getFechaDesde());
                statement.setString(2, closeDateDataStore.getFechaHasta());
                
                ResultSet resultSet = statement.executeQuery();


                while (resultSet.next()) {
                    
                    if (resultSet.getString("tipo_pago").equals("CREDITO")) {
                        Double total = resultSet.getDouble("total") - (resultSet.getDouble("descuento") * resultSet.getDouble("total") / 100);
                        closePaymentDataStore.setCredito(total);
                    }
                    
                    if (resultSet.getString("tipo_pago").equals("DEBITO")) {
                        
                        Double total = resultSet.getDouble("total") - (resultSet.getDouble("descuento") * resultSet.getDouble("total") / 100);
                        closePaymentDataStore.setDebito(total);                    
                    }                    

                    if (resultSet.getString("tipo_pago").equals("CUENTA_CORRIENTE")) {
                        Double total = resultSet.getDouble("total") - (resultSet.getDouble("descuento") * resultSet.getDouble("total") / 100);
                        closePaymentDataStore.setCuentaCorriente(total );                    
                    }       
                    
                    if (resultSet.getString("tipo_pago").equals("EFECTIVO")) {
                        Double total = resultSet.getDouble("total") - (resultSet.getDouble("descuento") * resultSet.getDouble("total") / 100);
                        closePaymentDataStore.setEfectivo(total);                    
                    }
                    
                    if (resultSet.getString("tipo_pago").equals("MERCADO_PAGO")) {
                        Double total = resultSet.getDouble("total") - (resultSet.getDouble("descuento") * resultSet.getDouble("total") / 100);
                        closePaymentDataStore.setMercadoPago(total);                    
                    }                    
                    
                }
                

            } catch (SQLException ex) {
                System.out.println("BBDD - Calculo Cierre");
            }
        }

        return closePaymentDataStore;
    }

}
