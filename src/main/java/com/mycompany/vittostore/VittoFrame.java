/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vittostore;

import com.mycompany.vittostore.controller.Products;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.awt.event.WindowEvent;

import com.mycompany.vittostore.generalitems.Product;
import com.mycompany.vittostore.generalitems.Table;
import com.mycompany.vittostore.generalitems.NoAlcoholDrinksEnum;
import com.mycompany.vittostore.controller.Users;
import com.mycompany.vittostore.user.User;
import com.mycompany.vittostore.dataStore.DataStore;

import com.mycompany.vittostore.controllerImpl.ProductsImpl;
import com.mycompany.vittostore.controllerImpl.UsersImpl;
import com.mycompany.vittostore.generalitems.GenericSelectedComponent;
import java.awt.Component;
import javafx.scene.control.CheckBox;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

public class VittoFrame extends javax.swing.JFrame {

    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    List<User> employeeList = new ArrayList<>();
    List<Product> productList = new ArrayList<>();
    Table selectedTable = new Table();
    Users usersImpl = new UsersImpl(1);
    Products productsImpl = new ProductsImpl(2);
    Map<String, GenericSelectedComponent> selectedComponentMap = new HashMap<>();
    
    Map<NoAlcoholDrinksEnum, Integer> noAlcoholDrinks;
    Map<NoAlcoholDrinksEnum, Integer> AlcoholDrinks;
    User tableUser;
    DataStore dataStore;
    // resto de las posibilidades por mesa

    /**
     * Creates new form VittoFrame
     *
     * sout -> System.out.println(); -> shortCut
     */
    public VittoFrame() {
        this.initComponents();
        this.initSelectedComponent();
        
        List<Integer> operatingTableList = this.productsImpl.getOperatingTable();
        
        operatingTableList.forEach(table -> 
                this.setTableColour(table, Color.yellow)
        );
        
        this.employeeList = this.usersImpl.getUsers();

        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
    }
    
    
    
    /**
     * Carga el mapa selectedComponentMap para tener los componentes activos, 
     * de esta manera podemos setearlos desde la BBDD.
     */
    private void initSelectedComponent() {
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.COCA_COLA.name(),  new GenericSelectedComponent(this.cocaColaCheck, this.cocaSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.COCA_LIGHT.name(),  new GenericSelectedComponent(this.cocaLightCheck, this.cocaLightSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.SPRITE.name(),  new GenericSelectedComponent(this.spriteCheck, this.spriteSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.SPRITE_ZERO.name(),  new GenericSelectedComponent(this.spriteZeroCheck, this.spriteZeroSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.PASO_TOROS_POMELO.name(),  new GenericSelectedComponent(this.pomeloTorosCheck, this.torosPomeloSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.PASO_TOROS_TONICA.name(),  new GenericSelectedComponent(this.tonicaCheck, this.torosTonicaSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.FANTA.name(),  new GenericSelectedComponent(this.fantaCheck, this.fantaSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.LEVITE_LIMONADA.name(),  new GenericSelectedComponent(this.limonadaCheck, this.limonadaSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.LEVITE_MANZANA.name(),  new GenericSelectedComponent(this.manzanaCheck, this.manzanaSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.LEVITE_NARANJA.name(),  new GenericSelectedComponent(this.naranjaCheck, this.naranjaSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.LEVITE_POMELO.name(),  new GenericSelectedComponent(this.pomeloCheck, this.pomeloSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.CEPITA.name(),  new GenericSelectedComponent(this.cepitaCheck, this.cepitaSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.SCHEWEPPES_POMELO.name(),  new GenericSelectedComponent(this.scheweppesPomeloCheck, this.scheweppesPomeloSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.SCHEWEPPES_TONICA.name(),  new GenericSelectedComponent(this.scheweppesTonicaCheck, this.scheweppesTonicaSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.AGUA.name(),  new GenericSelectedComponent(this.waterCheck, this.waterSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.AGUA_GAS.name(),  new GenericSelectedComponent(this.waterGasCheck, this.waterGasSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.EXPRIMIDO.name(),  new GenericSelectedComponent(this.exprimidoCheck, this.exprimidoSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.LICUADO_LECHE.name(),  new GenericSelectedComponent(this.licuadoLecheCheck, this.licuadoLecheSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.LICUADO_AGUA.name(),  new GenericSelectedComponent(this.licuadoAguaCheck, this.licuadoAguaSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.LIMONADA.name(),  new GenericSelectedComponent(this.limonadaElaboradoCheck, this.limonada_elaborada_Spinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.MILK_SHAKE_CHOCO.name(),  new GenericSelectedComponent(this.shakeChocolateCheck, this.shakeChocolateSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.MILK_SHAKE_DULCE_LECHE.name(),  new GenericSelectedComponent(this.shakeDulceLecheCheck, this.shakeDulceLecheSpinner));
    }

    private void chargeEmployee(String userTable) {
        List<String> employeeNamesList = new ArrayList<>();
        this.employeeList.forEach(t
                -> employeeNamesList.add(t.getNombre() + " " + t.getApellido())
        );

        DefaultComboBoxModel employeeModel = new DefaultComboBoxModel(employeeNamesList.toArray());
        this.employeeNameCombo.setModel(employeeModel);
        
        // this.employeeNameCombo
        
        if (userTable != null) {
            String[] userTableArray = userTable.split("_");
            this.employeeNameCombo.setSelectedItem(userTableArray[0] + " " + userTableArray[1]);
        } else {
            this.tableUser = this.usersImpl.findUserByCompleteName(employeeList.get(0).getNombre(), employeeList.get(0).getApellido());
        }

    }

    private void selectOrderView(int table) {
        SelectOrder.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        SelectOrder.setSize(800, 600);
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
        payAction = new javax.swing.JButton();
        seeConsuming = new javax.swing.JButton();
        deleteTable = new javax.swing.JButton();
        closeTable = new javax.swing.JButton();
        SaveButton = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        DrinkNoAlcoholFrame = new javax.swing.JFrame();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        acceptNoAlcoholDrinks = new javax.swing.JButton();
        cancelNoAlcoholDrinks = new javax.swing.JButton();
        cocaColaCheck = new javax.swing.JCheckBox();
        cocaSpinner = new javax.swing.JSpinner();
        cocaLightCheck = new javax.swing.JCheckBox();
        cocaLightSpinner = new javax.swing.JSpinner();
        spriteCheck = new javax.swing.JCheckBox();
        spriteSpinner = new javax.swing.JSpinner();
        spriteZeroCheck = new javax.swing.JCheckBox();
        spriteZeroSpinner = new javax.swing.JSpinner();
        tonicaCheck = new javax.swing.JCheckBox();
        torosTonicaSpinner = new javax.swing.JSpinner();
        fantaCheck = new javax.swing.JCheckBox();
        fantaSpinner = new javax.swing.JSpinner();
        naranjaCheck = new javax.swing.JCheckBox();
        naranjaSpinner = new javax.swing.JSpinner();
        pomeloCheck = new javax.swing.JCheckBox();
        pomeloSpinner = new javax.swing.JSpinner();
        manzanaCheck = new javax.swing.JCheckBox();
        manzanaSpinner = new javax.swing.JSpinner();
        limonadaCheck = new javax.swing.JCheckBox();
        limonadaSpinner = new javax.swing.JSpinner();
        pomeloTorosCheck = new javax.swing.JCheckBox();
        torosPomeloSpinner = new javax.swing.JSpinner();
        waterCheck = new javax.swing.JCheckBox();
        waterSpinner = new javax.swing.JSpinner();
        waterGasCheck = new javax.swing.JCheckBox();
        waterGasSpinner = new javax.swing.JSpinner();
        exprimidoCheck = new javax.swing.JCheckBox();
        exprimidoSpinner = new javax.swing.JSpinner();
        limonadaElaboradoCheck = new javax.swing.JCheckBox();
        limonada_elaborada_Spinner = new javax.swing.JSpinner();
        licuadoLecheCheck = new javax.swing.JCheckBox();
        licuadoAguaCheck = new javax.swing.JCheckBox();
        licuadoLecheSpinner = new javax.swing.JSpinner();
        licuadoAguaSpinner = new javax.swing.JSpinner();
        shakeDulceLecheCheck = new javax.swing.JCheckBox();
        shakeDulceLecheSpinner = new javax.swing.JSpinner();
        shakeChocolateCheck = new javax.swing.JCheckBox();
        shakeChocolateSpinner = new javax.swing.JSpinner();
        cepitaCheck = new javax.swing.JCheckBox();
        cepitaSpinner = new javax.swing.JSpinner();
        scheweppesPomeloCheck = new javax.swing.JCheckBox();
        scheweppesTonicaCheck = new javax.swing.JCheckBox();
        scheweppesPomeloSpinner = new javax.swing.JSpinner();
        scheweppesTonicaSpinner = new javax.swing.JSpinner();
        DrinkAlcoholFrame = new javax.swing.JFrame();
        CandyProductsFrame = new javax.swing.JFrame();
        SaladsProductFrame = new javax.swing.JFrame();
        CloseTableFrame = new javax.swing.JFrame();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        productDescriptionTable = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        MozoLabel = new javax.swing.JLabel();
        MesaLabel = new javax.swing.JLabel();
        TotalLabel = new javax.swing.JLabel();
        CloseTableButton = new javax.swing.JButton();
        SuggestPromosFrm = new javax.swing.JFrame();
        PayTableFrm = new javax.swing.JFrame();
        jLabel11 = new javax.swing.JLabel();
        totalPayLabel = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        tableIdPayLabel = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        userTableCompleteNamePay = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        cashPayment = new javax.swing.JButton();
        otraFormaDePago = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        PagoEfectivoFrame = new javax.swing.JFrame();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cashSubTotal = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        efectivoSubTotal2 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        efectivoSubTotal3 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        paymentCash = new javax.swing.JTextField();
        cashDiscount = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        OtraFormaDePagoFrame = new javax.swing.JFrame();
        cashDiscount2 = new javax.swing.JComboBox<>();
        jLabel37 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        efectivoSubTotal6 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        cashSubTotal2 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel45 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel36 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        cashPaymentLastName = new javax.swing.JTextField();
        cashPaymentName = new javax.swing.JTextField();
        cashPaymentDNI = new javax.swing.JTextField();
        cashPaymentCelphone = new javax.swing.JTextField();
        PagoEfectivoFrame1 = new javax.swing.JFrame();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        cashSubTotal1 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        efectivoSubTotal4 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        efectivoSubTotal5 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jButton3 = new javax.swing.JButton();
        paymentCash1 = new javax.swing.JTextField();
        cashDiscount1 = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        tableOne = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        tableTwo = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        tableThree = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        tableFour = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        employeeNameCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeNameComboActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setText("Quién sos ?");

        drinkNoAlcohol.setText("Bebidas sin alcohol");
        drinkNoAlcohol.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                drinkNoAlcoholAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
        });
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

        SaveButton.setText("Guardar Cambios");
        SaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveButtonActionPerformed(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jLabel40.setText("Requiere permisos de administrador");

        jLabel43.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel43.setText("Productos a Elegir");

        jLabel50.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel50.setText("Operaciones");

        javax.swing.GroupLayout SelectOrderLayout = new javax.swing.GroupLayout(SelectOrder.getContentPane());
        SelectOrder.getContentPane().setLayout(SelectOrderLayout);
        SelectOrderLayout.setHorizontalGroup(
            SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SelectOrderLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(employeeNameCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(231, 231, 231))
            .addGroup(SelectOrderLayout.createSequentialGroup()
                .addGroup(SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SelectOrderLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel40))
                    .addGroup(SelectOrderLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(seeConsuming)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(closeTable)
                        .addGap(50, 50, 50)
                        .addComponent(payAction, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(deleteTable, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
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
                                .addGap(64, 64, 64)
                                .addComponent(jLabel43)))
                        .addGap(0, 39, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(SelectOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SelectOrderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(SaveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(270, 270, 270))
            .addGroup(SelectOrderLayout.createSequentialGroup()
                .addGroup(SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SelectOrderLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel50))
                    .addGroup(SelectOrderLayout.createSequentialGroup()
                        .addGap(302, 302, 302)
                        .addComponent(jLabel4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        SelectOrderLayout.setVerticalGroup(
            SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SelectOrderLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employeeNameCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(promotions, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                    .addComponent(candyProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(drinkNoAlcohol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(drinkAlcohol, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                    .addComponent(saladProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(31, 31, 31)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel50)
                .addGap(18, 18, 18)
                .addComponent(SaveButton)
                .addGap(28, 28, 28)
                .addGroup(SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(seeConsuming)
                    .addComponent(closeTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteTable)
                    .addComponent(payAction))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel40)
                .addGap(17, 17, 17))
        );

        jLabel6.setText("Línea Gaseosas");

        jLabel7.setText("Línea Elaborados");

        acceptNoAlcoholDrinks.setText("Aceptar");
        acceptNoAlcoholDrinks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptNoAlcoholDrinksActionPerformed(evt);
            }
        });

        cancelNoAlcoholDrinks.setText("Cancelar");
        cancelNoAlcoholDrinks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelNoAlcoholDrinksActionPerformed(evt);
            }
        });

        cocaColaCheck.setText("coca cola");
        cocaColaCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cocaColaCheckActionPerformed(evt);
            }
        });

        cocaLightCheck.setText("coca light");
        cocaLightCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cocaLightCheckActionPerformed(evt);
            }
        });

        spriteCheck.setText("sprite");
        spriteCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spriteCheckActionPerformed(evt);
            }
        });

        spriteZeroCheck.setText("sprite zero");
        spriteZeroCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spriteZeroCheckActionPerformed(evt);
            }
        });

        tonicaCheck.setText("toros tónica");
        tonicaCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tonicaCheckActionPerformed(evt);
            }
        });

        fantaCheck.setText("fanta");
        fantaCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fantaCheckActionPerformed(evt);
            }
        });

        naranjaCheck.setText("levite naranja");
        naranjaCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                naranjaCheckActionPerformed(evt);
            }
        });

        pomeloCheck.setText("levite pomelo");
        pomeloCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pomeloCheckActionPerformed(evt);
            }
        });

        manzanaCheck.setText("levite manzana");
        manzanaCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manzanaCheckActionPerformed(evt);
            }
        });

        limonadaCheck.setText("levite limonada");
        limonadaCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limonadaCheckActionPerformed(evt);
            }
        });

        pomeloTorosCheck.setText("toros pomelo");
        pomeloTorosCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pomeloTorosCheckActionPerformed(evt);
            }
        });

        waterCheck.setText("agua");
        waterCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                waterCheckActionPerformed(evt);
            }
        });

        waterGasCheck.setText("agua c/gas");
        waterGasCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                waterGasCheckActionPerformed(evt);
            }
        });

        exprimidoCheck.setText("exprimido");
        exprimidoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exprimidoCheckActionPerformed(evt);
            }
        });

        limonadaElaboradoCheck.setText("limonada");
        limonadaElaboradoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limonadaElaboradoCheckActionPerformed(evt);
            }
        });

        licuadoLecheCheck.setText("licuado c/ leche");
        licuadoLecheCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                licuadoLecheCheckActionPerformed(evt);
            }
        });

        licuadoAguaCheck.setText("licuado c/ agua");
        licuadoAguaCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                licuadoAguaCheckActionPerformed(evt);
            }
        });

        shakeDulceLecheCheck.setText("Milk Shake (dulce de leche)");
        shakeDulceLecheCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shakeDulceLecheCheckActionPerformed(evt);
            }
        });

        shakeChocolateCheck.setText("Milk Shake (chocolate)");
        shakeChocolateCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shakeChocolateCheckActionPerformed(evt);
            }
        });

        cepitaCheck.setText("cepita");
        cepitaCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cepitaCheckActionPerformed(evt);
            }
        });

        scheweppesPomeloCheck.setText("scheweppes pomelo");
        scheweppesPomeloCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scheweppesPomeloCheckActionPerformed(evt);
            }
        });

        scheweppesTonicaCheck.setText("scheweppes tonica");
        scheweppesTonicaCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scheweppesTonicaCheckActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DrinkNoAlcoholFrameLayout = new javax.swing.GroupLayout(DrinkNoAlcoholFrame.getContentPane());
        DrinkNoAlcoholFrame.getContentPane().setLayout(DrinkNoAlcoholFrameLayout);
        DrinkNoAlcoholFrameLayout.setHorizontalGroup(
            DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2))
                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                .addComponent(shakeChocolateCheck)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(shakeChocolateSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                .addComponent(shakeDulceLecheCheck)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(shakeDulceLecheSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator3))
                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(exprimidoCheck)
                                            .addComponent(limonadaElaboradoCheck))
                                        .addGap(18, 18, 18)
                                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(limonada_elaborada_Spinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(exprimidoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                .addGap(101, 101, 101)
                                                .addComponent(cancelNoAlcoholDrinks)
                                                .addGap(41, 41, 41)
                                                .addComponent(acceptNoAlcoholDrinks, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                .addGap(68, 68, 68)
                                                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                        .addComponent(licuadoAguaCheck)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(licuadoAguaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                        .addComponent(licuadoLecheCheck)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(licuadoLecheSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(scheweppesPomeloCheck)
                                            .addComponent(scheweppesTonicaCheck))
                                        .addGap(18, 18, 18)
                                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(scheweppesTonicaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(scheweppesPomeloSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(spriteCheck)
                                    .addComponent(cocaLightCheck)
                                    .addComponent(cocaColaCheck)
                                    .addComponent(spriteZeroCheck)
                                    .addComponent(tonicaCheck)
                                    .addComponent(fantaCheck)
                                    .addComponent(pomeloTorosCheck))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cocaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cocaLightSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spriteSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(torosTonicaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(spriteZeroSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(torosPomeloSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(fantaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(32, 32, 32)
                                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(pomeloCheck)
                                            .addComponent(naranjaCheck))
                                        .addGap(18, 18, 18)
                                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(naranjaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(pomeloSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                        .addComponent(manzanaCheck)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(manzanaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(limonadaCheck)
                                            .addComponent(waterCheck)
                                            .addComponent(cepitaCheck)
                                            .addComponent(waterGasCheck))
                                        .addGap(21, 21, 21)
                                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cepitaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(waterGasSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(waterSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(limonadaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(56, 56, 56)))
                        .addContainerGap())))
        );
        DrinkNoAlcoholFrameLayout.setVerticalGroup(
            DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(21, 21, 21)
                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                .addComponent(cocaColaCheck)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cocaLightCheck))
                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(naranjaCheck)
                                    .addComponent(naranjaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(pomeloCheck)
                                    .addComponent(pomeloSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spriteCheck)
                            .addComponent(manzanaCheck)
                            .addComponent(manzanaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spriteZeroCheck)
                            .addComponent(limonadaCheck)
                            .addComponent(limonadaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tonicaCheck)
                            .addComponent(cepitaCheck)
                            .addComponent(cepitaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pomeloTorosCheck)
                            .addComponent(waterCheck)
                            .addComponent(waterSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fantaCheck)
                            .addComponent(waterGasCheck)
                            .addComponent(waterGasSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                        .addComponent(cocaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cocaLightSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spriteSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spriteZeroSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(torosTonicaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(torosPomeloSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(fantaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                        .addGap(3, 100, Short.MAX_VALUE)
                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(exprimidoCheck)
                                    .addComponent(exprimidoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(limonadaElaboradoCheck)
                                    .addComponent(limonada_elaborada_Spinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(licuadoLecheCheck)
                                    .addComponent(licuadoLecheSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(licuadoAguaCheck)
                                    .addComponent(licuadoAguaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(35, 35, 35)
                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(shakeDulceLecheSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(shakeDulceLecheCheck))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(shakeChocolateCheck)
                            .addComponent(shakeChocolateSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cancelNoAlcoholDrinks)
                            .addComponent(acceptNoAlcoholDrinks))
                        .addContainerGap())
                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(scheweppesPomeloSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(scheweppesPomeloCheck))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(scheweppesTonicaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(scheweppesTonicaCheck))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout DrinkAlcoholFrameLayout = new javax.swing.GroupLayout(DrinkAlcoholFrame.getContentPane());
        DrinkAlcoholFrame.getContentPane().setLayout(DrinkAlcoholFrameLayout);
        DrinkAlcoholFrameLayout.setHorizontalGroup(
            DrinkAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 712, Short.MAX_VALUE)
        );
        DrinkAlcoholFrameLayout.setVerticalGroup(
            DrinkAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 517, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout CandyProductsFrameLayout = new javax.swing.GroupLayout(CandyProductsFrame.getContentPane());
        CandyProductsFrame.getContentPane().setLayout(CandyProductsFrameLayout);
        CandyProductsFrameLayout.setHorizontalGroup(
            CandyProductsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 712, Short.MAX_VALUE)
        );
        CandyProductsFrameLayout.setVerticalGroup(
            CandyProductsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 517, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout SaladsProductFrameLayout = new javax.swing.GroupLayout(SaladsProductFrame.getContentPane());
        SaladsProductFrame.getContentPane().setLayout(SaladsProductFrameLayout);
        SaladsProductFrameLayout.setHorizontalGroup(
            SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 712, Short.MAX_VALUE)
        );
        SaladsProductFrameLayout.setVerticalGroup(
            SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 517, Short.MAX_VALUE)
        );

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel8.setText("Cerrar Mesa Nº: ");

        productDescriptionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Producto", "Cantidad", "Precio Unitario", "Precio Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(productDescriptionTable);

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel9.setText("Total: ");

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel10.setText("Mesa atendida por: ");

        MozoLabel.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        MozoLabel.setText(".");

        MesaLabel.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        MesaLabel.setText(".");

        TotalLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        TotalLabel.setText(".");

        CloseTableButton.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        CloseTableButton.setText("Cerrar Mesa");
        CloseTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseTableButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CloseTableFrameLayout = new javax.swing.GroupLayout(CloseTableFrame.getContentPane());
        CloseTableFrame.getContentPane().setLayout(CloseTableFrameLayout);
        CloseTableFrameLayout.setHorizontalGroup(
            CloseTableFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CloseTableFrameLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(CloseTableFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MesaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MozoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(250, Short.MAX_VALUE))
            .addGroup(CloseTableFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CloseTableFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(CloseTableFrameLayout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(TotalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CloseTableFrameLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(CloseTableButton, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(CloseTableFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(CloseTableFrameLayout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(486, Short.MAX_VALUE)))
        );
        CloseTableFrameLayout.setVerticalGroup(
            CloseTableFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CloseTableFrameLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(CloseTableFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MesaLabel)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(MozoLabel)
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(CloseTableFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(TotalLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(CloseTableButton)
                .addGap(24, 24, 24))
            .addGroup(CloseTableFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(CloseTableFrameLayout.createSequentialGroup()
                    .addGap(50, 50, 50)
                    .addComponent(jLabel10)
                    .addContainerGap(337, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout SuggestPromosFrmLayout = new javax.swing.GroupLayout(SuggestPromosFrm.getContentPane());
        SuggestPromosFrm.getContentPane().setLayout(SuggestPromosFrmLayout);
        SuggestPromosFrmLayout.setHorizontalGroup(
            SuggestPromosFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 712, Short.MAX_VALUE)
        );
        SuggestPromosFrmLayout.setVerticalGroup(
            SuggestPromosFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 517, Short.MAX_VALUE)
        );

        jLabel11.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel11.setText("Total a pagar");

        totalPayLabel.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        totalPayLabel.setText("Total_pago");

        jLabel12.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel12.setText("Mesa Nº ");

        tableIdPayLabel.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        tableIdPayLabel.setText("mesa_id");

        jLabel13.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel13.setText("Nombre Mozo");

        userTableCompleteNamePay.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        userTableCompleteNamePay.setText("nombre_mozo");

        jLabel15.setFont(new java.awt.Font("Dialog", 0, 13)); // NOI18N
        jLabel15.setText("Forma de pago");

        cashPayment.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cashPayment.setText("Efectivo");

        otraFormaDePago.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        otraFormaDePago.setText("Otros");

        jLabel14.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel14.setText("Pago de Mesa Usada:");

        javax.swing.GroupLayout PayTableFrmLayout = new javax.swing.GroupLayout(PayTableFrm.getContentPane());
        PayTableFrm.getContentPane().setLayout(PayTableFrmLayout);
        PayTableFrmLayout.setHorizontalGroup(
            PayTableFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PayTableFrmLayout.createSequentialGroup()
                .addGroup(PayTableFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PayTableFrmLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator4))
                    .addGroup(PayTableFrmLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(tableIdPayLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(userTableCompleteNamePay)
                        .addGap(73, 73, 73)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalPayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
            .addGroup(PayTableFrmLayout.createSequentialGroup()
                .addGap(274, 274, 274)
                .addComponent(jLabel14)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(PayTableFrmLayout.createSequentialGroup()
                .addGroup(PayTableFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PayTableFrmLayout.createSequentialGroup()
                        .addGap(259, 259, 259)
                        .addComponent(cashPayment)
                        .addGap(18, 18, 18)
                        .addComponent(otraFormaDePago, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PayTableFrmLayout.createSequentialGroup()
                        .addGap(303, 303, 303)
                        .addComponent(jLabel15)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PayTableFrmLayout.setVerticalGroup(
            PayTableFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PayTableFrmLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(PayTableFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(totalPayLabel)
                    .addComponent(jLabel12)
                    .addComponent(tableIdPayLabel)
                    .addComponent(jLabel13)
                    .addComponent(userTableCompleteNamePay))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PayTableFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(otraFormaDePago)
                    .addComponent(cashPayment))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jLabel16.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel16.setText("Pago Efectivo");

        jLabel17.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel17.setText("Subtotal a pagar");

        cashSubTotal.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cashSubTotal.setText("total");

        jLabel19.setText("$");

        jLabel18.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel18.setText("Efectivo recibido");

        jLabel20.setText("$");

        jLabel21.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel21.setText("Total a pagar");

        efectivoSubTotal2.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        efectivoSubTotal2.setText("total");

        jLabel22.setText("$");

        jLabel23.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel23.setText("Vuelto");

        efectivoSubTotal3.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        efectivoSubTotal3.setText("total");

        jLabel24.setText("$");

        jCheckBox1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jCheckBox1.setText("Aplica Descuento");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jButton1.setText("Pagar");

        paymentCash.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentCashActionPerformed(evt);
            }
        });

        cashDiscount.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cashDiscount.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5%", "10%", "15%", "20%", "25%", "30%", "50%", "100%" }));

        jButton2.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButton2.setText("Calcular");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel25.setText("Calcular Monto");

        javax.swing.GroupLayout PagoEfectivoFrameLayout = new javax.swing.GroupLayout(PagoEfectivoFrame.getContentPane());
        PagoEfectivoFrame.getContentPane().setLayout(PagoEfectivoFrameLayout);
        PagoEfectivoFrameLayout.setHorizontalGroup(
            PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PagoEfectivoFrameLayout.createSequentialGroup()
                .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PagoEfectivoFrameLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PagoEfectivoFrameLayout.createSequentialGroup()
                                .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel23))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PagoEfectivoFrameLayout.createSequentialGroup()
                                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(efectivoSubTotal3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(efectivoSubTotal2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PagoEfectivoFrameLayout.createSequentialGroup()
                                .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PagoEfectivoFrameLayout.createSequentialGroup()
                                        .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel18)
                                            .addComponent(jCheckBox1)
                                            .addComponent(jLabel25))
                                        .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(PagoEfectivoFrameLayout.createSequentialGroup()
                                                .addGap(36, 36, 36)
                                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PagoEfectivoFrameLayout.createSequentialGroup()
                                                .addGap(28, 28, 28)
                                                .addComponent(paymentCash, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(PagoEfectivoFrameLayout.createSequentialGroup()
                                                .addGap(38, 38, 38)
                                                .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(cashDiscount, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PagoEfectivoFrameLayout.createSequentialGroup()
                                        .addComponent(jLabel17)
                                        .addGap(67, 67, 67)
                                        .addComponent(jLabel19)
                                        .addGap(21, 21, 21)
                                        .addComponent(cashSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE))))
                    .addGroup(PagoEfectivoFrameLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))))
                .addGap(19, 19, 19))
        );
        PagoEfectivoFrameLayout.setVerticalGroup(
            PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PagoEfectivoFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(cashSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel20)
                    .addComponent(paymentCash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox1)
                    .addComponent(cashDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(efectivoSubTotal2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(efectivoSubTotal3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel24))
                    .addComponent(jLabel23))
                .addGap(23, 23, 23)
                .addComponent(jButton1)
                .addContainerGap())
        );

        cashDiscount2.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cashDiscount2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5%", "10%", "15%", "20%", "25%", "30%", "50%", "100%" }));

        jLabel37.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel37.setText("Total a pagar");

        jButton5.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButton5.setText("Calcular");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        efectivoSubTotal6.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        efectivoSubTotal6.setText("total");

        jLabel38.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel38.setText("  Calcular Monto");

        jLabel39.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel39.setText("$");

        jLabel41.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel41.setText("Otro medio de Pago:");

        jLabel42.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel42.setText("  Subtotal a pagar");

        cashSubTotal2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cashSubTotal2.setText("total");

        jLabel44.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel44.setText("$");

        jCheckBox3.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jCheckBox3.setText("Aplica Descuento");
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel45.setText("  Medio de Pago");

        jButton6.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButton6.setText("Pagar");

        jComboBox1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "debito", "credito", "cuenta_corriente", "mercado_pago" }));

        jLabel36.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel36.setText("  Datos Personales:");

        jLabel46.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel46.setText("  Nombre");

        jLabel47.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel47.setText("  D.N.I.");

        jLabel48.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel48.setText("  Apellido");

        jLabel49.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel49.setText("  Teléfono");

        cashPaymentName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cashPaymentNameActionPerformed(evt);
            }
        });

        cashPaymentCelphone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cashPaymentCelphoneActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout OtraFormaDePagoFrameLayout = new javax.swing.GroupLayout(OtraFormaDePagoFrame.getContentPane());
        OtraFormaDePagoFrame.getContentPane().setLayout(OtraFormaDePagoFrameLayout);
        OtraFormaDePagoFrameLayout.setHorizontalGroup(
            OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OtraFormaDePagoFrameLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(OtraFormaDePagoFrameLayout.createSequentialGroup()
                        .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel45)
                            .addComponent(jLabel38)
                            .addComponent(jLabel42)
                            .addComponent(jCheckBox3))
                        .addGap(43, 43, 43)
                        .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cashDiscount2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(OtraFormaDePagoFrameLayout.createSequentialGroup()
                                .addComponent(jLabel44)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cashSubTotal2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(OtraFormaDePagoFrameLayout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(OtraFormaDePagoFrameLayout.createSequentialGroup()
                                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(efectivoSubTotal6, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton6))
                        .addGap(1, 1, 1))
                    .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(OtraFormaDePagoFrameLayout.createSequentialGroup()
                            .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel47)
                                .addComponent(jLabel46)
                                .addComponent(jLabel48)
                                .addComponent(jLabel49))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cashPaymentDNI)
                                    .addComponent(cashPaymentCelphone, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(cashPaymentLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cashPaymentName, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
            .addGroup(OtraFormaDePagoFrameLayout.createSequentialGroup()
                .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(OtraFormaDePagoFrameLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(OtraFormaDePagoFrameLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel36)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        OtraFormaDePagoFrameLayout.setVerticalGroup(
            OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OtraFormaDePagoFrameLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(OtraFormaDePagoFrameLayout.createSequentialGroup()
                        .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel42)
                            .addComponent(cashSubTotal2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel44))
                        .addGap(25, 25, 25))
                    .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel45)))
                .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cashDiscount2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(jButton5))
                .addGap(27, 27, 27)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cashPaymentName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cashPaymentLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cashPaymentDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cashPaymentCelphone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(efectivoSubTotal6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6)
                .addGap(16, 16, 16))
        );

        jLabel26.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel26.setText("Pago Efectivo");

        jLabel27.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel27.setText("Subtotal a pagar");

        cashSubTotal1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cashSubTotal1.setText("total");

        jLabel28.setText("$");

        jLabel29.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel29.setText("Efectivo recibido");

        jLabel30.setText("$");

        jLabel31.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel31.setText("Total a pagar");

        efectivoSubTotal4.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        efectivoSubTotal4.setText("total");

        jLabel32.setText("$");

        jLabel33.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel33.setText("Vuelto");

        efectivoSubTotal5.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        efectivoSubTotal5.setText("total");

        jLabel34.setText("$");

        jCheckBox2.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jCheckBox2.setText("Aplica Descuento");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        jButton3.setText("Pagar");

        paymentCash1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentCash1ActionPerformed(evt);
            }
        });

        cashDiscount1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cashDiscount1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5%", "10%", "15%", "20%", "25%", "30%", "50%", "100%" }));

        jButton4.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButton4.setText("Calcular");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel35.setText("Calcular Monto");

        javax.swing.GroupLayout PagoEfectivoFrame1Layout = new javax.swing.GroupLayout(PagoEfectivoFrame1.getContentPane());
        PagoEfectivoFrame1.getContentPane().setLayout(PagoEfectivoFrame1Layout);
        PagoEfectivoFrame1Layout.setHorizontalGroup(
            PagoEfectivoFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PagoEfectivoFrame1Layout.createSequentialGroup()
                .addGroup(PagoEfectivoFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PagoEfectivoFrame1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3))
                    .addGroup(PagoEfectivoFrame1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(PagoEfectivoFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PagoEfectivoFrame1Layout.createSequentialGroup()
                                .addGroup(PagoEfectivoFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel31)
                                    .addComponent(jLabel33))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(PagoEfectivoFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PagoEfectivoFrame1Layout.createSequentialGroup()
                                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(efectivoSubTotal5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(PagoEfectivoFrame1Layout.createSequentialGroup()
                                        .addComponent(jLabel32)
                                        .addGap(18, 18, 18)
                                        .addComponent(efectivoSubTotal4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PagoEfectivoFrame1Layout.createSequentialGroup()
                                .addGroup(PagoEfectivoFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PagoEfectivoFrame1Layout.createSequentialGroup()
                                        .addComponent(jLabel27)
                                        .addGap(67, 67, 67)
                                        .addComponent(jLabel28)
                                        .addGap(21, 21, 21)
                                        .addComponent(cashSubTotal1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PagoEfectivoFrame1Layout.createSequentialGroup()
                                        .addGroup(PagoEfectivoFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel29)
                                            .addComponent(jCheckBox2)
                                            .addComponent(jLabel35))
                                        .addGap(38, 38, 38)
                                        .addGroup(PagoEfectivoFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(PagoEfectivoFrame1Layout.createSequentialGroup()
                                                .addComponent(jLabel30)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(paymentCash1))
                                            .addComponent(cashDiscount1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.LEADING))))
                .addGap(19, 19, 19))
        );
        PagoEfectivoFrame1Layout.setVerticalGroup(
            PagoEfectivoFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PagoEfectivoFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addGap(18, 18, 18)
                .addGroup(PagoEfectivoFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(cashSubTotal1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PagoEfectivoFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30)
                    .addComponent(paymentCash1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PagoEfectivoFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox2)
                    .addComponent(cashDiscount1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PagoEfectivoFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PagoEfectivoFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(efectivoSubTotal4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PagoEfectivoFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PagoEfectivoFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(efectivoSubTotal5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel34))
                    .addComponent(jLabel33))
                .addGap(44, 44, 44)
                .addComponent(jButton3)
                .addContainerGap())
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

        tableFour.setBackground(new java.awt.Color(51, 204, 0));
        tableFour.setText("4");
        tableFour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableFourActionPerformed(evt);
            }
        });

        jLabel5.setText("Mesa");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(410, Short.MAX_VALUE)
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
                        .addGap(47, 47, 47)
                        .addComponent(jLabel3)
                        .addGap(51, 51, 51)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(tableThree, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(tableFour, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(78, 78, 78))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tableTwo, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tableThree, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tableOne, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tableFour, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(703, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tableOneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableOneActionPerformed
        // TODO recuperar todos los valores de la BBDD -> para luego inyectarlos 
        //      en el modelo.

        // System.out.println(evt);
        this.selectedTable.setState("tableOne");
        this.selectedTable.setId(1);
        
        String tableUser = this.productsImpl.findTableUserByTableId(this.selectedTable.getId());
        
        this.chargeEmployee(tableUser);
        this.selectOrderView(1);

    }//GEN-LAST:event_tableOneActionPerformed

    private void employeeNameComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeNameComboActionPerformed

        String[] names = this.employeeNameCombo.getSelectedItem().toString().split(" ");
        System.out.println("selected name (employee combo) : " + names[0] + " ___ " + names[1]);

        this.tableUser = this.usersImpl.findUserByCompleteName(names[0], names[1]);
        
        System.out.println("tableUser --> " + this.tableUser.getNombre() + "- - - " + this.tableUser.getApellido() );

    }//GEN-LAST:event_employeeNameComboActionPerformed

    private void tableTwoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableTwoActionPerformed
        // TODO add your handling code here:
        this.selectedTable.setState("TableTwo");
        this.selectedTable.setId(2);
        
        String tableUser = this.productsImpl.findTableUserByTableId(this.selectedTable.getId());
        
        this.chargeEmployee(tableUser);
        this.selectOrderView(2);

    }//GEN-LAST:event_tableTwoActionPerformed

    private void tableThreeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableThreeActionPerformed
        // TODO add your handling code here:
        this.selectedTable.setState("TableThree");
        this.selectedTable.setId(3);
        
        String tableUser = this.productsImpl.findTableUserByTableId(this.selectedTable.getId());
        this.chargeEmployee(tableUser);
        this.selectOrderView(3);

    }//GEN-LAST:event_tableThreeActionPerformed

    
    
    private void drinkNoAlcoholActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drinkNoAlcoholActionPerformed

        DrinkNoAlcoholFrame.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        DrinkNoAlcoholFrame.setSize(550, 700);
        DrinkNoAlcoholFrame.setVisible(true);

        System.out.println("---- INIT: Table selected ----");
        System.out.println(this.selectedTable.getState());
        System.out.println(this.selectedTable.getId());
        System.out.println("---- END: Table Selected ----");
                
        List<Product> selectedTableProducts = this.productsImpl.findTableSelectedProducts(this.selectedTable.getId());

        if (selectedTableProducts.size() != 0) {
            /* Se encontraron datos en la bbdd
             *  por lo tanto hay que cargar las cantidades que se 
             *  hayan guardado.
            */
            
            
            for (Product product : selectedTableProducts) {
                this.selectedComponentMap.get(product.getBrand()).getCheckBox().setSelected(true);
                this.selectedComponentMap.get(product.getBrand()).getSpinner().setEnabled(true);
                this.selectedComponentMap.get(product.getBrand()).getSpinner().setValue(product.getAmountConsumed());
            }
            
        } else {
            /**
             * Caso contrario: no hay datos de la mesa en la bbdd por lo tanto
             * hay que resetear todo, para que el formulario quede limpio.
             */
            for (Component component : DrinkNoAlcoholFrame.getContentPane().getComponents()) {
                if (component instanceof JCheckBox) {
                    ((JCheckBox) component).setSelected(false);
                }

                if (component instanceof JSpinner) {

                    /*
                        Tenemos dos maneras de hacer lo mismo, 
                        esta en particular crea un JSpinner a partir del casteo del component
                        y luego, le aplica la propiedad que se esté buscando
                        
                        JSpinner spinner = (JSpinner) component;
                        spinner.setEnabled(false);
                     */
 /*
                        Es igual al caso anterior, pero se hace todo en una línea.
                     */
                    ((JSpinner) component).setEnabled(false);
                    ((JSpinner) component).setValue(0);
                }
            }
        }


    }//GEN-LAST:event_drinkNoAlcoholActionPerformed

    private void candyProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_candyProductsActionPerformed

        CandyProductsFrame.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        CandyProductsFrame.setSize(650, 700);
        CandyProductsFrame.setVisible(true);

    }//GEN-LAST:event_candyProductsActionPerformed

    private void promotionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_promotionsActionPerformed
        // TODO add your handling code here:
        CloseTableFrame.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        CloseTableFrame.setSize(650, 700);
        CloseTableFrame.setVisible(true);

    }//GEN-LAST:event_promotionsActionPerformed

    private void saladProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saladProductsActionPerformed
        // TODO add your handling code here:
        SaladsProductFrame.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        SaladsProductFrame.setSize(650, 700);
        SaladsProductFrame.setVisible(true);
    }//GEN-LAST:event_saladProductsActionPerformed

    private void drinkAlcoholActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drinkAlcoholActionPerformed
        // TODO add your handling code here:
        DrinkAlcoholFrame.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        DrinkAlcoholFrame.setSize(650, 700);
        DrinkAlcoholFrame.setVisible(true);

    }//GEN-LAST:event_drinkAlcoholActionPerformed

    private void seeConsumingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seeConsumingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seeConsumingActionPerformed

    private void closeTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeTableActionPerformed
        // TODO add your handling code here:

        System.out.println("MESA elegida --> state:  " + this.selectedTable.getState() + " ID: " + this.selectedTable.getId());

        if (this.tableUser != null && this.tableUser.getNombre() != null) {
            System.out.println("Usuario mesa: " + this.tableUser.getNombre());
        } else {
            this.tableUser.setNombre(this.employeeList.get(0).getNombre());
            this.tableUser.setApellido(this.employeeList.get(0).getApellido());
        }

        this.MesaLabel.setText(Integer.toString(this.selectedTable.getId()));
        this.MozoLabel.setText(this.tableUser.getNombre() + " " + this.tableUser.getApellido());

        /**
         *
         *
         * TODO: Cerrar mesa -> preparar la acción que termine seteando en false
         * la actividad de la mesa. Además tiene que pintar la mesa de color
         * Verde -> ya que queda liberada.
         *
         */
        Double total = 0.0;
        List<Product> consumingProductList = this.productsImpl.getConsumingProduct(this.selectedTable.getId(), this.tableUser.getNombre() + "_" + this.tableUser.getApellido());
        // para elegir intervalos.
        // productDescriptionTable.addRowSelectionInterval(0, 1);

        DefaultTableModel model = new DefaultTableModel();
        List<String> modelList;

        model.addColumn("Producto");
        model.addColumn("Cantidad");
        model.addColumn("Precio Unitario");
        model.addColumn("Total");

        for (Product product : consumingProductList) {
            modelList = new ArrayList<String>();
            modelList.add(product.getBrand());
            modelList.add(Integer.toString(product.getAmountConsumed()));
            modelList.add(Double.toString(product.getPrice()));
            modelList.add(Double.toString(product.getTotal()));
            total += product.getTotal();
            model.addRow(modelList.toArray());
        }

        productDescriptionTable.setModel(model);
        this.TotalLabel.setText(Double.toString(total));

        CloseTableFrame.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        CloseTableFrame.setSize(650, 500);
        CloseTableFrame.setVisible(true);
    }//GEN-LAST:event_closeTableActionPerformed

    private void deleteTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteTableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteTableActionPerformed

    private void payActionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payActionActionPerformed
        PayTableFrm.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        PayTableFrm.setSize(650, 500);
        PayTableFrm.setVisible(true);
        
        Double total = 0.0;
        // TODO: Tener en cuenta que en este momento el actividad = false -> y está buscando por true
        // TODO: Tener en cuenta que una vez cerrada la mesa se tiene que pagar -> mesa en rojo -> cancelar cualquier operación.
        List<Product> consumingProductList = this.productsImpl.getConsumingProduct(this.selectedTable.getId(), this.tableUser.getNombre() + "_" + this.tableUser.getApellido());
        
        for (Product product : consumingProductList) {
            total += product.getTotal();
        }

        
        this.tableIdPayLabel.setText(Integer.toString(this.selectedTable.getId()));
        this.userTableCompleteNamePay.setText(this.tableUser.getNombre() + " " + this.tableUser.getApellido());
        this.totalPayLabel.setText(Double.toString(total));
        
    }//GEN-LAST:event_payActionActionPerformed

    private void tableFourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableFourActionPerformed
        // TODO add your handling code here:
        this.selectedTable.setState("TableFour");
        this.selectedTable.setId(4);
        
        String tableUser = this.productsImpl.findTableUserByTableId(this.selectedTable.getId());
        
        this.tableFour.setBackground(Color.YELLOW);
        this.chargeEmployee(tableUser);
        this.selectOrderView(4);

    }//GEN-LAST:event_tableFourActionPerformed

    private void drinkNoAlcoholAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_drinkNoAlcoholAncestorAdded
        // TODO add your handling code here:

    }//GEN-LAST:event_drinkNoAlcoholAncestorAdded

    private void scheweppesTonicaCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scheweppesTonicaCheckActionPerformed
        if (scheweppesTonicaCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.scheweppesTonicaSpinner.setEnabled(true);
            this.scheweppesTonicaSpinner.setModel(model);
        } else {
            this.scheweppesTonicaSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_scheweppesTonicaCheckActionPerformed

    private void shakeDulceLecheCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shakeDulceLecheCheckActionPerformed
        if (shakeDulceLecheCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.shakeDulceLecheSpinner.setEnabled(true);
            this.shakeDulceLecheSpinner.setModel(model);
        } else {
            this.shakeDulceLecheSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_shakeDulceLecheCheckActionPerformed

    private void exprimidoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exprimidoCheckActionPerformed
        if (exprimidoCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.exprimidoSpinner.setEnabled(true);
            this.exprimidoSpinner.setModel(model);
        } else {
            this.exprimidoSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_exprimidoCheckActionPerformed

    private void manzanaCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manzanaCheckActionPerformed
        if (manzanaCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.manzanaSpinner.setEnabled(true);
            this.manzanaSpinner.setModel(model);
        } else {
            this.manzanaSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_manzanaCheckActionPerformed

    private void pomeloCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pomeloCheckActionPerformed
        if (pomeloCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.pomeloSpinner.setEnabled(true);
            this.pomeloSpinner.setModel(model);
        } else {
            this.pomeloSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_pomeloCheckActionPerformed

    private void naranjaCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_naranjaCheckActionPerformed
        if (naranjaCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.naranjaSpinner.setEnabled(true);
            this.naranjaSpinner.setModel(model);
        } else {
            this.naranjaSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_naranjaCheckActionPerformed

    private void fantaCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fantaCheckActionPerformed
        if (fantaCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.fantaSpinner.setEnabled(true);
            this.fantaSpinner.setModel(model);
        } else {
            this.fantaSpinner.setEnabled(false);
        }

    }//GEN-LAST:event_fantaCheckActionPerformed

    private void tonicaCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tonicaCheckActionPerformed
        if (tonicaCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.torosTonicaSpinner.setEnabled(true);
            this.torosTonicaSpinner.setModel(model);
        } else {
            this.torosTonicaSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_tonicaCheckActionPerformed

    private void spriteZeroCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spriteZeroCheckActionPerformed
        // TODO add your handling code here:
        if (spriteZeroCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.spriteZeroSpinner.setEnabled(true);
            this.spriteZeroSpinner.setModel(model);
        } else {
            this.spriteZeroSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_spriteZeroCheckActionPerformed

    private void spriteCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spriteCheckActionPerformed
        if (spriteCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.spriteSpinner.setEnabled(true);
            this.spriteSpinner.setModel(model);
        } else {
            this.spriteSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_spriteCheckActionPerformed

    private void cocaLightCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cocaLightCheckActionPerformed
        if (cocaLightCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.cocaLightSpinner.setEnabled(true);
            this.cocaLightSpinner.setModel(model);
        } else {
            this.cocaLightSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_cocaLightCheckActionPerformed

    private void cocaColaCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cocaColaCheckActionPerformed
        // TODO add your handling code here:

        if (cocaColaCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.cocaSpinner.setEnabled(true);
            this.cocaSpinner.setModel(model);
        } else {
            this.cocaSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_cocaColaCheckActionPerformed

    private void cancelNoAlcoholDrinksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelNoAlcoholDrinksActionPerformed
        // TODO add your handling code here:

        /*
        Vaciar todos los contadores
        No modificar el estado de la cuenta
         */
        this.closeGenericFrame(this.DrinkNoAlcoholFrame);
    }//GEN-LAST:event_cancelNoAlcoholDrinksActionPerformed

    private void acceptNoAlcoholDrinksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptNoAlcoholDrinksActionPerformed
        // TODO add your handling code here:

        /*
        Contabilizar todos los productos que se cargan
        Cambiar el estado de la mesa
        Guardarlos en la bbdd
         */
        this.noAlcoholDrinks = new HashMap();

        if (cocaColaCheck.isSelected() && Integer.parseInt(this.cocaSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.COCA_COLA), Integer.parseInt(this.cocaSpinner.getValue().toString()));
        }

        if (cocaLightCheck.isSelected() && Integer.parseInt(this.cocaLightSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.COCA_LIGHT), Integer.parseInt(this.cocaLightSpinner.getValue().toString()));
        }

        if (spriteCheck.isSelected() && Integer.parseInt(this.spriteSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.SPRITE), Integer.parseInt(this.spriteSpinner.getValue().toString()));
        }

        if (spriteZeroCheck.isSelected() && Integer.parseInt(this.spriteZeroSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.SPRITE_ZERO), Integer.parseInt(this.spriteZeroSpinner.getValue().toString()));
        }

        if (tonicaCheck.isSelected() && Integer.parseInt(this.torosTonicaSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.PASO_TOROS_TONICA), Integer.parseInt(this.torosTonicaSpinner.getValue().toString()));
        }

        if (pomeloTorosCheck.isSelected() && Integer.parseInt(this.torosPomeloSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.PASO_TOROS_POMELO), Integer.parseInt(this.torosPomeloSpinner.getValue().toString()));
        }

        if (fantaCheck.isSelected() && Integer.parseInt(this.fantaSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.FANTA), Integer.parseInt(this.fantaSpinner.getValue().toString()));
        }

        if (naranjaCheck.isSelected() && Integer.parseInt(this.naranjaSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.LEVITE_NARANJA), Integer.parseInt(this.naranjaSpinner.getValue().toString()));
        }

        if (pomeloCheck.isSelected() && Integer.parseInt(this.naranjaSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.LEVITE_POMELO), Integer.parseInt(this.naranjaSpinner.getValue().toString()));
        }

        if (manzanaCheck.isSelected() && Integer.parseInt(this.manzanaSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.LEVITE_MANZANA), Integer.parseInt(this.manzanaSpinner.getValue().toString()));
        }

        if (limonadaCheck.isSelected() && Integer.parseInt(this.limonadaSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.LEVITE_LIMONADA), Integer.parseInt(this.limonadaSpinner.getValue().toString()));
        }

        if (cepitaCheck.isSelected() && Integer.parseInt(this.cepitaSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.CEPITA), Integer.parseInt(this.cepitaSpinner.getValue().toString()));
        }

        if (scheweppesPomeloCheck.isSelected() && Integer.parseInt(this.scheweppesPomeloSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.SCHEWEPPES_POMELO), Integer.parseInt(this.scheweppesPomeloSpinner.getValue().toString()));
        }

        if (scheweppesTonicaCheck.isSelected() && Integer.parseInt(this.scheweppesTonicaSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.SCHEWEPPES_TONICA), Integer.parseInt(this.scheweppesTonicaSpinner.getValue().toString()));
        }

        if (waterCheck.isSelected() && Integer.parseInt(this.waterSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.AGUA), Integer.parseInt(this.waterSpinner.getValue().toString()));
        }

        if (waterGasCheck.isSelected() && Integer.parseInt(this.waterGasSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.AGUA_GAS), Integer.parseInt(this.waterGasSpinner.getValue().toString()));
        }

        if (exprimidoCheck.isSelected() && Integer.parseInt(this.exprimidoSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.EXPRIMIDO), Integer.parseInt(this.exprimidoSpinner.getValue().toString()));
        }

        if (licuadoLecheCheck.isSelected() && Integer.parseInt(this.licuadoLecheSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.LICUADO_LECHE), Integer.parseInt(this.licuadoLecheSpinner.getValue().toString()));
        }

        if (licuadoAguaCheck.isSelected() && Integer.parseInt(this.licuadoAguaSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.LICUADO_AGUA), Integer.parseInt(this.licuadoAguaSpinner.getValue().toString()));
        }

        if (limonadaCheck.isSelected() && Integer.parseInt(this.limonadaSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.LIMONADA), Integer.parseInt(this.limonadaSpinner.getValue().toString()));
        }

        if (shakeChocolateCheck.isSelected() && Integer.parseInt(this.shakeChocolateSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.MILK_SHAKE_CHOCO), Integer.parseInt(this.shakeChocolateSpinner.getValue().toString()));
        }

        if (shakeDulceLecheCheck.isSelected() && Integer.parseInt(this.shakeDulceLecheSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.MILK_SHAKE_DULCE_LECHE), Integer.parseInt(this.shakeDulceLecheSpinner.getValue().toString()));
        }

        // nombra la mesa elegida
        this.selectedTable.getState();
        //this.tableOne.setBackground(Color.red);

        for (Map.Entry<NoAlcoholDrinksEnum, Integer> entry : noAlcoholDrinks.entrySet()) {
            //System.out.println("key --> " + entry.getKey());
            //System.out.println("value --> " + entry.getValue());

        }

        /*
        Otra forma de iterar el map
        noAlcoholDrinks.entrySet().stream().map((entry) -> {
            Object key = entry.getKey();
            return entry;
        }).forEachOrdered((entry) -> {
            Object value = entry.getValue();
        });
         */
        this.closeGenericFrame(this.DrinkNoAlcoholFrame);
    }//GEN-LAST:event_acceptNoAlcoholDrinksActionPerformed

    private void pomeloTorosCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pomeloTorosCheckActionPerformed
        if (pomeloTorosCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.torosPomeloSpinner.setEnabled(true);
            this.torosPomeloSpinner.setModel(model);
        } else {
            this.torosPomeloSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_pomeloTorosCheckActionPerformed

    private void scheweppesPomeloCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scheweppesPomeloCheckActionPerformed
        if (scheweppesPomeloCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.scheweppesPomeloSpinner.setEnabled(true);
            this.scheweppesPomeloSpinner.setModel(model);
        } else {
            this.scheweppesPomeloSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_scheweppesPomeloCheckActionPerformed

    private void limonadaElaboradoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limonadaElaboradoCheckActionPerformed
        if (limonadaElaboradoCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.limonada_elaborada_Spinner.setEnabled(true);
            this.limonada_elaborada_Spinner.setModel(model);
        } else {
            this.limonada_elaborada_Spinner.setEnabled(false);
        }
    }//GEN-LAST:event_limonadaElaboradoCheckActionPerformed

    private void licuadoLecheCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_licuadoLecheCheckActionPerformed
        if (licuadoLecheCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.licuadoLecheSpinner.setEnabled(true);
            this.licuadoLecheSpinner.setModel(model);
        } else {
            this.licuadoLecheSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_licuadoLecheCheckActionPerformed

    private void licuadoAguaCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_licuadoAguaCheckActionPerformed
        if (licuadoAguaCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.licuadoAguaSpinner.setEnabled(true);
            this.licuadoAguaSpinner.setModel(model);
        } else {
            this.licuadoAguaSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_licuadoAguaCheckActionPerformed

    private void shakeChocolateCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shakeChocolateCheckActionPerformed
        if (shakeChocolateCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.shakeChocolateSpinner.setEnabled(true);
            this.shakeChocolateSpinner.setModel(model);
        } else {
            this.shakeChocolateSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_shakeChocolateCheckActionPerformed

    private void limonadaCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limonadaCheckActionPerformed
        if (limonadaCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.limonadaSpinner.setEnabled(true);
            this.limonadaSpinner.setModel(model);
        } else {
            this.limonadaSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_limonadaCheckActionPerformed

    private void cepitaCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cepitaCheckActionPerformed
        if (cepitaCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.cepitaSpinner.setEnabled(true);
            this.cepitaSpinner.setModel(model);
        } else {
            this.cepitaSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_cepitaCheckActionPerformed

    private void waterCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_waterCheckActionPerformed
        if (waterCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.waterSpinner.setEnabled(true);
            this.waterSpinner.setModel(model);
        } else {
            this.waterSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_waterCheckActionPerformed

    private void waterGasCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_waterGasCheckActionPerformed
        if (waterGasCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.waterGasSpinner.setEnabled(true);
            this.waterGasSpinner.setModel(model);
        } else {
            this.waterGasSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_waterGasCheckActionPerformed

    private void SaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveButtonActionPerformed
        // TODO add your handling code here:
        this.dataStore = new DataStore();

        System.out.println("mesa elegida state --> " + this.selectedTable.getState() + " mesa elegida ID -> " + this.selectedTable.getId());
        
        

        if (this.tableUser != null && this.tableUser.getNombre() != null) {
            System.out.println("Usuario mesa: " + this.tableUser.getNombre());
        } else {
            this.tableUser.setNombre(this.employeeList.get(0).getNombre());
            this.tableUser.setApellido(this.employeeList.get(0).getApellido());
        }

        this.dataStore.setMesa(this.selectedTable.getId());
        this.dataStore.setNombreMozo(this.tableUser.getNombre() + "_" + this.tableUser.getApellido());

        for (Map.Entry<NoAlcoholDrinksEnum, Integer> entry : this.noAlcoholDrinks.entrySet()) {
            System.out.println("key --> " + entry.getKey());
            System.out.println("value --> " + entry.getValue());

        }

        // TODO: Validación: hacerlo con todos los productos.
        if (!this.noAlcoholDrinks.isEmpty()) {
            dataStore.setNoAlcoholDrinks(noAlcoholDrinks);
        }

        this.productsImpl.insertProduct(dataStore);
        
        this.setTableColour(this.selectedTable.getId() , Color.YELLOW);


        // Crear un metodo que vacíe todos los maps
        this.emptyAllMenu();
        
        
        JOptionPane.showMessageDialog(null, "La mesa Nº: " + this.selectedTable.getId() + " Se ha guardado Correctamente");

        // validar -> nullPointerException.
        // this.AlcoholDrinks.clear();
        // TODO: cuando termina de insetar , todos los campos tienenen que ser reseteados.
        // Para que la próxima mesa empiece con los productos vaciós.
        // Y si se elige la mesa en cuestión tenemos que traer todos los resultados de la BBDD,
        // no puede quedar nada en memoria.
    }//GEN-LAST:event_SaveButtonActionPerformed

    private void emptyAllMenu() {
        if (!this.noAlcoholDrinks.isEmpty()) {
            this.noAlcoholDrinks.clear();
        }

        /*
        if (!this.AlcoholDrinks.isEmpty()) {
            this.noAlcoholDrinks.clear();
        }
         */
        /**
         * Terminar de hacer lo mismo con los demas Maps que tienen toda la info
         * del menu.
         */
    }


    private void CloseTableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseTableButtonActionPerformed
        
        this.productsImpl.closeTable(this.selectedTable.getId(), this.tableUser.getNombre() + "_" + this.tableUser.getApellido());

        this.closeGenericFrame(CloseTableFrame);
        this.closeGenericFrame(this.SelectOrder);

        // JOptionPane.showMessageDialog(null, "La mesa Nº: " + this.selectedTable.getId() + " fue Cerrada con éxito");
        
        this.setTableColour(this.selectedTable.getId(), Color.RED);

    }//GEN-LAST:event_CloseTableButtonActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void paymentCashActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentCashActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_paymentCashActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void paymentCash1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentCash1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_paymentCash1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void cashPaymentNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cashPaymentNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cashPaymentNameActionPerformed

    private void cashPaymentCelphoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cashPaymentCelphoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cashPaymentCelphoneActionPerformed

    
    private void setTableColour(int tableId , Color color) {
        
        switch (tableId) {
            case 1:
                this.tableOne.setBackground(color);
                break;
            case 2:
                this.tableTwo.setBackground(color);
                break;
            case 3:
                this.tableThree.setBackground(color);
                break;
            case 4:
                this.tableFour.setBackground(color);
                break;
            default:
                System.out.println("Error en el cerrado de la mesa");

        }        
    }
    
    
    private void closeGenericFrame(JFrame jframe) {
        jframe.dispatchEvent(new WindowEvent(jframe, WindowEvent.WINDOW_CLOSING));
    }

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
    private javax.swing.JFrame CandyProductsFrame;
    private javax.swing.JButton CloseTableButton;
    private javax.swing.JFrame CloseTableFrame;
    private javax.swing.JFrame DrinkAlcoholFrame;
    private javax.swing.JFrame DrinkNoAlcoholFrame;
    private javax.swing.JLabel MesaLabel;
    private javax.swing.JLabel MozoLabel;
    private javax.swing.JFrame OtraFormaDePagoFrame;
    private javax.swing.JFrame PagoEfectivoFrame;
    private javax.swing.JFrame PagoEfectivoFrame1;
    private javax.swing.JFrame PayTableFrm;
    private javax.swing.JFrame SaladsProductFrame;
    private javax.swing.JButton SaveButton;
    private javax.swing.JFrame SelectOrder;
    private javax.swing.JFrame SuggestPromosFrm;
    private javax.swing.JLabel TotalLabel;
    private javax.swing.JButton acceptNoAlcoholDrinks;
    private javax.swing.JButton cancelNoAlcoholDrinks;
    private javax.swing.JButton candyProducts;
    private javax.swing.JComboBox<String> cashDiscount;
    private javax.swing.JComboBox<String> cashDiscount1;
    private javax.swing.JComboBox<String> cashDiscount2;
    private javax.swing.JButton cashPayment;
    private javax.swing.JTextField cashPaymentCelphone;
    private javax.swing.JTextField cashPaymentDNI;
    private javax.swing.JTextField cashPaymentLastName;
    private javax.swing.JTextField cashPaymentName;
    private javax.swing.JLabel cashSubTotal;
    private javax.swing.JLabel cashSubTotal1;
    private javax.swing.JLabel cashSubTotal2;
    private javax.swing.JCheckBox cepitaCheck;
    private javax.swing.JSpinner cepitaSpinner;
    private javax.swing.JButton closeTable;
    private javax.swing.JCheckBox cocaColaCheck;
    private javax.swing.JCheckBox cocaLightCheck;
    private javax.swing.JSpinner cocaLightSpinner;
    private javax.swing.JSpinner cocaSpinner;
    private javax.swing.JButton deleteTable;
    private javax.swing.JButton drinkAlcohol;
    private javax.swing.JButton drinkNoAlcohol;
    private javax.swing.JLabel efectivoSubTotal2;
    private javax.swing.JLabel efectivoSubTotal3;
    private javax.swing.JLabel efectivoSubTotal4;
    private javax.swing.JLabel efectivoSubTotal5;
    private javax.swing.JLabel efectivoSubTotal6;
    private javax.swing.JComboBox<String> employeeNameCombo;
    private javax.swing.JCheckBox exprimidoCheck;
    private javax.swing.JSpinner exprimidoSpinner;
    private javax.swing.JCheckBox fantaCheck;
    private javax.swing.JSpinner fantaSpinner;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JCheckBox licuadoAguaCheck;
    private javax.swing.JSpinner licuadoAguaSpinner;
    private javax.swing.JCheckBox licuadoLecheCheck;
    private javax.swing.JSpinner licuadoLecheSpinner;
    private javax.swing.JCheckBox limonadaCheck;
    private javax.swing.JCheckBox limonadaElaboradoCheck;
    private javax.swing.JSpinner limonadaSpinner;
    private javax.swing.JSpinner limonada_elaborada_Spinner;
    private javax.swing.JCheckBox manzanaCheck;
    private javax.swing.JSpinner manzanaSpinner;
    private javax.swing.JCheckBox naranjaCheck;
    private javax.swing.JSpinner naranjaSpinner;
    private javax.swing.JButton otraFormaDePago;
    private javax.swing.JButton payAction;
    private javax.swing.JTextField paymentCash;
    private javax.swing.JTextField paymentCash1;
    private javax.swing.JCheckBox pomeloCheck;
    private javax.swing.JSpinner pomeloSpinner;
    private javax.swing.JCheckBox pomeloTorosCheck;
    private javax.swing.JTable productDescriptionTable;
    private javax.swing.JButton promotions;
    private javax.swing.JButton saladProducts;
    private javax.swing.JCheckBox scheweppesPomeloCheck;
    private javax.swing.JSpinner scheweppesPomeloSpinner;
    private javax.swing.JCheckBox scheweppesTonicaCheck;
    private javax.swing.JSpinner scheweppesTonicaSpinner;
    private javax.swing.JButton seeConsuming;
    private javax.swing.JCheckBox shakeChocolateCheck;
    private javax.swing.JSpinner shakeChocolateSpinner;
    private javax.swing.JCheckBox shakeDulceLecheCheck;
    private javax.swing.JSpinner shakeDulceLecheSpinner;
    private javax.swing.JCheckBox spriteCheck;
    private javax.swing.JSpinner spriteSpinner;
    private javax.swing.JCheckBox spriteZeroCheck;
    private javax.swing.JSpinner spriteZeroSpinner;
    private javax.swing.JButton tableFour;
    private javax.swing.JLabel tableIdPayLabel;
    private javax.swing.JButton tableOne;
    private javax.swing.JButton tableThree;
    private javax.swing.JButton tableTwo;
    private javax.swing.JCheckBox tonicaCheck;
    private javax.swing.JSpinner torosPomeloSpinner;
    private javax.swing.JSpinner torosTonicaSpinner;
    private javax.swing.JLabel totalPayLabel;
    private javax.swing.JLabel userTableCompleteNamePay;
    private javax.swing.JCheckBox waterCheck;
    private javax.swing.JCheckBox waterGasCheck;
    private javax.swing.JSpinner waterGasSpinner;
    private javax.swing.JSpinner waterSpinner;
    // End of variables declaration//GEN-END:variables
}
