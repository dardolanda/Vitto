/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vittostore.generalitems;

import javax.swing.JCheckBox;
import javax.swing.JSpinner;

public class GenericSelectedComponent {
    private JCheckBox checkBox;
    private JSpinner spinner;

    public GenericSelectedComponent(JCheckBox checkBox, JSpinner spinner) {
        this.checkBox = checkBox;
        this.spinner = spinner;
    }        

    public JCheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(JCheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public JSpinner getSpinner() {
        return spinner;
    }

    public void setSpinner(JSpinner spinner) {
        this.spinner = spinner;
    }
    
    
}
