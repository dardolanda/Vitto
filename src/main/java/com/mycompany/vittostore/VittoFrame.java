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
import com.mycompany.vittostore.generalitems.OperatingTableStateEnum;
import com.mycompany.vittostore.controller.Users;
import com.mycompany.vittostore.user.User;
import com.mycompany.vittostore.dataStore.DataStore;

import com.mycompany.vittostore.controllerImpl.ProductsImpl;
import com.mycompany.vittostore.controllerImpl.UsersImpl;
import com.mycompany.vittostore.dataStore.PaymentDataStore;
import com.mycompany.vittostore.generalitems.AlcoholDrinksEnum;
import com.mycompany.vittostore.generalitems.GenericSelectedComponent;
import com.mycompany.vittostore.generalitems.PaymentMethodsEnum;
import com.mycompany.vittostore.generalitems.ProductTypeEnum;
import com.mycompany.vittostore.generalitems.SweetProductsEnum;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.CheckBox;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

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
    Map<SweetProductsEnum, Map<Integer, Double>> sweetProducts;
    Map<AlcoholDrinksEnum, Map<Integer, Double>> alcoholProducts;
    Map<String, Map<Integer, Double>> products;
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

        List<Map<Integer, String>> operatingTableStateList = this.productsImpl.getOperatingTable();

        for (Map<Integer, String> tableStateMap : operatingTableStateList) {
            for (Map.Entry<Integer, String> entry : tableStateMap.entrySet()) {
                this.setTableColour(
                        entry.getKey(),
                        entry.getValue().equals(OperatingTableStateEnum.USO.toString())
                        ? Color.yellow
                        : Color.RED);
            }
        }

        this.employeeList = this.usersImpl.getUsers();

        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
    }

    /**
     * Carga el mapa selectedComponentMap para tener los componentes activos, de
     * esta manera podemos setearlos desde la BBDD.
     */
    private void initSelectedComponent() {
        /**
         * Bebidas sin alcohol
         */
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.COCA_COLA.name(), new GenericSelectedComponent(this.cocaColaCheck, this.cocaSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.COCA_LIGHT.name(), new GenericSelectedComponent(this.cocaLightCheck, this.cocaLightSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.SPRITE.name(), new GenericSelectedComponent(this.spriteCheck, this.spriteSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.SPRITE_ZERO.name(), new GenericSelectedComponent(this.spriteZeroCheck, this.spriteZeroSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.PASO_TOROS_POMELO.name(), new GenericSelectedComponent(this.pomeloTorosCheck, this.torosPomeloSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.PASO_TOROS_TONICA.name(), new GenericSelectedComponent(this.tonicaCheck, this.torosTonicaSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.FANTA.name(), new GenericSelectedComponent(this.fantaCheck, this.fantaSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.LEVITE_LIMONADA.name(), new GenericSelectedComponent(this.limonadaCheck, this.limonadaSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.LEVITE_MANZANA.name(), new GenericSelectedComponent(this.manzanaCheck, this.manzanaSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.LEVITE_NARANJA.name(), new GenericSelectedComponent(this.naranjaCheck, this.naranjaSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.LEVITE_POMELO.name(), new GenericSelectedComponent(this.pomeloCheck, this.pomeloSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.CEPITA.name(), new GenericSelectedComponent(this.cepitaCheck, this.cepitaSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.SCHEWEPPES_POMELO.name(), new GenericSelectedComponent(this.scheweppesPomeloCheck, this.scheweppesPomeloSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.SCHEWEPPES_TONICA.name(), new GenericSelectedComponent(this.scheweppesTonicaCheck, this.scheweppesTonicaSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.AGUA.name(), new GenericSelectedComponent(this.waterCheck, this.waterSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.AGUA_GAS.name(), new GenericSelectedComponent(this.waterGasCheck, this.waterGasSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.EXPRIMIDO.name(), new GenericSelectedComponent(this.exprimidoCheck, this.exprimidoSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.LICUADO_LECHE.name(), new GenericSelectedComponent(this.licuadoLecheCheck, this.licuadoLecheSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.LICUADO_AGUA.name(), new GenericSelectedComponent(this.licuadoAguaCheck, this.licuadoAguaSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.LIMONADA.name(), new GenericSelectedComponent(this.limonadaElaboradoCheck, this.limonada_elaborada_Spinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.MILK_SHAKE_CHOCO.name(), new GenericSelectedComponent(this.shakeChocolateCheck, this.shakeChocolateSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.MILK_SHAKE_DULCE_LECHE.name(), new GenericSelectedComponent(this.shakeDulceLecheCheck, this.shakeDulceLecheSpinner));

        /**
         * Productos Dulces
         */
        this.selectedComponentMap.put(SweetProductsEnum.ALFAJOR.name(), new GenericSelectedComponent((this.alfajorCheck), this.alfajorSpinner));
        this.selectedComponentMap.put(SweetProductsEnum.ALFAJOR_ARTESANAL.name(), new GenericSelectedComponent((this.alfajorArtesanalCheck), this.alfajorArtesanalSpinner));
        this.selectedComponentMap.put(SweetProductsEnum.CUADRADO_SECO.name(), new GenericSelectedComponent((this.cuadradoSecoCheck), this.cuadradoSecoSpinner));
        this.selectedComponentMap.put(SweetProductsEnum.ENSALADA_FRUTAS.name(), new GenericSelectedComponent((this.ensaladaDeFrutasCheck), this.ensaladaFrutasSpinner));
        this.selectedComponentMap.put(SweetProductsEnum.FRA_NUI.name(), new GenericSelectedComponent((this.fraNuiCheck), this.franuiSpinner));
        this.selectedComponentMap.put(SweetProductsEnum.FRUTA_ELECCION.name(), new GenericSelectedComponent((this.frutaEleccionCheck), this.frutaEleccionSpinner));
        this.selectedComponentMap.put(SweetProductsEnum.MEDIALUNA.name(), new GenericSelectedComponent((this.medialunaCheck), this.medialunaSpinner));
        this.selectedComponentMap.put(SweetProductsEnum.TORTA_INDIVIDUAL.name(), new GenericSelectedComponent((this.alfajorCheck), this.alfajorSpinner));
        this.selectedComponentMap.put(SweetProductsEnum.TOSTADAS.name(), new GenericSelectedComponent((this.tostadasCheck), this.tostadasSpinner));

        /**
         * Bebidas alcoholicas.
         */
        this.selectedComponentMap.put(AlcoholDrinksEnum.CERVEZA.name(), new GenericSelectedComponent(this.cervezaCheck, this.cervezaSpinner));

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
        SelectOrder.setSize(700, 600);
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
        whoAmILbl = new javax.swing.JLabel();
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
        jLabel58 = new javax.swing.JLabel();
        cervezaCheck = new javax.swing.JCheckBox();
        cervezaPrice = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        saverAlcoholOrder = new javax.swing.JButton();
        alcoholDrinkCancel = new javax.swing.JButton();
        cervezaSpinner = new javax.swing.JSpinner();
        CandyProductsFrame = new javax.swing.JFrame();
        jLabel56 = new javax.swing.JLabel();
        tortaIndividualCheck = new javax.swing.JCheckBox();
        jLabel70 = new javax.swing.JLabel();
        tortaIndividualSpinner = new javax.swing.JSpinner();
        tortaIndividualPrice = new javax.swing.JLabel();
        cuadradoSecoCheck = new javax.swing.JCheckBox();
        jLabel74 = new javax.swing.JLabel();
        cuadradoSecoSpinner = new javax.swing.JSpinner();
        cuadradoSecoPrice = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        alfajorSpinner = new javax.swing.JSpinner();
        alfajorPrice = new javax.swing.JLabel();
        alfajorCheck = new javax.swing.JCheckBox();
        tostadasCheck = new javax.swing.JCheckBox();
        jLabel80 = new javax.swing.JLabel();
        tostadasSpinner = new javax.swing.JSpinner();
        tostadasPrice = new javax.swing.JLabel();
        alfajorArtesanalCheck = new javax.swing.JCheckBox();
        alfajorArtesanalSpinner = new javax.swing.JSpinner();
        medialunaCheck = new javax.swing.JCheckBox();
        medialunaSpinner = new javax.swing.JSpinner();
        fraNuiCheck = new javax.swing.JCheckBox();
        jLabel59 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        ensaladaDeFrutasCheck = new javax.swing.JCheckBox();
        frutaEleccionCheck = new javax.swing.JCheckBox();
        franuiSpinner = new javax.swing.JSpinner();
        ensaladaFrutasSpinner = new javax.swing.JSpinner();
        frutaEleccionSpinner = new javax.swing.JSpinner();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        alfajorArtesanalPrice = new javax.swing.JLabel();
        medialunaPrice = new javax.swing.JLabel();
        fraNuiPrice = new javax.swing.JLabel();
        ensaladaFrutasPrice = new javax.swing.JLabel();
        frutaEleccionPrice = new javax.swing.JLabel();
        GuardarProductosDulces = new javax.swing.JButton();
        cancelCandyProducts = new javax.swing.JButton();
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
        cashTotalPay = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        cashChangeBack = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        cashPayDiscountCheck = new javax.swing.JCheckBox();
        cashPayAction = new javax.swing.JButton();
        paymentCash = new javax.swing.JTextField();
        cashDiscountCombo = new javax.swing.JComboBox<>();
        cashCalculateTotal = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        OtraFormaDePagoFrame = new javax.swing.JFrame();
        otherPaymentDiscountCombo = new javax.swing.JComboBox<>();
        jLabel37 = new javax.swing.JLabel();
        otherPaymentCalculate = new javax.swing.JButton();
        otherPaymentTotal = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        otherPaymentSubTotal = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        otherPaymentDiscountCheck = new javax.swing.JCheckBox();
        jLabel45 = new javax.swing.JLabel();
        otherPaymentPayAction = new javax.swing.JButton();
        otherPaymentMethodCombo = new javax.swing.JComboBox<>();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel36 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        otherPaymentLastName = new javax.swing.JTextField();
        otherPaymentName = new javax.swing.JTextField();
        otherPaymentDNI = new javax.swing.JTextField();
        otherPaymentCelphone = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        CloseDayFrame = new javax.swing.JFrame();
        DeleteTableFrm = new javax.swing.JFrame();
        jLabel60 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        AdminUserName = new javax.swing.JTextField();
        jLabel73 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        AdminUserPassword = new javax.swing.JPasswordField();
        jLabel75 = new javax.swing.JLabel();
        tableOne = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        tableTwo = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        tableThree = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        tableFour = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel28 = new javax.swing.JLabel();
        tableFive = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        tableSix = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        tableSeven = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        tableEight = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        tableNine = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        tableTen = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        tableEleven = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        tableTwelve = new javax.swing.JButton();
        jLabel51 = new javax.swing.JLabel();
        CloseDayAction = new javax.swing.JButton();
        jLabel52 = new javax.swing.JLabel();
        tableFourteen = new javax.swing.JButton();
        jLabel53 = new javax.swing.JLabel();
        tableFifteen = new javax.swing.JButton();
        jLabel54 = new javax.swing.JLabel();
        tableSixTeen = new javax.swing.JButton();
        jLabel55 = new javax.swing.JLabel();
        tableThirteen = new javax.swing.JButton();
        takeAway = new javax.swing.JButton();

        employeeNameCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeNameComboActionPerformed(evt);
            }
        });

        whoAmILbl.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        whoAmILbl.setText("Quién sos ?");

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
                        .addGap(0, 32, Short.MAX_VALUE))
                    .addGroup(SelectOrderLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SelectOrderLayout.createSequentialGroup()
                                .addComponent(jLabel50)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(SelectOrderLayout.createSequentialGroup()
                                .addComponent(seeConsuming)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(closeTable)
                                .addGap(50, 50, 50)
                                .addComponent(payAction, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addComponent(deleteTable, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)))))
                .addContainerGap())
            .addGroup(SelectOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addContainerGap())
            .addGroup(SelectOrderLayout.createSequentialGroup()
                .addGap(302, 302, 302)
                .addComponent(whoAmILbl)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        SelectOrderLayout.setVerticalGroup(
            SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SelectOrderLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(whoAmILbl)
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
                .addGap(33, 33, 33)
                .addGroup(SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(seeConsuming)
                    .addComponent(closeTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteTable)
                    .addComponent(payAction))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel40)
                .addContainerGap())
        );

        jLabel6.setText("Línea Gaseosas");

        jLabel7.setText("Línea Elaborados");

        acceptNoAlcoholDrinks.setText("Guardar Pedido");
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
                                        .addGap(68, 68, 68)
                                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                .addComponent(licuadoAguaCheck)
                                                .addGap(18, 18, 18)
                                                .addComponent(licuadoAguaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                .addComponent(licuadoLecheCheck)
                                                .addGap(18, 18, 18)
                                                .addComponent(licuadoLecheSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(scheweppesPomeloCheck)
                                            .addComponent(scheweppesTonicaCheck))
                                        .addGap(18, 18, 18)
                                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(scheweppesTonicaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(scheweppesPomeloSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 63, Short.MAX_VALUE))
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
            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(acceptNoAlcoholDrinks, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cancelNoAlcoholDrinks)
                .addContainerGap())
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
                        .addGap(46, 46, 46))
                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(scheweppesPomeloSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(scheweppesPomeloCheck))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(scheweppesTonicaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(scheweppesTonicaCheck))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelNoAlcoholDrinks)
                    .addComponent(acceptNoAlcoholDrinks))
                .addContainerGap())
        );

        jLabel58.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel58.setText("Bebidas con Alcohol");

        cervezaCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cervezaCheck.setText("Cerveza 500 ml");
        cervezaCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cervezaCheckActionPerformed(evt);
            }
        });

        cervezaPrice.setText("175");

        jLabel69.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel69.setText("$");

        saverAlcoholOrder.setText("Guardar Orden");
        saverAlcoholOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saverAlcoholOrderActionPerformed(evt);
            }
        });

        alcoholDrinkCancel.setText("Cancelar");
        alcoholDrinkCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alcoholDrinkCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DrinkAlcoholFrameLayout = new javax.swing.GroupLayout(DrinkAlcoholFrame.getContentPane());
        DrinkAlcoholFrame.getContentPane().setLayout(DrinkAlcoholFrameLayout);
        DrinkAlcoholFrameLayout.setHorizontalGroup(
            DrinkAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DrinkAlcoholFrameLayout.createSequentialGroup()
                .addGroup(DrinkAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DrinkAlcoholFrameLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(saverAlcoholOrder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(alcoholDrinkCancel))
                    .addGroup(DrinkAlcoholFrameLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(DrinkAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel58)
                            .addGroup(DrinkAlcoholFrameLayout.createSequentialGroup()
                                .addComponent(cervezaCheck)
                                .addGap(33, 33, 33)
                                .addComponent(cervezaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel69)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cervezaPrice)))
                        .addGap(0, 254, Short.MAX_VALUE)))
                .addContainerGap())
        );
        DrinkAlcoholFrameLayout.setVerticalGroup(
            DrinkAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DrinkAlcoholFrameLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel58)
                .addGap(36, 36, 36)
                .addGroup(DrinkAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cervezaCheck)
                    .addComponent(cervezaPrice)
                    .addComponent(jLabel69)
                    .addComponent(cervezaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addGroup(DrinkAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saverAlcoholOrder)
                    .addComponent(alcoholDrinkCancel))
                .addContainerGap())
        );

        jLabel56.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel56.setText("Dulces");

        tortaIndividualCheck.setText("Torta Individual");
        tortaIndividualCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tortaIndividualCheckActionPerformed(evt);
            }
        });

        jLabel70.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel70.setText("(Chocotorta, oreo, cheescake, manzana)");

        tortaIndividualPrice.setText("200");

        cuadradoSecoCheck.setText("Cuadrado Seco");
        cuadradoSecoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cuadradoSecoCheckActionPerformed(evt);
            }
        });

        jLabel74.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel74.setText("(Brownie, pastafrola batata o membrillo)");

        cuadradoSecoPrice.setText("140");

        jLabel57.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel57.setText("(Chocolate, chocolate blanco, maicena)");

        alfajorPrice.setText("60");

        alfajorCheck.setText("Alfajor");
        alfajorCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alfajorCheckActionPerformed(evt);
            }
        });

        tostadasCheck.setText("Tostadas ");
        tostadasCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tostadasCheckActionPerformed(evt);
            }
        });

        jLabel80.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel80.setText("(Con queso crema y dulce de leche a elección)");

        tostadasPrice.setText("175");

        alfajorArtesanalCheck.setText("Alfajor Artesanal");
        alfajorArtesanalCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alfajorArtesanalCheckActionPerformed(evt);
            }
        });

        medialunaCheck.setText("Medialuna c/u");
        medialunaCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                medialunaCheckActionPerformed(evt);
            }
        });

        fraNuiCheck.setText("Fra - nui");
        fraNuiCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fraNuiCheckActionPerformed(evt);
            }
        });

        jLabel59.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel59.setText("$");

        jLabel61.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel61.setText("$");

        jLabel62.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel62.setText("$");

        jLabel63.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel63.setText("$");

        ensaladaDeFrutasCheck.setText("Ensalada de Frutas");
        ensaladaDeFrutasCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ensaladaDeFrutasCheckActionPerformed(evt);
            }
        });

        frutaEleccionCheck.setText("Fruta a Elección");
        frutaEleccionCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                frutaEleccionCheckActionPerformed(evt);
            }
        });

        jLabel64.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel64.setText("$");

        jLabel65.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel65.setText("$");

        jLabel66.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel66.setText("$");

        jLabel67.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel67.setText("$");

        jLabel68.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel68.setText("$");

        alfajorArtesanalPrice.setText("90");

        medialunaPrice.setText("45");

        fraNuiPrice.setText("200");

        ensaladaFrutasPrice.setText("90");

        frutaEleccionPrice.setText("45");

        GuardarProductosDulces.setText("Guardar Orden");
        GuardarProductosDulces.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarProductosDulcesActionPerformed(evt);
            }
        });

        cancelCandyProducts.setText("Cancelar");
        cancelCandyProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelCandyProductsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CandyProductsFrameLayout = new javax.swing.GroupLayout(CandyProductsFrame.getContentPane());
        CandyProductsFrame.getContentPane().setLayout(CandyProductsFrameLayout);
        CandyProductsFrameLayout.setHorizontalGroup(
            CandyProductsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CandyProductsFrameLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(CandyProductsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CandyProductsFrameLayout.createSequentialGroup()
                        .addGroup(CandyProductsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CandyProductsFrameLayout.createSequentialGroup()
                                .addComponent(jLabel56)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(CandyProductsFrameLayout.createSequentialGroup()
                                .addGroup(CandyProductsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(CandyProductsFrameLayout.createSequentialGroup()
                                        .addGroup(CandyProductsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cuadradoSecoCheck)
                                            .addComponent(alfajorCheck))
                                        .addGap(29, 29, 29))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CandyProductsFrameLayout.createSequentialGroup()
                                        .addComponent(tostadasCheck)
                                        .addGap(66, 66, 66)))
                                .addGroup(CandyProductsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel57)
                                    .addComponent(jLabel74)
                                    .addComponent(jLabel80))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(CandyProductsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(CandyProductsFrameLayout.createSequentialGroup()
                                        .addComponent(jLabel62)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tostadasPrice))
                                    .addComponent(cuadradoSecoPrice)
                                    .addGroup(CandyProductsFrameLayout.createSequentialGroup()
                                        .addComponent(jLabel63)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(alfajorPrice))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CandyProductsFrameLayout.createSequentialGroup()
                                .addComponent(tortaIndividualCheck)
                                .addGap(26, 26, 26)
                                .addComponent(jLabel70)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                                .addGroup(CandyProductsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(CandyProductsFrameLayout.createSequentialGroup()
                                        .addComponent(cuadradoSecoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel61)
                                        .addGap(30, 30, 30))
                                    .addGroup(CandyProductsFrameLayout.createSequentialGroup()
                                        .addComponent(tortaIndividualSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(11, 11, 11)
                                        .addComponent(jLabel59)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tortaIndividualPrice))
                                    .addComponent(alfajorSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tostadasSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CandyProductsFrameLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(GuardarProductosDulces)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cancelCandyProducts)))
                        .addGap(20, 20, 20))
                    .addGroup(CandyProductsFrameLayout.createSequentialGroup()
                        .addGroup(CandyProductsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(CandyProductsFrameLayout.createSequentialGroup()
                                .addComponent(frutaEleccionCheck)
                                .addGap(66, 66, 66)
                                .addComponent(frutaEleccionSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(CandyProductsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(CandyProductsFrameLayout.createSequentialGroup()
                                    .addComponent(ensaladaDeFrutasCheck)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ensaladaFrutasSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, CandyProductsFrameLayout.createSequentialGroup()
                                    .addComponent(fraNuiCheck)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(franuiSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, CandyProductsFrameLayout.createSequentialGroup()
                                    .addGroup(CandyProductsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(alfajorArtesanalCheck)
                                        .addComponent(medialunaCheck))
                                    .addGap(59, 59, 59)
                                    .addGroup(CandyProductsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(alfajorArtesanalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(medialunaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(37, 37, 37)
                        .addGroup(CandyProductsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CandyProductsFrameLayout.createSequentialGroup()
                                .addComponent(jLabel64)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(alfajorArtesanalPrice))
                            .addGroup(CandyProductsFrameLayout.createSequentialGroup()
                                .addComponent(jLabel65)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(medialunaPrice))
                            .addGroup(CandyProductsFrameLayout.createSequentialGroup()
                                .addComponent(jLabel66)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fraNuiPrice))
                            .addGroup(CandyProductsFrameLayout.createSequentialGroup()
                                .addComponent(jLabel67)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ensaladaFrutasPrice))
                            .addGroup(CandyProductsFrameLayout.createSequentialGroup()
                                .addComponent(jLabel68)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(frutaEleccionPrice)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        CandyProductsFrameLayout.setVerticalGroup(
            CandyProductsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CandyProductsFrameLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel56)
                .addGap(18, 18, 18)
                .addGroup(CandyProductsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel70)
                    .addComponent(tortaIndividualCheck)
                    .addComponent(tortaIndividualPrice)
                    .addComponent(tortaIndividualSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel59))
                .addGap(21, 21, 21)
                .addGroup(CandyProductsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel74)
                    .addComponent(cuadradoSecoCheck)
                    .addComponent(cuadradoSecoPrice)
                    .addComponent(cuadradoSecoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel61))
                .addGap(18, 18, 18)
                .addGroup(CandyProductsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(alfajorSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(alfajorPrice)
                    .addComponent(alfajorCheck)
                    .addComponent(jLabel57)
                    .addComponent(jLabel63))
                .addGap(18, 18, 18)
                .addGroup(CandyProductsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tostadasSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tostadasPrice)
                    .addComponent(jLabel80)
                    .addComponent(tostadasCheck)
                    .addComponent(jLabel62))
                .addGap(66, 66, 66)
                .addGroup(CandyProductsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(alfajorArtesanalCheck)
                    .addComponent(alfajorArtesanalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel64)
                    .addComponent(alfajorArtesanalPrice))
                .addGap(8, 8, 8)
                .addGroup(CandyProductsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(medialunaCheck)
                    .addComponent(medialunaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel65)
                    .addComponent(medialunaPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CandyProductsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fraNuiCheck)
                    .addComponent(franuiSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel66)
                    .addComponent(fraNuiPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(CandyProductsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ensaladaDeFrutasCheck)
                    .addComponent(ensaladaFrutasSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel67)
                    .addComponent(ensaladaFrutasPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(CandyProductsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(frutaEleccionCheck)
                    .addComponent(frutaEleccionSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel68)
                    .addComponent(frutaEleccionPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(CandyProductsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GuardarProductosDulces)
                    .addComponent(cancelCandyProducts))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout SaladsProductFrameLayout = new javax.swing.GroupLayout(SaladsProductFrame.getContentPane());
        SaladsProductFrame.getContentPane().setLayout(SaladsProductFrameLayout);
        SaladsProductFrameLayout.setHorizontalGroup(
            SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 619, Short.MAX_VALUE)
        );
        SaladsProductFrameLayout.setVerticalGroup(
            SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 344, Short.MAX_VALUE)
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
        cashPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cashPaymentActionPerformed(evt);
            }
        });

        otraFormaDePago.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        otraFormaDePago.setText("Otros");
        otraFormaDePago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otraFormaDePagoActionPerformed(evt);
            }
        });

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
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PayTableFrmLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(PayTableFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addGroup(PayTableFrmLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(tableIdPayLabel)
                                .addGap(42, 42, 42)
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(userTableCompleteNamePay)
                                .addGap(32, 32, 32)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(totalPayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(14, 14, 14))
            .addGroup(PayTableFrmLayout.createSequentialGroup()
                .addGap(186, 186, 186)
                .addGroup(PayTableFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PayTableFrmLayout.createSequentialGroup()
                        .addComponent(cashPayment)
                        .addGap(18, 18, 18)
                        .addComponent(otraFormaDePago, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PayTableFrmLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabel15)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PayTableFrmLayout.setVerticalGroup(
            PayTableFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PayTableFrmLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PayTableFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(totalPayLabel)
                    .addComponent(jLabel12)
                    .addComponent(tableIdPayLabel)
                    .addComponent(jLabel13)
                    .addComponent(userTableCompleteNamePay))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PayTableFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(otraFormaDePago)
                    .addComponent(cashPayment))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jLabel16.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel16.setText("Pago Efectivo");

        jLabel17.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel17.setText(" Subtotal a pagar");

        cashSubTotal.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cashSubTotal.setText("total");

        jLabel19.setText("$");

        jLabel18.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel18.setText(" Efectivo recibido");

        jLabel20.setText("$");

        jLabel21.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel21.setText(" Total a pagar");

        cashTotalPay.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N

        jLabel22.setText("$");

        jLabel23.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel23.setText(" Vuelto");

        cashChangeBack.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cashChangeBack.setToolTipText("");

        jLabel24.setText("$");

        cashPayDiscountCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cashPayDiscountCheck.setText("Aplica Descuento");
        cashPayDiscountCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cashPayDiscountCheckActionPerformed(evt);
            }
        });

        cashPayAction.setText("Pagar");
        cashPayAction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cashPayActionActionPerformed(evt);
            }
        });

        paymentCash.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentCashActionPerformed(evt);
            }
        });

        cashDiscountCombo.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cashDiscountCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5%", "10%", "15%", "20%", "25%", "30%", "50%", "100%" }));

        cashCalculateTotal.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cashCalculateTotal.setText("Calcular");
        cashCalculateTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cashCalculateTotalActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel25.setText(" Calcular Monto");

        javax.swing.GroupLayout PagoEfectivoFrameLayout = new javax.swing.GroupLayout(PagoEfectivoFrame.getContentPane());
        PagoEfectivoFrame.getContentPane().setLayout(PagoEfectivoFrameLayout);
        PagoEfectivoFrameLayout.setHorizontalGroup(
            PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PagoEfectivoFrameLayout.createSequentialGroup()
                .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PagoEfectivoFrameLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PagoEfectivoFrameLayout.createSequentialGroup()
                                .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PagoEfectivoFrameLayout.createSequentialGroup()
                                            .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(PagoEfectivoFrameLayout.createSequentialGroup()
                                                    .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(cashPayDiscountCheck)
                                                        .addComponent(jLabel25))
                                                    .addGap(38, 38, 38))
                                                .addGroup(PagoEfectivoFrameLayout.createSequentialGroup()
                                                    .addComponent(jLabel18)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                            .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(paymentCash, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(cashDiscountCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(cashCalculateTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PagoEfectivoFrameLayout.createSequentialGroup()
                                            .addComponent(jLabel17)
                                            .addGap(48, 48, 48)
                                            .addComponent(jLabel19)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(cashSubTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PagoEfectivoFrameLayout.createSequentialGroup()
                                        .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel21)
                                            .addComponent(jLabel23))
                                        .addGap(77, 77, 77)
                                        .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 12, Short.MAX_VALUE)
                                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(cashChangeBack, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                                            .addComponent(cashTotalPay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(0, 10, Short.MAX_VALUE))))
                    .addGroup(PagoEfectivoFrameLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cashPayAction)))
                .addGap(19, 19, 19))
            .addGroup(PagoEfectivoFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(cashPayDiscountCheck)
                    .addComponent(cashDiscountCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(cashCalculateTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(cashTotalPay, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addGap(17, 17, 17)
                .addGroup(PagoEfectivoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel23)
                    .addComponent(cashChangeBack, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(cashPayAction)
                .addContainerGap())
        );

        otherPaymentDiscountCombo.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        otherPaymentDiscountCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5%", "10%", "15%", "20%", "25%", "30%", "50%", "100%" }));

        jLabel37.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel37.setText("Total a pagar");

        otherPaymentCalculate.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        otherPaymentCalculate.setText("Calcular");
        otherPaymentCalculate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherPaymentCalculateActionPerformed(evt);
            }
        });

        otherPaymentTotal.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        otherPaymentTotal.setText("total");

        jLabel38.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel38.setText("  Calcular Monto");

        jLabel39.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel39.setText("$");

        jLabel41.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel41.setText("Otro medio de Pago:");

        jLabel42.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel42.setText("  Subtotal a pagar");

        otherPaymentSubTotal.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        otherPaymentSubTotal.setText("total");

        jLabel44.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel44.setText("$");

        otherPaymentDiscountCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        otherPaymentDiscountCheck.setText("Aplica Descuento");
        otherPaymentDiscountCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherPaymentDiscountCheckActionPerformed(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel45.setText("  Medio de Pago");

        otherPaymentPayAction.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        otherPaymentPayAction.setText("Pagar");
        otherPaymentPayAction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherPaymentPayActionActionPerformed(evt);
            }
        });

        otherPaymentMethodCombo.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        otherPaymentMethodCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "debito", "credito", "cuenta_corriente", "mercado_pago" }));

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

        otherPaymentName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherPaymentNameActionPerformed(evt);
            }
        });

        otherPaymentCelphone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherPaymentCelphoneActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jLabel4.setText("campo obligatorio");

        jLabel26.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jLabel26.setText("campo obligatorio");

        jLabel27.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jLabel27.setText("campo obligatorio");

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
                            .addComponent(otherPaymentDiscountCheck))
                        .addGap(43, 43, 43)
                        .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(otherPaymentDiscountCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(OtraFormaDePagoFrameLayout.createSequentialGroup()
                                .addComponent(jLabel44)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(otherPaymentSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(otherPaymentMethodCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(otherPaymentCalculate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(OtraFormaDePagoFrameLayout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(OtraFormaDePagoFrameLayout.createSequentialGroup()
                                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(otherPaymentTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(otherPaymentPayAction))
                        .addGap(1, 1, 1))
                    .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(OtraFormaDePagoFrameLayout.createSequentialGroup()
                            .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(OtraFormaDePagoFrameLayout.createSequentialGroup()
                                    .addComponent(jLabel47)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel27))
                                .addGroup(OtraFormaDePagoFrameLayout.createSequentialGroup()
                                    .addComponent(jLabel46)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel4))
                                .addGroup(OtraFormaDePagoFrameLayout.createSequentialGroup()
                                    .addComponent(jLabel48)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel26))
                                .addComponent(jLabel49))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(otherPaymentDNI)
                                    .addComponent(otherPaymentCelphone, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(otherPaymentLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(otherPaymentName, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                            .addComponent(otherPaymentSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel44))
                        .addGap(25, 25, 25))
                    .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(otherPaymentMethodCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel45)))
                .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(otherPaymentDiscountCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(otherPaymentDiscountCheck))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(otherPaymentCalculate))
                .addGap(27, 27, 27)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(otherPaymentName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(otherPaymentLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(otherPaymentDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(otherPaymentCelphone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(OtraFormaDePagoFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(otherPaymentTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(otherPaymentPayAction)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout CloseDayFrameLayout = new javax.swing.GroupLayout(CloseDayFrame.getContentPane());
        CloseDayFrame.getContentPane().setLayout(CloseDayFrameLayout);
        CloseDayFrameLayout.setHorizontalGroup(
            CloseDayFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 712, Short.MAX_VALUE)
        );
        CloseDayFrameLayout.setVerticalGroup(
            CloseDayFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 196, Short.MAX_VALUE)
        );

        jLabel60.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel60.setText("Eliminar Mesa Nº: ");

        jLabel72.setText("Mesa");

        jLabel71.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel71.setText("Requiere Permisos de administrador");

        jLabel73.setText("Usuario");

        jButton1.setText("Eliminar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        AdminUserPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdminUserPasswordActionPerformed(evt);
            }
        });

        jLabel75.setText("Contraseña");

        javax.swing.GroupLayout DeleteTableFrmLayout = new javax.swing.GroupLayout(DeleteTableFrm.getContentPane());
        DeleteTableFrm.getContentPane().setLayout(DeleteTableFrmLayout);
        DeleteTableFrmLayout.setHorizontalGroup(
            DeleteTableFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DeleteTableFrmLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DeleteTableFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DeleteTableFrmLayout.createSequentialGroup()
                        .addComponent(jLabel60)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel72)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                        .addComponent(jLabel71))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DeleteTableFrmLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DeleteTableFrmLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(DeleteTableFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel73)
                    .addComponent(jLabel75))
                .addGap(33, 33, 33)
                .addGroup(DeleteTableFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(AdminUserPassword)
                    .addComponent(AdminUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(84, 84, 84))
        );
        DeleteTableFrmLayout.setVerticalGroup(
            DeleteTableFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DeleteTableFrmLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DeleteTableFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(DeleteTableFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel60)
                        .addComponent(jLabel72))
                    .addComponent(jLabel71, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(DeleteTableFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(DeleteTableFrmLayout.createSequentialGroup()
                        .addGroup(DeleteTableFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AdminUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel73))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AdminUserPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel75))
                .addGap(26, 26, 26)
                .addComponent(jButton1)
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

        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel28.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel28.setText("Mesas Operativas");

        tableFive.setBackground(new java.awt.Color(51, 204, 0));
        tableFive.setText("5");
        tableFive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableFiveActionPerformed(evt);
            }
        });

        jLabel29.setText("Mesa");

        tableSix.setBackground(new java.awt.Color(51, 204, 0));
        tableSix.setText("6");
        tableSix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableSixActionPerformed(evt);
            }
        });

        jLabel30.setText("Mesa");

        tableSeven.setBackground(new java.awt.Color(51, 204, 0));
        tableSeven.setText("7");
        tableSeven.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableSevenActionPerformed(evt);
            }
        });

        jLabel31.setText("Mesa");

        tableEight.setBackground(new java.awt.Color(51, 204, 0));
        tableEight.setText("8");
        tableEight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableEightActionPerformed(evt);
            }
        });

        jLabel32.setText("Mesa");

        tableNine.setBackground(new java.awt.Color(51, 204, 0));
        tableNine.setText("9");
        tableNine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableNineActionPerformed(evt);
            }
        });

        jLabel33.setText("Mesa");

        tableTen.setBackground(new java.awt.Color(51, 204, 0));
        tableTen.setText("10");
        tableTen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableTenActionPerformed(evt);
            }
        });

        jLabel34.setText("Mesa");

        tableEleven.setBackground(new java.awt.Color(51, 204, 0));
        tableEleven.setText("11");
        tableEleven.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableElevenActionPerformed(evt);
            }
        });

        jLabel35.setText("Mesa");

        tableTwelve.setBackground(new java.awt.Color(51, 204, 0));
        tableTwelve.setText("12");
        tableTwelve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableTwelveActionPerformed(evt);
            }
        });

        jLabel51.setText("Mesa");

        CloseDayAction.setBackground(new java.awt.Color(153, 153, 255));
        CloseDayAction.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        CloseDayAction.setText("Cierre Diario");
        CloseDayAction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseDayActionActionPerformed(evt);
            }
        });

        jLabel52.setText("Mesa");

        tableFourteen.setBackground(new java.awt.Color(51, 204, 0));
        tableFourteen.setText("14");
        tableFourteen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableFourteenActionPerformed(evt);
            }
        });

        jLabel53.setText("Mesa");

        tableFifteen.setBackground(new java.awt.Color(51, 204, 0));
        tableFifteen.setText("15");
        tableFifteen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableFifteenActionPerformed(evt);
            }
        });

        jLabel54.setText("Mesa");

        tableSixTeen.setBackground(new java.awt.Color(51, 204, 0));
        tableSixTeen.setText("16");
        tableSixTeen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableSixTeenActionPerformed(evt);
            }
        });

        jLabel55.setText("Mesa");

        tableThirteen.setBackground(new java.awt.Color(51, 204, 0));
        tableThirteen.setText("13");
        tableThirteen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableThirteenActionPerformed(evt);
            }
        });

        takeAway.setBackground(new java.awt.Color(153, 153, 255));
        takeAway.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        takeAway.setText("Take Away");
        takeAway.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                takeAwayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(CloseDayAction, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(takeAway, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(tableFive, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(19, 19, 19)
                                    .addComponent(jLabel29)))
                            .addGap(28, 28, 28)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(tableSix, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(19, 19, 19)
                                    .addComponent(jLabel30)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(47, 47, 47)
                                    .addComponent(jLabel31)
                                    .addGap(51, 51, 51)
                                    .addComponent(jLabel32))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(26, 26, 26)
                                    .addComponent(tableSeven, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(tableEight, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createSequentialGroup()
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
                                    .addComponent(tableFour, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(tableNine, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(19, 19, 19)
                                    .addComponent(jLabel33)))
                            .addGap(28, 28, 28)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(tableTen, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(19, 19, 19)
                                    .addComponent(jLabel34)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(47, 47, 47)
                                    .addComponent(jLabel35)
                                    .addGap(51, 51, 51)
                                    .addComponent(jLabel51))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(26, 26, 26)
                                    .addComponent(tableEleven, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(tableTwelve, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(19, 19, 19)
                                    .addComponent(jLabel52))
                                .addComponent(tableThirteen, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(28, 28, 28)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(tableFourteen, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(19, 19, 19)
                                    .addComponent(jLabel53)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(47, 47, 47)
                                    .addComponent(jLabel54)
                                    .addGap(51, 51, 51)
                                    .addComponent(jLabel55))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(26, 26, 26)
                                    .addComponent(tableFifteen, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(tableSixTeen, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jLabel28))
                .addGap(66, 66, 66))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel28)
                        .addGap(18, 18, 18)
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
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tableSix, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tableSeven, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tableFive, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tableEight, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tableTen, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tableEleven, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tableNine, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tableTwelve, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tableFourteen, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tableFifteen, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tableThirteen, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(65, 65, 65))
                            .addComponent(tableSixTeen, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 38, Short.MAX_VALUE))
                    .addComponent(jSeparator7)
                    .addComponent(jSeparator6))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(takeAway, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86)
                .addComponent(CloseDayAction, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(163, 163, 163))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tableOneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableOneActionPerformed
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

        System.out.println("tableUser --> " + this.tableUser.getNombre() + "- - - " + this.tableUser.getApellido());

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

        /**
         * Aseguramos de traer solo los tipos de prouductos que especifica el
         * actionPerformed.
         */
        List<Product> selectedTableProducts = this.productsImpl.findTableSelectedProducts(this.selectedTable.getId(), ProductTypeEnum.BEBIDAS_SIN_ALCOHOL);

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
             *
             * Iteración de componentes dentro de un frame
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
        CandyProductsFrame.setSize(650, 650);
        CandyProductsFrame.setVisible(true);

        System.out.println("PRODUCTOS DULECES:");
        System.out.println("---- INIT: Table selected ----");
        System.out.println(this.selectedTable.getState());
        System.out.println(this.selectedTable.getId());
        System.out.println("---- END: Table Selected ----");

        List<Product> selectedTableProducts = this.productsImpl.findTableSelectedProducts(this.selectedTable.getId(), ProductTypeEnum.DULCES);

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
             *
             * Iteración de componentes dentro de un frame
             */
            for (Component component : CandyProductsFrame.getContentPane().getComponents()) {
                System.out.println("candy products frame iteration");

                if (component instanceof JCheckBox) {
                    ((JCheckBox) component).setSelected(false);
                }

                if (component instanceof JSpinner) {

                    System.out.println("candy products ");
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

    }//GEN-LAST:event_candyProductsActionPerformed

    private void promotionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_promotionsActionPerformed
        // TODO add your handling code here:
        SuggestPromosFrm.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        SuggestPromosFrm.setSize(650, 700);
        SuggestPromosFrm.setVisible(true);

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
        DrinkAlcoholFrame.setSize(450, 200);
        DrinkAlcoholFrame.setVisible(true);

        this.findProductsForOperatingTable(ProductTypeEnum.BEBIDAS_ALCHOLICAS, this.selectedTable.getId(), this.DrinkAlcoholFrame);


    }//GEN-LAST:event_drinkAlcoholActionPerformed

    private void findProductsForOperatingTable(ProductTypeEnum productTypeEnum, int selectedTable, JFrame jFrame) {
        /**
         * Aseguramos de traer solo los tipos de prouductos que especifica el
         * actionPerformed.
         */
        List<Product> selectedTableProducts = this.productsImpl.findTableSelectedProducts(selectedTable, productTypeEnum);

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
             *
             * Iteración de componentes dentro de un frame
             */
            for (Component component : jFrame.getContentPane().getComponents()) {
                System.out.println("testing frame component --> " + component);
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

    }


    private void seeConsumingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seeConsumingActionPerformed
        this.closeTableActionPerformed(evt);
        this.CloseTableButton.setVisible(false);
        
    }//GEN-LAST:event_seeConsumingActionPerformed

    private void closeTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeTableActionPerformed
        // TODO add your handling code here:

        this.CloseTableButton.setVisible(true);
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
        DeleteTableFrm.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        DeleteTableFrm.setSize(400, 200);
        DeleteTableFrm.setVisible(true);        
    }//GEN-LAST:event_deleteTableActionPerformed

    private void payActionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payActionActionPerformed
        PayTableFrm.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        PayTableFrm.setSize(500, 200);
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
        this.dataStore = new DataStore();
        this.dataStore.setProductTypeEnum(ProductTypeEnum.BEBIDAS_SIN_ALCOHOL);

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

        if (!this.noAlcoholDrinks.isEmpty()) {
            dataStore.setNoAlcoholDrinks(noAlcoholDrinks);
        }

        /**
         * Inserta los productos -> teniendo en cuenta sus precios.
         */
        this.productsImpl.insertProduct(dataStore);

        this.setTableColour(this.selectedTable.getId(), Color.YELLOW);

        if (!this.noAlcoholDrinks.isEmpty()) {
            this.noAlcoholDrinks.clear();
        }

        JOptionPane.showMessageDialog(null, "La mesa Nº: " + this.selectedTable.getId() + " Se ha guardado Correctamente");

        // validar -> nullPointerException.
        // this.AlcoholDrinks.clear();
        // TODO: cuando termina de insetar , todos los campos tienenen que ser reseteados.
        // Para que la próxima mesa empiece con los productos vaciós.
        // Y si se elige la mesa en cuestión tenemos que traer todos los resultados de la BBDD,
        // no puede quedar nada en memoria.
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


    private void CloseTableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseTableButtonActionPerformed

        this.productsImpl.closeTable(this.selectedTable.getId(), this.tableUser.getNombre() + "_" + this.tableUser.getApellido());

        this.closeGenericFrame(CloseTableFrame);
        this.closeGenericFrame(this.SelectOrder);

        // JOptionPane.showMessageDialog(null, "La mesa Nº: " + this.selectedTable.getId() + " fue Cerrada con éxito");
        this.setTableColour(this.selectedTable.getId(), Color.RED);
        

    }//GEN-LAST:event_CloseTableButtonActionPerformed

    private void cashPayDiscountCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cashPayDiscountCheckActionPerformed
        if (this.cashPayDiscountCheck.isSelected()) {
            this.cashDiscountCombo.setEnabled(true);
        } else {
            this.cashDiscountCombo.setEnabled(false);
        }


    }//GEN-LAST:event_cashPayDiscountCheckActionPerformed

    private void paymentCashActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentCashActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_paymentCashActionPerformed

    private void cashCalculateTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cashCalculateTotalActionPerformed
        double cashPayment = Double.parseDouble(this.paymentCash.getText());
        double cashSbtTotal = Double.parseDouble(this.cashSubTotal.getText());

        if (cashPayDiscountCheck.isSelected()) {
            // aplica descuento
            System.out.println("combo selected item -> " + this.cashDiscountCombo.getSelectedItem().toString());

            int percentage = Integer.parseInt(this.cashDiscountCombo.getSelectedItem().toString().split("%")[0]);

            double discount = (cashSbtTotal * percentage) / 100;

            this.cashTotalPay.setText(Double.toString(cashSbtTotal - discount));

            this.cashChangeBack.setText(Double.toString(cashPayment - cashSbtTotal - discount));

        } else {
            // no aplica Descuento:

            this.cashTotalPay.setText(Double.toString(cashSbtTotal));
            this.cashChangeBack.setText(Double.toString(cashPayment - cashSbtTotal));

        }

        // this.cashDiscountCombo.setEnabled(false);

    }//GEN-LAST:event_cashCalculateTotalActionPerformed

    private void otherPaymentCalculateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherPaymentCalculateActionPerformed
        double otherPaymenSubTtl = Double.parseDouble(this.otherPaymentSubTotal.getText());
        if (otherPaymentDiscountCheck.isSelected()) {
            // aplica descuento
            int percentage = Integer.parseInt(this.otherPaymentDiscountCombo.getSelectedItem().toString().split("%")[0]);
            double discount = (otherPaymenSubTtl * percentage) / 100;
            this.otherPaymentTotal.setText(Double.toString(otherPaymenSubTtl - discount));
        } else {
            // no aplica descuento
            this.otherPaymentTotal.setText(this.otherPaymentSubTotal.getText());
        }
    }//GEN-LAST:event_otherPaymentCalculateActionPerformed

    private void otherPaymentDiscountCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherPaymentDiscountCheckActionPerformed
        // TODO add your handling code here:
        if (this.otherPaymentDiscountCheck.isSelected()) {
            this.otherPaymentDiscountCombo.setEnabled(true);
        } else {
            this.otherPaymentDiscountCombo.setEnabled(false);
        }
    }//GEN-LAST:event_otherPaymentDiscountCheckActionPerformed

    private void otherPaymentNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherPaymentNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_otherPaymentNameActionPerformed

    private void otherPaymentCelphoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherPaymentCelphoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_otherPaymentCelphoneActionPerformed

    private void cashPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cashPaymentActionPerformed
        this.PagoEfectivoFrame.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.PagoEfectivoFrame.setSize(320, 350);
        this.PagoEfectivoFrame.setVisible(true);

        Double total = 0.0;

        List<Product> consumingProductList = this.productsImpl.getConsumingProduct(this.selectedTable.getId(), this.tableUser.getNombre() + "_" + this.tableUser.getApellido());

        for (Product product : consumingProductList) {
            total += product.getTotal();
        }

        this.cashSubTotal.setText(Double.toString(total));
        this.cashPayDiscountCheck.setSelected(false);
        this.cashDiscountCombo.setEnabled(false);

        /**
         * Validación Caracteres Numéricos
         */
        this.paymentCash.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent keyEvent) {
                String value = paymentCash.getText();
                /**
                 * Solo se permiten valores numéricos y se permite la tecla de
                 * borrado. TODO: Chris: Validar la tecla de borrado
                 */
                if ((keyEvent.getKeyChar() >= '0' && keyEvent.getKeyChar() <= '9') || (keyEvent.getKeyCode() == 8)) {
                    paymentCash.setEditable(true);
                } else {
                    System.out.println("caractar borrado --> " + keyEvent.getKeyCode());
                    paymentCash.setEditable(false);
                    JOptionPane.showMessageDialog(null, " Solo puede ingresar un valor Numérico");
                    paymentCash.setEditable(true);
                }
            }
        });


    }//GEN-LAST:event_cashPaymentActionPerformed

    private void cashPayActionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cashPayActionActionPerformed
        double discount = cashPayDiscountCheck.isSelected() ? Double.parseDouble(this.cashDiscountCombo.getSelectedItem().toString().split("%")[0]) : 0.00;

        PaymentDataStore paymentDataStore = new PaymentDataStore(this.selectedTable.getId(),
                Double.parseDouble(this.cashSubTotal.getText()),
                discount,
                this.tableUser.getNombre() + "_" + this.tableUser.getApellido(),
                PaymentMethodsEnum.EFECTIVO);

        this.productsImpl.payTable(paymentDataStore);

        // mensaje de Pago efectuado.
        JOptionPane.showMessageDialog(null, "La mesa Nº: " + this.selectedTable.getId() + " ha efectuado el pago Correctamente");

        // cerrar popUps abiertos
        this.closeGenericFrame(PayTableFrm); // Elige el método de pago
        this.closeGenericFrame(PagoEfectivoFrame); // Paga en efectivo
        this.closeGenericFrame(SelectOrder); // elige el tipo de producto

        // libera la mesa
        this.setTableColour(this.selectedTable.getId(), Color.GREEN);

        this.paymentCash.setText("");
        this.cashTotalPay.setText("");
        this.cashChangeBack.setText("");

    }//GEN-LAST:event_cashPayActionActionPerformed

    private void otraFormaDePagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otraFormaDePagoActionPerformed
        // TODO add your handling code here:
        this.OtraFormaDePagoFrame.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.OtraFormaDePagoFrame.setSize(400, 500);
        this.OtraFormaDePagoFrame.setVisible(true);

        Double total = 0.0;

        List<Product> consumingProductList = this.productsImpl.getConsumingProduct(this.selectedTable.getId(), this.tableUser.getNombre() + "_" + this.tableUser.getApellido());

        for (Product product : consumingProductList) {
            total += product.getTotal();
        }

        this.otherPaymentSubTotal.setText(Double.toString(total));
        this.otherPaymentDiscountCheck.setSelected(false);
        this.otherPaymentDiscountCombo.setEnabled(false);

        /**
         * Tener en cuenta que no se requiere la validación de los caracteres
         * que se ingresan, ya que el pago es totaly
         */
    }//GEN-LAST:event_otraFormaDePagoActionPerformed

    private void otherPaymentPayActionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherPaymentPayActionActionPerformed
        double discount = otherPaymentDiscountCheck.isSelected() ? Double.parseDouble(this.otherPaymentDiscountCombo.getSelectedItem().toString().split("%")[0]) : 0.00;

        Map<String, PaymentMethodsEnum> paymentMethodMap = new HashMap<>();
        paymentMethodMap.put("debito", PaymentMethodsEnum.DEBITO);
        paymentMethodMap.put("credito", PaymentMethodsEnum.CREDITO);
        paymentMethodMap.put("cuenta_corriente", PaymentMethodsEnum.CUENTA_CORRIENTE);
        paymentMethodMap.put("mercado_pago", PaymentMethodsEnum.MERCADO_PAGO);

        PaymentDataStore paymentDataStore = new PaymentDataStore(this.selectedTable.getId(),
                Double.parseDouble(this.otherPaymentSubTotal.getText()),
                discount,
                this.tableUser.getNombre() + "_" + this.tableUser.getApellido(),
                paymentMethodMap.get(this.otherPaymentMethodCombo.getSelectedItem().toString()),
                this.otherPaymentName.getText(),
                this.otherPaymentLastName.getText(),
                this.otherPaymentDNI.getText(),
                this.otherPaymentCelphone.getText()
        );

        if ((this.otherPaymentName.getText() == null || "".equals(this.otherPaymentName.getText()))
                || (this.otherPaymentLastName.getText() == null || "".equals(this.otherPaymentLastName.getText()))
                || (this.otherPaymentDNI.getText() == null || "".equals(this.otherPaymentDNI.getText()))) {
            JOptionPane.showMessageDialog(null, "Los campos: Nombre , Apellido y DNI son obligatorios");
        } else {

            this.productsImpl.payTable(paymentDataStore);

            // mensaje de Pago efectuado.
            JOptionPane.showMessageDialog(null, "La mesa Nº: " + this.selectedTable.getId() + " ha efectuado el pago Correctamente");

            // cerrar popUps abiertos
            this.closeGenericFrame(PayTableFrm); // Elige el método de pago
            this.closeGenericFrame(OtraFormaDePagoFrame); // Paga en efectivo
            this.closeGenericFrame(SelectOrder); // elige el tipo de producto

            // libera la mesa
            this.setTableColour(this.selectedTable.getId(), Color.GREEN);

            this.otherPaymentSubTotal.setText("");
            this.otherPaymentTotal.setText("");

            this.otherPaymentDNI.setText("");
            this.otherPaymentLastName.setText("");
            this.otherPaymentName.setText("");
            this.otherPaymentCelphone.setText("");

        }
    }//GEN-LAST:event_otherPaymentPayActionActionPerformed

    private void tableFiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableFiveActionPerformed
        // TODO add your handling code here:
        this.selectedTable.setState("tableFive");
        this.selectedTable.setId(5);

        String tableUser = this.productsImpl.findTableUserByTableId(this.selectedTable.getId());

        this.chargeEmployee(tableUser);
        this.selectOrderView(5);
    }//GEN-LAST:event_tableFiveActionPerformed

    private void tableSixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableSixActionPerformed
        this.selectedTable.setState("tableSix");
        this.selectedTable.setId(6);

        String tableUser = this.productsImpl.findTableUserByTableId(this.selectedTable.getId());

        this.chargeEmployee(tableUser);
        this.selectOrderView(6);
    }//GEN-LAST:event_tableSixActionPerformed

    private void tableSevenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableSevenActionPerformed
        this.selectedTable.setState("tableSeven");
        this.selectedTable.setId(7);

        String tableUser = this.productsImpl.findTableUserByTableId(this.selectedTable.getId());

        this.chargeEmployee(tableUser);
        this.selectOrderView(7);
    }//GEN-LAST:event_tableSevenActionPerformed

    private void tableEightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableEightActionPerformed
        this.selectedTable.setState("tableEight");
        this.selectedTable.setId(8);

        String tableUser = this.productsImpl.findTableUserByTableId(this.selectedTable.getId());

        this.chargeEmployee(tableUser);
        this.selectOrderView(8);
    }//GEN-LAST:event_tableEightActionPerformed

    private void tableNineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableNineActionPerformed
        this.selectedTable.setState("tableNine");
        this.selectedTable.setId(9);

        String tableUser = this.productsImpl.findTableUserByTableId(this.selectedTable.getId());

        this.chargeEmployee(tableUser);
        this.selectOrderView(9);
    }//GEN-LAST:event_tableNineActionPerformed

    private void tableTenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableTenActionPerformed
        this.selectedTable.setState("tableTen");
        this.selectedTable.setId(10);

        String tableUser = this.productsImpl.findTableUserByTableId(this.selectedTable.getId());

        this.chargeEmployee(tableUser);
        this.selectOrderView(10);
    }//GEN-LAST:event_tableTenActionPerformed

    private void tableElevenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableElevenActionPerformed
        this.selectedTable.setState("tableEleven");
        this.selectedTable.setId(11);

        String tableUser = this.productsImpl.findTableUserByTableId(this.selectedTable.getId());

        this.chargeEmployee(tableUser);
        this.selectOrderView(11);
    }//GEN-LAST:event_tableElevenActionPerformed

    private void tableTwelveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableTwelveActionPerformed
        this.selectedTable.setState("tableTwelve");
        this.selectedTable.setId(12);

        String tableUser = this.productsImpl.findTableUserByTableId(this.selectedTable.getId());

        this.chargeEmployee(tableUser);
        this.selectOrderView(12);
    }//GEN-LAST:event_tableTwelveActionPerformed

    private void CloseDayActionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseDayActionActionPerformed
        // TODO add your handling code here:

        this.CloseDayFrame.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.CloseDayFrame.setSize(400, 500);
        this.CloseDayFrame.setVisible(true);

        JFormattedTextField input;
        MaskFormatter formatter2;
        JPanel panel;

        MaskFormatter formatter;
        try {
            formatter = new MaskFormatter("###'-##'-####");
            input = new JFormattedTextField(formatter);
            input.setValue("123-45-6789");
            panel = new JPanel();
            panel.add(input);

            this.CloseDayFrame.add(panel);

        } catch (ParseException ex) {
            Logger.getLogger(VittoFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_CloseDayActionActionPerformed

    private void tableFourteenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableFourteenActionPerformed
        this.selectedTable.setState("tableFourteen");
        this.selectedTable.setId(14);

        String tableUser = this.productsImpl.findTableUserByTableId(this.selectedTable.getId());

        this.chargeEmployee(tableUser);
        this.selectOrderView(14);
    }//GEN-LAST:event_tableFourteenActionPerformed

    private void tableFifteenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableFifteenActionPerformed
        this.selectedTable.setState("tableFifteen");
        this.selectedTable.setId(15);

        String tableUser = this.productsImpl.findTableUserByTableId(this.selectedTable.getId());

        this.chargeEmployee(tableUser);
        this.selectOrderView(15);
    }//GEN-LAST:event_tableFifteenActionPerformed

    private void tableSixTeenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableSixTeenActionPerformed
        this.selectedTable.setState("tableSixteen");
        this.selectedTable.setId(16);

        String tableUser = this.productsImpl.findTableUserByTableId(this.selectedTable.getId());

        this.chargeEmployee(tableUser);
        this.selectOrderView(16);
    }//GEN-LAST:event_tableSixTeenActionPerformed

    private void tableThirteenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableThirteenActionPerformed
        this.selectedTable.setState("tableThirteen");
        this.selectedTable.setId(13);

        String tableUser = this.productsImpl.findTableUserByTableId(this.selectedTable.getId());

        this.chargeEmployee(tableUser);
        this.selectOrderView(13);
    }//GEN-LAST:event_tableThirteenActionPerformed

    private void takeAwayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_takeAwayActionPerformed
        // TODO add your handling code here:
        this.selectedTable.setState("takeAway");
        this.selectedTable.setId(0);

        String tableUser = this.productsImpl.findTableUserByTableId(this.selectedTable.getId());

        this.chargeEmployee(tableUser);
        this.selectOrderView(0);

    }//GEN-LAST:event_takeAwayActionPerformed

    private void alfajorCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alfajorCheckActionPerformed
        if (alfajorCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.alfajorSpinner.setEnabled(true);
            this.alfajorSpinner.setModel(model);
        } else {
            this.alfajorSpinner.setEnabled(false);
        }

    }//GEN-LAST:event_alfajorCheckActionPerformed

    private void tortaIndividualCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tortaIndividualCheckActionPerformed
        if (tortaIndividualCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.tortaIndividualSpinner.setEnabled(true);
            this.tortaIndividualSpinner.setModel(model);
        } else {
            this.tortaIndividualSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_tortaIndividualCheckActionPerformed

    private void cuadradoSecoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cuadradoSecoCheckActionPerformed
        if (cuadradoSecoCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.cuadradoSecoSpinner.setEnabled(true);
            this.cuadradoSecoSpinner.setModel(model);
        } else {
            this.cuadradoSecoSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_cuadradoSecoCheckActionPerformed

    private void tostadasCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tostadasCheckActionPerformed
        if (tostadasCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.tostadasSpinner.setEnabled(true);
            this.tostadasSpinner.setModel(model);
        } else {
            this.tostadasSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_tostadasCheckActionPerformed

    private void alfajorArtesanalCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alfajorArtesanalCheckActionPerformed
        if (alfajorArtesanalCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.alfajorArtesanalSpinner.setEnabled(true);
            this.alfajorArtesanalSpinner.setModel(model);
        } else {
            this.alfajorArtesanalSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_alfajorArtesanalCheckActionPerformed

    private void medialunaCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medialunaCheckActionPerformed
        if (medialunaCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.medialunaSpinner.setEnabled(true);
            this.medialunaSpinner.setModel(model);
        } else {
            this.medialunaSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_medialunaCheckActionPerformed

    private void fraNuiCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fraNuiCheckActionPerformed
        if (fraNuiCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.franuiSpinner.setEnabled(true);
            this.franuiSpinner.setModel(model);
        } else {
            this.franuiSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_fraNuiCheckActionPerformed

    private void ensaladaDeFrutasCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ensaladaDeFrutasCheckActionPerformed
        if (ensaladaDeFrutasCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.ensaladaFrutasSpinner.setEnabled(true);
            this.ensaladaFrutasSpinner.setModel(model);
        } else {
            this.ensaladaFrutasSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_ensaladaDeFrutasCheckActionPerformed

    private void frutaEleccionCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_frutaEleccionCheckActionPerformed
        if (frutaEleccionCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.frutaEleccionSpinner.setEnabled(true);
            this.frutaEleccionSpinner.setModel(model);
        } else {
            this.frutaEleccionSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_frutaEleccionCheckActionPerformed

    private void cancelCandyProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelCandyProductsActionPerformed
        // TODO add your handling code here:
        this.closeGenericFrame(CandyProductsFrame);
    }//GEN-LAST:event_cancelCandyProductsActionPerformed

    private void GuardarProductosDulcesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarProductosDulcesActionPerformed
        products = new HashMap<>();

        if (tortaIndividualCheck.isSelected() && Integer.parseInt(this.tortaIndividualSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtySweetProduct = new HashMap<>();

            priceQtySweetProduct.put(
                    Integer.parseInt(this.tortaIndividualSpinner.getValue().toString()),
                    Double.parseDouble(this.tortaIndividualPrice.getText())
            );
            products.put(SweetProductsEnum.TORTA_INDIVIDUAL.toString(), priceQtySweetProduct);
        }

        if (cuadradoSecoCheck.isSelected() && Integer.parseInt(this.cuadradoSecoSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtySweetProduct = new HashMap<>();

            priceQtySweetProduct.put(
                    Integer.parseInt(this.cuadradoSecoSpinner.getValue().toString()),
                    Double.parseDouble(this.cuadradoSecoPrice.getText())
            );
            products.put(SweetProductsEnum.CUADRADO_SECO.toString(), priceQtySweetProduct);
        }

        if (alfajorCheck.isSelected() && Integer.parseInt(this.alfajorSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtySweetProduct = new HashMap<>();

            priceQtySweetProduct.put(
                    Integer.parseInt(this.alfajorSpinner.getValue().toString()),
                    Double.parseDouble(this.alfajorPrice.getText())
            );
            products.put(SweetProductsEnum.ALFAJOR.toString(), priceQtySweetProduct);
        }

        if (tostadasCheck.isSelected() && Integer.parseInt(this.tostadasSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtySweetProduct = new HashMap<>();

            priceQtySweetProduct.put(
                    Integer.parseInt(this.tostadasSpinner.getValue().toString()),
                    Double.parseDouble(this.tostadasPrice.getText())
            );
            products.put(SweetProductsEnum.TOSTADAS.toString(), priceQtySweetProduct);
        }

        if (alfajorArtesanalCheck.isSelected() && Integer.parseInt(this.alfajorArtesanalSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtySweetProduct = new HashMap<>();

            priceQtySweetProduct.put(
                    Integer.parseInt(this.alfajorArtesanalSpinner.getValue().toString()),
                    Double.parseDouble(this.alfajorArtesanalPrice.getText())
            );
            products.put(SweetProductsEnum.ALFAJOR_ARTESANAL.toString(), priceQtySweetProduct);
            
        }

        if (medialunaCheck.isSelected() && Integer.parseInt(this.medialunaSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtySweetProduct = new HashMap<>();

            priceQtySweetProduct.put(
                    Integer.parseInt(this.medialunaSpinner.getValue().toString()),
                    Double.parseDouble(this.medialunaPrice.getText())
            );
            products.put(SweetProductsEnum.MEDIALUNA.toString(), priceQtySweetProduct);
        }

        if (fraNuiCheck.isSelected() && Integer.parseInt(this.franuiSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtySweetProduct = new HashMap<>();

            priceQtySweetProduct.put(
                    Integer.parseInt(this.franuiSpinner.getValue().toString()),
                    Double.parseDouble(this.fraNuiPrice.getText())
            );
            products.put(SweetProductsEnum.FRA_NUI.toString(), priceQtySweetProduct);
        }

        if (ensaladaDeFrutasCheck.isSelected() && Integer.parseInt(this.ensaladaFrutasSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtySweetProduct = new HashMap<>();

            priceQtySweetProduct.put(
                    Integer.parseInt(this.ensaladaFrutasSpinner.getValue().toString()),
                    Double.parseDouble(this.ensaladaFrutasPrice.getText())
            );
            products.put(SweetProductsEnum.ENSALADA_FRUTAS.toString(), priceQtySweetProduct);
        }

        if (frutaEleccionCheck.isSelected() && Integer.parseInt(this.frutaEleccionSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtySweetProduct = new HashMap<>();

            priceQtySweetProduct.put(
                    Integer.parseInt(this.frutaEleccionSpinner.getValue().toString()),
                    Double.parseDouble(this.frutaEleccionPrice.getText())
            );
            products.put(SweetProductsEnum.FRUTA_ELECCION.toString(), priceQtySweetProduct);
        }

        this.dataStore = new DataStore();
        this.dataStore.setProductTypeEnum(ProductTypeEnum.DULCES);

        System.out.println("mesa elegida state --> " + this.selectedTable.getState() + " mesa elegida ID -> " + this.selectedTable.getId());

        if (this.tableUser != null && this.tableUser.getNombre() != null) {
            System.out.println("Usuario mesa: " + this.tableUser.getNombre());
        } else {
            this.tableUser.setNombre(this.employeeList.get(0).getNombre());
            this.tableUser.setApellido(this.employeeList.get(0).getApellido());
        }

        this.dataStore.setMesa(this.selectedTable.getId());
        this.dataStore.setNombreMozo(this.tableUser.getNombre() + "_" + this.tableUser.getApellido());

        if (!products.isEmpty()) {
            dataStore.setProducts(products);
        }

        /**
         * Inserta los productos -> teniendo en cuenta sus precios.
         */
        this.productsImpl.insertProductWithPrices(dataStore);

        System.out.println("Selected table -> " + this.selectedTable.getId());
        this.setTableColour(this.selectedTable.getId(), Color.YELLOW);

        if (!this.products.isEmpty()) {
            this.products.clear();
        }

        JOptionPane.showMessageDialog(null, "La mesa Nº: " + this.selectedTable.getId() + " Se ha guardado Correctamente");

        // validar -> nullPointerException.
        // this.AlcoholDrinks.clear();
        // TODO: cuando termina de insetar , todos los campos tienenen que ser reseteados.
        // Para que la próxima mesa empiece con los productos vaciós.
        // Y si se elige la mesa en cuestión tenemos que traer todos los resultados de la BBDD,
        // no puede quedar nada en memoria.
        this.closeGenericFrame(this.CandyProductsFrame);

    }//GEN-LAST:event_GuardarProductosDulcesActionPerformed

    private void alcoholDrinkCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alcoholDrinkCancelActionPerformed
        this.closeGenericFrame(this.DrinkAlcoholFrame);
    }//GEN-LAST:event_alcoholDrinkCancelActionPerformed

    private void cervezaCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cervezaCheckActionPerformed
        if (cervezaCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.cervezaSpinner.setEnabled(true);
            this.cervezaSpinner.setModel(model);
        } else {
            this.cervezaSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_cervezaCheckActionPerformed

    private void saverAlcoholOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saverAlcoholOrderActionPerformed
        products = new HashMap<>();
        

        if (cervezaCheck.isSelected() && Integer.parseInt(this.cervezaSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.cervezaSpinner.getValue().toString()),
                    Double.parseDouble(this.cervezaPrice.getText())
            );
            products.put(AlcoholDrinksEnum.CERVEZA.toString(), priceQtyAlcoholProduct);
        }
        
        this.dataStore = new DataStore();
        this.dataStore.setProductTypeEnum(ProductTypeEnum.BEBIDAS_ALCHOLICAS);

        System.out.println("mesa elegida state --> " + this.selectedTable.getState() + " mesa elegida ID -> " + this.selectedTable.getId());

        if (this.tableUser != null && this.tableUser.getNombre() != null) {
            System.out.println("Usuario mesa: " + this.tableUser.getNombre());
        } else {
            this.tableUser.setNombre(this.employeeList.get(0).getNombre());
            this.tableUser.setApellido(this.employeeList.get(0).getApellido());
        }

        this.dataStore.setMesa(this.selectedTable.getId());
        this.dataStore.setNombreMozo(this.tableUser.getNombre() + "_" + this.tableUser.getApellido());

        if (!products.isEmpty()) {
            dataStore.setProducts(products);
        }

        /**
         * Inserta los productos -> teniendo en cuenta sus precios.
         */
        this.productsImpl.insertProductWithPrices(dataStore);

        System.out.println("Selected table -> " + this.selectedTable.getId());
        this.setTableColour(this.selectedTable.getId(), Color.YELLOW);

        if (!this.products.isEmpty()) {
            this.products.clear();
        }

        JOptionPane.showMessageDialog(null, "La mesa Nº: " + this.selectedTable.getId() + " Se ha guardado Correctamente");

        // validar -> nullPointerException.
        // this.AlcoholDrinks.clear();
        // TODO: cuando termina de insetar , todos los campos tienenen que ser reseteados.
        // Para que la próxima mesa empiece con los productos vaciós.
        // Y si se elige la mesa en cuestión tenemos que traer todos los resultados de la BBDD,
        // no puede quedar nada en memoria.
        this.closeGenericFrame(this.DrinkAlcoholFrame);
        


    }//GEN-LAST:event_saverAlcoholOrderActionPerformed

    private void AdminUserPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdminUserPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AdminUserPasswordActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        String adminUser = this.AdminUserName.getText();
        String password = this.AdminUserPassword.getText();
        
        System.out.println("Password --> " + password);
        String user = "christian";
        String pass = "lasalle";
        
        if (adminUser.equals(user) && password.equals(pass)) {
            JOptionPane.showMessageDialog(null, "usuario válido para eliminar la mesa");
            this.AdminUserName.setText("");
            this.AdminUserPassword.setText("");  
        } else {
            JOptionPane.showMessageDialog(null, "Usuario /  Pass Incorrecto");
            this.AdminUserName.setText("");
            this.AdminUserPassword.setText("");  
        }
        
        this.DeleteTableFrm.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent w) {
                System.out.println("TEsting closeing ");
            }
        });
    }//GEN-LAST:event_jButton1ActionPerformed

    private void setTableColour(int tableId, Color color) {

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
            case 5:
                this.tableFive.setBackground(color);
                break;
            case 6:
                this.tableSix.setBackground(color);
                break;
            case 7:
                this.tableSeven.setBackground(color);
                break;
            case 8:
                this.tableEight.setBackground(color);
                break;
            case 9:
                this.tableNine.setBackground(color);
                break;
            case 10:
                this.tableTen.setBackground(color);
                break;
            case 11:
                this.tableEleven.setBackground(color);
                break;
            case 12:
                this.tableTwelve.setBackground(color);
                break;
            case 13:
                this.tableThirteen.setBackground(color);
                break;
            case 14:
                this.tableFourteen.setBackground(color);
                break;
            case 15:
                this.tableFifteen.setBackground(color);
                break;
            case 16:
                this.tableSixTeen.setBackground(color);
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
    private javax.swing.JTextField AdminUserName;
    private javax.swing.JPasswordField AdminUserPassword;
    private javax.swing.JFrame CandyProductsFrame;
    private javax.swing.JButton CloseDayAction;
    private javax.swing.JFrame CloseDayFrame;
    private javax.swing.JButton CloseTableButton;
    private javax.swing.JFrame CloseTableFrame;
    private javax.swing.JFrame DeleteTableFrm;
    private javax.swing.JFrame DrinkAlcoholFrame;
    private javax.swing.JFrame DrinkNoAlcoholFrame;
    private javax.swing.JButton GuardarProductosDulces;
    private javax.swing.JLabel MesaLabel;
    private javax.swing.JLabel MozoLabel;
    private javax.swing.JFrame OtraFormaDePagoFrame;
    private javax.swing.JFrame PagoEfectivoFrame;
    private javax.swing.JFrame PayTableFrm;
    private javax.swing.JFrame SaladsProductFrame;
    private javax.swing.JFrame SelectOrder;
    private javax.swing.JFrame SuggestPromosFrm;
    private javax.swing.JLabel TotalLabel;
    private javax.swing.JButton acceptNoAlcoholDrinks;
    private javax.swing.JButton alcoholDrinkCancel;
    private javax.swing.JCheckBox alfajorArtesanalCheck;
    private javax.swing.JLabel alfajorArtesanalPrice;
    private javax.swing.JSpinner alfajorArtesanalSpinner;
    private javax.swing.JCheckBox alfajorCheck;
    private javax.swing.JLabel alfajorPrice;
    private javax.swing.JSpinner alfajorSpinner;
    private javax.swing.JButton cancelCandyProducts;
    private javax.swing.JButton cancelNoAlcoholDrinks;
    private javax.swing.JButton candyProducts;
    private javax.swing.JButton cashCalculateTotal;
    private javax.swing.JLabel cashChangeBack;
    private javax.swing.JComboBox<String> cashDiscountCombo;
    private javax.swing.JButton cashPayAction;
    private javax.swing.JCheckBox cashPayDiscountCheck;
    private javax.swing.JButton cashPayment;
    private javax.swing.JLabel cashSubTotal;
    private javax.swing.JLabel cashTotalPay;
    private javax.swing.JCheckBox cepitaCheck;
    private javax.swing.JSpinner cepitaSpinner;
    private javax.swing.JCheckBox cervezaCheck;
    private javax.swing.JLabel cervezaPrice;
    private javax.swing.JSpinner cervezaSpinner;
    private javax.swing.JButton closeTable;
    private javax.swing.JCheckBox cocaColaCheck;
    private javax.swing.JCheckBox cocaLightCheck;
    private javax.swing.JSpinner cocaLightSpinner;
    private javax.swing.JSpinner cocaSpinner;
    private javax.swing.JCheckBox cuadradoSecoCheck;
    private javax.swing.JLabel cuadradoSecoPrice;
    private javax.swing.JSpinner cuadradoSecoSpinner;
    private javax.swing.JButton deleteTable;
    private javax.swing.JButton drinkAlcohol;
    private javax.swing.JButton drinkNoAlcohol;
    private javax.swing.JComboBox<String> employeeNameCombo;
    private javax.swing.JCheckBox ensaladaDeFrutasCheck;
    private javax.swing.JLabel ensaladaFrutasPrice;
    private javax.swing.JSpinner ensaladaFrutasSpinner;
    private javax.swing.JCheckBox exprimidoCheck;
    private javax.swing.JSpinner exprimidoSpinner;
    private javax.swing.JCheckBox fantaCheck;
    private javax.swing.JSpinner fantaSpinner;
    private javax.swing.JCheckBox fraNuiCheck;
    private javax.swing.JLabel fraNuiPrice;
    private javax.swing.JSpinner franuiSpinner;
    private javax.swing.JCheckBox frutaEleccionCheck;
    private javax.swing.JLabel frutaEleccionPrice;
    private javax.swing.JSpinner frutaEleccionSpinner;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
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
    private javax.swing.JCheckBox medialunaCheck;
    private javax.swing.JLabel medialunaPrice;
    private javax.swing.JSpinner medialunaSpinner;
    private javax.swing.JCheckBox naranjaCheck;
    private javax.swing.JSpinner naranjaSpinner;
    private javax.swing.JButton otherPaymentCalculate;
    private javax.swing.JTextField otherPaymentCelphone;
    private javax.swing.JTextField otherPaymentDNI;
    private javax.swing.JCheckBox otherPaymentDiscountCheck;
    private javax.swing.JComboBox<String> otherPaymentDiscountCombo;
    private javax.swing.JTextField otherPaymentLastName;
    private javax.swing.JComboBox<String> otherPaymentMethodCombo;
    private javax.swing.JTextField otherPaymentName;
    private javax.swing.JButton otherPaymentPayAction;
    private javax.swing.JLabel otherPaymentSubTotal;
    private javax.swing.JLabel otherPaymentTotal;
    private javax.swing.JButton otraFormaDePago;
    private javax.swing.JButton payAction;
    private javax.swing.JTextField paymentCash;
    private javax.swing.JCheckBox pomeloCheck;
    private javax.swing.JSpinner pomeloSpinner;
    private javax.swing.JCheckBox pomeloTorosCheck;
    private javax.swing.JTable productDescriptionTable;
    private javax.swing.JButton promotions;
    private javax.swing.JButton saladProducts;
    private javax.swing.JButton saverAlcoholOrder;
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
    private javax.swing.JButton tableEight;
    private javax.swing.JButton tableEleven;
    private javax.swing.JButton tableFifteen;
    private javax.swing.JButton tableFive;
    private javax.swing.JButton tableFour;
    private javax.swing.JButton tableFourteen;
    private javax.swing.JLabel tableIdPayLabel;
    private javax.swing.JButton tableNine;
    private javax.swing.JButton tableOne;
    private javax.swing.JButton tableSeven;
    private javax.swing.JButton tableSix;
    private javax.swing.JButton tableSixTeen;
    private javax.swing.JButton tableTen;
    private javax.swing.JButton tableThirteen;
    private javax.swing.JButton tableThree;
    private javax.swing.JButton tableTwelve;
    private javax.swing.JButton tableTwo;
    private javax.swing.JButton takeAway;
    private javax.swing.JCheckBox tonicaCheck;
    private javax.swing.JSpinner torosPomeloSpinner;
    private javax.swing.JSpinner torosTonicaSpinner;
    private javax.swing.JCheckBox tortaIndividualCheck;
    private javax.swing.JLabel tortaIndividualPrice;
    private javax.swing.JSpinner tortaIndividualSpinner;
    private javax.swing.JCheckBox tostadasCheck;
    private javax.swing.JLabel tostadasPrice;
    private javax.swing.JSpinner tostadasSpinner;
    private javax.swing.JLabel totalPayLabel;
    private javax.swing.JLabel userTableCompleteNamePay;
    private javax.swing.JCheckBox waterCheck;
    private javax.swing.JCheckBox waterGasCheck;
    private javax.swing.JSpinner waterGasSpinner;
    private javax.swing.JSpinner waterSpinner;
    private javax.swing.JLabel whoAmILbl;
    // End of variables declaration//GEN-END:variables
}
