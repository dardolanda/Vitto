/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vittostore.controllerImpl;

import com.mycompany.vittostore.VittoFrame;
import com.mycompany.vittostore.database.VittoStoreDDBBRepository;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VittoConnection {

    VittoStoreDDBBRepository vittoDDBBStore;

    public VittoConnection(int sequence) {
        try {

            this.vittoDDBBStore = new VittoStoreDDBBRepository(sequence);

        } catch (SQLException ex) {
            Logger.getLogger(VittoFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
