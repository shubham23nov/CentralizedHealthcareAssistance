/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package userinterface.CAPatientManagerRole;

import Business.Disease;
import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Organization.CAOTPManagement;
import Business.Organization.CAPatientAuthorization;
import Business.Organization.CAPatientManagementOrganization;
import Business.Organization.Organization;
import Business.Patient.Patient;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.CANewPatientOTPAuthRequest;
import Business.WorkQueue.CANewPatientVerifyRequest;
import Business.WorkQueue.CAPatientAuthorizationRequest;
import Business.WorkQueue.WorkRequest;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import userinterface.DoctorRole.DoctorWorkAreaJPanel;

/**
 *
 * @author darsh
 */
public class PatientDetailsJPanel extends javax.swing.JPanel {

    /**
     * Creates new form PatientDetailsJPanel
     */
    
    private JPanel userProcessContainer;
    private UserAccount account;
    private CAPatientManagementOrganization organization;
    private Enterprise enterprise;
    private EcoSystem business;
    private Patient patient;
    private EcoSystem ecosystem;
    private DefaultListModel lstPatientModel;
    private DefaultListModel lstCommonModel ;
    private DefaultListModel lstAllergiesModel;
    private Border errorBorder;
    private Border border; 
    private boolean addUpdate;
    
    public PatientDetailsJPanel(boolean addUpdate, Patient patient,JPanel userProcessContainer, UserAccount account, CAPatientManagementOrganization organization, Enterprise enterprise, EcoSystem business) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.account = account;
        this.organization = organization;
        this.enterprise = enterprise; 
        this.business = business;
        this.addUpdate = addUpdate;
        errorBorder = BorderFactory.createLineBorder(Color.RED, 5);
        this.patient = patient;
        if (addUpdate){
            loadAddition();
            btnAddPatient.setText("Add Patient");
            lblFormTitle.setText("Add Patient");
        }
        else {
            loadUpdate();
            btnAddPatient.setText("Update Patient");
            lblFormTitle.setText("Update Patient");
        }
        eventListners();
    }
    
    public void loadUpdate(){
        txtName.setText(patient.getName());
        txtAddress.setText(patient.getAddress());
        txtCity.setText(patient.getCity());
        cboState.setSelectedItem(patient.getState());
        txtZip.setText(String.valueOf(patient.getZip()));
        txtCellNo.setText(patient.getCellNo());
        Calendar ca = Calendar.getInstance();
        ca.setTime(patient.getDob());
        dateDOB.setSelectedDate(ca);
        cboSex.setSelectedItem(patient.getSex());
        txtEmail.setText(patient.getEmail());
        txtGovtId.setText(patient.getId());
        txtEmergencyName1.setText(patient.getEmerName1());
        txtEmergencyName2.setText(patient.getEmerName2());
        txtEmergencyPhone1.setText(patient.getEmerNumber1());
        txtEmergencyPhone2.setText(patient.getEmerNumber2());
        txtEmergencyRelationship1.setText(patient.getEmerRelationship1());
        txtEmergencyRelationship2.setText(patient.getEmerRelationship2());
        
        if (patient.isCaffinatedDrink()){
            rdoBtnYes.setSelected(true);
            txtNoOfCups.setText(String.valueOf(patient.getNoOfCups()));
            txtNoOfCups.setEnabled(true);
        }else{
            rdoBtnNo.setSelected(true);
            txtNoOfCups.setText(String.valueOf(patient.getNoOfCups()));
            txtNoOfCups.setEnabled(false);
        }
        
        if (patient.getAlcoholFrequency().equalsIgnoreCase("Daily")){
            rdoBtnDaily.setSelected(true);
        }
        else if (patient.getAlcoholFrequency().equalsIgnoreCase("Weekly")){
            rdoBtnWeekly.setSelected(true);
        }else if (patient.getAlcoholFrequency().equalsIgnoreCase("Occasionally")){
            rdoBtnOccasionally.setSelected(true);
        }else {
            rdoBtnNever.setSelected(true);
        }
        
        if (patient.isSmoke()){
            rdoBtnSmokeYes.setSelected(true);
            txtSmokePacks.setText(String.valueOf(patient.getPacksPerDay()));
            txtSmokePacks.setEnabled(true);
        } else {
            rdoBtnSmokeNo.setSelected(true);
            txtSmokePacks.setText(String.valueOf(patient.getPacksPerDay()));
            txtSmokePacks.setEnabled(false);
        }
        
        txtExerciseTimes.setText(String.valueOf(patient.getExercisePerWeek()));
        txtExerciseType.setText(patient.getTypeOfExercise());
        
        txtAreaFamilyHistory.setText(patient.getFamilyMedicalHistory());
        
        lstAllergiesModel = new DefaultListModel();        
        for (String str: patient.getAllergies()){
            
            lstAllergiesModel.addElement(str);
            
        }
        lstAllergies.setModel(lstAllergiesModel);
        
        if (patient.getDietaryPreference().equalsIgnoreCase("Standard")){
            rdoBtnStandard.setSelected(true);
        }
        else if (patient.getDietaryPreference().equalsIgnoreCase("Pescetarian")){
            rdoBtnPescetarian.setSelected(true);
        }
        else if (patient.getDietaryPreference().equalsIgnoreCase("Vegetarian")){
            rdoBtnVegetarian.setSelected(true);
        }
        else if (patient.getDietaryPreference().equalsIgnoreCase("LactoVegetarian")){
            rdoBtnLactoVegetarian.setSelected(true);
        }
        else if (patient.getDietaryPreference().equalsIgnoreCase("OvoVegetarian")){
            rdoBtnOvoVegetarian.setSelected(true);
        }
        else if (patient.getDietaryPreference().equalsIgnoreCase("Vegan")){
            rdoBtnVegan.setSelected(true);
        }
        
        EcoSystem ecosystem1 = business;
        lstCommonModel = new DefaultListModel();
        lstPatientModel = new DefaultListModel();
        for (Disease disease: ecosystem1.getDiseaseList()){
            if (disease.isLifelongDisease()){
                if (patient.getPreviousDisease().contains(disease)){
                lstPatientModel.addElement(disease);
                }else{
                lstCommonModel.addElement(disease);
                }
            }
        };
        lstCommon.setModel(lstCommonModel);
        lstPatient.setModel(lstPatientModel);
        
        if (patient.getPhoto() != null){
        ImageIcon logoPreview = new ImageIcon(patient.getPhoto());
        lblPhoto.setIcon(logoPreview);}
        
        if (patient.getStatus().equalsIgnoreCase("Approved")){
            txtName.setEnabled(false);
            txtGovtId.setEnabled(false);
            dateDOB.setEnabled(false);
            cboSex.setEnabled(false);
        }
    }
    
    
    public void loadAddition(){
        txtNoOfCups.setText("0");
        txtNoOfCups.setEnabled(false);
        txtSmokePacks.setText("0");
        txtSmokePacks.setEnabled(false);
        txtExerciseTimes.setText("5");
        lstAllergiesModel = new DefaultListModel();
        loadDiseaseList();
    }
    
    public void loadDiseaseList(){
        ecosystem = EcoSystem.getInstance();
        lstCommonModel = new DefaultListModel();
        lstPatientModel = new DefaultListModel();
        for (Disease disease: ecosystem.getDiseaseList()){
            if (disease.isLifelongDisease()){
                lstCommonModel.addElement(disease);
            }
        };
        lstCommon.setModel(lstCommonModel);
    }
    
    public boolean validation(){
        if (validateName(txtName) && validateZip(txtZip) && validateCellNo(txtCellNo) && validateEmail(txtEmail)
                && validateGovtID(txtGovtId) && validateName(txtEmergencyName1) && validateName(txtEmergencyName2) &&
                validateCellNo(txtEmergencyPhone1) && validateCellNo(txtEmergencyPhone2) && validateCupsPerDay() && 
                validateSmokePacks() && validateExerciseTimes() && validateDOB()){return true;}
        else {return false;}
    }
    
    public void eventListners(){
        txtName.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
               if (validateName(txtName)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Name can contain only characters and spaces");
               }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                               if (validateName(txtName)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Name can contain only characters and spaces");
               }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                               if (validateName(txtName)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Name can contain only characters and spaces");
               }
            }
        });
        
        txtZip.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
               if (validateZip(txtZip)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Zip code format should be 12345 or 12345-6789");
               }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
               if (validateZip(txtZip)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Zip code format should be 12345 or 12345-6789");
               }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
              if (validateZip(txtZip)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Zip code format should be 12345 or 12345-6789");
               }
            }
        });
 
       txtCellNo.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
               if (validateCellNo(txtCellNo)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Phone number can contain 10 digits");
               }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
               if (validateCellNo(txtCellNo)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Phone number can contain 10 digits");
               }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
              if (validateCellNo(txtCellNo)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Phone number can contain 10 digits");
               }
            }
        });
 
             txtEmail.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
               if (validateEmail(txtEmail)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Please enter a valid emailID");
               }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
               if (validateEmail(txtEmail)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Please enter a valid emailID");
               }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
              if (validateEmail(txtEmail)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Please enter a valid emailID");
               }
            }
        });
             
             
        txtGovtId.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
               if (validateGovtID(txtGovtId)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Please enter a valid SSN");
               }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
               if (validateGovtID(txtGovtId)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Please enter a valid SSN");
               }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
              if (validateGovtID(txtGovtId)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Please enter a valid SSN");
               }
            }
        });     
 
        
        txtEmergencyName1.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
               if (validateName(txtEmergencyName1)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Name can contain only characters and spaces");
               }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
               if (validateName(txtEmergencyName1)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Name can contain only characters and spaces");
               }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
               if (validateName(txtEmergencyName1)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Name can contain only characters and spaces");
               }
            }
        });
        
        
                
        txtEmergencyName2.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
               if (validateName(txtEmergencyName2)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Name can contain only characters and spaces");
               }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
               if (validateName(txtEmergencyName2)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Name can contain only characters and spaces");
               }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
               if (validateName(txtEmergencyName2)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Name can contain only characters and spaces");
               }
            }
        });
        
        
        txtEmergencyPhone1.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
               if (validateCellNo(txtEmergencyPhone1)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Phone number can contain 10 digits");
               }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
               if (validateCellNo(txtEmergencyPhone1)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Phone number can contain 10 digits");
               }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
              if (validateCellNo(txtEmergencyPhone1)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Phone number can contain 10 digits");
               }
            }
        });
 

        txtEmergencyPhone2.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
               if (validateCellNo(txtEmergencyPhone2)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Phone number can contain 10 digits");
               }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
               if (validateCellNo(txtEmergencyPhone2)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Phone number can contain 10 digits");
               }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
              if (validateCellNo(txtEmergencyPhone2)){
                  lblError.setText("");
               }
               else {
               lblError.setText("Phone number can contain 10 digits");
               }
            }
        });

        txtNoOfCups.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
               if (validateCupsPerDay()){
                  lblError.setText("");
               }
               else {
               lblError.setText("Number of cups per days should be integer");
               }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
               if (validateCupsPerDay()){
                  lblError.setText("");
               }
               else {
               lblError.setText("Number of cups per days should be integer");
               }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
               if (validateCupsPerDay()){
                  lblError.setText("");
               }
               else {
               lblError.setText("Number of cups per days should be integer");
               }
            }
        });

        txtSmokePacks.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
               if (validateSmokePacks()){
                  lblError.setText("");
               }
               else {
               lblError.setText("Number of packs should be integer");
               }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
               if (validateSmokePacks()){
                  lblError.setText("");
               }
               else {
               lblError.setText("Number of packs should be integer");
               }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
               if (validateSmokePacks()){
                  lblError.setText("");
               }
               else {
               lblError.setText("Number of packs should be integer");
               }
            }
        });

              
        txtExerciseTimes.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
               if (validateExerciseTimes()){
                  lblError.setText("");
               }
               else {
               lblError.setText("Excersice days can be between 1-7");
               }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
               if (validateExerciseTimes()){
                  lblError.setText("");
               }
               else {
               lblError.setText("Excersice days can be between 1-7");
               }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
               if (validateExerciseTimes()){
                  lblError.setText("");
               }
               else {
               lblError.setText("Excersice days can be between 1-7");
               }
            }
        });
      
        
        
    }
    
    public boolean validateName(JTextField field){
        String regex = "^[\\p{L} .'-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(field.getText());
        if (matcher.matches()){
            field.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            return true;}
        else {
        field.setBorder(errorBorder);
        return false;}
    }
   

    public boolean validateZip(JTextField field){
        String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(field.getText());
        if (matcher.matches()){
            field.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            return true;}
        else {
            field.setBorder(errorBorder);
            return false;}
    }
    
    public boolean validateCellNo(JTextField field){
        String regex = "\\d{10}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(field.getText());
        if (matcher.matches()){
            field.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            return true;}
        else {
            field.setBorder(errorBorder);
            return false;}
    }
    
    public boolean validateEmail(JTextField field){
        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(field.getText());
        if (matcher.matches()){
            field.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            return true;}
        else {
            field.setBorder(errorBorder);
            return false;}
    }    
    
    public boolean validateGovtID(JTextField field){
        String regex = "\\d{9}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(field.getText());
        if (matcher.matches()){
            field.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            return true;}
        else {
            field.setBorder(errorBorder);
            return false;}
    }
    
    public boolean validateCupsPerDay(){
    try {
        int i = Integer.parseInt(txtNoOfCups.getText());
    }
    catch(NumberFormatException e){
        return false;
    }
    return true;
    }
    
    public boolean validateSmokePacks(){
    try {
        int i = Integer.parseInt(txtSmokePacks.getText());
    }
    catch(NumberFormatException e){
        return false;
    }
    return true;
    }
    
    public boolean validateExerciseTimes(){
    try {
        int i = Integer.parseInt(txtExerciseTimes.getText());
        if (!(i >= 1 && i <= 7)){
            return false;
        }
    }
    catch(NumberFormatException e){
        return false;
    }
    return true;
    }
    
    public boolean validateDOB(){
        Date date = new Date();
        if (dateDOB.getSelectedDate().getTime().compareTo(date) > 0 ){
            return false;
        }
        return true;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGrpCaffinated = new javax.swing.ButtonGroup();
        btnGrpAlcohol = new javax.swing.ButtonGroup();
        btnGrpSmoke = new javax.swing.ButtonGroup();
        btnGrpDietary = new javax.swing.ButtonGroup();
        lblFormTitle = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblAddress = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        lblCity = new javax.swing.JLabel();
        txtCity = new javax.swing.JTextField();
        lblState = new javax.swing.JLabel();
        cboState = new javax.swing.JComboBox();
        lblZip = new javax.swing.JLabel();
        txtZip = new javax.swing.JTextField();
        lblTextNo = new javax.swing.JLabel();
        txtCellNo = new javax.swing.JTextField();
        lblDOB = new javax.swing.JLabel();
        dateDOB = new datechooser.beans.DateChooserCombo();
        lblSex = new javax.swing.JLabel();
        cboSex = new javax.swing.JComboBox();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnUploadPhoto = new javax.swing.JButton();
        lblEmergencyName1 = new javax.swing.JLabel();
        txtEmergencyName1 = new javax.swing.JTextField();
        lblRelationship1 = new javax.swing.JLabel();
        txtEmergencyRelationship1 = new javax.swing.JTextField();
        lblEmergencyPhone1 = new javax.swing.JLabel();
        txtEmergencyPhone1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtEmergencyName2 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtEmergencyRelationship2 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtEmergencyPhone2 = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstCommon = new javax.swing.JList();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstPatient = new javax.swing.JList();
        btnToPatient = new javax.swing.JButton();
        btnToCommon = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        rdoBtnYes = new javax.swing.JRadioButton();
        rdoBtnNo = new javax.swing.JRadioButton();
        jLabel18 = new javax.swing.JLabel();
        txtNoOfCups = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        rdoBtnDaily = new javax.swing.JRadioButton();
        rdoBtnWeekly = new javax.swing.JRadioButton();
        rdoBtnOccasionally = new javax.swing.JRadioButton();
        jLabel24 = new javax.swing.JLabel();
        rdoBtnSmokeYes = new javax.swing.JRadioButton();
        rdoBtnSmokeNo = new javax.swing.JRadioButton();
        jLabel25 = new javax.swing.JLabel();
        txtSmokePacks = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtExerciseTimes = new javax.swing.JTextField();
        lblExerciseType = new javax.swing.JLabel();
        txtExerciseType = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel30 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAreaFamilyHistory = new javax.swing.JTextArea();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel31 = new javax.swing.JLabel();
        txtAllergies = new javax.swing.JTextField();
        btnAllergiesAdd = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        lstAllergies = new javax.swing.JList();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel32 = new javax.swing.JLabel();
        rdoBtnStandard = new javax.swing.JRadioButton();
        jLabel33 = new javax.swing.JLabel();
        rdoBtnPescetarian = new javax.swing.JRadioButton();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        rdoBtnVegetarian = new javax.swing.JRadioButton();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        rdoBtnLactoVegetarian = new javax.swing.JRadioButton();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        rdoBtnOvoVegetarian = new javax.swing.JRadioButton();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        rdoBtnVegan = new javax.swing.JRadioButton();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        btnVerifyPatient = new javax.swing.JButton();
        jLabel45 = new javax.swing.JLabel();
        txtPIN = new javax.swing.JTextField();
        btnAddPatient = new javax.swing.JButton();
        lblGovtId = new javax.swing.JLabel();
        txtGovtId = new javax.swing.JTextField();
        btnBack = new javax.swing.JButton();
        lblError = new javax.swing.JLabel();
        lblPhoto = new javax.swing.JLabel();
        btnRemoveAllergies = new javax.swing.JButton();
        rdoBtnNever = new javax.swing.JRadioButton();

        lblFormTitle.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        lblFormTitle.setText("Add Patient ");

        lblName.setText("Name: ");

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });
        txtName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNameKeyPressed(evt);
            }
        });

        lblAddress.setText("Address:");

        txtAddress.setMinimumSize(new java.awt.Dimension(6, 26));
        txtAddress.setPreferredSize(new java.awt.Dimension(6, 26));

        lblCity.setText("City:");

        txtCity.setMinimumSize(new java.awt.Dimension(6, 26));
        txtCity.setPreferredSize(new java.awt.Dimension(6, 26));

        lblState.setText("State:");

        cboState.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming" }));

        lblZip.setText("Zip:");

        lblTextNo.setText("Cell no:");

        lblDOB.setText("Date of Birth:");

        dateDOB.setCalendarPreferredSize(new java.awt.Dimension(350, 210));

        lblSex.setText("Sex:");

        cboSex.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Male", "Female", "Others" }));

        lblEmail.setText("Email:");

        jLabel10.setText("Photo:");

        btnUploadPhoto.setText("Upload Photo");
        btnUploadPhoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadPhotoActionPerformed(evt);
            }
        });

        lblEmergencyName1.setText("Name:");

        lblRelationship1.setText("Relationship:");

        lblEmergencyPhone1.setText("Phone:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel15.setText("Emergency Contact 1: ");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel19.setText("Emergency Contact 2:");

        jLabel20.setText("Name:");

        txtEmergencyName2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmergencyName2ActionPerformed(evt);
            }
        });

        jLabel21.setText("Relationship:");

        jLabel22.setText("Phone:");

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jScrollPane1.setViewportView(lstCommon);

        jLabel11.setText("Previously Diagnosed for:");

        jScrollPane2.setViewportView(lstPatient);

        btnToPatient.setText(">>");
        btnToPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToPatientActionPerformed(evt);
            }
        });

        btnToCommon.setText("<<");
        btnToCommon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToCommonActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel16.setText("Social Lifestyle Evaluation:");

        jLabel17.setText("Do you drink coffee, caffeinated soft drinks, or tea?");

        btnGrpCaffinated.add(rdoBtnYes);
        rdoBtnYes.setText("Yes");
        rdoBtnYes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoBtnYesActionPerformed(evt);
            }
        });

        btnGrpCaffinated.add(rdoBtnNo);
        rdoBtnNo.setSelected(true);
        rdoBtnNo.setText("No");
        rdoBtnNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoBtnNoActionPerformed(evt);
            }
        });

        jLabel18.setText("If yes, How many cups per day:");

        jLabel23.setText("Do you drink alcohol?");

        btnGrpAlcohol.add(rdoBtnDaily);
        rdoBtnDaily.setText("Daily");
        rdoBtnDaily.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoBtnDailyActionPerformed(evt);
            }
        });

        btnGrpAlcohol.add(rdoBtnWeekly);
        rdoBtnWeekly.setText("Weekly");

        btnGrpAlcohol.add(rdoBtnOccasionally);
        rdoBtnOccasionally.setText("Occasionally");
        rdoBtnOccasionally.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoBtnOccasionallyActionPerformed(evt);
            }
        });

        jLabel24.setText("Do you smoke?");

        btnGrpSmoke.add(rdoBtnSmokeYes);
        rdoBtnSmokeYes.setText("Yes");
        rdoBtnSmokeYes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoBtnSmokeYesActionPerformed(evt);
            }
        });

        btnGrpSmoke.add(rdoBtnSmokeNo);
        rdoBtnSmokeNo.setSelected(true);
        rdoBtnSmokeNo.setText("No");
        rdoBtnSmokeNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoBtnSmokeNoActionPerformed(evt);
            }
        });

        jLabel25.setText("If Yes, How much:");

        jLabel26.setText("packs per day.");

        jLabel27.setText("Do you exercise regularly?");

        jLabel28.setText("How many times a week?");

        lblExerciseType.setText("What type of exercise?");

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel30.setText("Family Medical History (If any to be shared)");

        txtAreaFamilyHistory.setColumns(20);
        txtAreaFamilyHistory.setRows(5);
        jScrollPane3.setViewportView(txtAreaFamilyHistory);

        jSeparator8.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel31.setText("Allergies (If any):");

        btnAllergiesAdd.setText("Add");
        btnAllergiesAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAllergiesAddActionPerformed(evt);
            }
        });

        jScrollPane4.setViewportView(lstAllergies);

        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel32.setText("Dietary Preference:");

        btnGrpDietary.add(rdoBtnStandard);
        rdoBtnStandard.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        rdoBtnStandard.setSelected(true);
        rdoBtnStandard.setText("Standard");

        jLabel33.setText("Includes red meat, fish, poultry, eggs, milk & plant");

        btnGrpDietary.add(rdoBtnPescetarian);
        rdoBtnPescetarian.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        rdoBtnPescetarian.setText("Pescetarian");
        rdoBtnPescetarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoBtnPescetarianActionPerformed(evt);
            }
        });

        jLabel34.setText("Includes fish, eggs, milk and plant products");

        jLabel35.setText("Excludes Red meat and poultry");

        btnGrpDietary.add(rdoBtnVegetarian);
        rdoBtnVegetarian.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        rdoBtnVegetarian.setText("Vegetarian");

        jLabel36.setText("Includes eggs, milk and plant products");

        jLabel37.setText("Excludes Red Meat, Fish and Poultry");

        btnGrpDietary.add(rdoBtnLactoVegetarian);
        rdoBtnLactoVegetarian.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        rdoBtnLactoVegetarian.setText("Lacto-vegetarian");
        rdoBtnLactoVegetarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoBtnLactoVegetarianActionPerformed(evt);
            }
        });

        jLabel38.setText("Includes milk and plant products");

        jLabel39.setText("Excludes Red Meat, Fish, Eggs and Poultry");

        btnGrpDietary.add(rdoBtnOvoVegetarian);
        rdoBtnOvoVegetarian.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        rdoBtnOvoVegetarian.setText("Ovo-vegetarian");

        jLabel40.setText("Includes eggs and plant products");

        jLabel41.setText("Excludes Red Meat, Fish, Milk products and Poultry");

        btnGrpDietary.add(rdoBtnVegan);
        rdoBtnVegan.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        rdoBtnVegan.setText("Vegan");
        rdoBtnVegan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoBtnVeganActionPerformed(evt);
            }
        });

        jLabel42.setText("Includes plant based foods only");

        jLabel43.setText("Excludes animal food of any kind");

        jLabel44.setText("1. Verify Patient");

        btnVerifyPatient.setText("Verify");
        btnVerifyPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerifyPatientActionPerformed(evt);
            }
        });

        jLabel45.setText("2. Confirm PIN and add patient: ");

        btnAddPatient.setText("Add Patient");
        btnAddPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPatientActionPerformed(evt);
            }
        });

        lblGovtId.setText("Government ID:");

        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        lblError.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblError.setForeground(new java.awt.Color(255, 0, 0));

        btnRemoveAllergies.setText("Remove Allergies");
        btnRemoveAllergies.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveAllergiesActionPerformed(evt);
            }
        });

        btnGrpAlcohol.add(rdoBtnNever);
        rdoBtnNever.setSelected(true);
        rdoBtnNever.setText("Never");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblAddress)
                                            .addComponent(lblName))
                                        .addGap(45, 45, 45)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtName)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblEmergencyName1)
                                            .addComponent(lblRelationship1)
                                            .addComponent(lblEmergencyPhone1))
                                        .addGap(26, 26, 26)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtEmergencyPhone1)
                                            .addComponent(txtEmergencyRelationship1)
                                            .addComponent(txtEmergencyName1, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblState)
                                            .addComponent(lblZip)
                                            .addComponent(lblTextNo)
                                            .addComponent(lblCity))
                                        .addGap(52, 52, 52)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtCellNo)
                                            .addComponent(txtZip)
                                            .addComponent(cboState, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtCity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblDOB)
                                            .addComponent(lblSex)
                                            .addComponent(lblEmail))
                                        .addGap(19, 19, 19)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cboSex, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(dateDOB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtEmail)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblGovtId)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtGovtId))
                                    .addComponent(jLabel15))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnUploadPhoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(157, 157, 157)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEmergencyRelationship2)
                                    .addComponent(txtEmergencyPhone2)
                                    .addComponent(txtEmergencyName2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)))
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel17)
                                        .addGap(29, 29, 29)
                                        .addComponent(rdoBtnYes)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rdoBtnNo)
                                        .addGap(28, 28, 28)
                                        .addComponent(jLabel18)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNoOfCups, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel27)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(jLabel24)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(rdoBtnSmokeYes)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(rdoBtnSmokeNo)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel25)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtSmokePacks))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(jLabel23)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(rdoBtnDaily)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(rdoBtnWeekly)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(rdoBtnOccasionally)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel26)
                                            .addComponent(rdoBtnNever))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator6))
                                .addContainerGap())
                            .addGroup(layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel28)
                                    .addComponent(lblExerciseType))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtExerciseType)
                                    .addComponent(txtExerciseTimes))
                                .addGap(650, 650, 650))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel30))
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel31)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtAllergies, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnAllergiesAdd))
                                    .addComponent(jScrollPane4)
                                    .addComponent(btnRemoveAllergies, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel32)
                                            .addComponent(rdoBtnStandard)
                                            .addComponent(rdoBtnPescetarian)
                                            .addComponent(jLabel34)
                                            .addComponent(jLabel35)
                                            .addComponent(rdoBtnVegetarian)
                                            .addComponent(jLabel36)
                                            .addComponent(jLabel37)
                                            .addComponent(rdoBtnLactoVegetarian)
                                            .addComponent(jLabel38)
                                            .addComponent(jLabel39)
                                            .addComponent(rdoBtnOvoVegetarian)
                                            .addComponent(jLabel40)
                                            .addComponent(jLabel41)
                                            .addComponent(rdoBtnVegan)
                                            .addComponent(jLabel42)
                                            .addComponent(jLabel43))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(43, 43, 43)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(btnToCommon, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                                            .addComponent(btnToPatient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(38, 38, 38)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addGap(12, 12, 12))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator5)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblError)
                        .addGap(336, 336, 336)
                        .addComponent(jLabel44)
                        .addGap(18, 18, 18)
                        .addComponent(btnVerifyPatient)
                        .addGap(141, 141, 141)
                        .addComponent(jLabel45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPIN, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddPatient)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                        .addComponent(lblFormTitle)
                        .addGap(1254, 1254, 1254))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFormTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBack))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblName)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAddress)
                            .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblCity)
                            .addComponent(txtCity, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblState)
                            .addComponent(cboState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(lblZip))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtZip, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTextNo)
                            .addComponent(txtCellNo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDOB)
                            .addComponent(dateDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSex)
                            .addComponent(cboSex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEmail))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGovtId, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGovtId))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEmergencyName1)
                            .addComponent(txtEmergencyName1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblRelationship1)
                            .addComponent(txtEmergencyRelationship1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEmergencyPhone1)
                            .addComponent(txtEmergencyPhone1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(txtEmergencyName2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txtEmergencyRelationship2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(txtEmergencyPhone2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(btnUploadPhoto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 839, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel11)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jScrollPane1)
                                            .addComponent(jScrollPane2)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(43, 43, 43)
                                        .addComponent(btnToPatient)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnToCommon)))
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17)
                                    .addComponent(rdoBtnYes)
                                    .addComponent(rdoBtnNo)
                                    .addComponent(jLabel18)
                                    .addComponent(txtNoOfCups, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel23)
                                    .addComponent(rdoBtnDaily)
                                    .addComponent(rdoBtnWeekly)
                                    .addComponent(rdoBtnOccasionally)
                                    .addComponent(rdoBtnNever))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel24)
                                    .addComponent(rdoBtnSmokeYes)
                                    .addComponent(rdoBtnSmokeNo)
                                    .addComponent(jLabel25)
                                    .addComponent(txtSmokePacks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel26))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel28)
                                    .addComponent(txtExerciseTimes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblExerciseType)
                                    .addComponent(txtExerciseType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel30)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel31)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtAllergies, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnAllergiesAdd))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnRemoveAllergies))
                                    .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel32)
                                        .addGap(18, 18, 18)
                                        .addComponent(rdoBtnStandard)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel33)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rdoBtnPescetarian)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel34)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel35)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rdoBtnVegetarian)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel36)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel37)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rdoBtnLactoVegetarian)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel38)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel39)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rdoBtnOvoVegetarian)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel40)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel41)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rdoBtnVegan)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel42)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel43)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(btnVerifyPatient)
                    .addComponent(jLabel45)
                    .addComponent(txtPIN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddPatient)
                    .addComponent(lblError))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtNameActionPerformed

    private void rdoBtnYesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoBtnYesActionPerformed
        // TODO add your handling code here:
        txtNoOfCups.setEnabled(true);
    }//GEN-LAST:event_rdoBtnYesActionPerformed

    private void rdoBtnDailyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoBtnDailyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoBtnDailyActionPerformed

    private void rdoBtnOccasionallyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoBtnOccasionallyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoBtnOccasionallyActionPerformed

    private void rdoBtnPescetarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoBtnPescetarianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoBtnPescetarianActionPerformed

    private void rdoBtnLactoVegetarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoBtnLactoVegetarianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoBtnLactoVegetarianActionPerformed

    private void rdoBtnVeganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoBtnVeganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoBtnVeganActionPerformed

    private void btnAddPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPatientActionPerformed
        // TODO add your handling code here:
        
        try{
            int i = Integer.parseInt(txtPIN.getText());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "OTP can only be integers");
            txtPIN.setText("");
            return;
        }
        
        for (Organization org: enterprise.getOrganizationDirectory().getOrganizationList()){
            if (org instanceof CAOTPManagement){
                CANewPatientVerifyRequest request = new CANewPatientVerifyRequest();
                request.setEmailID(txtEmail.getText());
                request.setOtp(Integer.parseInt(txtPIN.getText()));
                request.setMessage("New Patient Verification");
                request.setReceiver(null);
                request.setRequestDate(new Date());
                request.setSender(account);
               if (((CAOTPManagement)org).verifyEmailOTP(request)) {
                JOptionPane.showMessageDialog(null, "OTP Verification successful");
                loadPatient(this.patient);
                
        userProcessContainer.remove(this);
        Component[] componentArray = userProcessContainer.getComponents();
        Component component = componentArray[componentArray.length - 1];
        CAPatientManagerWorkAreaJPanel dwjp = (CAPatientManagerWorkAreaJPanel) component;
        CardLayout layout = (CardLayout)userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
        
 
         for (Component comp : componentArray){ 
             if (comp instanceof CAPatientManagerWorkAreaJPanel){
             CAPatientManagerWorkAreaJPanel cp = (CAPatientManagerWorkAreaJPanel)comp;
             cp.loadTable();
             }
            
            }
                
               }
               else{
               JOptionPane.showMessageDialog(null, "OTP Verification failed. Kidly try regenerating OTP");
               txtPIN.setText("");
               }
            }
        }

        
    }//GEN-LAST:event_btnAddPatientActionPerformed

    private void loadPatient(Patient patient){
      patient.setName(txtName.getText());
      patient.setAddress(txtAddress.getText());
      patient.setCity(txtCity.getText());
      patient.setState(cboState.getSelectedItem().toString());
      patient.setZip(Integer.parseInt(txtZip.getText()));
      //patient
      patient.setCellNo(txtCellNo.getText());
      patient.setDob(dateDOB.getSelectedDate().getTime());
      patient.setSex(cboSex.getSelectedItem().toString());
      patient.setEmail(txtEmail.getText());
      patient.setId(txtGovtId.getText());
      patient.setEmerName1(txtEmergencyName1.getText());
      patient.setEmerRelationship1(txtEmergencyRelationship1.getText());
      patient.setEmerNumber1(txtEmergencyPhone1.getText());
      patient.setEmerName2(txtEmergencyName2.getText());
      patient.setEmerRelationship2(txtEmergencyRelationship2.getText());
      patient.setEmerNumber2(txtEmergencyPhone2.getText());
      
      if (rdoBtnYes.isSelected())
      {patient.setCaffinatedDrink(true);}
      else {patient.setCaffinatedDrink(false);};
      
      patient.setNoOfCups(Integer.parseInt(txtNoOfCups.getText()));
      
      if (rdoBtnDaily.isSelected())
      {patient.setAlcoholFrequency("Daily");}
      else if (rdoBtnWeekly.isSelected()){
          patient.setAlcoholFrequency("Weekly");
      }
      else {patient.setAlcoholFrequency("Occasionally");};
      
      if(rdoBtnSmokeYes.isSelected())
          patient.setSmoke(true);
      else 
          patient.setSmoke(false);
      
      patient.setPacksPerDay(Integer.parseInt(txtSmokePacks.getText()));
      
      patient.setExercisePerWeek(Integer.parseInt(txtExerciseTimes.getText()));
      patient.setTypeOfExercise(txtExerciseType.getText());
      patient.setFamilyMedicalHistory(txtAreaFamilyHistory.getText());
      
      if (rdoBtnStandard.isSelected())
          patient.setDietaryPreference("Standard");
      else if (rdoBtnPescetarian.isSelected())
          patient.setDietaryPreference("Pescetarian");
      else if (rdoBtnVegetarian.isSelected())
          patient.setDietaryPreference("Vegetarian");
      else if (rdoBtnLactoVegetarian.isSelected())
          patient.setDietaryPreference("LactoVegetarian");
      else if (rdoBtnOvoVegetarian.isSelected())
          patient.setDietaryPreference("OvoVegetarian");
      else
          patient.setDietaryPreference("Vegan");
      
       patient.setPreviousDisease(new ArrayList<Disease>());
      for (int i = 0; i < lstPatientModel.getSize(); i++ ){
          patient.getPreviousDisease().add((Disease)lstPatientModel.getElementAt(i));
      }
      
      patient.setAllergies(new ArrayList<String>());
      for(int i = 0; i < lstAllergiesModel.getSize(); i++){
          patient.getAllergies().add((String)lstAllergiesModel.getElementAt(i));
      }
      
     
      
      if (addUpdate || (addUpdate == false && !patient.getStatus().equalsIgnoreCase("Approved"))){
       if (organization.getPatientDirectory().getPatientList().contains(patient)){
           organization.getPatientDirectory().getPatientList().remove(patient);
       }   
       patient.setStatus("Pending");   
       CAPatientAuthorizationRequest request = new CAPatientAuthorizationRequest();
       request.setMessage("Patient Authorization");
       request.setPatient(patient);
       request.setReceiver(null);
       request.setRequestDate(new Date());
       request.setSender(account);
       request.setStatus("Pending");
       
        for (Organization org: enterprise.getOrganizationDirectory().getOrganizationList()){
            if (org instanceof CAPatientAuthorization){
                ((CAPatientAuthorization)org).getWorkQueue().getWorkRequestList().add(request);
            }
        }
      }
 //     this.organization.getPatientDirectory().addPatient(patient);}
      
      
    }
    
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        
        userProcessContainer.remove(this);
        Component[] componentArray = userProcessContainer.getComponents();
        Component component = componentArray[componentArray.length - 1];
        CAPatientManagerWorkAreaJPanel dwjp = (CAPatientManagerWorkAreaJPanel) component;
        CardLayout layout = (CardLayout)userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
        
 
         for (Component comp : componentArray){ 
             if (comp instanceof CAPatientManagerWorkAreaJPanel){
             CAPatientManagerWorkAreaJPanel cp = (CAPatientManagerWorkAreaJPanel)comp;
             cp.loadTable();
             }
            
            }
        
    }//GEN-LAST:event_btnBackActionPerformed

    private void txtNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyPressed

    }//GEN-LAST:event_txtNameKeyPressed

    private void btnUploadPhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadPhotoActionPerformed
        // TODO add your handling code here:
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Please select an photo to upload...");
        FileNameExtensionFilter fileFormatFilter = new FileNameExtensionFilter("Images", "jpg", "gif", "bmp", "png", "jpeg");
        fileChooser.setFileFilter(fileFormatFilter);
        

        
        // You should use the parent component instead of null
        // but it was impossible to tell from the code snippet what that was.
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                patient.setPhoto(ImageIO.read(selectedFile));
            } catch (IOException ex) {
                System.out.println("Exception: " + ex.getMessage());
            }
            ImageIcon logoPreview = new ImageIcon(patient.getPhoto());
        lblPhoto.setIcon(logoPreview);
        };
    }//GEN-LAST:event_btnUploadPhotoActionPerformed

    private void btnToPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToPatientActionPerformed
        // TODO add your handling code here:
        
        
        if (lstCommon.getSelectedIndex() == -1){
            JOptionPane.showMessageDialog(null, "No data selected from the list to move");}
        else {
        
            List list = lstCommon.getSelectedValuesList();
            int[] selectedIndexes = lstCommon.getSelectedIndices();
            Object disease[] = list.toArray();
            
            for(int i = 0 ; i < list.size(); i++){
                lstPatientModel.addElement((Disease)disease[i]);
            }
            
            lstPatient.setModel(lstPatientModel);
            
            if (lstCommonModel.getSize() != 0){
                for (int j = 0 ; j < list.size(); j++){
                    lstCommonModel.removeElement((Disease)disease[j]);
                };
                lstCommon.setModel(lstCommonModel);
            }
        
        }
    }//GEN-LAST:event_btnToPatientActionPerformed

    private void btnToCommonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToCommonActionPerformed
        // TODO add your handling code here:
        
                
        if (lstPatient.getSelectedIndex() == -1){
            JOptionPane.showMessageDialog(null, "No data selected from the list to move");}
        else {
        
            List list = lstPatient.getSelectedValuesList();
            int[] selectedIndexes = lstPatient.getSelectedIndices();
            Object disease[] = list.toArray();
            
            for(int i = 0 ; i < list.size(); i++){
                lstCommonModel.addElement((Disease)disease[i]);
            }
            
            lstCommon.setModel(lstCommonModel);
            
            if (lstPatientModel.getSize() != 0){
                for (int j = 0 ; j < list.size(); j++){
                    lstPatientModel.removeElement((Disease)disease[j]);
                };
                lstPatient.setModel(lstPatientModel);
            }
        
        }
        
    }//GEN-LAST:event_btnToCommonActionPerformed

    private void btnAllergiesAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAllergiesAddActionPerformed
        // TODO add your handling code here:
        
        for (int i = 0; i < lstAllergiesModel.getSize(); i++){
            if (txtAllergies.getText().equalsIgnoreCase((String)lstAllergiesModel.getElementAt(i))){
                JOptionPane.showMessageDialog(null, "Your are trying to add already reported allergy");
                 txtAllergies.setText("");
                return;
            }
        }
        
        lstAllergiesModel.addElement(txtAllergies.getText());
        lstAllergies.setModel(lstAllergiesModel);
        txtAllergies.setText("");
        
    }//GEN-LAST:event_btnAllergiesAddActionPerformed

    private void btnRemoveAllergiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveAllergiesActionPerformed
        // TODO add your handling code here:
            List list = lstAllergies.getSelectedValuesList();
            int[] selectedIndexes = lstAllergies.getSelectedIndices();
            Object allergies[] = list.toArray();
            
             if (lstAllergiesModel.getSize() != 0){
                for (int j = 0 ; j < list.size(); j++){
                    lstAllergiesModel.removeElement((String)allergies[j]);
                };
                lstAllergies.setModel(lstAllergiesModel);
            }
    }//GEN-LAST:event_btnRemoveAllergiesActionPerformed

    private void btnVerifyPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerifyPatientActionPerformed
        // TODO add your handling code here:
      if (validation()){  
        if (addUpdate){  
        for (Network network:  business.getNetworkList()){
           
           for (Enterprise ent: network.getEnterpriseDirectory().getEnterpriseList()){
           
               for (Organization org: ent.getOrganizationDirectory().getOrganizationList()){
               
                   if (org instanceof CAPatientManagementOrganization){
                       for (Patient pat: ((CAPatientManagementOrganization)org).getPatientDirectory().getPatientList()){
                           if (pat.getId().equalsIgnoreCase(txtGovtId.getText())){
                               JOptionPane.showMessageDialog(null, "Patient with Govt ID already exist in system");
                               return;
                           }
                       } 
                   }
                   
                   if (org instanceof CAPatientAuthorization){
                       
                       for (WorkRequest requ: ((CAPatientAuthorization)org).getWorkQueue().getWorkRequestList()){
                          if ( ((CAPatientAuthorizationRequest)requ).getPatient().getId().equalsIgnoreCase(txtGovtId.getText())){
                               JOptionPane.showMessageDialog(null, "Patient with Govt ID already under processing");
                               return;  
                          }
                       }
                       
                   }
               
               }
           
           }
       
       }
        
        
        } 
        for (Organization org: enterprise.getOrganizationDirectory().getOrganizationList()){
            if (org instanceof CAOTPManagement){
                
                CANewPatientOTPAuthRequest request = new CANewPatientOTPAuthRequest();
                request.setEmail(txtEmail.getText());
                request.setMessage("New Patient Verification");
                request.setReceiver(null);
                request.setRequestDate(new Date());
                request.setSender(account);
                request.setStatus("Pending");
                ((CAOTPManagement)org).generateEmailOtp(request);
                JOptionPane.showMessageDialog(null, "OTP Sent on Email: " + txtEmail.getText());
            }
        }
      }else
      {JOptionPane.showMessageDialog(null, "Kindly check all the values");}
        
    }//GEN-LAST:event_btnVerifyPatientActionPerformed

    private void rdoBtnNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoBtnNoActionPerformed
        // TODO add your handling code here:
        txtNoOfCups.setText("0");
        txtNoOfCups.setEnabled(false);
    }//GEN-LAST:event_rdoBtnNoActionPerformed

    private void rdoBtnSmokeYesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoBtnSmokeYesActionPerformed
        // TODO add your handling code here:
        txtSmokePacks.setEnabled(true);
        txtSmokePacks.setText("");
    }//GEN-LAST:event_rdoBtnSmokeYesActionPerformed

    private void rdoBtnSmokeNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoBtnSmokeNoActionPerformed
        // TODO add your handling code here:
        txtSmokePacks.setEnabled(false);
        txtSmokePacks.setText("0");
    }//GEN-LAST:event_rdoBtnSmokeNoActionPerformed

    private void txtEmergencyName2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmergencyName2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmergencyName2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddPatient;
    private javax.swing.JButton btnAllergiesAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.ButtonGroup btnGrpAlcohol;
    private javax.swing.ButtonGroup btnGrpCaffinated;
    private javax.swing.ButtonGroup btnGrpDietary;
    private javax.swing.ButtonGroup btnGrpSmoke;
    private javax.swing.JButton btnRemoveAllergies;
    private javax.swing.JButton btnToCommon;
    private javax.swing.JButton btnToPatient;
    private javax.swing.JButton btnUploadPhoto;
    private javax.swing.JButton btnVerifyPatient;
    private javax.swing.JComboBox cboSex;
    private javax.swing.JComboBox cboState;
    private datechooser.beans.DateChooserCombo dateDOB;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
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
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblCity;
    private javax.swing.JLabel lblDOB;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEmergencyName1;
    private javax.swing.JLabel lblEmergencyPhone1;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblExerciseType;
    private javax.swing.JLabel lblFormTitle;
    private javax.swing.JLabel lblGovtId;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPhoto;
    private javax.swing.JLabel lblRelationship1;
    private javax.swing.JLabel lblSex;
    private javax.swing.JLabel lblState;
    private javax.swing.JLabel lblTextNo;
    private javax.swing.JLabel lblZip;
    private javax.swing.JList lstAllergies;
    private javax.swing.JList lstCommon;
    private javax.swing.JList lstPatient;
    private javax.swing.JRadioButton rdoBtnDaily;
    private javax.swing.JRadioButton rdoBtnLactoVegetarian;
    private javax.swing.JRadioButton rdoBtnNever;
    private javax.swing.JRadioButton rdoBtnNo;
    private javax.swing.JRadioButton rdoBtnOccasionally;
    private javax.swing.JRadioButton rdoBtnOvoVegetarian;
    private javax.swing.JRadioButton rdoBtnPescetarian;
    private javax.swing.JRadioButton rdoBtnSmokeNo;
    private javax.swing.JRadioButton rdoBtnSmokeYes;
    private javax.swing.JRadioButton rdoBtnStandard;
    private javax.swing.JRadioButton rdoBtnVegan;
    private javax.swing.JRadioButton rdoBtnVegetarian;
    private javax.swing.JRadioButton rdoBtnWeekly;
    private javax.swing.JRadioButton rdoBtnYes;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtAllergies;
    private javax.swing.JTextArea txtAreaFamilyHistory;
    private javax.swing.JTextField txtCellNo;
    private javax.swing.JTextField txtCity;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEmergencyName1;
    private javax.swing.JTextField txtEmergencyName2;
    private javax.swing.JTextField txtEmergencyPhone1;
    private javax.swing.JTextField txtEmergencyPhone2;
    private javax.swing.JTextField txtEmergencyRelationship1;
    private javax.swing.JTextField txtEmergencyRelationship2;
    private javax.swing.JTextField txtExerciseTimes;
    private javax.swing.JTextField txtExerciseType;
    private javax.swing.JTextField txtGovtId;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNoOfCups;
    private javax.swing.JTextField txtPIN;
    private javax.swing.JTextField txtSmokePacks;
    private javax.swing.JTextField txtZip;
    // End of variables declaration//GEN-END:variables
}
