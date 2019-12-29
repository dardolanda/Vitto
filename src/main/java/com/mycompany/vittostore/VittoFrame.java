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
import com.mycompany.vittostore.generalitems.PromotionBreakfast;
import com.mycompany.vittostore.generalitems.PromotionsLunchEnum;
import com.mycompany.vittostore.generalitems.SaladsEnum;
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
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.POCILLO.name(), new GenericSelectedComponent(this.pocilloCheck, this.pocilloSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.JARRITO.name(), new GenericSelectedComponent(this.jarritoCheck, this.jarritoSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.CAFE_CON_LECHE.name(), new GenericSelectedComponent(this.cafeConLecheCheck, this.cafeConLecheSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.CAFE_DOBLE.name(), new GenericSelectedComponent(this.cafeDobleCheck, this.cafeDobleSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.CAPPUCCINO_ITALIANO.name(), new GenericSelectedComponent(this.CapuccinoItalianoCheck, this.CapuccinoItalianoSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.CAPPUCCINO.name(), new GenericSelectedComponent(this.CapuccinoCheck, this.CapuccinoSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.LATTE_SABORIZADO.name(), new GenericSelectedComponent(this.LatteSaborizadosCheck, this.LattesaborizadosSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.CAFE_ICE_MASSIMO.name(), new GenericSelectedComponent(this.cafeIceMassimoCheck, this.cafeIceMassimoSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.CAFE_ICE_CAPPUCCINO.name(), new GenericSelectedComponent(this.cafeIceCappuCheck, this.cafeIceCappuSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.DEL_TIEMPO.name(), new GenericSelectedComponent(this.delTiempoCheck, this.tonicCoffeeSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.BOMBON.name(), new GenericSelectedComponent(this.bombonCheck, this.bombonSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.TONIC_COFFEE.name(), new GenericSelectedComponent(this.tonicCoffeeCheck, this.cafeDelTiempoSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.SUBMARINO.name(), new GenericSelectedComponent(this.submarinoCheck, this.submarinoSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.CHOCOLATADA.name(), new GenericSelectedComponent(this.chocolatadaCheck, this.chocolatadaSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.ICE_TEA_SABORIZADO.name(), new GenericSelectedComponent(this.iceTeaCheck, this.iceTeaSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.HOTCIOK.name(), new GenericSelectedComponent(this.hotCiokCheck, this.hotCiokSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.TE.name(), new GenericSelectedComponent(this.teCheck, this.teSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.FRAPPUCCINO.name(), new GenericSelectedComponent(this.FrapuccinoCheck, this.FrapuccinoSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.MILKSHAKE.name(), new GenericSelectedComponent(this.milkShakeCheck, this.MilkshakeSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.LICUADO.name(), new GenericSelectedComponent(this.LicuadoCheck, this.licuadosSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.LIMONADA.name(), new GenericSelectedComponent(this.limonadaCheck, this.limonadaSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.EXPRIMIDO_NARANJA.name(), new GenericSelectedComponent(this.batidoExprimidoCheck, this.batidoExprimidoSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.GASEOSA_LINEA_COCA.name(), new GenericSelectedComponent(this.GaseosaLineaCocacheck, this.GaseosaLineaCocaSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.SABORIZADA.name(), new GenericSelectedComponent(this.aguaSaborizadaCheck, this.aguaSaborizadaSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.CEPITA.name(), new GenericSelectedComponent(this.cepitaCheck, this.cepitaSpinner));
        this.selectedComponentMap.put(NoAlcoholDrinksEnum.AGUA_con_sin_gas.name(), new GenericSelectedComponent(this.AguaC_S_GasCheck, this.AguaC_S_GasSpinner));


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
        
        /**
         * Promos Meriendas
         */        
        this.selectedComponentMap.put(PromotionBreakfast.CAFE_2_MEDIALUNAS.name(),new GenericSelectedComponent((this.cafe2medialunasCheck), this.cafe2MedialunasSpinner));
        this.selectedComponentMap.put(PromotionBreakfast.CAFE_TORTA_INDIVIDUAL.name(),new GenericSelectedComponent((this.cafeTortaIndividualCheck), this.cafeTortaIndSpinner));
        this.selectedComponentMap.put(PromotionBreakfast.CAFE_TOSTADO_ARABE.name(),new GenericSelectedComponent((this.cafeTostadoArabeCheck), this.cafeTostadoArabeSpinner));
        this.selectedComponentMap.put(PromotionBreakfast.CAFE_CUADRADO_DULCE.name(),new GenericSelectedComponent((this.cafeCuadradoDucleCheck), this.cafeCuadraroDulceSpinner));
        this.selectedComponentMap.put(PromotionBreakfast.CAFE_GALLETAS_CHOCO.name(),new GenericSelectedComponent((this.cafe2GallesChocoCheck), this.Cafe2gallesChocoSpinner));
        this.selectedComponentMap.put(PromotionBreakfast.CAPPUCCINO_TOSTADAS.name(),new GenericSelectedComponent((this.cappuccinoTostadasCheck), this.cappuccinoTostadaSppiner));
        this.selectedComponentMap.put(PromotionBreakfast.CAPPUCCINO_MEDIALUNAS.name(),new GenericSelectedComponent((this.cappucinoMedialunaCheck), this.cappucinnoMedialunaSpinner));
        this.selectedComponentMap.put(PromotionBreakfast.NARANJA_MEDIALUNAS.name(),new GenericSelectedComponent((this.naranja2mediaLunasCheck), this.naranja2medialunasSpinner));
        this.selectedComponentMap.put(PromotionBreakfast.NARANJA_TOSTADOS.name(),new GenericSelectedComponent((this.naranjaTostadoCheck), this.naranjaTostadoSpinner));
        this.selectedComponentMap.put(PromotionBreakfast.LICUADO_medio_TOSTADO.name(),new GenericSelectedComponent((this.licuadoMedioTostadoCheck), this.licuadoMedioTostadoSpinner));
        this.selectedComponentMap.put(PromotionBreakfast.LIMONADA_TORTA_IND.name(),new GenericSelectedComponent((this.limonadaTortaIndividiualCheck), this.limonadaTortaIndividualSpinner));
        this.selectedComponentMap.put(PromotionBreakfast.ALFAJOR_MAICENA_CHOCO.name(),new GenericSelectedComponent((this.alfajorMaicenaChocolatadaCheck), this.alfajorMaicenaChocoSpinner));
        this.selectedComponentMap.put(PromotionBreakfast.MILKSHAKES_TORTA_IND.name(),new GenericSelectedComponent((this.milkshakesTortaCheck), this.milkshakesTortaIndividualSpinner));
        this.selectedComponentMap.put(PromotionBreakfast.LICUADOS_2_1_TOSTADO.name(),new GenericSelectedComponent((this.licuado2_1_TostadoIndCheck), this.licuados_2_1_TostadoSpinner));
        this.selectedComponentMap.put(PromotionBreakfast.COPA_YOGURT.name(),new GenericSelectedComponent((this.copaYogurtCheck), this.yogurtSpinner));
        this.selectedComponentMap.put(PromotionBreakfast.TOSTADAS_EXP_ESP.name(),new GenericSelectedComponent((this.tostadasDobleExprEspCheck), this.tostadasExpEspSpinner));
        this.selectedComponentMap.put(PromotionBreakfast.FRUTA_CAFE_TOSTADO.name(),new GenericSelectedComponent((this.frutasCafeTostadoCheck), this.frutaCafeTostadoSpinner));
        this.selectedComponentMap.put(PromotionBreakfast.ICE_CAPPUCCINO.name(),new GenericSelectedComponent((this.iceCappuccinoCheck), this.iceCapuccinoSpinner));
        
        /**
         * Promos Para comer
         */
        this.selectedComponentMap.put(PromotionsLunchEnum.ENSALADA_CESAR.name(),new GenericSelectedComponent((this.ensaladaCesarCheck), this.ensaladaCesarSpinner));
        this.selectedComponentMap.put(PromotionsLunchEnum.ENSALADA_IBERICA.name(),new GenericSelectedComponent((this.ensaladaIbericaCheck), this.ensaladaIbericaSpinner));
        this.selectedComponentMap.put(PromotionsLunchEnum.ENSALADA_PRIMAVERA.name(),new GenericSelectedComponent((this.ensaladaPrimaveraCheck), this.ensaladaPrimaveraSpinner));
        this.selectedComponentMap.put(PromotionsLunchEnum.ENSALADA_TROPICAL.name(),new GenericSelectedComponent((this.ensaladaTropicalCheck), this.ensaladaTropicalSpinner));
        this.selectedComponentMap.put(PromotionsLunchEnum.SANDWICH_REIMS_PROMO.name(),new GenericSelectedComponent((this.sandwichReimsPromoCheck), this.sandwichReimsPromoSpinner));
        this.selectedComponentMap.put(PromotionsLunchEnum.MILANESA_PROMO.name(),new GenericSelectedComponent((this.milanesaPromoCheck), this.milanesaPromoSpinner));
        this.selectedComponentMap.put(PromotionsLunchEnum.PIZZA_PROMO.name(),new GenericSelectedComponent((this.pizzaPromoCheck), this.pizzaPromoSpinner));
        this.selectedComponentMap.put(PromotionsLunchEnum.EMPANADA_PROMO.name(),new GenericSelectedComponent((this.empanadaPromoCheck), this.empanadaPromoSpinner));
        this.selectedComponentMap.put(PromotionsLunchEnum.PANCHO_PROMO.name(),new GenericSelectedComponent((this.panchoPromoCheck), this.panchoPromoSpinner));
        this.selectedComponentMap.put(PromotionsLunchEnum.ARABE_PROMO.name(),new GenericSelectedComponent((this.arabePromoCheck), this.arabePromoSpinner));
        this.selectedComponentMap.put(PromotionsLunchEnum.SANDWICH_PROMO.name(),new GenericSelectedComponent((this.sandwichPromoCheck), this.sandwichPromoSpinner));        
        
        /**
         * Salados
         */
        this.selectedComponentMap.put(SaladsEnum.TOSTADO.name(),new GenericSelectedComponent((this.tostadoSdwCheck), this.tostadoSdwSpinner));
        this.selectedComponentMap.put(SaladsEnum.ARABE.name(),new GenericSelectedComponent((this.arabeSdwCheck), this.arabeSdwSpinner));
        this.selectedComponentMap.put(SaladsEnum.MEDIALUNA_RELLENA.name(),new GenericSelectedComponent((this.medialunaSdwCheck), this.medialunaSdwSpinner));
        this.selectedComponentMap.put(SaladsEnum.CIABATTA_POLLO.name(),new GenericSelectedComponent((this.cbtPolloCheck), this.cbtPolloSpinner));
        this.selectedComponentMap.put(SaladsEnum.CIABATTA_ATUN.name(),new GenericSelectedComponent((this.sbtAtunCheck), this.sbtAtunSpinner));
        this.selectedComponentMap.put(SaladsEnum.CIABATTA_MILANESA.name(),new GenericSelectedComponent((this.sbtMilanesaCheck), this.sbtMilanesaSpinner));
        this.selectedComponentMap.put(SaladsEnum.CIABATTA_VERDURAS.name(),new GenericSelectedComponent((this.sbtVerdurasCheck), this.sbtVerdurasSpinner));
        this.selectedComponentMap.put(SaladsEnum.CIABATTA_JAMON_CRUDO.name(),new GenericSelectedComponent((this.sbtCrudoCheck), this.sbtCrudoSpinner));
        this.selectedComponentMap.put(SaladsEnum.EMPANADAS.name(),new GenericSelectedComponent((this.sdwEmpandaCheck), this.sdwEmpandaSpinner));
        this.selectedComponentMap.put(SaladsEnum.TARTA.name(),new GenericSelectedComponent((this.sdwTartasCheck), this.sdwTartasSpinner));
        

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
        
        // validaciones por mesa -> id:
        this.orderValidationActions(table);

    }
    
    
    private void orderValidationActions(int tableId) {
        
        String estado = productsImpl.getTableState(tableId);
        
        switch(estado) {
            case "LIBRE":
                this.seeConsuming.setEnabled(false);
                this.closeTable.setEnabled(false);
                this.payAction.setEnabled(false);
                this.deleteTable.setEnabled(false);
            break;
            
            case "USO":
                this.seeConsuming.setEnabled(true);
                this.closeTable.setEnabled(true);
                this.payAction.setEnabled(false);
                this.deleteTable.setEnabled(true);
            break;
            
            case "CERRADA":
                this.seeConsuming.setEnabled(true);
                this.closeTable.setEnabled(false);
                this.payAction.setEnabled(true);
                this.deleteTable.setEnabled(true);
        }        
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
        ComerPromos = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        payAction = new javax.swing.JButton();
        seeConsuming = new javax.swing.JButton();
        deleteTable = new javax.swing.JButton();
        closeTable = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        DesayunoMerienda = new javax.swing.JButton();
        empleadosPromo = new javax.swing.JButton();
        jLabel77 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        DrinkNoAlcoholFrame = new javax.swing.JFrame();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        acceptNoAlcoholDrinks = new javax.swing.JButton();
        cancelNoAlcoholDrinks = new javax.swing.JButton();
        pocilloCheck = new javax.swing.JCheckBox();
        pocilloSpinner = new javax.swing.JSpinner();
        jarritoCheck = new javax.swing.JCheckBox();
        jarritoSpinner = new javax.swing.JSpinner();
        cafeConLecheCheck = new javax.swing.JCheckBox();
        cafeConLecheSpinner = new javax.swing.JSpinner();
        cafeDobleCheck = new javax.swing.JCheckBox();
        cafeDobleSpinner = new javax.swing.JSpinner();
        CapuccinoCheck = new javax.swing.JCheckBox();
        CapuccinoSpinner = new javax.swing.JSpinner();
        LatteSaborizadosCheck = new javax.swing.JCheckBox();
        LattesaborizadosSpinner = new javax.swing.JSpinner();
        delTiempoCheck = new javax.swing.JCheckBox();
        tonicCoffeeSpinner = new javax.swing.JSpinner();
        bombonCheck = new javax.swing.JCheckBox();
        bombonSpinner = new javax.swing.JSpinner();
        cafeIceCappuCheck = new javax.swing.JCheckBox();
        cafeIceCappuSpinner = new javax.swing.JSpinner();
        cafeIceMassimoCheck = new javax.swing.JCheckBox();
        cafeIceMassimoSpinner = new javax.swing.JSpinner();
        CapuccinoItalianoCheck = new javax.swing.JCheckBox();
        CapuccinoItalianoSpinner = new javax.swing.JSpinner();
        iceTeaCheck = new javax.swing.JCheckBox();
        iceTeaSpinner = new javax.swing.JSpinner();
        GaseosaLineaCocacheck = new javax.swing.JCheckBox();
        GaseosaLineaCocaSpinner = new javax.swing.JSpinner();
        AguaC_S_GasCheck = new javax.swing.JCheckBox();
        AguaC_S_GasSpinner = new javax.swing.JSpinner();
        aguaSaborizadaCheck = new javax.swing.JCheckBox();
        cepitaCheck = new javax.swing.JCheckBox();
        aguaSaborizadaSpinner = new javax.swing.JSpinner();
        cepitaSpinner = new javax.swing.JSpinner();
        tonicCoffeeCheck = new javax.swing.JCheckBox();
        cafeDelTiempoSpinner = new javax.swing.JSpinner();
        submarinoCheck = new javax.swing.JCheckBox();
        chocolatadaCheck = new javax.swing.JCheckBox();
        submarinoSpinner = new javax.swing.JSpinner();
        chocolatadaSpinner = new javax.swing.JSpinner();
        jLabel98 = new javax.swing.JLabel();
        pocilloPrice = new javax.swing.JLabel();
        jLabel119 = new javax.swing.JLabel();
        jLabel122 = new javax.swing.JLabel();
        jarritoPrice = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        cafeConLechePrice = new javax.swing.JLabel();
        jLabel135 = new javax.swing.JLabel();
        cafeDoblePrice = new javax.swing.JLabel();
        jLabel137 = new javax.swing.JLabel();
        CapuccinoPrice = new javax.swing.JLabel();
        jLabel139 = new javax.swing.JLabel();
        CapuccinoItalianoPrice = new javax.swing.JLabel();
        jLabel141 = new javax.swing.JLabel();
        LattesaborizadosPrice = new javax.swing.JLabel();
        submarinoPrice = new javax.swing.JLabel();
        jLabel142 = new javax.swing.JLabel();
        chocolatadaPrice = new javax.swing.JLabel();
        jLabel143 = new javax.swing.JLabel();
        hotCiokCheck = new javax.swing.JCheckBox();
        hotCiokSpinner = new javax.swing.JSpinner();
        jLabel144 = new javax.swing.JLabel();
        hotCiokPrice = new javax.swing.JLabel();
        teCheck = new javax.swing.JCheckBox();
        teSpinner = new javax.swing.JSpinner();
        jLabel145 = new javax.swing.JLabel();
        tePrice = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        cafeDelTiempoPrice = new javax.swing.JLabel();
        jLabel134 = new javax.swing.JLabel();
        bombonPrice = new javax.swing.JLabel();
        jLabel138 = new javax.swing.JLabel();
        cafeIceCappuPrice = new javax.swing.JLabel();
        jLabel146 = new javax.swing.JLabel();
        jLabel147 = new javax.swing.JLabel();
        jLabel148 = new javax.swing.JLabel();
        tonicCoffeePrice = new javax.swing.JLabel();
        jLabel150 = new javax.swing.JLabel();
        iceTeaPrice = new javax.swing.JLabel();
        jLabel124 = new javax.swing.JLabel();
        FrapuccinoCheck = new javax.swing.JCheckBox();
        FrapuccinoSpinner = new javax.swing.JSpinner();
        jLabel136 = new javax.swing.JLabel();
        FrapuccinoPrice = new javax.swing.JLabel();
        milkShakeCheck = new javax.swing.JCheckBox();
        MilkshakeSpinner = new javax.swing.JSpinner();
        jLabel140 = new javax.swing.JLabel();
        MilkshakePrice = new javax.swing.JLabel();
        LicuadoCheck = new javax.swing.JCheckBox();
        licuadosSpinner = new javax.swing.JSpinner();
        jLabel149 = new javax.swing.JLabel();
        licuadosPrice = new javax.swing.JLabel();
        limonadaCheck = new javax.swing.JCheckBox();
        limonadaSpinner = new javax.swing.JSpinner();
        jLabel151 = new javax.swing.JLabel();
        limonadaPrice = new javax.swing.JLabel();
        batidoExprimidoCheck = new javax.swing.JCheckBox();
        batidoExprimidoSpinner = new javax.swing.JSpinner();
        jLabel152 = new javax.swing.JLabel();
        batidoExprimidoPrice = new javax.swing.JLabel();
        jLabel153 = new javax.swing.JLabel();
        GaseosaLineaCocaPrice = new javax.swing.JLabel();
        jLabel155 = new javax.swing.JLabel();
        AguaC_S_GasPrice = new javax.swing.JLabel();
        jLabel157 = new javax.swing.JLabel();
        aguaSaborizadaPrice = new javax.swing.JLabel();
        jLabel159 = new javax.swing.JLabel();
        cepitaPrice = new javax.swing.JLabel();
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
        jLabel84 = new javax.swing.JLabel();
        tostadoSdwCheck = new javax.swing.JCheckBox();
        tostadoSdwSpinner = new javax.swing.JSpinner();
        jLabel87 = new javax.swing.JLabel();
        tostadoSdwPrice = new javax.swing.JLabel();
        arabeSdwCheck = new javax.swing.JCheckBox();
        arabeSdwSpinner = new javax.swing.JSpinner();
        arabeSdwPrice = new javax.swing.JLabel();
        medialunaSdwCheck = new javax.swing.JCheckBox();
        medialunaSdwSpinner = new javax.swing.JSpinner();
        medialunaSdwPrice = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        cbtPolloCheck = new javax.swing.JCheckBox();
        cbtPolloSpinner = new javax.swing.JSpinner();
        cbtPolloPrice = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        jLabel121 = new javax.swing.JLabel();
        sbtMilanesaCheck = new javax.swing.JCheckBox();
        sbtMilanesaSpinner = new javax.swing.JSpinner();
        sbtMilanesaPrice = new javax.swing.JLabel();
        jLabel123 = new javax.swing.JLabel();
        sbtVerdurasCheck = new javax.swing.JCheckBox();
        sbtVerdurasSpinner = new javax.swing.JSpinner();
        sbtVerdurasPrice = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        sbtAtunCheck = new javax.swing.JCheckBox();
        sbtAtunSpinner = new javax.swing.JSpinner();
        sbtAtunPrice = new javax.swing.JLabel();
        jLabel127 = new javax.swing.JLabel();
        sbtCrudoCheck = new javax.swing.JCheckBox();
        sbtCrudoSpinner = new javax.swing.JSpinner();
        sbtCrudoPrice = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        jLabel130 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        sdwEmpandaCheck = new javax.swing.JCheckBox();
        sdwEmpandaSpinner = new javax.swing.JSpinner();
        jLabel131 = new javax.swing.JLabel();
        sdwEmpandaPRice = new javax.swing.JLabel();
        sdwTartasCheck = new javax.swing.JCheckBox();
        sdwTartasSpinner = new javax.swing.JSpinner();
        jLabel133 = new javax.swing.JLabel();
        sdwTartasPrice = new javax.swing.JLabel();
        saveSandwichORder = new javax.swing.JButton();
        closeSandwichORder = new javax.swing.JButton();
        jLabel90 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
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
        PromosBreakfastFrm = new javax.swing.JFrame();
        jLabel78 = new javax.swing.JLabel();
        cafe2medialunasCheck = new javax.swing.JCheckBox();
        cafe2medilunasPrice = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        cafe2MedialunasSpinner = new javax.swing.JSpinner();
        cafeTortaIndividualCheck = new javax.swing.JCheckBox();
        cafeTortaIndPrice = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        cafeTortaIndSpinner = new javax.swing.JSpinner();
        cafeTostadoArabeCheck = new javax.swing.JCheckBox();
        cafeArabePrice = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        cafeTostadoArabeSpinner = new javax.swing.JSpinner();
        cafeCuadradoDucleCheck = new javax.swing.JCheckBox();
        cafeCuadradoDulcePrice = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        cafeCuadraroDulceSpinner = new javax.swing.JSpinner();
        cafe2GallesChocoCheck = new javax.swing.JCheckBox();
        cafe2gallesPrice = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        Cafe2gallesChocoSpinner = new javax.swing.JSpinner();
        cappuccinoTostadasCheck = new javax.swing.JCheckBox();
        cappuccinoTostadaPrice = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        cappuccinoTostadaSppiner = new javax.swing.JSpinner();
        cappucinoMedialunaCheck = new javax.swing.JCheckBox();
        cappucinoMedialunaPrice = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        cappucinnoMedialunaSpinner = new javax.swing.JSpinner();
        naranja2mediaLunasCheck = new javax.swing.JCheckBox();
        naranja2medialunasSpinner = new javax.swing.JSpinner();
        naranja2medialunasPrice = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        naranjaTostadoCheck = new javax.swing.JCheckBox();
        naranjaTostadoSpinner = new javax.swing.JSpinner();
        naranjaTostadoPrice = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        licuadoMedioTostadoCheck = new javax.swing.JCheckBox();
        licuadoMedioTostadoSpinner = new javax.swing.JSpinner();
        licuadoMedioTostadoPrice = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        limonadaTortaIndividiualCheck = new javax.swing.JCheckBox();
        limonadaTortaIndividualSpinner = new javax.swing.JSpinner();
        limonadaTortaIndPrice = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        alfajorMaicenaChocolatadaCheck = new javax.swing.JCheckBox();
        alfajorMaicenaChocoSpinner = new javax.swing.JSpinner();
        alfajorMaicenaChocoPrice = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        milkshakesTortaCheck = new javax.swing.JCheckBox();
        milkshakesTortaIndividualSpinner = new javax.swing.JSpinner();
        milkshakesTortaIndividualPrice = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        licuado2_1_TostadoIndCheck = new javax.swing.JCheckBox();
        licuados_2_1_TostadoSpinner = new javax.swing.JSpinner();
        licuados_2_1_TostadoPrice = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        copaYogurtCheck = new javax.swing.JCheckBox();
        yogurtSpinner = new javax.swing.JSpinner();
        yogurtPrice = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        tostadasDobleExprEspCheck = new javax.swing.JCheckBox();
        tostadasExpEspSpinner = new javax.swing.JSpinner();
        tostadasExpEspPrice = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        frutasCafeTostadoCheck = new javax.swing.JCheckBox();
        frutaCafeTostadoSpinner = new javax.swing.JSpinner();
        frutaCafePrice = new javax.swing.JLabel();
        iceCappuccinoCheck = new javax.swing.JCheckBox();
        iceCapuccinoSpinner = new javax.swing.JSpinner();
        iceCapuccinoPrice = new javax.swing.JLabel();
        savePromoBreakfast = new javax.swing.JButton();
        cosePromoBreakfast = new javax.swing.JButton();
        jLabel117 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
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
        deleteTableButton = new javax.swing.JButton();
        AdminUserPassword = new javax.swing.JPasswordField();
        jLabel75 = new javax.swing.JLabel();
        PromosLunchFrm = new javax.swing.JFrame();
        jLabel79 = new javax.swing.JLabel();
        ensaladaCesarCheck = new javax.swing.JCheckBox();
        ensaladaCesarSpinner = new javax.swing.JSpinner();
        jLabel82 = new javax.swing.JLabel();
        ensaladaCesarPrice = new javax.swing.JLabel();
        ensaladaIbericaCheck = new javax.swing.JCheckBox();
        ensaladaIbericaSpinner = new javax.swing.JSpinner();
        jLabel86 = new javax.swing.JLabel();
        ensaladaIbericaPrice = new javax.swing.JLabel();
        ensaladaPrimaveraCheck = new javax.swing.JCheckBox();
        ensaladaPrimaveraSpinner = new javax.swing.JSpinner();
        jLabel88 = new javax.swing.JLabel();
        ensaladaPrimaveraPrice = new javax.swing.JLabel();
        ensaladaTropicalCheck = new javax.swing.JCheckBox();
        ensaladaTropicalSpinner = new javax.swing.JSpinner();
        jLabel92 = new javax.swing.JLabel();
        ensaladaTropicalPrice = new javax.swing.JLabel();
        sandwichReimsPromoCheck = new javax.swing.JCheckBox();
        sandwichReimsPromoSpinner = new javax.swing.JSpinner();
        jLabel96 = new javax.swing.JLabel();
        sandwichReimsPromoPrice = new javax.swing.JLabel();
        milanesaPromoCheck = new javax.swing.JCheckBox();
        milanesaPromoSpinner = new javax.swing.JSpinner();
        jLabel100 = new javax.swing.JLabel();
        milanesaPromoPrice = new javax.swing.JLabel();
        pizzaPromoCheck = new javax.swing.JCheckBox();
        pizzaPromoSpinner = new javax.swing.JSpinner();
        jLabel104 = new javax.swing.JLabel();
        pizzaPromoPrice = new javax.swing.JLabel();
        panchoPromoCheck = new javax.swing.JCheckBox();
        panchoPromoSpinner = new javax.swing.JSpinner();
        jLabel108 = new javax.swing.JLabel();
        panchoPromoPrice = new javax.swing.JLabel();
        empanadaPromoCheck = new javax.swing.JCheckBox();
        empanadaPromoSpinner = new javax.swing.JSpinner();
        jLabel112 = new javax.swing.JLabel();
        empanadaPromoPrice = new javax.swing.JLabel();
        arabePromoCheck = new javax.swing.JCheckBox();
        arabePromoSpinner = new javax.swing.JSpinner();
        jLabel116 = new javax.swing.JLabel();
        arabePromoPrice = new javax.swing.JLabel();
        sandwichPromoCheck = new javax.swing.JCheckBox();
        sandwichPromoSpinner = new javax.swing.JSpinner();
        jLabel120 = new javax.swing.JLabel();
        sandwichPromoPrice = new javax.swing.JLabel();
        savePromoLunch = new javax.swing.JButton();
        cancelPromoLunch = new javax.swing.JButton();
        EmpleadosFrame = new javax.swing.JFrame();
        jLabel126 = new javax.swing.JLabel();
        jLabel128 = new javax.swing.JLabel();
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
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                drinkNoAlcoholAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
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

        ComerPromos.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        ComerPromos.setText("Para Comer");
        ComerPromos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComerPromosActionPerformed(evt);
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

        jLabel50.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel50.setText("Operaciones");

        jLabel76.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel76.setText("Promociones");

        DesayunoMerienda.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        DesayunoMerienda.setText("Desayunos Meriendas");
        DesayunoMerienda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DesayunoMeriendaActionPerformed(evt);
            }
        });

        empleadosPromo.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        empleadosPromo.setText("Empleados");
        empleadosPromo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empleadosPromoActionPerformed(evt);
            }
        });

        jLabel77.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        jLabel77.setText("Uso Personal");

        jLabel43.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel43.setText("Productos a elegir");

        javax.swing.GroupLayout SelectOrderLayout = new javax.swing.GroupLayout(SelectOrder.getContentPane());
        SelectOrder.getContentPane().setLayout(SelectOrderLayout);
        SelectOrderLayout.setHorizontalGroup(
            SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SelectOrderLayout.createSequentialGroup()
                .addGap(302, 302, 302)
                .addComponent(whoAmILbl)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(SelectOrderLayout.createSequentialGroup()
                .addGroup(SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SelectOrderLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel40))
                    .addGroup(SelectOrderLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1))
                    .addGroup(SelectOrderLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SelectOrderLayout.createSequentialGroup()
                                .addComponent(seeConsuming)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(closeTable)
                                .addGap(46, 46, 46)
                                .addComponent(payAction, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(deleteTable, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35))
                            .addGroup(SelectOrderLayout.createSequentialGroup()
                                .addComponent(jLabel50)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(SelectOrderLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SelectOrderLayout.createSequentialGroup()
                        .addComponent(jLabel43)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(SelectOrderLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(employeeNameCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(231, 231, 231))
                    .addGroup(SelectOrderLayout.createSequentialGroup()
                        .addGroup(SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(drinkAlcohol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(drinkNoAlcohol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(44, 44, 44)
                        .addGroup(SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(candyProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(saladProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SelectOrderLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                                .addGroup(SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(ComerPromos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(empleadosPromo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(DesayunoMerienda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(48, Short.MAX_VALUE))
                            .addGroup(SelectOrderLayout.createSequentialGroup()
                                .addGap(82, 82, 82)
                                .addComponent(jLabel76)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(SelectOrderLayout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(jLabel77)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        SelectOrderLayout.setVerticalGroup(
            SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SelectOrderLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(SelectOrderLayout.createSequentialGroup()
                        .addComponent(whoAmILbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(employeeNameCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel43)
                        .addGap(18, 18, 18)
                        .addGroup(SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(candyProducts, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                            .addComponent(drinkNoAlcohol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(SelectOrderLayout.createSequentialGroup()
                        .addComponent(jLabel76)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(DesayunoMerienda, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComerPromos, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(SelectOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(drinkAlcohol, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                    .addComponent(saladProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(SelectOrderLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel77)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(empleadosPromo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 17)); // NOI18N
        jLabel6.setText("Cafe");

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 17)); // NOI18N
        jLabel7.setText("Bebidas");

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

        pocilloCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        pocilloCheck.setText("Pocillo");
        pocilloCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pocilloCheckActionPerformed(evt);
            }
        });

        jarritoCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jarritoCheck.setText("Jarrito");
        jarritoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jarritoCheckActionPerformed(evt);
            }
        });

        cafeConLecheCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cafeConLecheCheck.setText("Café con leche");
        cafeConLecheCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cafeConLecheCheckActionPerformed(evt);
            }
        });

        cafeDobleCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cafeDobleCheck.setText("Doble");
        cafeDobleCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cafeDobleCheckActionPerformed(evt);
            }
        });

        CapuccinoCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        CapuccinoCheck.setText("Capuccino");
        CapuccinoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CapuccinoCheckActionPerformed(evt);
            }
        });

        LatteSaborizadosCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        LatteSaborizadosCheck.setText("Latte saborizados");
        LatteSaborizadosCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LatteSaborizadosCheckActionPerformed(evt);
            }
        });

        delTiempoCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        delTiempoCheck.setText("Del Tiempo");
        delTiempoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delTiempoCheckActionPerformed(evt);
            }
        });

        bombonCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        bombonCheck.setText("Bombon");
        bombonCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bombonCheckActionPerformed(evt);
            }
        });

        cafeIceCappuCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cafeIceCappuCheck.setText("ice Cappuccino");
        cafeIceCappuCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cafeIceCappuCheckActionPerformed(evt);
            }
        });

        cafeIceMassimoCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cafeIceMassimoCheck.setText("ice massimo");
        cafeIceMassimoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cafeIceMassimoCheckActionPerformed(evt);
            }
        });

        CapuccinoItalianoCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        CapuccinoItalianoCheck.setText("Capuccino Italiano");
        CapuccinoItalianoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CapuccinoItalianoCheckActionPerformed(evt);
            }
        });

        iceTeaCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        iceTeaCheck.setText("ice Tea ");
        iceTeaCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iceTeaCheckActionPerformed(evt);
            }
        });

        GaseosaLineaCocacheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        GaseosaLineaCocacheck.setText("Gaseosas línea Coca 354ml");
        GaseosaLineaCocacheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GaseosaLineaCocacheckActionPerformed(evt);
            }
        });

        AguaC_S_GasCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        AguaC_S_GasCheck.setText("Agua, Agua con gas");
        AguaC_S_GasCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AguaC_S_GasCheckActionPerformed(evt);
            }
        });

        aguaSaborizadaCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        aguaSaborizadaCheck.setText("Saborizada (pomelo, manzana, Naranja)");
        aguaSaborizadaCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aguaSaborizadaCheckActionPerformed(evt);
            }
        });

        cepitaCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cepitaCheck.setText("Cepita (naranja, manzana)");
        cepitaCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cepitaCheckActionPerformed(evt);
            }
        });

        tonicCoffeeCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        tonicCoffeeCheck.setText("tonic coffee");
        tonicCoffeeCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tonicCoffeeCheckActionPerformed(evt);
            }
        });

        submarinoCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        submarinoCheck.setText("Submarino");
        submarinoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submarinoCheckActionPerformed(evt);
            }
        });

        chocolatadaCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        chocolatadaCheck.setText("Chocolatada");
        chocolatadaCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chocolatadaCheckActionPerformed(evt);
            }
        });

        jLabel98.setFont(new java.awt.Font("Dialog", 0, 17)); // NOI18N
        jLabel98.setText("Café frío");

        pocilloPrice.setText("75");

        jLabel119.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel119.setText("$");

        jLabel122.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel122.setText("$");

        jarritoPrice.setText("90");

        jLabel132.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel132.setText("$");

        cafeConLechePrice.setText("100");

        jLabel135.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel135.setText("$");

        cafeDoblePrice.setText("105");

        jLabel137.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel137.setText("$");

        CapuccinoPrice.setText("150");

        jLabel139.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel139.setText("$");

        CapuccinoItalianoPrice.setText("180");

        jLabel141.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel141.setText("$");

        LattesaborizadosPrice.setText("150");

        submarinoPrice.setText("150");

        jLabel142.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel142.setText("$");

        chocolatadaPrice.setText("120");

        jLabel143.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel143.setText("$");

        hotCiokCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        hotCiokCheck.setText("Hotciok");
        hotCiokCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hotCiokCheckActionPerformed(evt);
            }
        });

        jLabel144.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel144.setText("$");

        hotCiokPrice.setText("190");

        teCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        teCheck.setText("Té");
        teCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teCheckActionPerformed(evt);
            }
        });

        jLabel145.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel145.setText("$");

        tePrice.setText("100");

        jLabel106.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel106.setText("$");

        cafeDelTiempoPrice.setText("100");

        jLabel134.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel134.setText("$");

        bombonPrice.setText("125");

        jLabel138.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel138.setText("$");

        cafeIceCappuPrice.setText("160");

        jLabel146.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel146.setText("$");

        jLabel147.setText("150");

        jLabel148.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel148.setText("$");

        tonicCoffeePrice.setText("170");

        jLabel150.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel150.setText("$");

        iceTeaPrice.setText("100");

        jLabel124.setFont(new java.awt.Font("Dialog", 0, 17)); // NOI18N
        jLabel124.setText("Batidos");

        FrapuccinoCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        FrapuccinoCheck.setText("Frapuccino");
        FrapuccinoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FrapuccinoCheckActionPerformed(evt);
            }
        });

        jLabel136.setText("$");

        FrapuccinoPrice.setText("190");

        milkShakeCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        milkShakeCheck.setText("Milkshake");
        milkShakeCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                milkShakeCheckActionPerformed(evt);
            }
        });

        jLabel140.setText("$");

        MilkshakePrice.setText("190");

        LicuadoCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        LicuadoCheck.setText("LIcuados");
        LicuadoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LicuadoCheckActionPerformed(evt);
            }
        });

        jLabel149.setText("$");

        licuadosPrice.setText("175");

        limonadaCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        limonadaCheck.setText("Limonada");
        limonadaCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limonadaCheckActionPerformed(evt);
            }
        });

        jLabel151.setText("$");

        limonadaPrice.setText("150");

        batidoExprimidoCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        batidoExprimidoCheck.setText("exprimido");
        batidoExprimidoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batidoExprimidoCheckActionPerformed(evt);
            }
        });

        jLabel152.setText("$");

        batidoExprimidoPrice.setText("15");

        jLabel153.setText("$");

        GaseosaLineaCocaPrice.setText("90");

        jLabel155.setText("$");

        AguaC_S_GasPrice.setText("90");

        jLabel157.setText("$");

        aguaSaborizadaPrice.setText("90");

        jLabel159.setText("$");

        cepitaPrice.setText("60");

        javax.swing.GroupLayout DrinkNoAlcoholFrameLayout = new javax.swing.GroupLayout(DrinkNoAlcoholFrame.getContentPane());
        DrinkNoAlcoholFrame.getContentPane().setLayout(DrinkNoAlcoholFrameLayout);
        DrinkNoAlcoholFrameLayout.setHorizontalGroup(
            DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cafeConLecheCheck)
                                                    .addComponent(jarritoCheck)
                                                    .addComponent(pocilloCheck)
                                                    .addComponent(cafeDobleCheck)
                                                    .addComponent(LatteSaborizadosCheck)
                                                    .addComponent(CapuccinoItalianoCheck)
                                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                        .addComponent(cafeConLecheSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel132)
                                                        .addGap(2, 2, 2)
                                                        .addComponent(cafeConLechePrice))
                                                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                        .addGap(2, 2, 2)
                                                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                                .addComponent(CapuccinoItalianoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel139)
                                                                .addGap(2, 2, 2)
                                                                .addComponent(CapuccinoItalianoPrice))
                                                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                                .addComponent(cafeDobleSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel135)
                                                                .addGap(2, 2, 2)
                                                                .addComponent(cafeDoblePrice))
                                                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                                .addComponent(LattesaborizadosSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel141)
                                                                .addGap(2, 2, 2)
                                                                .addComponent(LattesaborizadosPrice))
                                                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                                .addComponent(submarinoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel142)
                                                                .addGap(2, 2, 2)
                                                                .addComponent(submarinoPrice))
                                                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                                .addComponent(chocolatadaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel143)
                                                                .addGap(2, 2, 2)
                                                                .addComponent(chocolatadaPrice))))
                                                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                        .addComponent(pocilloSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel119)
                                                        .addGap(2, 2, 2)
                                                        .addComponent(pocilloPrice))
                                                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                        .addComponent(jarritoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel122)
                                                        .addGap(2, 2, 2)
                                                        .addComponent(jarritoPrice))))
                                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                .addComponent(CapuccinoCheck)
                                                .addGap(18, 18, Short.MAX_VALUE)
                                                .addComponent(CapuccinoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel137)
                                                .addGap(2, 2, 2)
                                                .addComponent(CapuccinoPrice))
                                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                .addComponent(chocolatadaCheck)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(hotCiokCheck)
                                                    .addComponent(teCheck))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                        .addComponent(hotCiokSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel144)
                                                        .addGap(2, 2, 2)
                                                        .addComponent(hotCiokPrice))
                                                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                        .addComponent(teSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel145)
                                                        .addGap(2, 2, 2)
                                                        .addComponent(tePrice)))))
                                        .addGap(63, 63, 63))
                                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                        .addComponent(submarinoCheck)
                                        .addGap(181, 181, 181)))
                                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cafeIceMassimoCheck)
                                                    .addComponent(iceTeaCheck)
                                                    .addComponent(tonicCoffeeCheck))
                                                .addGap(25, 25, 25)
                                                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(tonicCoffeeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(iceTeaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(cafeIceMassimoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(cafeDelTiempoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                    .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel124)
                                                        .addComponent(cafeIceCappuCheck))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(cafeIceCappuSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(bombonSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                .addComponent(jLabel150)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(iceTeaPrice)
                                                .addGap(5, 5, 5))
                                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                        .addComponent(jLabel138)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(cafeIceCappuPrice))
                                                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                        .addComponent(jLabel146)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel147))
                                                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                        .addComponent(jLabel148)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(tonicCoffeePrice)))
                                                .addGap(0, 0, Short.MAX_VALUE))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                        .addComponent(acceptNoAlcoholDrinks, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cancelNoAlcoholDrinks))
                                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                .addComponent(batidoExprimidoCheck)
                                                .addGap(18, 18, 18)
                                                .addComponent(batidoExprimidoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel152)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(batidoExprimidoPrice))
                                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                .addComponent(FrapuccinoCheck)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(FrapuccinoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel136)
                                                .addGap(1, 1, 1)
                                                .addComponent(FrapuccinoPrice))
                                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(delTiempoCheck)
                                                    .addComponent(bombonCheck))
                                                .addGap(74, 74, 74)
                                                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                        .addComponent(jLabel106)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(cafeDelTiempoPrice))
                                                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                        .addComponent(jLabel134)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(bombonPrice))))
                                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                .addGap(64, 64, 64)
                                                .addComponent(jLabel98))
                                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                .addComponent(LicuadoCheck)
                                                .addGap(24, 24, 24)
                                                .addComponent(licuadosSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel149)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(licuadosPrice))
                                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                .addComponent(milkShakeCheck)
                                                .addGap(21, 21, 21)
                                                .addComponent(MilkshakeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel140)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(MilkshakePrice))
                                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                .addComponent(limonadaCheck)
                                                .addGap(21, 21, 21)
                                                .addComponent(limonadaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel151)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(limonadaPrice)))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(aguaSaborizadaCheck)
                                            .addComponent(cepitaCheck))
                                        .addGap(18, 18, 18)
                                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                .addComponent(cepitaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel159)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cepitaPrice))
                                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                .addComponent(aguaSaborizadaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel157)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(aguaSaborizadaPrice))))
                                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(GaseosaLineaCocacheck)
                                            .addComponent(AguaC_S_GasCheck))
                                        .addGap(95, 95, 95)
                                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(GaseosaLineaCocaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(AguaC_S_GasSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                .addComponent(jLabel153)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(GaseosaLineaCocaPrice))
                                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                                .addComponent(jLabel155)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(AguaC_S_GasPrice)))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        DrinkNoAlcoholFrameLayout.setVerticalGroup(
            DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel98)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                    .addComponent(pocilloCheck)
                                    .addGap(4, 4, 4)
                                    .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jarritoCheck)
                                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jarritoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jarritoPrice)
                                            .addComponent(jLabel122))))
                                .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                    .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(delTiempoCheck)
                                        .addComponent(jLabel106)
                                        .addComponent(cafeDelTiempoPrice)
                                        .addComponent(cafeDelTiempoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(bombonCheck)
                                        .addComponent(bombonSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel134)
                                        .addComponent(bombonPrice))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(pocilloSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(pocilloPrice)
                                    .addComponent(jLabel119))
                                .addGap(40, 40, 40)))
                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cafeIceCappuCheck)
                            .addComponent(jLabel138)
                            .addComponent(cafeIceCappuPrice)
                            .addComponent(cafeIceCappuSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(cafeConLecheCheck, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cafeConLecheSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cafeConLechePrice)
                        .addComponent(jLabel132)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cafeDobleSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cafeDoblePrice)
                        .addComponent(jLabel135)
                        .addComponent(cafeDobleCheck))
                    .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cafeIceMassimoCheck)
                        .addComponent(cafeIceMassimoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel146)
                        .addComponent(jLabel147)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(CapuccinoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(CapuccinoPrice)
                        .addComponent(jLabel137)
                        .addComponent(tonicCoffeeCheck)
                        .addComponent(tonicCoffeeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel148)
                        .addComponent(tonicCoffeePrice))
                    .addComponent(CapuccinoCheck, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DrinkNoAlcoholFrameLayout.createSequentialGroup()
                        .addComponent(iceTeaCheck)
                        .addGap(18, 18, 18)
                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LatteSaborizadosCheck)
                            .addComponent(jLabel124)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DrinkNoAlcoholFrameLayout.createSequentialGroup()
                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CapuccinoItalianoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CapuccinoItalianoPrice)
                            .addComponent(jLabel139)
                            .addComponent(CapuccinoItalianoCheck))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LattesaborizadosSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LattesaborizadosPrice)
                            .addComponent(jLabel141)))
                    .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel150)
                        .addComponent(iceTeaPrice)
                        .addComponent(iceTeaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(submarinoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(submarinoCheck)
                    .addComponent(submarinoPrice)
                    .addComponent(jLabel142)
                    .addComponent(FrapuccinoCheck)
                    .addComponent(FrapuccinoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel136)
                    .addComponent(FrapuccinoPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chocolatadaCheck)
                    .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(chocolatadaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(chocolatadaPrice)
                        .addComponent(jLabel143)
                        .addComponent(milkShakeCheck)
                        .addComponent(MilkshakeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel140)
                        .addComponent(MilkshakePrice)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                        .addComponent(hotCiokCheck)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(teCheck))
                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LicuadoCheck)
                            .addComponent(licuadosSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel149)
                            .addComponent(licuadosPrice))
                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(batidoExprimidoCheck))
                            .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(limonadaCheck)
                                    .addComponent(limonadaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel151)
                                    .addComponent(limonadaPrice))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(batidoExprimidoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel152)
                                    .addComponent(batidoExprimidoPrice)))))
                    .addGroup(DrinkNoAlcoholFrameLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(teSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tePrice)
                            .addComponent(jLabel145)))
                    .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(hotCiokSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(hotCiokPrice)
                        .addComponent(jLabel144)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GaseosaLineaCocacheck)
                    .addComponent(GaseosaLineaCocaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel153)
                    .addComponent(GaseosaLineaCocaPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AguaC_S_GasCheck)
                    .addComponent(AguaC_S_GasSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel155)
                        .addComponent(AguaC_S_GasPrice)))
                .addGap(6, 6, 6)
                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aguaSaborizadaCheck)
                    .addComponent(aguaSaborizadaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel157)
                        .addComponent(aguaSaborizadaPrice)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cepitaCheck)
                    .addComponent(cepitaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(DrinkNoAlcoholFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel159)
                        .addComponent(cepitaPrice)))
                .addGap(39, 39, 39)
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

        jLabel84.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel84.setText("Sándwich ");

        tostadoSdwCheck.setText("Tostado");
        tostadoSdwCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tostadoSdwCheckActionPerformed(evt);
            }
        });

        jLabel87.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel87.setText("$");

        tostadoSdwPrice.setText("200");

        arabeSdwCheck.setText("Árabe");
        arabeSdwCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arabeSdwCheckActionPerformed(evt);
            }
        });

        arabeSdwPrice.setText("180");

        medialunaSdwCheck.setText("Medialuna Rellena ");
        medialunaSdwCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                medialunaSdwCheckActionPerformed(evt);
            }
        });

        medialunaSdwPrice.setText("70");

        jLabel102.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel102.setText(" Ciabatta De: ");

        cbtPolloCheck.setText("Pollo, Vegetales asados");
        cbtPolloCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbtPolloCheckActionPerformed(evt);
            }
        });

        cbtPolloPrice.setText("290");

        jLabel110.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel110.setText("$");

        jLabel115.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel115.setText("$");

        jLabel121.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel121.setText("$");

        sbtMilanesaCheck.setText("Milanesa Con lechuga y tomate");
        sbtMilanesaCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sbtMilanesaCheckActionPerformed(evt);
            }
        });

        sbtMilanesaPrice.setText("250");

        jLabel123.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel123.setText("$");

        sbtVerdurasCheck.setText("Verduras Asadas");
        sbtVerdurasCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sbtVerdurasCheckActionPerformed(evt);
            }
        });

        sbtVerdurasPrice.setText("250");

        jLabel125.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel125.setText("$");

        sbtAtunCheck.setText("Atún, huevo, olivas, mayonesa, rúcula y tomate");
        sbtAtunCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sbtAtunCheckActionPerformed(evt);
            }
        });

        sbtAtunPrice.setText("290");

        jLabel127.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel127.setText("$");

        sbtCrudoCheck.setText("Jamon crudo, queso, rucula, tomate");
        sbtCrudoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sbtCrudoCheckActionPerformed(evt);
            }
        });

        sbtCrudoPrice.setText("250");

        jLabel129.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel129.setText("$");

        jLabel130.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel130.setText("Salados");

        sdwEmpandaCheck.setText("Empanadas (carne, pollo, jamon y queso) c/u");
        sdwEmpandaCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sdwEmpandaCheckActionPerformed(evt);
            }
        });

        jLabel131.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel131.setText("$");

        sdwEmpandaPRice.setText("55");

        sdwTartasCheck.setText("Tartas (verduras asadas, jamon y queso)");
        sdwTartasCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sdwTartasCheckActionPerformed(evt);
            }
        });

        jLabel133.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel133.setText("$");

        sdwTartasPrice.setText("220");

        saveSandwichORder.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        saveSandwichORder.setText("Guardar Orden");
        saveSandwichORder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveSandwichORderActionPerformed(evt);
            }
        });

        closeSandwichORder.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        closeSandwichORder.setText("Cerrar");
        closeSandwichORder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeSandwichORderActionPerformed(evt);
            }
        });

        jLabel90.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        jLabel90.setText("cebolla, zapallito, tomate, berenjena, zanahoria, morrones, queso cremoso");

        jLabel94.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        jLabel94.setText("cebolla, zapallito, tomate, berenjena, zanahoria, morrones, queso, semilla");

        javax.swing.GroupLayout SaladsProductFrameLayout = new javax.swing.GroupLayout(SaladsProductFrame.getContentPane());
        SaladsProductFrame.getContentPane().setLayout(SaladsProductFrameLayout);
        SaladsProductFrameLayout.setHorizontalGroup(
            SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SaladsProductFrameLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(saveSandwichORder)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(closeSandwichORder)
                .addGap(16, 16, 16))
            .addComponent(jSeparator10)
            .addGroup(SaladsProductFrameLayout.createSequentialGroup()
                .addGroup(SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SaladsProductFrameLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel130))
                    .addGroup(SaladsProductFrameLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(SaladsProductFrameLayout.createSequentialGroup()
                                .addComponent(sdwEmpandaCheck)
                                .addGap(18, 18, 18)
                                .addComponent(sdwEmpandaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(jLabel131))
                            .addGroup(SaladsProductFrameLayout.createSequentialGroup()
                                .addComponent(sdwTartasCheck)
                                .addGap(46, 46, 46)
                                .addComponent(sdwTartasSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel133)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sdwTartasPrice)
                            .addComponent(sdwEmpandaPRice)))
                    .addGroup(SaladsProductFrameLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SaladsProductFrameLayout.createSequentialGroup()
                                .addGroup(SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel90, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel94, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SaladsProductFrameLayout.createSequentialGroup()
                                        .addComponent(sbtAtunSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(19, 19, 19)
                                        .addComponent(jLabel127)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sbtAtunPrice))
                            .addGroup(SaladsProductFrameLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SaladsProductFrameLayout.createSequentialGroup()
                                        .addComponent(sbtAtunCheck)
                                        .addGap(30, 30, 30))
                                    .addGroup(SaladsProductFrameLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(SaladsProductFrameLayout.createSequentialGroup()
                                                .addComponent(sbtCrudoCheck)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(SaladsProductFrameLayout.createSequentialGroup()
                                                        .addComponent(sbtMilanesaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jLabel123)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(sbtMilanesaPrice))
                                                    .addGroup(SaladsProductFrameLayout.createSequentialGroup()
                                                        .addComponent(sbtCrudoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jLabel129)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(sbtCrudoPrice))))
                                            .addComponent(sbtMilanesaCheck)))))
                            .addGroup(SaladsProductFrameLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbtPolloCheck)
                                    .addComponent(sbtVerdurasCheck))
                                .addGap(51, 51, 51)
                                .addGroup(SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(SaladsProductFrameLayout.createSequentialGroup()
                                        .addComponent(cbtPolloSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(25, 25, 25)
                                        .addComponent(jLabel121)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbtPolloPrice))
                                    .addGroup(SaladsProductFrameLayout.createSequentialGroup()
                                        .addComponent(sbtVerdurasSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(25, 25, 25)
                                        .addComponent(jLabel125)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(sbtVerdurasPrice))))
                            .addComponent(jLabel102)))
                    .addGroup(SaladsProductFrameLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SaladsProductFrameLayout.createSequentialGroup()
                                .addComponent(arabeSdwCheck)
                                .addGap(50, 50, 50)
                                .addComponent(arabeSdwSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel110)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(arabeSdwPrice))
                            .addGroup(SaladsProductFrameLayout.createSequentialGroup()
                                .addComponent(tostadoSdwCheck)
                                .addGap(35, 35, 35)
                                .addComponent(tostadoSdwSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21)
                                .addComponent(jLabel87)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tostadoSdwPrice))
                            .addComponent(jLabel84)
                            .addGroup(SaladsProductFrameLayout.createSequentialGroup()
                                .addComponent(medialunaSdwCheck)
                                .addGap(35, 35, 35)
                                .addComponent(medialunaSdwSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel115)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(medialunaSdwPrice)))))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        SaladsProductFrameLayout.setVerticalGroup(
            SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SaladsProductFrameLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel84)
                .addGap(18, 18, 18)
                .addGroup(SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tostadoSdwCheck)
                    .addComponent(tostadoSdwSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel87)
                    .addComponent(tostadoSdwPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(arabeSdwCheck)
                    .addComponent(arabeSdwSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(arabeSdwPrice)
                    .addComponent(jLabel110))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(medialunaSdwCheck)
                    .addComponent(medialunaSdwSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(medialunaSdwPrice)
                    .addComponent(jLabel115))
                .addGap(18, 18, 18)
                .addComponent(jLabel102)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbtPolloCheck)
                    .addComponent(cbtPolloSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbtPolloPrice)
                    .addComponent(jLabel121))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel90)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sbtVerdurasCheck)
                    .addComponent(sbtVerdurasSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sbtVerdurasPrice)
                    .addComponent(jLabel125))
                .addGap(4, 4, 4)
                .addComponent(jLabel94)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sbtAtunCheck)
                    .addComponent(sbtAtunSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sbtAtunPrice)
                    .addComponent(jLabel127))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sbtMilanesaCheck)
                    .addComponent(sbtMilanesaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sbtMilanesaPrice)
                    .addComponent(jLabel123))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sbtCrudoCheck)
                    .addComponent(sbtCrudoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sbtCrudoPrice)
                    .addComponent(jLabel129))
                .addGap(26, 26, 26)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel130)
                .addGap(18, 18, 18)
                .addGroup(SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sdwEmpandaCheck)
                    .addComponent(sdwEmpandaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sdwEmpandaPRice)
                    .addComponent(jLabel131))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sdwTartasCheck)
                    .addComponent(sdwTartasSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sdwTartasPrice)
                    .addComponent(jLabel133))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addGroup(SaladsProductFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveSandwichORder)
                    .addComponent(closeSandwichORder))
                .addGap(18, 18, 18))
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

        jLabel78.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel78.setText("Desayunos y Meriendas");

        cafe2medialunasCheck.setText("Café + 2 Medialunas ");
        cafe2medialunasCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cafe2medialunasCheckActionPerformed(evt);
            }
        });

        cafe2medilunasPrice.setText("125");

        jLabel81.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel81.setText("$");

        cafeTortaIndividualCheck.setText("Café + Torta individual");
        cafeTortaIndividualCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cafeTortaIndividualCheckActionPerformed(evt);
            }
        });

        cafeTortaIndPrice.setText("275");

        jLabel83.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel83.setText("$");

        cafeTostadoArabeCheck.setText("Café + Tostado Árabe");
        cafeTostadoArabeCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cafeTostadoArabeCheckActionPerformed(evt);
            }
        });

        cafeArabePrice.setText("250");

        jLabel85.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel85.setText("$");

        cafeCuadradoDucleCheck.setText("Café + Cuadrado Dulce");
        cafeCuadradoDucleCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cafeCuadradoDucleCheckActionPerformed(evt);
            }
        });

        cafeCuadradoDulcePrice.setText("190");

        jLabel89.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel89.setText("$");

        cafe2GallesChocoCheck.setText("Café + 2 Galletas de chip de chocolate artesanales");
        cafe2GallesChocoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cafe2GallesChocoCheckActionPerformed(evt);
            }
        });

        cafe2gallesPrice.setText("125");

        jLabel91.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel91.setText("$");

        cappuccinoTostadasCheck.setText("Cappuccino + Tostadas integrales");
        cappuccinoTostadasCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cappuccinoTostadasCheckActionPerformed(evt);
            }
        });

        cappuccinoTostadaPrice.setText("250");

        jLabel93.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel93.setText("$");

        cappucinoMedialunaCheck.setText("Cappuccino + Medialuna Jyq");
        cappucinoMedialunaCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cappucinoMedialunaCheckActionPerformed(evt);
            }
        });

        cappucinoMedialunaPrice.setText("180");

        jLabel95.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel95.setText("$");

        naranja2mediaLunasCheck.setText("Exprimido de Naranja + 2 Medialunas Jyq");
        naranja2mediaLunasCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                naranja2mediaLunasCheckActionPerformed(evt);
            }
        });

        naranja2medialunasPrice.setText("250");

        jLabel97.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel97.setText("$");

        naranjaTostadoCheck.setText("Exprimido de Naranja + Tostado");
        naranjaTostadoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                naranjaTostadoCheckActionPerformed(evt);
            }
        });

        naranjaTostadoPrice.setText("330");

        jLabel99.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel99.setText("$");

        licuadoMedioTostadoCheck.setText("Licuado(banana, durazno, frutilla) + ½ tostado");
        licuadoMedioTostadoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                licuadoMedioTostadoCheckActionPerformed(evt);
            }
        });

        licuadoMedioTostadoPrice.setText("250");

        jLabel101.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel101.setText("$");

        limonadaTortaIndividiualCheck.setText("Limonada + Torta Individual");
        limonadaTortaIndividiualCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limonadaTortaIndividiualCheckActionPerformed(evt);
            }
        });

        limonadaTortaIndPrice.setText("300");

        jLabel103.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel103.setText("$");

        alfajorMaicenaChocolatadaCheck.setText("Alfajor de Maicena + Chocolatada");
        alfajorMaicenaChocolatadaCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alfajorMaicenaChocolatadaCheckActionPerformed(evt);
            }
        });

        alfajorMaicenaChocoPrice.setText("180");

        jLabel105.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel105.setText("$");

        milkshakesTortaCheck.setText("2 Milkshakes + Torta Individual");
        milkshakesTortaCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                milkshakesTortaCheckActionPerformed(evt);
            }
        });

        milkshakesTortaIndividualPrice.setText("400");

        jLabel107.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel107.setText("$");

        licuado2_1_TostadoIndCheck.setText("2 Licuados + 1 Tostado");
        licuado2_1_TostadoIndCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                licuado2_1_TostadoIndCheckActionPerformed(evt);
            }
        });

        licuados_2_1_TostadoPrice.setText("380");

        jLabel109.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel109.setText("$");

        copaYogurtCheck.setText("Copa de yogurt con granola patagónica y colchón de frutas");
        copaYogurtCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copaYogurtCheckActionPerformed(evt);
            }
        });

        yogurtPrice.setText("145");

        jLabel111.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel111.setText("$");

        tostadasDobleExprEspCheck.setText("Tostadas (2) con queso crema, laminas de fruta y miel + exprimido +espresso");
        tostadasDobleExprEspCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tostadasDobleExprEspCheckActionPerformed(evt);
            }
        });

        tostadasExpEspPrice.setText("300");

        jLabel113.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel113.setText("$");

        jLabel114.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        jLabel114.setText("con queso crema y mermelada o dulce de leche");

        frutasCafeTostadoCheck.setText("Frutas de estación + café + ½ tostado");
        frutasCafeTostadoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                frutasCafeTostadoCheckActionPerformed(evt);
            }
        });

        frutaCafePrice.setText("300");

        iceCappuccinoCheck.setText("Ice capuccino + alfajor");
        iceCappuccinoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iceCappuccinoCheckActionPerformed(evt);
            }
        });

        iceCapuccinoPrice.setText("140");

        savePromoBreakfast.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        savePromoBreakfast.setText("Guardar Orden");
        savePromoBreakfast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savePromoBreakfastActionPerformed(evt);
            }
        });

        cosePromoBreakfast.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cosePromoBreakfast.setText("Cancelar");
        cosePromoBreakfast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cosePromoBreakfastActionPerformed(evt);
            }
        });

        jLabel117.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel117.setText("$");

        jLabel118.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel118.setText("$");

        javax.swing.GroupLayout PromosBreakfastFrmLayout = new javax.swing.GroupLayout(PromosBreakfastFrm.getContentPane());
        PromosBreakfastFrm.getContentPane().setLayout(PromosBreakfastFrmLayout);
        PromosBreakfastFrmLayout.setHorizontalGroup(
            PromosBreakfastFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PromosBreakfastFrmLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(PromosBreakfastFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PromosBreakfastFrmLayout.createSequentialGroup()
                        .addComponent(iceCappuccinoCheck)
                        .addGap(33, 33, 33)
                        .addComponent(iceCapuccinoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel118)
                        .addGap(1, 1, 1)
                        .addComponent(iceCapuccinoPrice))
                    .addGroup(PromosBreakfastFrmLayout.createSequentialGroup()
                        .addComponent(frutasCafeTostadoCheck)
                        .addGap(18, 18, 18)
                        .addComponent(frutaCafeTostadoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel117)
                        .addGap(7, 7, 7)
                        .addComponent(frutaCafePrice))
                    .addGroup(PromosBreakfastFrmLayout.createSequentialGroup()
                        .addComponent(tostadasDobleExprEspCheck)
                        .addGap(18, 18, 18)
                        .addComponent(tostadasExpEspSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel113)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tostadasExpEspPrice))
                    .addGroup(PromosBreakfastFrmLayout.createSequentialGroup()
                        .addComponent(copaYogurtCheck)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(yogurtSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel111)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(yogurtPrice))
                    .addGroup(PromosBreakfastFrmLayout.createSequentialGroup()
                        .addComponent(licuado2_1_TostadoIndCheck)
                        .addGap(99, 99, 99)
                        .addComponent(licuados_2_1_TostadoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel109)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(licuados_2_1_TostadoPrice))
                    .addGroup(PromosBreakfastFrmLayout.createSequentialGroup()
                        .addComponent(milkshakesTortaCheck)
                        .addGap(42, 42, 42)
                        .addComponent(milkshakesTortaIndividualSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel107)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(milkshakesTortaIndividualPrice))
                    .addGroup(PromosBreakfastFrmLayout.createSequentialGroup()
                        .addComponent(alfajorMaicenaChocolatadaCheck)
                        .addGap(23, 23, 23)
                        .addComponent(alfajorMaicenaChocoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel105)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(alfajorMaicenaChocoPrice))
                    .addGroup(PromosBreakfastFrmLayout.createSequentialGroup()
                        .addComponent(limonadaTortaIndividiualCheck)
                        .addGap(66, 66, 66)
                        .addComponent(limonadaTortaIndividualSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel103)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(limonadaTortaIndPrice))
                    .addGroup(PromosBreakfastFrmLayout.createSequentialGroup()
                        .addComponent(licuadoMedioTostadoCheck)
                        .addGap(32, 32, 32)
                        .addComponent(licuadoMedioTostadoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel101)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(licuadoMedioTostadoPrice))
                    .addGroup(PromosBreakfastFrmLayout.createSequentialGroup()
                        .addComponent(naranjaTostadoCheck)
                        .addGap(42, 42, 42)
                        .addComponent(naranjaTostadoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel99)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(naranjaTostadoPrice))
                    .addGroup(PromosBreakfastFrmLayout.createSequentialGroup()
                        .addComponent(naranja2mediaLunasCheck)
                        .addGap(30, 30, 30)
                        .addComponent(naranja2medialunasSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel97)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(naranja2medialunasPrice))
                    .addGroup(PromosBreakfastFrmLayout.createSequentialGroup()
                        .addComponent(cappucinoMedialunaCheck)
                        .addGap(23, 23, 23)
                        .addComponent(cappucinnoMedialunaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel95)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cappucinoMedialunaPrice))
                    .addGroup(PromosBreakfastFrmLayout.createSequentialGroup()
                        .addComponent(cappuccinoTostadasCheck)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel114)
                        .addGap(26, 26, 26)
                        .addComponent(cappuccinoTostadaSppiner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel93)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cappuccinoTostadaPrice))
                    .addGroup(PromosBreakfastFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(PromosBreakfastFrmLayout.createSequentialGroup()
                            .addComponent(cafeCuadradoDucleCheck)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cafeCuadraroDulceSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel89)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cafeCuadradoDulcePrice))
                        .addGroup(PromosBreakfastFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PromosBreakfastFrmLayout.createSequentialGroup()
                                .addComponent(cafeTostadoArabeCheck)
                                .addGap(13, 13, 13)
                                .addComponent(cafeTostadoArabeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel85)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cafeArabePrice))
                            .addGroup(PromosBreakfastFrmLayout.createSequentialGroup()
                                .addComponent(cafeTortaIndividualCheck)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cafeTortaIndSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel83)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cafeTortaIndPrice))
                            .addGroup(PromosBreakfastFrmLayout.createSequentialGroup()
                                .addComponent(cafe2medialunasCheck)
                                .addGap(18, 18, 18)
                                .addComponent(cafe2MedialunasSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel81)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cafe2medilunasPrice))))
                    .addGroup(PromosBreakfastFrmLayout.createSequentialGroup()
                        .addGroup(PromosBreakfastFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel78)
                            .addGroup(PromosBreakfastFrmLayout.createSequentialGroup()
                                .addComponent(cafe2GallesChocoCheck)
                                .addGap(21, 21, 21)
                                .addComponent(Cafe2gallesChocoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(13, 13, 13)
                        .addComponent(jLabel91)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cafe2gallesPrice)))
                .addContainerGap(32, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PromosBreakfastFrmLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(savePromoBreakfast)
                .addGap(18, 18, 18)
                .addComponent(cosePromoBreakfast)
                .addGap(48, 48, 48))
        );
        PromosBreakfastFrmLayout.setVerticalGroup(
            PromosBreakfastFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PromosBreakfastFrmLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel78)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PromosBreakfastFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cafe2medialunasCheck)
                    .addComponent(cafe2medilunasPrice)
                    .addComponent(jLabel81)
                    .addComponent(cafe2MedialunasSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PromosBreakfastFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cafeTortaIndividualCheck)
                    .addComponent(cafeTortaIndPrice)
                    .addComponent(jLabel83)
                    .addComponent(cafeTortaIndSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PromosBreakfastFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cafeTostadoArabeCheck)
                    .addComponent(cafeArabePrice)
                    .addComponent(jLabel85)
                    .addComponent(cafeTostadoArabeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PromosBreakfastFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cafeCuadradoDucleCheck)
                    .addComponent(cafeCuadradoDulcePrice)
                    .addComponent(jLabel89)
                    .addComponent(cafeCuadraroDulceSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PromosBreakfastFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cafe2GallesChocoCheck)
                    .addComponent(cafe2gallesPrice)
                    .addComponent(jLabel91)
                    .addComponent(Cafe2gallesChocoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PromosBreakfastFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cappuccinoTostadasCheck)
                    .addComponent(cappuccinoTostadaPrice)
                    .addComponent(jLabel93)
                    .addComponent(cappuccinoTostadaSppiner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel114))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PromosBreakfastFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cappucinoMedialunaCheck)
                    .addComponent(cappucinoMedialunaPrice)
                    .addComponent(jLabel95)
                    .addComponent(cappucinnoMedialunaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PromosBreakfastFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(naranja2mediaLunasCheck)
                    .addComponent(naranja2medialunasPrice)
                    .addComponent(jLabel97)
                    .addComponent(naranja2medialunasSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PromosBreakfastFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(naranjaTostadoCheck)
                    .addComponent(naranjaTostadoPrice)
                    .addComponent(jLabel99)
                    .addComponent(naranjaTostadoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PromosBreakfastFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(licuadoMedioTostadoCheck)
                    .addComponent(licuadoMedioTostadoPrice)
                    .addComponent(jLabel101)
                    .addComponent(licuadoMedioTostadoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PromosBreakfastFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(limonadaTortaIndividiualCheck)
                    .addComponent(limonadaTortaIndPrice)
                    .addComponent(jLabel103)
                    .addComponent(limonadaTortaIndividualSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PromosBreakfastFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(alfajorMaicenaChocolatadaCheck)
                    .addComponent(alfajorMaicenaChocoPrice)
                    .addComponent(jLabel105)
                    .addComponent(alfajorMaicenaChocoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PromosBreakfastFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(milkshakesTortaCheck)
                    .addComponent(milkshakesTortaIndividualPrice)
                    .addComponent(jLabel107)
                    .addComponent(milkshakesTortaIndividualSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PromosBreakfastFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(licuado2_1_TostadoIndCheck)
                    .addComponent(licuados_2_1_TostadoPrice)
                    .addComponent(jLabel109)
                    .addComponent(licuados_2_1_TostadoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PromosBreakfastFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(copaYogurtCheck)
                    .addComponent(yogurtPrice)
                    .addComponent(jLabel111)
                    .addComponent(yogurtSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PromosBreakfastFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tostadasDobleExprEspCheck)
                    .addComponent(tostadasExpEspPrice)
                    .addComponent(jLabel113)
                    .addComponent(tostadasExpEspSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PromosBreakfastFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(frutasCafeTostadoCheck)
                    .addComponent(frutaCafePrice)
                    .addComponent(frutaCafeTostadoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel117))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PromosBreakfastFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(iceCappuccinoCheck)
                    .addComponent(iceCapuccinoPrice)
                    .addComponent(iceCapuccinoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel118))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PromosBreakfastFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(savePromoBreakfast)
                    .addComponent(cosePromoBreakfast))
                .addContainerGap(30, Short.MAX_VALUE))
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

        deleteTableButton.setText("Eliminar");
        deleteTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteTableButtonActionPerformed(evt);
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
                        .addComponent(deleteTableButton)))
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
                .addComponent(deleteTableButton)
                .addContainerGap())
        );

        jLabel79.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel79.setText("Para Comer");

        ensaladaCesarCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        ensaladaCesarCheck.setText("Ensalada cesar + exprimido de naranja");
        ensaladaCesarCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ensaladaCesarCheckActionPerformed(evt);
            }
        });

        jLabel82.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel82.setText("$");

        ensaladaCesarPrice.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        ensaladaCesarPrice.setText("350");

        ensaladaIbericaCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        ensaladaIbericaCheck.setText("Ensalada ibérica (colchón verde, jamon crudo, queso azul, semillas, olivas) + limonada");
        ensaladaIbericaCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ensaladaIbericaCheckActionPerformed(evt);
            }
        });

        jLabel86.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel86.setText("$");

        ensaladaIbericaPrice.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        ensaladaIbericaPrice.setText("380");

        ensaladaPrimaveraCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        ensaladaPrimaveraCheck.setText("Ensalada primavera (arroz, atun, tomate, huevo, zanahoria) + saborizada");
        ensaladaPrimaveraCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ensaladaPrimaveraCheckActionPerformed(evt);
            }
        });

        jLabel88.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel88.setText("$");

        ensaladaPrimaveraPrice.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        ensaladaPrimaveraPrice.setText("330");

        ensaladaTropicalCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        ensaladaTropicalCheck.setText("Ensalada tropical (colchón verde, tomate, jamon, queso, huevo, crutons) + exprimido");
        ensaladaTropicalCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ensaladaTropicalCheckActionPerformed(evt);
            }
        });

        jLabel92.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel92.setText("$");

        ensaladaTropicalPrice.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        ensaladaTropicalPrice.setText("300");

        sandwichReimsPromoCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        sandwichReimsPromoCheck.setText("Sándwich Reims + exprimido de naranja (Vegetales asados, pollo, queso)");
        sandwichReimsPromoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sandwichReimsPromoCheckActionPerformed(evt);
            }
        });

        jLabel96.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel96.setText("$");

        sandwichReimsPromoPrice.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        sandwichReimsPromoPrice.setText("350");

        milanesaPromoCheck.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        milanesaPromoCheck.setText("Milanesa de pollo o ternera con ensalada  (lechuga y tomate o zanahoria y huevo) +  exprimido de naranja");
        milanesaPromoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                milanesaPromoCheckActionPerformed(evt);
            }
        });

        jLabel100.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel100.setText("$");

        milanesaPromoPrice.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        milanesaPromoPrice.setText("380");

        pizzaPromoCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        pizzaPromoCheck.setText("Pizza + gaseosa");
        pizzaPromoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pizzaPromoCheckActionPerformed(evt);
            }
        });

        jLabel104.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel104.setText("$");

        pizzaPromoPrice.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        pizzaPromoPrice.setText("290");

        panchoPromoCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        panchoPromoCheck.setText("2 panchos + Saborizada");
        panchoPromoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                panchoPromoCheckActionPerformed(evt);
            }
        });

        jLabel108.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel108.setText("$");

        panchoPromoPrice.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        panchoPromoPrice.setText("160");

        empanadaPromoCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        empanadaPromoCheck.setText("3 empanadas + gaseosa");
        empanadaPromoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empanadaPromoCheckActionPerformed(evt);
            }
        });

        jLabel112.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel112.setText("$");

        empanadaPromoPrice.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        empanadaPromoPrice.setText("200");

        arabePromoCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        arabePromoCheck.setText("Árabe de atún, rucula, tomate, olivas y huevo + Saborizada");
        arabePromoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arabePromoCheckActionPerformed(evt);
            }
        });

        jLabel116.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel116.setText("$");

        arabePromoPrice.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        arabePromoPrice.setText("360");

        sandwichPromoCheck.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        sandwichPromoCheck.setText("Sandwich jamon crudo, queso, rucula, tomate y olivas + cerveza");
        sandwichPromoCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sandwichPromoCheckActionPerformed(evt);
            }
        });

        jLabel120.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel120.setText("$");

        sandwichPromoPrice.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        sandwichPromoPrice.setText("380");

        savePromoLunch.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        savePromoLunch.setText("Guardar Orden");
        savePromoLunch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savePromoLunchActionPerformed(evt);
            }
        });

        cancelPromoLunch.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        cancelPromoLunch.setText("Cancelar");
        cancelPromoLunch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelPromoLunchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PromosLunchFrmLayout = new javax.swing.GroupLayout(PromosLunchFrm.getContentPane());
        PromosLunchFrm.getContentPane().setLayout(PromosLunchFrmLayout);
        PromosLunchFrmLayout.setHorizontalGroup(
            PromosLunchFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PromosLunchFrmLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(savePromoLunch)
                .addGap(18, 18, 18)
                .addComponent(cancelPromoLunch)
                .addContainerGap())
            .addGroup(PromosLunchFrmLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(PromosLunchFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel79)
                    .addGroup(PromosLunchFrmLayout.createSequentialGroup()
                        .addComponent(milanesaPromoCheck)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(milanesaPromoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel100)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(milanesaPromoPrice))
                    .addGroup(PromosLunchFrmLayout.createSequentialGroup()
                        .addComponent(sandwichReimsPromoCheck)
                        .addGap(20, 20, 20)
                        .addComponent(sandwichReimsPromoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel96)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sandwichReimsPromoPrice))
                    .addGroup(PromosLunchFrmLayout.createSequentialGroup()
                        .addComponent(ensaladaTropicalCheck)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ensaladaTropicalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel92)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ensaladaTropicalPrice))
                    .addGroup(PromosLunchFrmLayout.createSequentialGroup()
                        .addComponent(ensaladaPrimaveraCheck)
                        .addGap(20, 20, 20)
                        .addComponent(ensaladaPrimaveraSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel88)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ensaladaPrimaveraPrice))
                    .addGroup(PromosLunchFrmLayout.createSequentialGroup()
                        .addComponent(ensaladaIbericaCheck)
                        .addGap(20, 20, 20)
                        .addComponent(ensaladaIbericaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel86)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ensaladaIbericaPrice))
                    .addGroup(PromosLunchFrmLayout.createSequentialGroup()
                        .addComponent(ensaladaCesarCheck)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ensaladaCesarSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel82)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ensaladaCesarPrice))
                    .addGroup(PromosLunchFrmLayout.createSequentialGroup()
                        .addGroup(PromosLunchFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(arabePromoCheck)
                            .addGroup(PromosLunchFrmLayout.createSequentialGroup()
                                .addGroup(PromosLunchFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pizzaPromoCheck)
                                    .addComponent(panchoPromoCheck)
                                    .addComponent(empanadaPromoCheck))
                                .addGap(24, 24, 24)
                                .addGroup(PromosLunchFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PromosLunchFrmLayout.createSequentialGroup()
                                        .addComponent(empanadaPromoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel112)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(empanadaPromoPrice))
                                    .addGroup(PromosLunchFrmLayout.createSequentialGroup()
                                        .addComponent(panchoPromoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel108)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(panchoPromoPrice))
                                    .addGroup(PromosLunchFrmLayout.createSequentialGroup()
                                        .addComponent(pizzaPromoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel104)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(pizzaPromoPrice)))))
                        .addGap(28, 28, 28)
                        .addComponent(arabePromoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel116)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(arabePromoPrice))
                    .addGroup(PromosLunchFrmLayout.createSequentialGroup()
                        .addComponent(sandwichPromoCheck)
                        .addGap(18, 18, 18)
                        .addComponent(sandwichPromoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel120)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sandwichPromoPrice)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        PromosLunchFrmLayout.setVerticalGroup(
            PromosLunchFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PromosLunchFrmLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel79)
                .addGap(18, 18, 18)
                .addGroup(PromosLunchFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ensaladaCesarCheck)
                    .addComponent(ensaladaCesarSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel82)
                    .addComponent(ensaladaCesarPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PromosLunchFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ensaladaIbericaCheck)
                    .addComponent(ensaladaIbericaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel86)
                    .addComponent(ensaladaIbericaPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PromosLunchFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ensaladaPrimaveraCheck)
                    .addComponent(ensaladaPrimaveraSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel88)
                    .addComponent(ensaladaPrimaveraPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PromosLunchFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ensaladaTropicalCheck)
                    .addComponent(ensaladaTropicalSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel92)
                    .addComponent(ensaladaTropicalPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PromosLunchFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sandwichReimsPromoCheck)
                    .addComponent(sandwichReimsPromoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel96)
                    .addComponent(sandwichReimsPromoPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PromosLunchFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(milanesaPromoCheck)
                    .addComponent(milanesaPromoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel100)
                    .addComponent(milanesaPromoPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PromosLunchFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pizzaPromoCheck)
                    .addComponent(pizzaPromoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel104)
                    .addComponent(pizzaPromoPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PromosLunchFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(panchoPromoCheck)
                    .addComponent(panchoPromoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel108)
                    .addComponent(panchoPromoPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PromosLunchFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(empanadaPromoCheck)
                    .addComponent(empanadaPromoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel112)
                    .addComponent(empanadaPromoPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PromosLunchFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(arabePromoCheck)
                    .addComponent(arabePromoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel116)
                    .addComponent(arabePromoPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PromosLunchFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sandwichPromoCheck)
                    .addComponent(sandwichPromoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel120)
                    .addComponent(sandwichPromoPrice))
                .addGap(38, 38, 38)
                .addGroup(PromosLunchFrmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(savePromoLunch)
                    .addComponent(cancelPromoLunch))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout EmpleadosFrameLayout = new javax.swing.GroupLayout(EmpleadosFrame.getContentPane());
        EmpleadosFrame.getContentPane().setLayout(EmpleadosFrameLayout);
        EmpleadosFrameLayout.setHorizontalGroup(
            EmpleadosFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 619, Short.MAX_VALUE)
        );
        EmpleadosFrameLayout.setVerticalGroup(
            EmpleadosFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 344, Short.MAX_VALUE)
        );

        jLabel126.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel126.setText("$");

        jLabel128.setText("75");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(107, 106, 104));

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
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
        DrinkNoAlcoholFrame.setSize(610, 700);
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

    private void ComerPromosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComerPromosActionPerformed
        // TODO add your handling code here:
        PromosLunchFrm.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        PromosLunchFrm.setSize(820, 580);
        PromosLunchFrm.setVisible(true);
        
        System.out.println("PROMO MERIENDAS:");
        System.out.println("---- INIT: Table selected ----");
        System.out.println(this.selectedTable.getState());
        System.out.println(this.selectedTable.getId());
        System.out.println("---- END: Table Selected ----");

        List<Product> selectedTableProducts = this.productsImpl.findTableSelectedProducts(this.selectedTable.getId(), ProductTypeEnum.PROMOCION_COMIDAS);

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
            for (Component component : PromosLunchFrm.getContentPane().getComponents()) {
                System.out.println("promos lunch products frame iteration");

                if (component instanceof JCheckBox) {
                    System.out.println("component check --> " + ((JCheckBox) component).getText());
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

    }//GEN-LAST:event_ComerPromosActionPerformed

    private void saladProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saladProductsActionPerformed
        // TODO add your handling code here:
        SaladsProductFrame.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        SaladsProductFrame.setSize(650, 700);
        SaladsProductFrame.setVisible(true);
        
        this.findProductsForOperatingTable(ProductTypeEnum.SALADOS, this.selectedTable.getId(), this.SaladsProductFrame);
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
        PayTableFrm.setSize(600, 200);
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

        this.chargeEmployee(tableUser);
        this.selectOrderView(4);

    }//GEN-LAST:event_tableFourActionPerformed

    private void drinkNoAlcoholAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_drinkNoAlcoholAncestorAdded
        // TODO add your handling code here:

    }//GEN-LAST:event_drinkNoAlcoholAncestorAdded

    private void chocolatadaCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chocolatadaCheckActionPerformed
        if (chocolatadaCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.chocolatadaSpinner.setEnabled(true);
            this.chocolatadaSpinner.setModel(model);
        } else {
            this.chocolatadaSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_chocolatadaCheckActionPerformed

    private void GaseosaLineaCocacheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GaseosaLineaCocacheckActionPerformed
        if (GaseosaLineaCocacheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.GaseosaLineaCocaSpinner.setEnabled(true);
            this.GaseosaLineaCocaSpinner.setModel(model);
        } else {
            this.GaseosaLineaCocaSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_GaseosaLineaCocacheckActionPerformed

    private void cafeIceCappuCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cafeIceCappuCheckActionPerformed
        if (cafeIceCappuCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.cafeIceCappuSpinner.setEnabled(true);
            this.cafeIceCappuSpinner.setModel(model);
        } else {
            this.cafeIceCappuSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_cafeIceCappuCheckActionPerformed

    private void bombonCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bombonCheckActionPerformed
        if (bombonCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.bombonSpinner.setEnabled(true);
            this.bombonSpinner.setModel(model);
        } else {
            this.bombonSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_bombonCheckActionPerformed

    private void delTiempoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delTiempoCheckActionPerformed
        if (delTiempoCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.cafeDelTiempoSpinner.setEnabled(true);
            this.cafeDelTiempoSpinner.setModel(model);
        } else {
            this.cafeDelTiempoSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_delTiempoCheckActionPerformed

    private void LatteSaborizadosCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LatteSaborizadosCheckActionPerformed
        if (LatteSaborizadosCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.LattesaborizadosSpinner.setEnabled(true);
            this.LattesaborizadosSpinner.setModel(model);
        } else {
            this.LattesaborizadosSpinner.setEnabled(false);
        }

    }//GEN-LAST:event_LatteSaborizadosCheckActionPerformed

    private void CapuccinoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CapuccinoCheckActionPerformed
        if (CapuccinoCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.CapuccinoSpinner.setEnabled(true);
            this.CapuccinoSpinner.setModel(model);
        } else {
            this.CapuccinoSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_CapuccinoCheckActionPerformed

    private void cafeDobleCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cafeDobleCheckActionPerformed
        // TODO add your handling code here:
        if (cafeDobleCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.cafeDobleSpinner.setEnabled(true);
            this.cafeDobleSpinner.setModel(model);
        } else {
            this.cafeDobleSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_cafeDobleCheckActionPerformed

    private void cafeConLecheCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cafeConLecheCheckActionPerformed
        if (cafeConLecheCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.cafeConLecheSpinner.setEnabled(true);
            this.cafeConLecheSpinner.setModel(model);
        } else {
            this.cafeConLecheSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_cafeConLecheCheckActionPerformed

    private void jarritoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jarritoCheckActionPerformed
        if (jarritoCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.jarritoSpinner.setEnabled(true);
            this.jarritoSpinner.setModel(model);
        } else {
            this.jarritoSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_jarritoCheckActionPerformed

    private void pocilloCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pocilloCheckActionPerformed
        // TODO add your handling code here:

        if (pocilloCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.pocilloSpinner.setEnabled(true);
            this.pocilloSpinner.setModel(model);
        } else {
            this.pocilloSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_pocilloCheckActionPerformed

    private void cancelNoAlcoholDrinksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelNoAlcoholDrinksActionPerformed
        // TODO add your handling code here:

        /*
        Vaciar todos los contadores
        No modificar el estado de la cuenta
         */
        this.closeGenericFrame(this.DrinkNoAlcoholFrame);
    }//GEN-LAST:event_cancelNoAlcoholDrinksActionPerformed

    private void acceptNoAlcoholDrinksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptNoAlcoholDrinksActionPerformed

        products = new HashMap<>();        

        if (cervezaCheck.isSelected() && Integer.parseInt(this.cervezaSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.cervezaSpinner.getValue().toString()),
                    Double.parseDouble(this.cervezaPrice.getText())
            );
            products.put(AlcoholDrinksEnum.CERVEZA.toString(), priceQtyAlcoholProduct);
        }


        if (pocilloCheck.isSelected() && Integer.parseInt(this.pocilloSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.COCA_COLA), Integer.parseInt(this.pocilloSpinner.getValue().toString()));
        }

        if (jarritoCheck.isSelected() && Integer.parseInt(this.jarritoSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.COCA_LIGHT), Integer.parseInt(this.jarritoSpinner.getValue().toString()));
        }

        if (cafeConLecheCheck.isSelected() && Integer.parseInt(this.cafeConLecheSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.SPRITE), Integer.parseInt(this.cafeConLecheSpinner.getValue().toString()));
        }

        if (cafeDobleCheck.isSelected() && Integer.parseInt(this.cafeDobleSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.SPRITE_ZERO), Integer.parseInt(this.cafeDobleSpinner.getValue().toString()));
        }

        if (CapuccinoCheck.isSelected() && Integer.parseInt(this.CapuccinoSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.PASO_TOROS_TONICA), Integer.parseInt(this.CapuccinoSpinner.getValue().toString()));
        }

        if (CapuccinoItalianoCheck.isSelected() && Integer.parseInt(this.CapuccinoItalianoSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.PASO_TOROS_POMELO), Integer.parseInt(this.CapuccinoItalianoSpinner.getValue().toString()));
        }

        if (LatteSaborizadosCheck.isSelected() && Integer.parseInt(this.LattesaborizadosSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.FANTA), Integer.parseInt(this.LattesaborizadosSpinner.getValue().toString()));
        }

        if (delTiempoCheck.isSelected() && Integer.parseInt(this.tonicCoffeeSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.LEVITE_NARANJA), Integer.parseInt(this.tonicCoffeeSpinner.getValue().toString()));
        }

        if (bombonCheck.isSelected() && Integer.parseInt(this.tonicCoffeeSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.LEVITE_POMELO), Integer.parseInt(this.tonicCoffeeSpinner.getValue().toString()));
        }

        if (cafeIceCappuCheck.isSelected() && Integer.parseInt(this.cafeIceCappuSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.LEVITE_MANZANA), Integer.parseInt(this.cafeIceCappuSpinner.getValue().toString()));
        }

        if (cafeIceMassimoCheck.isSelected() && Integer.parseInt(this.cafeIceMassimoSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.LEVITE_LIMONADA), Integer.parseInt(this.cafeIceMassimoSpinner.getValue().toString()));
        }

        if (tonicCoffeeCheck.isSelected() && Integer.parseInt(this.cafeDelTiempoSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.CEPITA), Integer.parseInt(this.cafeDelTiempoSpinner.getValue().toString()));
        }

        if (submarinoCheck.isSelected() && Integer.parseInt(this.submarinoSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.SCHEWEPPES_POMELO), Integer.parseInt(this.submarinoSpinner.getValue().toString()));
        }

        if (chocolatadaCheck.isSelected() && Integer.parseInt(this.chocolatadaSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.SCHEWEPPES_TONICA), Integer.parseInt(this.chocolatadaSpinner.getValue().toString()));
        }

        if (iceTeaCheck.isSelected() && Integer.parseInt(this.iceTeaSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.AGUA), Integer.parseInt(this.iceTeaSpinner.getValue().toString()));
        }

        if (GaseosaLineaCocacheck.isSelected() && Integer.parseInt(this.GaseosaLineaCocaSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.EXPRIMIDO), Integer.parseInt(this.GaseosaLineaCocaSpinner.getValue().toString()));
        }

        if (aguaSaborizadaCheck.isSelected() && Integer.parseInt(this.aguaSaborizadaSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.LICUADO_LECHE), Integer.parseInt(this.aguaSaborizadaSpinner.getValue().toString()));
        }

        if (cepitaCheck.isSelected() && Integer.parseInt(this.cepitaSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.LICUADO_AGUA), Integer.parseInt(this.cepitaSpinner.getValue().toString()));
        }

        if (cafeIceMassimoCheck.isSelected() && Integer.parseInt(this.cafeIceMassimoSpinner.getValue().toString()) > 0) {
            noAlcoholDrinks.put((NoAlcoholDrinksEnum.LIMONADA), Integer.parseInt(this.cafeIceMassimoSpinner.getValue().toString()));
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

        if (this.selectedTable.getId() == 0) {
            JOptionPane.showMessageDialog(null, "Take Away ha sido guardad correctamente");
        } else {
            JOptionPane.showMessageDialog(null, "La mesa Nº: " + this.selectedTable.getId() + " Se ha guardado Correctamente");
        }

        this.orderValidationActions(this.selectedTable.getId());
        
        this.closeGenericFrame(this.DrinkNoAlcoholFrame);


    }//GEN-LAST:event_acceptNoAlcoholDrinksActionPerformed

    private void CapuccinoItalianoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CapuccinoItalianoCheckActionPerformed
        if (CapuccinoItalianoCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.CapuccinoItalianoSpinner.setEnabled(true);
            this.CapuccinoItalianoSpinner.setModel(model);
        } else {
            this.CapuccinoItalianoSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_CapuccinoItalianoCheckActionPerformed

    private void submarinoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submarinoCheckActionPerformed
        if (submarinoCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.submarinoSpinner.setEnabled(true);
            this.submarinoSpinner.setModel(model);
        } else {
            this.submarinoSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_submarinoCheckActionPerformed

    private void AguaC_S_GasCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AguaC_S_GasCheckActionPerformed
        if (AguaC_S_GasCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.AguaC_S_GasSpinner.setEnabled(true);
            this.AguaC_S_GasSpinner.setModel(model);
        } else {
            this.AguaC_S_GasSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_AguaC_S_GasCheckActionPerformed

    private void aguaSaborizadaCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aguaSaborizadaCheckActionPerformed
        if (aguaSaborizadaCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.aguaSaborizadaSpinner.setEnabled(true);
            this.aguaSaborizadaSpinner.setModel(model);
        } else {
            this.aguaSaborizadaSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_aguaSaborizadaCheckActionPerformed

    private void cepitaCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cepitaCheckActionPerformed
        if (cepitaCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.cepitaSpinner.setEnabled(true);
            this.cepitaSpinner.setModel(model);
        } else {
            this.cepitaSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_cepitaCheckActionPerformed

    private void cafeIceMassimoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cafeIceMassimoCheckActionPerformed
        if (cafeIceMassimoCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.cafeIceMassimoSpinner.setEnabled(true);
            this.cafeIceMassimoSpinner.setModel(model);
        } else {
            this.cafeIceMassimoSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_cafeIceMassimoCheckActionPerformed

    private void tonicCoffeeCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tonicCoffeeCheckActionPerformed
        if (tonicCoffeeCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.tonicCoffeeSpinner.setEnabled(true);
            this.tonicCoffeeSpinner.setModel(model);
        } else {
            this.tonicCoffeeSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_tonicCoffeeCheckActionPerformed

    private void iceTeaCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iceTeaCheckActionPerformed
        if (iceTeaCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.iceTeaSpinner.setEnabled(true);
            this.iceTeaSpinner.setModel(model);
        } else {
            this.iceTeaSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_iceTeaCheckActionPerformed


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
        if (this.selectedTable.getId() == 0) {
            JOptionPane.showMessageDialog(null, "Take Away ha efectuado el pago Correctamente");
        } else {
            JOptionPane.showMessageDialog(null, "La mesa Nº: " + this.selectedTable.getId() + " ha efectuado el pago Correctamente");
        }
        

        // cerrar popUps abiertos
        this.closeGenericFrame(PayTableFrm); // Elige el método de pago
        this.closeGenericFrame(PagoEfectivoFrame); // Paga en efectivo
        this.closeGenericFrame(SelectOrder); // elige el tipo de producto

        // libera la mesa
        if (this.selectedTable.getId() == 0) { 
            this.setTableColour(this.selectedTable.getId(), new Color(153,153, 255));
        } else {
            this.setTableColour(this.selectedTable.getId(), Color.GREEN);
        }

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
            if(this.selectedTable.getId() == 0) {
                JOptionPane.showMessageDialog(null, "TakeAway ha efectuado el pago Correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "La mesa Nº: " + this.selectedTable.getId() + " ha efectuado el pago Correctamente");
            }            

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

        this.orderValidationActions(this.selectedTable.getId());
        
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

        this.orderValidationActions(this.selectedTable.getId());
        
        this.closeGenericFrame(this.DrinkAlcoholFrame);
        


    }//GEN-LAST:event_saverAlcoholOrderActionPerformed

    private void AdminUserPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdminUserPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AdminUserPasswordActionPerformed

    private void deleteTableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteTableButtonActionPerformed
        
        String adminUser = this.AdminUserName.getText();
        String password = this.AdminUserPassword.getText();
        
        System.out.println("Password --> " + password);
        String user = "christian";
        String pass = "lasalle";
        
        if (adminUser.equals(user) && password.equals(pass)) {
            System.out.println("Table to delete --> " + this.selectedTable.getId());
            
            this.productsImpl.deleteTable(this.selectedTable.getId());
            
            this.AdminUserName.setText("");
            this.AdminUserPassword.setText("");  
        } else {
            JOptionPane.showMessageDialog(null, "Usuario /  Pass Incorrecto");
            this.AdminUserName.setText("");
            this.AdminUserPassword.setText("");  
        }
        
        
        /**
         * Reset de user y pass cuando cierra la ventana, para que no quede 
         * almacenado ningún dato.
         */
        this.DeleteTableFrm.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent w) {
                AdminUserName.setText("");
                AdminUserPassword.setText("");  
            }
        });
        
        this.setTableColour(this.selectedTable.getId(), Color.GREEN);
        
        this.closeGenericFrame(this.DeleteTableFrm);
        this.closeGenericFrame(this.SelectOrder);
    }//GEN-LAST:event_deleteTableButtonActionPerformed

    private void DesayunoMeriendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DesayunoMeriendaActionPerformed
        PromosBreakfastFrm.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        PromosBreakfastFrm.setSize(750, 750);
        PromosBreakfastFrm.setVisible(true);

        System.out.println("PROMO MERIENDAS:");
        System.out.println("---- INIT: Table selected ----");
        System.out.println(this.selectedTable.getState());
        System.out.println(this.selectedTable.getId());
        System.out.println("---- END: Table Selected ----");

        List<Product> selectedTableProducts = this.productsImpl.findTableSelectedProducts(this.selectedTable.getId(), ProductTypeEnum.PROMOCION_MERIENDAS);

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
            for (Component component : PromosBreakfastFrm.getContentPane().getComponents()) {
                System.out.println("candy products frame iteration");

                if (component instanceof JCheckBox) {
                    System.out.println("component check --> " + ((JCheckBox) component).getText());
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

    }//GEN-LAST:event_DesayunoMeriendaActionPerformed

    private void cafeCuadradoDucleCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cafeCuadradoDucleCheckActionPerformed
        if (cafeCuadradoDucleCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.cafeCuadraroDulceSpinner.setEnabled(true);
            this.cafeCuadraroDulceSpinner.setModel(model);
        } else {
            this.cafeCuadraroDulceSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_cafeCuadradoDucleCheckActionPerformed

    private void cafe2GallesChocoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cafe2GallesChocoCheckActionPerformed
        if (cafe2GallesChocoCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.Cafe2gallesChocoSpinner.setEnabled(true);
            this.Cafe2gallesChocoSpinner.setModel(model);
        } else {
            this.Cafe2gallesChocoSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_cafe2GallesChocoCheckActionPerformed

    private void cafe2medialunasCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cafe2medialunasCheckActionPerformed
        if (cafe2medialunasCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.cafe2MedialunasSpinner.setEnabled(true);
            this.cafe2MedialunasSpinner.setModel(model);
        } else {
            this.cafe2MedialunasSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_cafe2medialunasCheckActionPerformed

    private void cafeTortaIndividualCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cafeTortaIndividualCheckActionPerformed
        if (cafeTortaIndividualCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.cafeTortaIndSpinner.setEnabled(true);
            this.cafeTortaIndSpinner.setModel(model);
        } else {
            this.cafeTortaIndSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_cafeTortaIndividualCheckActionPerformed

    private void cafeTostadoArabeCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cafeTostadoArabeCheckActionPerformed
        if (cafeTostadoArabeCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.cafeTostadoArabeSpinner.setEnabled(true);
            this.cafeTostadoArabeSpinner.setModel(model);
        } else {
            this.cafeTostadoArabeSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_cafeTostadoArabeCheckActionPerformed

    private void cappuccinoTostadasCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cappuccinoTostadasCheckActionPerformed
        if (cappuccinoTostadasCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.cappuccinoTostadaSppiner.setEnabled(true);
            this.cappuccinoTostadaSppiner.setModel(model);
        } else {
            this.cappuccinoTostadaSppiner.setEnabled(false);
        }
    }//GEN-LAST:event_cappuccinoTostadasCheckActionPerformed

    private void cappucinoMedialunaCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cappucinoMedialunaCheckActionPerformed
        if (cappucinoMedialunaCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.cappucinnoMedialunaSpinner.setEnabled(true);
            this.cappucinnoMedialunaSpinner.setModel(model);
        } else {
            this.cappucinnoMedialunaSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_cappucinoMedialunaCheckActionPerformed

    private void naranja2mediaLunasCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_naranja2mediaLunasCheckActionPerformed
        if (naranja2mediaLunasCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.naranja2medialunasSpinner.setEnabled(true);
            this.naranja2medialunasSpinner.setModel(model);
        } else {
            this.naranja2medialunasSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_naranja2mediaLunasCheckActionPerformed

    private void naranjaTostadoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_naranjaTostadoCheckActionPerformed
        if (naranjaTostadoCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.naranjaTostadoSpinner.setEnabled(true);
            this.naranjaTostadoSpinner.setModel(model);
        } else {
            this.naranjaTostadoSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_naranjaTostadoCheckActionPerformed

    private void licuadoMedioTostadoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_licuadoMedioTostadoCheckActionPerformed
        if (licuadoMedioTostadoCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.licuadoMedioTostadoSpinner.setEnabled(true);
            this.licuadoMedioTostadoSpinner.setModel(model);
        } else {
            this.licuadoMedioTostadoSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_licuadoMedioTostadoCheckActionPerformed

    private void limonadaTortaIndividiualCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limonadaTortaIndividiualCheckActionPerformed
        if (limonadaTortaIndividiualCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.limonadaTortaIndividualSpinner.setEnabled(true);
            this.limonadaTortaIndividualSpinner.setModel(model);
        } else {
            this.limonadaTortaIndividualSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_limonadaTortaIndividiualCheckActionPerformed

    private void alfajorMaicenaChocolatadaCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alfajorMaicenaChocolatadaCheckActionPerformed
        if (alfajorMaicenaChocolatadaCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.alfajorMaicenaChocoSpinner.setEnabled(true);
            this.alfajorMaicenaChocoSpinner.setModel(model);
        } else {
            this.alfajorMaicenaChocoSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_alfajorMaicenaChocolatadaCheckActionPerformed

    private void milkshakesTortaCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_milkshakesTortaCheckActionPerformed
        if (milkshakesTortaCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.milkshakesTortaIndividualSpinner.setEnabled(true);
            this.milkshakesTortaIndividualSpinner.setModel(model);
        } else {
            this.milkshakesTortaIndividualSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_milkshakesTortaCheckActionPerformed

    private void licuado2_1_TostadoIndCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_licuado2_1_TostadoIndCheckActionPerformed
        if (licuado2_1_TostadoIndCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.licuados_2_1_TostadoSpinner.setEnabled(true);
            this.licuados_2_1_TostadoSpinner.setModel(model);
        } else {
            this.licuados_2_1_TostadoSpinner.setEnabled(false);
        }        
    }//GEN-LAST:event_licuado2_1_TostadoIndCheckActionPerformed

    private void copaYogurtCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copaYogurtCheckActionPerformed
        if (copaYogurtCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.yogurtSpinner.setEnabled(true);
            this.yogurtSpinner.setModel(model);
        } else {
            this.yogurtSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_copaYogurtCheckActionPerformed

    private void tostadasDobleExprEspCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tostadasDobleExprEspCheckActionPerformed
        if (tostadasDobleExprEspCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.tostadasExpEspSpinner.setEnabled(true);
            this.tostadasExpEspSpinner.setModel(model);
        } else {
            this.tostadasExpEspSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_tostadasDobleExprEspCheckActionPerformed

    private void frutasCafeTostadoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_frutasCafeTostadoCheckActionPerformed
        if (frutasCafeTostadoCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.frutaCafeTostadoSpinner.setEnabled(true);
            this.frutaCafeTostadoSpinner.setModel(model);
        } else {
            this.frutaCafeTostadoSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_frutasCafeTostadoCheckActionPerformed

    private void iceCappuccinoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iceCappuccinoCheckActionPerformed
        if (iceCappuccinoCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.iceCapuccinoSpinner.setEnabled(true);
            this.iceCapuccinoSpinner.setModel(model);
        } else {
            this.iceCapuccinoSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_iceCappuccinoCheckActionPerformed

    private void cosePromoBreakfastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cosePromoBreakfastActionPerformed
        this.closeGenericFrame(this.PromosBreakfastFrm);
    }//GEN-LAST:event_cosePromoBreakfastActionPerformed

    private void savePromoBreakfastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savePromoBreakfastActionPerformed
        products = new HashMap<>();
        

        if (cafe2medialunasCheck.isSelected() && Integer.parseInt(this.cafe2MedialunasSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.cafe2MedialunasSpinner.getValue().toString()),
                    Double.parseDouble(this.cafe2medilunasPrice.getText())
            );
            products.put(PromotionBreakfast.CAFE_2_MEDIALUNAS.toString(), priceQtyAlcoholProduct);
        }

        if (cafeTortaIndividualCheck.isSelected() && Integer.parseInt(this.cafeTortaIndSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.cafeTortaIndSpinner.getValue().toString()),
                    Double.parseDouble(this.cafeTortaIndPrice.getText())
            );
            products.put(PromotionBreakfast.CAFE_TORTA_INDIVIDUAL.toString(), priceQtyAlcoholProduct);
        }

        if (cafeTostadoArabeCheck.isSelected() && Integer.parseInt(this.cafeTostadoArabeSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.cafeTostadoArabeSpinner.getValue().toString()),
                    Double.parseDouble(this.cafeArabePrice.getText())
            );
            products.put(PromotionBreakfast.CAFE_TOSTADO_ARABE.toString(), priceQtyAlcoholProduct);
        }

        if (cafeCuadradoDucleCheck.isSelected() && Integer.parseInt(this.cafeCuadraroDulceSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.cafeCuadraroDulceSpinner.getValue().toString()),
                    Double.parseDouble(this.cafeCuadradoDulcePrice.getText())
            );
            products.put(PromotionBreakfast.CAFE_CUADRADO_DULCE.toString(), priceQtyAlcoholProduct);
        }

        if (cafe2GallesChocoCheck.isSelected() && Integer.parseInt(this.Cafe2gallesChocoSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.Cafe2gallesChocoSpinner.getValue().toString()),
                    Double.parseDouble(this.cafe2gallesPrice.getText())
            );
            products.put(PromotionBreakfast.CAFE_GALLETAS_CHOCO.toString(), priceQtyAlcoholProduct);
        }

        if (cappuccinoTostadasCheck.isSelected() && Integer.parseInt(this.cappuccinoTostadaSppiner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.cappuccinoTostadaSppiner.getValue().toString()),
                    Double.parseDouble(this.cappuccinoTostadaPrice.getText())
            );
            products.put(PromotionBreakfast.CAPPUCCINO_TOSTADAS.toString(), priceQtyAlcoholProduct);
        }

        if (cappucinoMedialunaCheck.isSelected() && Integer.parseInt(this.cappucinnoMedialunaSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.cappucinnoMedialunaSpinner.getValue().toString()),
                    Double.parseDouble(this.cappucinoMedialunaPrice.getText())
            );
            products.put(PromotionBreakfast.CAPPUCCINO_MEDIALUNAS.toString(), priceQtyAlcoholProduct);
        }

        if (naranja2mediaLunasCheck.isSelected() && Integer.parseInt(this.naranja2medialunasSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.naranja2medialunasSpinner.getValue().toString()),
                    Double.parseDouble(this.naranja2medialunasPrice.getText())
            );
            products.put(PromotionBreakfast.NARANJA_MEDIALUNAS.toString(), priceQtyAlcoholProduct);
        }

        if (naranjaTostadoCheck.isSelected() && Integer.parseInt(this.naranjaTostadoSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.naranjaTostadoSpinner.getValue().toString()),
                    Double.parseDouble(this.naranjaTostadoPrice.getText())
            );
            products.put(PromotionBreakfast.NARANJA_TOSTADOS.toString(), priceQtyAlcoholProduct);
        }


        if (licuadoMedioTostadoCheck.isSelected() && Integer.parseInt(this.licuadoMedioTostadoSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.licuadoMedioTostadoSpinner.getValue().toString()),
                    Double.parseDouble(this.licuadoMedioTostadoPrice.getText())
            );
            products.put(PromotionBreakfast.LICUADO_medio_TOSTADO.toString(), priceQtyAlcoholProduct);
        }

        if (limonadaTortaIndividiualCheck.isSelected() && Integer.parseInt(this.limonadaTortaIndividualSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.limonadaTortaIndividualSpinner.getValue().toString()),
                    Double.parseDouble(this.limonadaTortaIndPrice.getText())
            );
            products.put(PromotionBreakfast.LIMONADA_TORTA_IND.toString(), priceQtyAlcoholProduct);
        }

        if (alfajorMaicenaChocolatadaCheck.isSelected() && Integer.parseInt(this.alfajorMaicenaChocoSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.alfajorMaicenaChocoSpinner.getValue().toString()),
                    Double.parseDouble(this.alfajorMaicenaChocoPrice.getText())
            );
            products.put(PromotionBreakfast.ALFAJOR_MAICENA_CHOCO.toString(), priceQtyAlcoholProduct);
        }

        if (milkshakesTortaCheck.isSelected() && Integer.parseInt(this.milkshakesTortaIndividualSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.milkshakesTortaIndividualSpinner.getValue().toString()),
                    Double.parseDouble(this.milkshakesTortaIndividualPrice.getText())
            );
            products.put(PromotionBreakfast.MILKSHAKES_TORTA_IND.toString(), priceQtyAlcoholProduct);
        }

        if (licuado2_1_TostadoIndCheck.isSelected() && Integer.parseInt(this.licuados_2_1_TostadoSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.licuados_2_1_TostadoSpinner.getValue().toString()),
                    Double.parseDouble(this.licuados_2_1_TostadoPrice.getText())
            );
            products.put(PromotionBreakfast.LICUADOS_2_1_TOSTADO.toString(), priceQtyAlcoholProduct);
        }

        if (copaYogurtCheck.isSelected() && Integer.parseInt(this.yogurtSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.yogurtSpinner.getValue().toString()),
                    Double.parseDouble(this.yogurtPrice.getText())
            );
            products.put(PromotionBreakfast.COPA_YOGURT.toString(), priceQtyAlcoholProduct);
        }

        if (tostadasDobleExprEspCheck.isSelected() && Integer.parseInt(this.tostadasExpEspSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.tostadasExpEspSpinner.getValue().toString()),
                    Double.parseDouble(this.tostadasExpEspPrice.getText())
            );
            products.put(PromotionBreakfast.TOSTADAS_EXP_ESP.toString(), priceQtyAlcoholProduct);
        }

        if (frutasCafeTostadoCheck.isSelected() && Integer.parseInt(this.frutaCafeTostadoSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.frutaCafeTostadoSpinner.getValue().toString()),
                    Double.parseDouble(this.frutaCafePrice.getText())
            );
            products.put(PromotionBreakfast.FRUTA_CAFE_TOSTADO.toString(), priceQtyAlcoholProduct);
        }

        if (iceCappuccinoCheck.isSelected() && Integer.parseInt(this.iceCapuccinoSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.iceCapuccinoSpinner.getValue().toString()),
                    Double.parseDouble(this.iceCapuccinoPrice.getText())
            );
            products.put(PromotionBreakfast.ICE_CAPPUCCINO.toString(), priceQtyAlcoholProduct);
        }

        
        this.dataStore = new DataStore();
        this.dataStore.setProductTypeEnum(ProductTypeEnum.PROMOCION_MERIENDAS);

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

        this.orderValidationActions(this.selectedTable.getId());
        
        this.closeGenericFrame(this.PromosBreakfastFrm);

    }//GEN-LAST:event_savePromoBreakfastActionPerformed

    private void sandwichPromoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sandwichPromoCheckActionPerformed
        if (sandwichPromoCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.sandwichPromoSpinner.setEnabled(true);
            this.sandwichPromoSpinner.setModel(model);
        } else {
            this.sandwichPromoSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_sandwichPromoCheckActionPerformed

    private void sandwichReimsPromoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sandwichReimsPromoCheckActionPerformed
        if (sandwichReimsPromoCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.sandwichReimsPromoSpinner.setEnabled(true);
            this.sandwichReimsPromoSpinner.setModel(model);
        } else {
            this.sandwichReimsPromoSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_sandwichReimsPromoCheckActionPerformed

    private void ensaladaCesarCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ensaladaCesarCheckActionPerformed
        if (ensaladaCesarCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.ensaladaCesarSpinner.setEnabled(true);
            this.ensaladaCesarSpinner.setModel(model);
        } else {
            this.ensaladaCesarSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_ensaladaCesarCheckActionPerformed

    private void ensaladaIbericaCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ensaladaIbericaCheckActionPerformed
        if (ensaladaIbericaCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.ensaladaIbericaSpinner.setEnabled(true);
            this.ensaladaIbericaSpinner.setModel(model);
        } else {
            this.ensaladaIbericaSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_ensaladaIbericaCheckActionPerformed

    private void ensaladaPrimaveraCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ensaladaPrimaveraCheckActionPerformed
        if (ensaladaPrimaveraCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.ensaladaPrimaveraSpinner.setEnabled(true);
            this.ensaladaPrimaveraSpinner.setModel(model);
        } else {
            this.ensaladaPrimaveraSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_ensaladaPrimaveraCheckActionPerformed

    private void ensaladaTropicalCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ensaladaTropicalCheckActionPerformed
        if (ensaladaTropicalCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.ensaladaTropicalSpinner.setEnabled(true);
            this.ensaladaTropicalSpinner.setModel(model);
        } else {
            this.ensaladaTropicalSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_ensaladaTropicalCheckActionPerformed

    private void milanesaPromoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_milanesaPromoCheckActionPerformed
        if (milanesaPromoCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.milanesaPromoSpinner.setEnabled(true);
            this.milanesaPromoSpinner.setModel(model);
        } else {
            this.milanesaPromoSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_milanesaPromoCheckActionPerformed

    private void pizzaPromoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pizzaPromoCheckActionPerformed
        if (pizzaPromoCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.pizzaPromoSpinner.setEnabled(true);
            this.pizzaPromoSpinner.setModel(model);
        } else {
            this.pizzaPromoSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_pizzaPromoCheckActionPerformed

    private void panchoPromoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_panchoPromoCheckActionPerformed
        if (panchoPromoCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.panchoPromoSpinner.setEnabled(true);
            this.panchoPromoSpinner.setModel(model);
        } else {
            this.panchoPromoSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_panchoPromoCheckActionPerformed

    private void empanadaPromoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empanadaPromoCheckActionPerformed
        if (empanadaPromoCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.empanadaPromoSpinner.setEnabled(true);
            this.empanadaPromoSpinner.setModel(model);
        } else {
            this.empanadaPromoSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_empanadaPromoCheckActionPerformed

    private void arabePromoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arabePromoCheckActionPerformed
        if (arabePromoCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.arabePromoSpinner.setEnabled(true);
            this.arabePromoSpinner.setModel(model);
        } else {
            this.empanadaPromoSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_arabePromoCheckActionPerformed

    private void cancelPromoLunchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelPromoLunchActionPerformed
        this.closeGenericFrame(PromosLunchFrm);
    }//GEN-LAST:event_cancelPromoLunchActionPerformed

    private void savePromoLunchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savePromoLunchActionPerformed
        products = new HashMap<>();

        if (this.ensaladaCesarCheck.isSelected() && Integer.parseInt(this.ensaladaCesarSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.ensaladaCesarSpinner.getValue().toString()),
                    Double.parseDouble(this.ensaladaCesarPrice.getText())
            );
            products.put(PromotionsLunchEnum.ENSALADA_CESAR.toString(), priceQtyAlcoholProduct);
        }

        if (ensaladaIbericaCheck.isSelected() && Integer.parseInt(this.ensaladaIbericaSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.ensaladaIbericaSpinner.getValue().toString()),
                    Double.parseDouble(this.ensaladaIbericaPrice.getText())
            );
            products.put(PromotionsLunchEnum.ENSALADA_IBERICA.toString(), priceQtyAlcoholProduct);
        }

        if (ensaladaPrimaveraCheck.isSelected() && Integer.parseInt(this.ensaladaPrimaveraSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.ensaladaPrimaveraSpinner.getValue().toString()),
                    Double.parseDouble(this.ensaladaPrimaveraPrice.getText())
            );
            products.put(PromotionsLunchEnum.ENSALADA_PRIMAVERA.toString(), priceQtyAlcoholProduct);
        }

        if (ensaladaTropicalCheck.isSelected() && Integer.parseInt(this.ensaladaTropicalSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.ensaladaTropicalSpinner.getValue().toString()),
                    Double.parseDouble(this.ensaladaTropicalPrice.getText())
            );
            products.put(PromotionsLunchEnum.ENSALADA_TROPICAL.toString(), priceQtyAlcoholProduct);
        }

        if (sandwichReimsPromoCheck.isSelected() && Integer.parseInt(this.sandwichReimsPromoSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.sandwichReimsPromoSpinner.getValue().toString()),
                    Double.parseDouble(this.sandwichReimsPromoPrice.getText())
            );
            products.put(PromotionsLunchEnum.SANDWICH_REIMS_PROMO.toString(), priceQtyAlcoholProduct);
        }

        if (milanesaPromoCheck.isSelected() && Integer.parseInt(this.milanesaPromoSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.milanesaPromoSpinner.getValue().toString()),
                    Double.parseDouble(this.milanesaPromoPrice.getText())
            );
            products.put(PromotionsLunchEnum.MILANESA_PROMO.toString(), priceQtyAlcoholProduct);
        }

        if (pizzaPromoCheck.isSelected() && Integer.parseInt(this.pizzaPromoSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.pizzaPromoSpinner.getValue().toString()),
                    Double.parseDouble(this.pizzaPromoPrice.getText())
            );
            products.put(PromotionsLunchEnum.PIZZA_PROMO.toString(), priceQtyAlcoholProduct);
        }

        if (panchoPromoCheck.isSelected() && Integer.parseInt(this.panchoPromoSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.panchoPromoSpinner.getValue().toString()),
                    Double.parseDouble(this.panchoPromoPrice.getText())
            );
            products.put(PromotionsLunchEnum.PANCHO_PROMO.toString(), priceQtyAlcoholProduct);
        }

        if (empanadaPromoCheck.isSelected() && Integer.parseInt(this.empanadaPromoSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.empanadaPromoSpinner.getValue().toString()),
                    Double.parseDouble(this.empanadaPromoPrice.getText())
            );
            products.put(PromotionsLunchEnum.EMPANADA_PROMO.toString(), priceQtyAlcoholProduct);
        }


        if (arabePromoCheck.isSelected() && Integer.parseInt(this.arabePromoSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.arabePromoSpinner.getValue().toString()),
                    Double.parseDouble(this.arabePromoPrice.getText())
            );
            products.put(PromotionsLunchEnum.ARABE_PROMO.toString(), priceQtyAlcoholProduct);
        }

        if (sandwichPromoCheck.isSelected() && Integer.parseInt(this.sandwichPromoSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.sandwichPromoSpinner.getValue().toString()),
                    Double.parseDouble(this.sandwichPromoPrice.getText())
            );
            products.put(PromotionsLunchEnum.SANDWICH_PROMO.toString(), priceQtyAlcoholProduct);
        }
        
        this.dataStore = new DataStore();
        this.dataStore.setProductTypeEnum(ProductTypeEnum.PROMOCION_COMIDAS);

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

        this.orderValidationActions(this.selectedTable.getId());
        
        this.closeGenericFrame(this.PromosLunchFrm);        
    }//GEN-LAST:event_savePromoLunchActionPerformed

    private void empleadosPromoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empleadosPromoActionPerformed
        JOptionPane.showMessageDialog(null, "Lista de precios empleados en construcción");
    }//GEN-LAST:event_empleadosPromoActionPerformed

    private void cbtPolloCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbtPolloCheckActionPerformed
        if (cbtPolloCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.cbtPolloSpinner.setEnabled(true);
            this.cbtPolloSpinner.setModel(model);
        } else {
            this.cbtPolloSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_cbtPolloCheckActionPerformed

    private void closeSandwichORderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeSandwichORderActionPerformed
        this.closeGenericFrame(SaladsProductFrame);
    }//GEN-LAST:event_closeSandwichORderActionPerformed

    private void sbtVerdurasCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sbtVerdurasCheckActionPerformed
        if (sbtVerdurasCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.sbtVerdurasSpinner.setEnabled(true);
            this.sbtVerdurasSpinner.setModel(model);
        } else {
            this.sbtVerdurasSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_sbtVerdurasCheckActionPerformed

    private void sbtAtunCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sbtAtunCheckActionPerformed
        if (sbtAtunCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.sbtAtunSpinner.setEnabled(true);
            this.sbtAtunSpinner.setModel(model);
        } else {
            this.sbtAtunSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_sbtAtunCheckActionPerformed

    private void saveSandwichORderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveSandwichORderActionPerformed
        products = new HashMap<>();

        if (this.tostadoSdwCheck.isSelected() && Integer.parseInt(this.tostadoSdwSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.tostadoSdwSpinner.getValue().toString()),
                    Double.parseDouble(this.tostadoSdwPrice.getText())
            );
            products.put(SaladsEnum.TOSTADO.toString(), priceQtyAlcoholProduct);
        }

        if (arabeSdwCheck.isSelected() && Integer.parseInt(this.arabeSdwSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.arabeSdwSpinner.getValue().toString()),
                    Double.parseDouble(this.arabeSdwPrice.getText())
            );
            products.put(SaladsEnum.ARABE.toString(), priceQtyAlcoholProduct);
        }

        if (medialunaSdwCheck.isSelected() && Integer.parseInt(this.medialunaSdwSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.medialunaSdwSpinner.getValue().toString()),
                    Double.parseDouble(this.medialunaSdwPrice.getText())
            );
            products.put(SaladsEnum.MEDIALUNA_RELLENA.toString(), priceQtyAlcoholProduct);
        }

        if (cbtPolloCheck.isSelected() && Integer.parseInt(this.cbtPolloSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.cbtPolloSpinner.getValue().toString()),
                    Double.parseDouble(this.cbtPolloPrice.getText())
            );
            products.put(SaladsEnum.CIABATTA_POLLO.toString(), priceQtyAlcoholProduct);
        }

        if (sbtMilanesaCheck.isSelected() && Integer.parseInt(this.sbtMilanesaSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.sbtMilanesaSpinner.getValue().toString()),
                    Double.parseDouble(this.sbtMilanesaPrice.getText())
            );
            products.put(SaladsEnum.CIABATTA_MILANESA.toString(), priceQtyAlcoholProduct);
        }

        if (sbtVerdurasCheck.isSelected() && Integer.parseInt(this.sbtVerdurasSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.sbtVerdurasSpinner.getValue().toString()),
                    Double.parseDouble(this.sbtVerdurasPrice.getText())
            );
            products.put(SaladsEnum.CIABATTA_VERDURAS.toString(), priceQtyAlcoholProduct);
        }

        if (sbtAtunCheck.isSelected() && Integer.parseInt(this.sbtAtunSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.sbtAtunSpinner.getValue().toString()),
                    Double.parseDouble(this.sbtAtunPrice.getText())
            );
            products.put(SaladsEnum.CIABATTA_ATUN.toString(), priceQtyAlcoholProduct);
        }

        if (sbtCrudoCheck.isSelected() && Integer.parseInt(this.sbtCrudoSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.sbtCrudoSpinner.getValue().toString()),
                    Double.parseDouble(this.sbtCrudoPrice.getText())
            );
            products.put(SaladsEnum.CIABATTA_JAMON_CRUDO.toString(), priceQtyAlcoholProduct);
        }

        if (sdwEmpandaCheck.isSelected() && Integer.parseInt(this.sdwEmpandaSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.sdwEmpandaSpinner.getValue().toString()),
                    Double.parseDouble(this.sdwEmpandaPRice.getText())
            );
            products.put(SaladsEnum.EMPANADAS.toString(), priceQtyAlcoholProduct);
        }


        if (sdwTartasCheck.isSelected() && Integer.parseInt(this.sdwTartasSpinner.getValue().toString()) > 0) {
            Map<Integer, Double> priceQtyAlcoholProduct = new HashMap<>();

            priceQtyAlcoholProduct.put(
                    Integer.parseInt(this.sdwTartasSpinner.getValue().toString()),
                    Double.parseDouble(this.sdwTartasPrice.getText())
            );
            products.put(SaladsEnum.TARTA.toString(), priceQtyAlcoholProduct);
        }
        
        this.dataStore = new DataStore();
        this.dataStore.setProductTypeEnum(ProductTypeEnum.SALADOS);

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

        this.orderValidationActions(this.selectedTable.getId());
        
        this.closeGenericFrame(this.SaladsProductFrame);
    }//GEN-LAST:event_saveSandwichORderActionPerformed

    private void tostadoSdwCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tostadoSdwCheckActionPerformed
        if (tostadoSdwCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.tostadoSdwSpinner.setEnabled(true);
            this.tostadoSdwSpinner.setModel(model);
        } else {
            this.tostadoSdwSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_tostadoSdwCheckActionPerformed

    private void arabeSdwCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arabeSdwCheckActionPerformed
        if (tostadoSdwCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.tostadoSdwSpinner.setEnabled(true);
            this.tostadoSdwSpinner.setModel(model);
        } else {
            this.tostadoSdwSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_arabeSdwCheckActionPerformed

    private void medialunaSdwCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medialunaSdwCheckActionPerformed
        if (medialunaSdwCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.medialunaSdwSpinner.setEnabled(true);
            this.medialunaSdwSpinner.setModel(model);
        } else {
            this.medialunaSdwSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_medialunaSdwCheckActionPerformed

    private void sbtMilanesaCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sbtMilanesaCheckActionPerformed
       if (sbtMilanesaCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.sbtMilanesaSpinner.setEnabled(true);
            this.sbtMilanesaSpinner.setModel(model);
        } else {
            this.sbtMilanesaSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_sbtMilanesaCheckActionPerformed

    private void sbtCrudoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sbtCrudoCheckActionPerformed
       if (sbtCrudoCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.sbtCrudoSpinner.setEnabled(true);
            this.sbtCrudoSpinner.setModel(model);
        } else {
            this.sbtCrudoSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_sbtCrudoCheckActionPerformed

    private void sdwEmpandaCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sdwEmpandaCheckActionPerformed
       if (sdwEmpandaCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.sdwEmpandaSpinner.setEnabled(true);
            this.sdwEmpandaSpinner.setModel(model);
        } else {
            this.sdwEmpandaSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_sdwEmpandaCheckActionPerformed

    private void sdwTartasCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sdwTartasCheckActionPerformed
       if (sdwTartasCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.sdwTartasSpinner.setEnabled(true);
            this.sdwTartasSpinner.setModel(model);
        } else {
            this.sdwTartasSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_sdwTartasCheckActionPerformed

    private void hotCiokCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hotCiokCheckActionPerformed
       if (hotCiokCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.hotCiokSpinner.setEnabled(true);
            this.hotCiokSpinner.setModel(model);
        } else {
            this.hotCiokSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_hotCiokCheckActionPerformed

    private void teCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teCheckActionPerformed
       if (teCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.teSpinner.setEnabled(true);
            this.teSpinner.setModel(model);
        } else {
            this.teSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_teCheckActionPerformed

    private void FrapuccinoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FrapuccinoCheckActionPerformed
       if (FrapuccinoCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.FrapuccinoSpinner.setEnabled(true);
            this.FrapuccinoSpinner.setModel(model);
        } else {
            this.FrapuccinoSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_FrapuccinoCheckActionPerformed

    private void milkShakeCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_milkShakeCheckActionPerformed
       if (milkShakeCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.MilkshakeSpinner.setEnabled(true);
            this.MilkshakeSpinner.setModel(model);
        } else {
            this.MilkshakeSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_milkShakeCheckActionPerformed

    private void LicuadoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LicuadoCheckActionPerformed
       if (LicuadoCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.licuadosSpinner.setEnabled(true);
            this.licuadosSpinner.setModel(model);
        } else {
            this.licuadosSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_LicuadoCheckActionPerformed

    private void limonadaCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limonadaCheckActionPerformed
       if (limonadaCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.limonadaSpinner.setEnabled(true);
            this.limonadaSpinner.setModel(model);
        } else {
            this.limonadaSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_limonadaCheckActionPerformed

    private void batidoExprimidoCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batidoExprimidoCheckActionPerformed
       if (batidoExprimidoCheck.isSelected()) {
            SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 50, 1);
            this.batidoExprimidoSpinner.setEnabled(true);
            this.batidoExprimidoSpinner.setModel(model);
        } else {
            this.batidoExprimidoSpinner.setEnabled(false);
        }
    }//GEN-LAST:event_batidoExprimidoCheckActionPerformed
    

    private void setTableColour(int tableId, Color color) {

        switch (tableId) {
            case 0:
                this.takeAway.setBackground(color);
                break;
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
    private javax.swing.JCheckBox AguaC_S_GasCheck;
    private javax.swing.JLabel AguaC_S_GasPrice;
    private javax.swing.JSpinner AguaC_S_GasSpinner;
    private javax.swing.JSpinner Cafe2gallesChocoSpinner;
    private javax.swing.JFrame CandyProductsFrame;
    private javax.swing.JCheckBox CapuccinoCheck;
    private javax.swing.JCheckBox CapuccinoItalianoCheck;
    private javax.swing.JLabel CapuccinoItalianoPrice;
    private javax.swing.JSpinner CapuccinoItalianoSpinner;
    private javax.swing.JLabel CapuccinoPrice;
    private javax.swing.JSpinner CapuccinoSpinner;
    private javax.swing.JButton CloseDayAction;
    private javax.swing.JFrame CloseDayFrame;
    private javax.swing.JButton CloseTableButton;
    private javax.swing.JFrame CloseTableFrame;
    private javax.swing.JButton ComerPromos;
    private javax.swing.JFrame DeleteTableFrm;
    private javax.swing.JButton DesayunoMerienda;
    private javax.swing.JFrame DrinkAlcoholFrame;
    private javax.swing.JFrame DrinkNoAlcoholFrame;
    private javax.swing.JFrame EmpleadosFrame;
    private javax.swing.JCheckBox FrapuccinoCheck;
    private javax.swing.JLabel FrapuccinoPrice;
    private javax.swing.JSpinner FrapuccinoSpinner;
    private javax.swing.JLabel GaseosaLineaCocaPrice;
    private javax.swing.JSpinner GaseosaLineaCocaSpinner;
    private javax.swing.JCheckBox GaseosaLineaCocacheck;
    private javax.swing.JButton GuardarProductosDulces;
    private javax.swing.JCheckBox LatteSaborizadosCheck;
    private javax.swing.JLabel LattesaborizadosPrice;
    private javax.swing.JSpinner LattesaborizadosSpinner;
    private javax.swing.JCheckBox LicuadoCheck;
    private javax.swing.JLabel MesaLabel;
    private javax.swing.JLabel MilkshakePrice;
    private javax.swing.JSpinner MilkshakeSpinner;
    private javax.swing.JLabel MozoLabel;
    private javax.swing.JFrame OtraFormaDePagoFrame;
    private javax.swing.JFrame PagoEfectivoFrame;
    private javax.swing.JFrame PayTableFrm;
    private javax.swing.JFrame PromosBreakfastFrm;
    private javax.swing.JFrame PromosLunchFrm;
    private javax.swing.JFrame SaladsProductFrame;
    private javax.swing.JFrame SelectOrder;
    private javax.swing.JLabel TotalLabel;
    private javax.swing.JButton acceptNoAlcoholDrinks;
    private javax.swing.JCheckBox aguaSaborizadaCheck;
    private javax.swing.JLabel aguaSaborizadaPrice;
    private javax.swing.JSpinner aguaSaborizadaSpinner;
    private javax.swing.JButton alcoholDrinkCancel;
    private javax.swing.JCheckBox alfajorArtesanalCheck;
    private javax.swing.JLabel alfajorArtesanalPrice;
    private javax.swing.JSpinner alfajorArtesanalSpinner;
    private javax.swing.JCheckBox alfajorCheck;
    private javax.swing.JLabel alfajorMaicenaChocoPrice;
    private javax.swing.JSpinner alfajorMaicenaChocoSpinner;
    private javax.swing.JCheckBox alfajorMaicenaChocolatadaCheck;
    private javax.swing.JLabel alfajorPrice;
    private javax.swing.JSpinner alfajorSpinner;
    private javax.swing.JCheckBox arabePromoCheck;
    private javax.swing.JLabel arabePromoPrice;
    private javax.swing.JSpinner arabePromoSpinner;
    private javax.swing.JCheckBox arabeSdwCheck;
    private javax.swing.JLabel arabeSdwPrice;
    private javax.swing.JSpinner arabeSdwSpinner;
    private javax.swing.JCheckBox batidoExprimidoCheck;
    private javax.swing.JLabel batidoExprimidoPrice;
    private javax.swing.JSpinner batidoExprimidoSpinner;
    private javax.swing.JCheckBox bombonCheck;
    private javax.swing.JLabel bombonPrice;
    private javax.swing.JSpinner bombonSpinner;
    private javax.swing.JCheckBox cafe2GallesChocoCheck;
    private javax.swing.JSpinner cafe2MedialunasSpinner;
    private javax.swing.JLabel cafe2gallesPrice;
    private javax.swing.JCheckBox cafe2medialunasCheck;
    private javax.swing.JLabel cafe2medilunasPrice;
    private javax.swing.JLabel cafeArabePrice;
    private javax.swing.JCheckBox cafeConLecheCheck;
    private javax.swing.JLabel cafeConLechePrice;
    private javax.swing.JSpinner cafeConLecheSpinner;
    private javax.swing.JCheckBox cafeCuadradoDucleCheck;
    private javax.swing.JLabel cafeCuadradoDulcePrice;
    private javax.swing.JSpinner cafeCuadraroDulceSpinner;
    private javax.swing.JLabel cafeDelTiempoPrice;
    private javax.swing.JSpinner cafeDelTiempoSpinner;
    private javax.swing.JCheckBox cafeDobleCheck;
    private javax.swing.JLabel cafeDoblePrice;
    private javax.swing.JSpinner cafeDobleSpinner;
    private javax.swing.JCheckBox cafeIceCappuCheck;
    private javax.swing.JLabel cafeIceCappuPrice;
    private javax.swing.JSpinner cafeIceCappuSpinner;
    private javax.swing.JCheckBox cafeIceMassimoCheck;
    private javax.swing.JSpinner cafeIceMassimoSpinner;
    private javax.swing.JLabel cafeTortaIndPrice;
    private javax.swing.JSpinner cafeTortaIndSpinner;
    private javax.swing.JCheckBox cafeTortaIndividualCheck;
    private javax.swing.JCheckBox cafeTostadoArabeCheck;
    private javax.swing.JSpinner cafeTostadoArabeSpinner;
    private javax.swing.JButton cancelCandyProducts;
    private javax.swing.JButton cancelNoAlcoholDrinks;
    private javax.swing.JButton cancelPromoLunch;
    private javax.swing.JButton candyProducts;
    private javax.swing.JLabel cappuccinoTostadaPrice;
    private javax.swing.JSpinner cappuccinoTostadaSppiner;
    private javax.swing.JCheckBox cappuccinoTostadasCheck;
    private javax.swing.JSpinner cappucinnoMedialunaSpinner;
    private javax.swing.JCheckBox cappucinoMedialunaCheck;
    private javax.swing.JLabel cappucinoMedialunaPrice;
    private javax.swing.JButton cashCalculateTotal;
    private javax.swing.JLabel cashChangeBack;
    private javax.swing.JComboBox<String> cashDiscountCombo;
    private javax.swing.JButton cashPayAction;
    private javax.swing.JCheckBox cashPayDiscountCheck;
    private javax.swing.JButton cashPayment;
    private javax.swing.JLabel cashSubTotal;
    private javax.swing.JLabel cashTotalPay;
    private javax.swing.JCheckBox cbtPolloCheck;
    private javax.swing.JLabel cbtPolloPrice;
    private javax.swing.JSpinner cbtPolloSpinner;
    private javax.swing.JCheckBox cepitaCheck;
    private javax.swing.JLabel cepitaPrice;
    private javax.swing.JSpinner cepitaSpinner;
    private javax.swing.JCheckBox cervezaCheck;
    private javax.swing.JLabel cervezaPrice;
    private javax.swing.JSpinner cervezaSpinner;
    private javax.swing.JCheckBox chocolatadaCheck;
    private javax.swing.JLabel chocolatadaPrice;
    private javax.swing.JSpinner chocolatadaSpinner;
    private javax.swing.JButton closeSandwichORder;
    private javax.swing.JButton closeTable;
    private javax.swing.JCheckBox copaYogurtCheck;
    private javax.swing.JButton cosePromoBreakfast;
    private javax.swing.JCheckBox cuadradoSecoCheck;
    private javax.swing.JLabel cuadradoSecoPrice;
    private javax.swing.JSpinner cuadradoSecoSpinner;
    private javax.swing.JCheckBox delTiempoCheck;
    private javax.swing.JButton deleteTable;
    private javax.swing.JButton deleteTableButton;
    private javax.swing.JButton drinkAlcohol;
    private javax.swing.JButton drinkNoAlcohol;
    private javax.swing.JCheckBox empanadaPromoCheck;
    private javax.swing.JLabel empanadaPromoPrice;
    private javax.swing.JSpinner empanadaPromoSpinner;
    private javax.swing.JButton empleadosPromo;
    private javax.swing.JComboBox<String> employeeNameCombo;
    private javax.swing.JCheckBox ensaladaCesarCheck;
    private javax.swing.JLabel ensaladaCesarPrice;
    private javax.swing.JSpinner ensaladaCesarSpinner;
    private javax.swing.JCheckBox ensaladaDeFrutasCheck;
    private javax.swing.JLabel ensaladaFrutasPrice;
    private javax.swing.JSpinner ensaladaFrutasSpinner;
    private javax.swing.JCheckBox ensaladaIbericaCheck;
    private javax.swing.JLabel ensaladaIbericaPrice;
    private javax.swing.JSpinner ensaladaIbericaSpinner;
    private javax.swing.JCheckBox ensaladaPrimaveraCheck;
    private javax.swing.JLabel ensaladaPrimaveraPrice;
    private javax.swing.JSpinner ensaladaPrimaveraSpinner;
    private javax.swing.JCheckBox ensaladaTropicalCheck;
    private javax.swing.JLabel ensaladaTropicalPrice;
    private javax.swing.JSpinner ensaladaTropicalSpinner;
    private javax.swing.JCheckBox fraNuiCheck;
    private javax.swing.JLabel fraNuiPrice;
    private javax.swing.JSpinner franuiSpinner;
    private javax.swing.JLabel frutaCafePrice;
    private javax.swing.JSpinner frutaCafeTostadoSpinner;
    private javax.swing.JCheckBox frutaEleccionCheck;
    private javax.swing.JLabel frutaEleccionPrice;
    private javax.swing.JSpinner frutaEleccionSpinner;
    private javax.swing.JCheckBox frutasCafeTostadoCheck;
    private javax.swing.JCheckBox hotCiokCheck;
    private javax.swing.JLabel hotCiokPrice;
    private javax.swing.JSpinner hotCiokSpinner;
    private javax.swing.JCheckBox iceCappuccinoCheck;
    private javax.swing.JLabel iceCapuccinoPrice;
    private javax.swing.JSpinner iceCapuccinoSpinner;
    private javax.swing.JCheckBox iceTeaCheck;
    private javax.swing.JLabel iceTeaPrice;
    private javax.swing.JSpinner iceTeaSpinner;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel157;
    private javax.swing.JLabel jLabel159;
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
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JCheckBox jarritoCheck;
    private javax.swing.JLabel jarritoPrice;
    private javax.swing.JSpinner jarritoSpinner;
    private javax.swing.JCheckBox licuado2_1_TostadoIndCheck;
    private javax.swing.JCheckBox licuadoMedioTostadoCheck;
    private javax.swing.JLabel licuadoMedioTostadoPrice;
    private javax.swing.JSpinner licuadoMedioTostadoSpinner;
    private javax.swing.JLabel licuadosPrice;
    private javax.swing.JSpinner licuadosSpinner;
    private javax.swing.JLabel licuados_2_1_TostadoPrice;
    private javax.swing.JSpinner licuados_2_1_TostadoSpinner;
    private javax.swing.JCheckBox limonadaCheck;
    private javax.swing.JLabel limonadaPrice;
    private javax.swing.JSpinner limonadaSpinner;
    private javax.swing.JLabel limonadaTortaIndPrice;
    private javax.swing.JCheckBox limonadaTortaIndividiualCheck;
    private javax.swing.JSpinner limonadaTortaIndividualSpinner;
    private javax.swing.JCheckBox medialunaCheck;
    private javax.swing.JLabel medialunaPrice;
    private javax.swing.JCheckBox medialunaSdwCheck;
    private javax.swing.JLabel medialunaSdwPrice;
    private javax.swing.JSpinner medialunaSdwSpinner;
    private javax.swing.JSpinner medialunaSpinner;
    private javax.swing.JCheckBox milanesaPromoCheck;
    private javax.swing.JLabel milanesaPromoPrice;
    private javax.swing.JSpinner milanesaPromoSpinner;
    private javax.swing.JCheckBox milkShakeCheck;
    private javax.swing.JCheckBox milkshakesTortaCheck;
    private javax.swing.JLabel milkshakesTortaIndividualPrice;
    private javax.swing.JSpinner milkshakesTortaIndividualSpinner;
    private javax.swing.JCheckBox naranja2mediaLunasCheck;
    private javax.swing.JLabel naranja2medialunasPrice;
    private javax.swing.JSpinner naranja2medialunasSpinner;
    private javax.swing.JCheckBox naranjaTostadoCheck;
    private javax.swing.JLabel naranjaTostadoPrice;
    private javax.swing.JSpinner naranjaTostadoSpinner;
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
    private javax.swing.JCheckBox panchoPromoCheck;
    private javax.swing.JLabel panchoPromoPrice;
    private javax.swing.JSpinner panchoPromoSpinner;
    private javax.swing.JButton payAction;
    private javax.swing.JTextField paymentCash;
    private javax.swing.JCheckBox pizzaPromoCheck;
    private javax.swing.JLabel pizzaPromoPrice;
    private javax.swing.JSpinner pizzaPromoSpinner;
    private javax.swing.JCheckBox pocilloCheck;
    private javax.swing.JLabel pocilloPrice;
    private javax.swing.JSpinner pocilloSpinner;
    private javax.swing.JTable productDescriptionTable;
    private javax.swing.JButton saladProducts;
    private javax.swing.JCheckBox sandwichPromoCheck;
    private javax.swing.JLabel sandwichPromoPrice;
    private javax.swing.JSpinner sandwichPromoSpinner;
    private javax.swing.JCheckBox sandwichReimsPromoCheck;
    private javax.swing.JLabel sandwichReimsPromoPrice;
    private javax.swing.JSpinner sandwichReimsPromoSpinner;
    private javax.swing.JButton savePromoBreakfast;
    private javax.swing.JButton savePromoLunch;
    private javax.swing.JButton saveSandwichORder;
    private javax.swing.JButton saverAlcoholOrder;
    private javax.swing.JCheckBox sbtAtunCheck;
    private javax.swing.JLabel sbtAtunPrice;
    private javax.swing.JSpinner sbtAtunSpinner;
    private javax.swing.JCheckBox sbtCrudoCheck;
    private javax.swing.JLabel sbtCrudoPrice;
    private javax.swing.JSpinner sbtCrudoSpinner;
    private javax.swing.JCheckBox sbtMilanesaCheck;
    private javax.swing.JLabel sbtMilanesaPrice;
    private javax.swing.JSpinner sbtMilanesaSpinner;
    private javax.swing.JCheckBox sbtVerdurasCheck;
    private javax.swing.JLabel sbtVerdurasPrice;
    private javax.swing.JSpinner sbtVerdurasSpinner;
    private javax.swing.JCheckBox sdwEmpandaCheck;
    private javax.swing.JLabel sdwEmpandaPRice;
    private javax.swing.JSpinner sdwEmpandaSpinner;
    private javax.swing.JCheckBox sdwTartasCheck;
    private javax.swing.JLabel sdwTartasPrice;
    private javax.swing.JSpinner sdwTartasSpinner;
    private javax.swing.JButton seeConsuming;
    private javax.swing.JCheckBox submarinoCheck;
    private javax.swing.JLabel submarinoPrice;
    private javax.swing.JSpinner submarinoSpinner;
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
    private javax.swing.JCheckBox teCheck;
    private javax.swing.JLabel tePrice;
    private javax.swing.JSpinner teSpinner;
    private javax.swing.JCheckBox tonicCoffeeCheck;
    private javax.swing.JLabel tonicCoffeePrice;
    private javax.swing.JSpinner tonicCoffeeSpinner;
    private javax.swing.JCheckBox tortaIndividualCheck;
    private javax.swing.JLabel tortaIndividualPrice;
    private javax.swing.JSpinner tortaIndividualSpinner;
    private javax.swing.JCheckBox tostadasCheck;
    private javax.swing.JCheckBox tostadasDobleExprEspCheck;
    private javax.swing.JLabel tostadasExpEspPrice;
    private javax.swing.JSpinner tostadasExpEspSpinner;
    private javax.swing.JLabel tostadasPrice;
    private javax.swing.JSpinner tostadasSpinner;
    private javax.swing.JCheckBox tostadoSdwCheck;
    private javax.swing.JLabel tostadoSdwPrice;
    private javax.swing.JSpinner tostadoSdwSpinner;
    private javax.swing.JLabel totalPayLabel;
    private javax.swing.JLabel userTableCompleteNamePay;
    private javax.swing.JLabel whoAmILbl;
    private javax.swing.JLabel yogurtPrice;
    private javax.swing.JSpinner yogurtSpinner;
    // End of variables declaration//GEN-END:variables
}
