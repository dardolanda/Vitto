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

import java.util.*;

public class VittoStoreConnection {
    private Connection DDBBConnection = null;
    
    public VittoStoreConnection() throws SQLException {
        try {
            Class.forName("org.h2.Driver");
            DDBBConnection = DriverManager.getConnection("jdbc:h2:./VittoStore","vito","vito");
            // /media/landa/Eva-00/Desarrollo_Vitto/VittoStore/VittoDataBase.mv.db
            JOptionPane.showMessageDialog(null, "Conexión DDBB OK!");
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VittoStoreConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        User user = null;
        
        if (this.DDBBConnection != null ) {
            String getUsers = "SELECT * FROM users ";
            
            try {
                PreparedStatement statement = this.DDBBConnection.prepareStatement(getUsers);
                ResultSet resultSet = statement.executeQuery();
                
                while(resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    String apellido = resultSet.getString("apellido");
                    String dni = resultSet.getString("dni");
                    
                    users.add(new User(id, nombre, apellido, dni));
                    
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(VittoStoreConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
                    
        }
        
        return users;
        
    }
    
}
