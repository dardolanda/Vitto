/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vittostore.controllerImpl;

import com.mycompany.vittostore.controller.Users;
import com.mycompany.vittostore.user.User;

import java.util.List;


public class UsersImpl extends VittoConnection implements Users {

    public UsersImpl(int sequence) {
        super(sequence);
    }

    @Override
    public List<User> getUsers() {        
        return this.vittoDDBBStore.getUsers();
    }

    @Override
    public User findUserByCompleteName(String name, String surname) {
        return this.vittoDDBBStore.findUserByCompleteName(name, surname);
    }

    @Override
    public boolean isAnyUserLoggedIn() {
        
        return false;
    }

}
