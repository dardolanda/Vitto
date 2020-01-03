/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vittostore.controller;

import com.mycompany.vittostore.user.User;
import java.util.List;

public interface Users {
    
    public List<User> getUsers();
    
    public User findUserByCompleteName(String name, String surname);
    
    public boolean isAnyUserLoggedIn();
    
}
