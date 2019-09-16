/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vittostore.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VittoStoreConnection {
    
    public VittoStoreConnection() throws SQLException {
        try {
            Class.forName("org.h2.driver");
            Connection connection = DriverManager.getConnection("jdbc:h2:");
            
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VittoStoreConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
