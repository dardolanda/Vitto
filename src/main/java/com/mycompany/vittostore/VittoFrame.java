/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vittostore;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;


/**
 *
 * @author landa
 */
public class VittoFrame extends javax.swing.JFrame {
    
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    List<String> employeeList = new ArrayList<>();

    /**
     * Creates new form VittoFrame
     */
    public VittoFrame() {
        initComponents();
        // Tomar los valores de la bbdd 
        employeeList.add("Cristian Decarli");
        employeeList.add("Florencio Varela");
        
        
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }
    
    
    
    private void chargeEmployee() {        
        DefaultComboBoxModel employeeModel = new DefaultComboBoxModel(employeeList.toArray());
        this.employeeNameCombo.setModel(employeeModel);
        this.selectOrderView();
        
    }
    
    
    
    private void selectOrderView() {
        SelectOrder.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        SelectOrder.setSize(700, 500);        
        SelectOrder.setVisible(true); 
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SelectOrder = new javax.swing.JFrame();
        employeeNameCombo = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        drinkNoAlcohol = new javax.swing.JButton();
        drinkAlcohol = new javax.swing.JButton();
        candyProducts = new javax.swing.JButton();
        saladProducts = new javax.swing.JButton();
        promotions = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        consuming = new javax.swing.JButton();
        payAction = new javax.swing.JButton();
        seeConsuming = new javax.swing.JButton();
        deleteTable = new javax.swing.JButton();
        closeTable = new javax.swing.JButton();
        tableOne = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        tableTwo = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        tableThree = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        employeeNameCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeNameComboActionPerformed(evt);
            }
        });

        jLabel4.setText("Quién sos ?");

        drinkNoAlcohol.setText("Bebidas sin alcohol");
        drinkNoAlcohol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drinkNoAlcoholActionPerformed(evt);
            }
        });

        drinkAlcohol.setText("Bebidas con alcohol");
        drinkAlcohol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drinkAlcoholActionPerformed(evt);
            }
        });

        candyProducts.setText("Productos Dulces");
        candyProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                candyProductsActionPerformed(evt);
            }
        });

        saladProducts.setText("Productos Salados");
        saladProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saladProductsActionPerformed(evt);
            }
        });

        promotions.setText("Promos Sugeridas");
        promotions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                promotionsActionPerformed(evt);
            }
        });

        consuming.setText("Consumiendo");
        consuming.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consumingActionPerformed(evt);
            }
        });

        payAction.setText("Pagar");
        payAction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payActionActionPerformed(evt);
            }
        });

        seeConsuming.setText("Ver Consumo");
        seeConsuming.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seeConsumingActionPerformed(evt);
            }
        });

        deleteTable.setText("Eliminar");
        deleteTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteTableActionPerformed(evt);
            }
        });

        closeTable.setText("Cerrar Mesa");
        closeTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeTableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SelectOrderLayout = new javax.swing.GroupLayout(SelectOrder.getContentPane());
        SelectOrder.getContentPane().setLayout(SelectOrderLayout);
        SelectOrderLayout.setHorizontalGroup(
            SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(SelectOrderLayout.createSequentialGroup()
                .addGroup(SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SelectOrderLayout.createSequentialGroup()
                        .addGroup(SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SelectOrderLayout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addGroup(SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(drinkAlcohol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(drinkNoAlcohol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(44, 44, 44)
                                .addGroup(SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(candyProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(saladProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(41, 41, 41)
                                .addComponent(promotions, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(SelectOrderLayout.createSequentialGroup()
                                .addGap(311, 311, 311)
                                .addComponent(jLabel4)))
                        .addGap(0, 34, Short.MAX_VALUE))
                    .addGroup(SelectOrderLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(consuming)
                        .addGap(18, 18, 18)
                        .addComponent(payAction, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(seeConsuming)
                        .addGap(29, 29, 29)
                        .addComponent(deleteTable, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SelectOrderLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SelectOrderLayout.createSequentialGroup()
                        .addComponent(closeTable, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(264, 264, 264))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SelectOrderLayout.createSequentialGroup()
                        .addComponent(employeeNameCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(231, 231, 231))))
        );
        SelectOrderLayout.setVerticalGroup(
            SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SelectOrderLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employeeNameCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addGroup(SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(promotions, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                    .addComponent(candyProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(drinkNoAlcohol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(drinkAlcohol, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                    .addComponent(saladProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(58, 58, 58)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(consuming)
                    .addComponent(payAction)
                    .addComponent(seeConsuming)
                    .addComponent(deleteTable))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(closeTable, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tableOne.setBackground(new java.awt.Color(51, 204, 0));
        tableOne.setText("1");
        tableOne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableOneActionPerformed(evt);
            }
        });

        jLabel1.setText("Mesa");

        tableTwo.setBackground(new java.awt.Color(51, 204, 0));
        tableTwo.setText("2");
        tableTwo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableTwoActionPerformed(evt);
            }
        });

        jLabel2.setText("Mesa");

        tableThree.setBackground(new java.awt.Color(51, 204, 0));
        tableThree.setText("3");
        tableThree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableThreeActionPerformed(evt);
            }
        });

        jLabel3.setText("Mesa");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(307, 307, 307)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tableOne, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel1)))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tableTwo, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel2)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(tableThree, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel3)))
                .addContainerGap(614, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tableTwo, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tableThree, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tableOne, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(758, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tableOneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableOneActionPerformed
        // TODO add your handling code here:
        
        System.out.println("click...");
        System.out.println(evt);
        this.chargeEmployee();        
    
    }//GEN-LAST:event_tableOneActionPerformed

    private void employeeNameComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeNameComboActionPerformed
        // TODO add your handling code here:
        this.chargeEmployee();    
    }//GEN-LAST:event_employeeNameComboActionPerformed

    private void tableTwoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableTwoActionPerformed
        // TODO add your handling code here:
        this.chargeEmployee();    
    }//GEN-LAST:event_tableTwoActionPerformed

    private void tableThreeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableThreeActionPerformed
        // TODO add your handling code here:
        this.chargeEmployee();    
    }//GEN-LAST:event_tableThreeActionPerformed

    private void drinkNoAlcoholActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drinkNoAlcoholActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_drinkNoAlcoholActionPerformed

    private void candyProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_candyProductsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_candyProductsActionPerformed

    private void promotionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_promotionsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_promotionsActionPerformed

    private void saladProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saladProductsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_saladProductsActionPerformed

    private void drinkAlcoholActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drinkAlcoholActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_drinkAlcoholActionPerformed

    private void consumingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consumingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_consumingActionPerformed

    private void seeConsumingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seeConsumingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seeConsumingActionPerformed

    private void closeTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeTableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_closeTableActionPerformed

    private void deleteTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteTableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteTableActionPerformed

    private void payActionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payActionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_payActionActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        
        System.out.println("Start Vitto Project");
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VittoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VittoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VittoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VittoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                System.out.println("Start run vittoFrame");
                new VittoFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame SelectOrder;
    private javax.swing.JButton candyProducts;
    private javax.swing.JButton closeTable;
    private javax.swing.JButton consuming;
    private javax.swing.JButton deleteTable;
    private javax.swing.JButton drinkAlcohol;
    private javax.swing.JButton drinkNoAlcohol;
    private javax.swing.JComboBox<String> employeeNameCombo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton payAction;
    private javax.swing.JButton promotions;
    private javax.swing.JButton saladProducts;
    private javax.swing.JButton seeConsuming;
    private javax.swing.JButton tableOne;
    private javax.swing.JButton tableThree;
    private javax.swing.JButton tableTwo;
    // End of variables declaration//GEN-END:variables
}
