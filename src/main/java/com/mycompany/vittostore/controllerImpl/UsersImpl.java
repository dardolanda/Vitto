/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vittostore.controllerImpl;

import com.mycompany.vittostore.VittoFrame;
import com.mycompany.vittostore.controller.Users;
import com.mycompany.vittostore.user.User;
import com.mycompany.vittostore.database.VittoStoreDDBBRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsersImpl implements Users {
    VittoStoreDDBBRepository vittoDDBBStore;
    

    public UsersImpl() {
        try {
            
            this.vittoDDBBStore = new VittoStoreDDBBRepository();
            
        } catch (SQLException ex) {
            Logger.getLogger(VittoFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public List<User> getUsers() {        
        return this.vittoDDBBStore.getUsers();
    }

    @Override
    public User findUserByCompleteName(String name, String surname) {
        return this.vittoDDBBStore.findUserByCompleteName(name, surname);
    }

}
