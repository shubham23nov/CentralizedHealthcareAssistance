/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface.PatientHistory;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.GeneralTools;
import Business.Hospital.Appointment;
import Business.Network.Network;
import Business.Organization.HospitalBillingOrganization;
import Business.Organization.HospitalDoctorOrganization;
import Business.Organization.Organization;
import Business.Patient.LabTest;
import Business.Patient.MedicalFile;
import Business.Patient.Notes;
import Business.Patient.Patient;
import Business.Patient.Prescription;
import Business.Patient.PrescriptionDetails;
import Business.Patient.TestDetails;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.HospitalBillingAppointmentWorkRequest;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import userinterface.DoctorRole.DoctorWorkAreaJPanel;
import userinterface.Patient.PatientProfileJPanel;

/**
 *
 * @author rruch
 */
public class PatientHistoryJPanel extends javax.swing.JPanel {

    /**
     * Creates new form PatientHistory
     */
    private JPanel userProcessContainer;
    private HospitalDoctorOrganization organization;
    private Enterprise enterprise;
    private UserAccount userAccount;
    Patient patient;
    EcoSystem business;
    MedicalFile hiddenMedicalFile = null;
    String hiddenInitialNotesTextArea;
    Appointment appointment;

    public PatientHistoryJPanel(JPanel userProcessContainer, UserAccount doctorAccount, HospitalDoctorOrganization HospOrganization, Enterprise enterprise, Patient patient, Appointment appointment, EcoSystem business) {
        initComponents();

        this.userProcessContainer = userProcessContainer;
        this.organization = HospOrganization;
        this.enterprise = enterprise;
        this.userAccount = doctorAccount;
        this.patient = patient;
        this.business = business;
        this.appointment = appointment;

        populate();

        //adding all listeners
        addlisteners();

    }

    public void addlisteners() {

        //add Listner to medical Records Table
        addListnerToMedicalRecords();

        //add listener to Note Table
        addListnerToNoteTable();

        // add listener to Prescription table
        addListnerToPrescriptionTable();

        //add listener to Lab Test Table
        addListnerToLabTestTable();
    }

    public void populate() {

        btnCreateNewNote1.setEnabled(false);
        btnMarkAsCured.setEnabled(false);
        btnCreateNewRecord.setEnabled(true);
        btnOrderNewLabTest.setEnabled(false);
        btnCreateNewPrescription.setEnabled(false);
        btnAppointmentComplete.setEnabled(false);

        chkBoxPatientVisibility.setSelected(true);
        hiddenInitialNotesTextArea = txtAreaAddNote.getText();
        lblPatientName.setText(patient.getName());
        txtAreaFamilyHistory.setText(patient.getFamilyMedicalHistory());
        BufferedImage photo = patient.getPhoto();
        System.out.println(photo);
//        if(photo != null){
//        ImageIcon logoPreview = new ImageIcon(patient.getPhoto());
//        lblPhoto.setIcon(logoPreview);
//        }

        DefaultListModel<String> list = new DefaultListModel<>();
        ArrayList<String> allergies = patient.getAllergies();
        System.out.println("userinterface.PatientHistory.PatientHistoryJPanel.populate() -- patientAlergies List  =  " + allergies);
        for (String allergy : allergies) {
            list.addElement(allergy);
        }
        lstAllergies.setModel(list);

        //populate table, medical Records.
        populateMediclRecordsTable();

    }

    public void populateMediclRecordsTable() {

        DefaultTableModel model = (DefaultTableModel) tblMedicalRecords.getModel();

        model.setRowCount(0);

        for (MedicalFile medicalFile : patient.getMedicalFile()) {
//        date,disease,doc,hosp,loc
            Object[] row = new Object[7];
            row[0] = medicalFile.getCreatedDate();
            row[1] = medicalFile.getProblemDiseases();
            System.out.println("userinterface.PatientHistory.PatientHistoryJPanel.populateMediclRecordsTable() -- medicalFile.getProblemDiseases() --  " + medicalFile.getProblemDiseases());
            UserAccount doc = medicalFile.getDoctorName();
            row[2] = doc;
            row[3] = GeneralTools.getMyEnterprise(doc, business);
            Network net = GeneralTools.getMyNetwork(organization, business);
            //System.out.println("userinterface.PatientHistory.PatientHistoryJPanel.populateMediclRecordsTable() -- network " + net);
            row[4] = net;
            row[5] = medicalFile;
            row[6] = medicalFile.isIsCured();

            model.addRow(row);

        }

    }

    public void addListnerToMedicalRecords() {

        tblMedicalRecords.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (tblMedicalRecords.getSelectedRow() > -1) {

                    btnCreateNewNote1.setEnabled(true);
                    btnMarkAsCured.setEnabled(true);
                    btnCreateNewRecord.setEnabled(true);
                    btnOrderNewLabTest.setEnabled(true);
                    btnCreateNewPrescription.setEnabled(true);
                    btnAppointmentComplete.setEnabled(true);

                    int selectedRow = tblMedicalRecords.getSelectedRow();
                    MedicalFile medFile = (MedicalFile) tblMedicalRecords.getValueAt(selectedRow, 5);

                    lblHaveBeenDiagBefore.setText(medFile.getHaveBeenDiagnosedBefore());
                    lblProblemInterferenceDailyLife.setText(medFile.getProblemInteferenceWithDailyActivities());
                    lblHowLong.setText(medFile.getHowLongProblemBegan());
                    lbltreatementBforeVisit.setText(medFile.getKindOfTreatmentTried());

                    hiddenMedicalFile = medFile;
                    // populate note table
                    populateNoteTableAsPerRecord(medFile);

                    //populate prescription table
                    populatePrescriptionTableAsPerRecord(medFile);

                    //populate lab test table
                    populateLabTestTableAsPerRecord(medFile);
                }
            }

        });

    }

    public void populateNoteTableAsPerRecord(MedicalFile medFile) {

        DefaultTableModel model = (DefaultTableModel) tblDoctorNotes.getModel();

        model.setRowCount(0);

        for (Notes note : medFile.getNotesList()) {
//        date,disease,doc,hosp,loc
            Object[] row = new Object[4];
            row[0] = note;
            UserAccount doc = medFile.getDoctorName();
            row[1] = doc;
            row[2] = GeneralTools.getMyEnterprise(doc, business);
            row[3] = GeneralTools.getMyNetwork(organization, business);

            model.addRow(row);

        }

    }

    public void populatePrescriptionTableAsPerRecord(MedicalFile medFile) {

        DefaultTableModel model = (DefaultTableModel) tblPrescriptions.getModel();

        model.setRowCount(0);

        for (Prescription prescription : medFile.getPrescription()) {
//        date,disease,doc,hosp,loc
            Object[] row = new Object[6];
            row[0] = prescription.getDate();
            row[1] = prescription;
            UserAccount doc = prescription.getDoctorName();
            row[2] = doc;
            row[3] = GeneralTools.getMyEnterprise(doc, business);
            row[4] = GeneralTools.getMyNetwork(organization, business);
            row[5] = (prescription.isStatus()) ? "Collected" : "Open";

            model.addRow(row);

        }

    }

    public void populateLabTestTableAsPerRecord(MedicalFile medFile) {

        DefaultTableModel model = (DefaultTableModel) tblLabTests.getModel();

        model.setRowCount(0);

        for (LabTest labTest : medFile.getLabTest()) {
//        date,disease,doc,hosp,loc
            Object[] row = new Object[9];
            row[0] = labTest.getDate();
            row[1] = labTest;
            UserAccount doc = labTest.getRefDoctorname();
            row[2] = doc;
            row[3] = GeneralTools.getMyEnterprise(doc, business);
            row[4] = GeneralTools.getMyNetwork(organization, business);
            UserAccount labAssistant = labTest.getLabAssistant();
            row[5] = labAssistant;
            row[6] = GeneralTools.getMyEnterprise(labAssistant, business);
            row[7] = GeneralTools.getMyNetwork(labAssistant, business);
            row[8] = (labTest.getStatus());

            model.addRow(row);

        }

    }

    public void addListnerToNoteTable() {

        tblDoctorNotes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (tblDoctorNotes.getSelectedRow() > -1) {
                    int selectedRow = tblDoctorNotes.getSelectedRow();
                    Notes note = (Notes) tblDoctorNotes.getValueAt(selectedRow, 0);

                    txtAreaViewNote.setText(note.getNotes());
                }
            }

        });

    }

    public void addListnerToPrescriptionTable() {

        tblPrescriptions.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (tblPrescriptions.getSelectedRow() > -1) {
                    int selectedRow = tblPrescriptions.getSelectedRow();
                    Prescription prescription = (Prescription) tblPrescriptions.getValueAt(selectedRow, 1);

                    DefaultTableModel model = (DefaultTableModel) tblPrescriptionDetails.getModel();

                    model.setRowCount(0);

                    for (PrescriptionDetails prescriptionDetails : prescription.getMedicineDetails()) {
                        //        MED,DOSE,FREQ,NUM OF DAYS,CONDITION,NOTES
                        Object[] row = new Object[6];
                        row[0] = prescriptionDetails.getMedicineName();
                        row[1] = prescriptionDetails.getDose();
                        row[2] = prescriptionDetails.getFrequency();
                        row[3] = prescriptionDetails.getNoOfDays();
                        row[4] = prescriptionDetails.getCondition();
                        row[5] = prescriptionDetails.getNotes();

                        model.addRow(row);

                    }

                }
            }

        });

    }

    public void addListnerToLabTestTable() {

        tblLabTests.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (tblLabTests.getSelectedRow() > -1) {
                    int selectedRow = tblLabTests.getSelectedRow();
                    LabTest labTest = (LabTest) tblLabTests.getValueAt(selectedRow, 1);

                    DefaultTableModel model = (DefaultTableModel) tblTestDetails.getModel();

                    model.setRowCount(0);

                    for (TestDetails testDetails : labTest.getTestDetails()) {
                        //        MED,DOSE,FREQ,NUM OF DAYS,CONDITION,NOTES
                        Object[] row = new Object[5];
                        row[0] = testDetails.getTestName();
                        row[1] = testDetails.getResult();
                        row[2] = testDetails.getRefRange();
                        row[3] = testDetails.getUnit();
                        row[4] = testDetails.getObservation();

                        model.addRow(row);

                    }

                }
            }

        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblPhoto = new javax.swing.JLabel();
        lblFormTitle = new javax.swing.JLabel();
        lblPatientName = new javax.swing.JLabel();
        btnViewProfile = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMedicalRecords = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblHaveBeenDiagBefore = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblProblemInterferenceDailyLife = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblHowLong = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbltreatementBforeVisit = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDoctorNotes = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        btnCreateNewRecord = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAreaViewNote = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtAreaAddNote = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        btnCreateNewNote1 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        lstAllergies = new javax.swing.JList();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblPrescriptions = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtAreaFamilyHistory = new javax.swing.JTextArea();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblPrescriptionDetails = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblLabTests = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblTestDetails = new javax.swing.JTable();
        jLabel20 = new javax.swing.JLabel();
        btnCreateNewPrescription = new javax.swing.JButton();
        btnOrderNewLabTest = new javax.swing.JButton();
        btnAppointmentComplete = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        chkBoxPatientVisibility = new javax.swing.JCheckBox();
        btnMarkAsCured = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        txtFees = new javax.swing.JTextField();

        lblFormTitle.setFont(new java.awt.Font("Calibri", 3, 36)); // NOI18N
        lblFormTitle.setText("Patient Medical History");

        lblPatientName.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblPatientName.setText("Patient Name");

        btnViewProfile.setText("View / Edit   Profile");
        btnViewProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewProfileActionPerformed(evt);
            }
        });

        tblMedicalRecords.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date Created", "Disease", "Doctor", "Hospital", "Location", "Record ID", "IS Cured ?"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMedicalRecords.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblMedicalRecords);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setText("» Alergies");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("• Have been Diagnosed Before ?");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setText("→");

        lblHaveBeenDiagBefore.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setText("• Problems in interference with daily life ?");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setText("→");

        lblProblemInterferenceDailyLife.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setText("• How Long did the problem began before this visit ?");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setText("→");

        lblHowLong.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setText("• Kind of treatment taken before this visit ?");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setText("→");

        lbltreatementBforeVisit.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        tblDoctorNotes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date Created", "Doctor", "Hospital", "Location"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDoctorNotes.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblDoctorNotes);

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel10.setText("» Patient Medical Records");

        btnCreateNewRecord.setText("Create New Record");
        btnCreateNewRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateNewRecordActionPerformed(evt);
            }
        });

        txtAreaViewNote.setEditable(false);
        txtAreaViewNote.setColumns(20);
        txtAreaViewNote.setRows(5);
        txtAreaViewNote.setEnabled(false);
        jScrollPane3.setViewportView(txtAreaViewNote);

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel11.setText("View Note");

        txtAreaAddNote.setColumns(20);
        txtAreaAddNote.setRows(5);
        txtAreaAddNote.setText("Write Your Note here and click Add.");
        txtAreaAddNote.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAreaAddNoteFocusGained(evt);
            }
        });
        jScrollPane4.setViewportView(txtAreaAddNote);

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel12.setText("→");

        btnCreateNewNote1.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        btnCreateNewNote1.setText("<< Add");
        btnCreateNewNote1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateNewNote1ActionPerformed(evt);
            }
        });

        lstAllergies.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane6.setViewportView(lstAllergies);

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel14.setText("» Doctor's Notes");

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel15.setText("» Prescriptions");

        tblPrescriptions.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "ID", "Doctor", "Hospital", "Location", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPrescriptions.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(tblPrescriptions);

        txtAreaFamilyHistory.setEditable(false);
        txtAreaFamilyHistory.setColumns(20);
        txtAreaFamilyHistory.setRows(5);
        txtAreaFamilyHistory.setEnabled(false);
        jScrollPane5.setViewportView(txtAreaFamilyHistory);

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel16.setText("» Family History");

        tblPrescriptionDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Medicine", "Dose", "Frequency", "No. of days", "Condition", "Notes"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPrescriptionDetails.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(tblPrescriptionDetails);

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel17.setText("» Prescription Details");

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel13.setText("→");

        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        tblLabTests.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "ID", "Doctor", "Hospital", "Location", "Lab Assistant", "Lab's Hospital", "Lab's Location", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLabTests.getTableHeader().setReorderingAllowed(false);
        jScrollPane9.setViewportView(tblLabTests);

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel18.setText("» Lab Tests");

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel19.setText("→");

        tblTestDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Test Name", "Result", "Ref Range", "Unit", "Observation"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTestDetails.getTableHeader().setReorderingAllowed(false);
        jScrollPane10.setViewportView(tblTestDetails);

        jLabel20.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel20.setText("» Fees :");

        btnCreateNewPrescription.setText("New Prescription");
        btnCreateNewPrescription.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateNewPrescriptionActionPerformed(evt);
            }
        });

        btnOrderNewLabTest.setText("Order New Lab Test");
        btnOrderNewLabTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrderNewLabTestActionPerformed(evt);
            }
        });

        btnAppointmentComplete.setText("Appointment Complete");
        btnAppointmentComplete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAppointmentCompleteActionPerformed(evt);
            }
        });

        chkBoxPatientVisibility.setText("Patient Visibility");

        btnMarkAsCured.setText("Mark as Cured/Not Cured");
        btnMarkAsCured.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarkAsCuredActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel21.setText("» Test Details");

        txtFees.setText("0");
        txtFees.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFeesFocusGained(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblPatientName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPhoto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(65, 65, 65)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(348, 348, 348)
                                .addComponent(lblFormTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addComponent(jSeparator1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnViewProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 698, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(248, 248, 248)
                                        .addComponent(btnOrderNewLabTest, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(24, 24, 24))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnCreateNewPrescription, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(1, 1, 1)
                                                .addComponent(jLabel11)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel12))
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGap(23, 23, 23)
                                                    .addComponent(btnCreateNewNote1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(chkBoxPatientVisibility))))
                                        .addGap(31, 31, 31)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 613, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 613, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 821, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnMarkAsCured, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnCreateNewRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addGap(12, 12, 12)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                        .addComponent(jLabel3)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(lblHaveBeenDiagBefore, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                        .addComponent(jLabel7)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(lblHowLong, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                        .addComponent(jLabel5)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(lblProblemInterferenceDailyLife, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                        .addComponent(jLabel9)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(lbltreatementBforeVisit, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 725, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(0, 276, Short.MAX_VALUE)
                                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtFees, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(58, 58, 58)))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAppointmentComplete, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(1222, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(355, 355, 355)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblFormTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(1, 1, 1)))
                        .addGap(13, 13, 13)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(btnCreateNewRecord)
                            .addComponent(btnMarkAsCured))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(lblHaveBeenDiagBefore, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(lblProblemInterferenceDailyLife, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(lblHowLong, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbltreatementBforeVisit, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(212, 212, 212)
                                .addComponent(lblPatientName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnViewProfile)))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCreateNewNote1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chkBoxPatientVisibility))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(btnCreateNewPrescription)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOrderNewLabTest)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(81, 81, 81)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(txtFees, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(97, 97, 97)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(104, 117, Short.MAX_VALUE)))
                .addComponent(btnAppointmentComplete, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(668, Short.MAX_VALUE)
                    .addComponent(jLabel21)
                    .addGap(332, 332, 332)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:

        userProcessContainer.remove(this);
        Component[] componentArray = userProcessContainer.getComponents();
        Component component = componentArray[componentArray.length - 1];
        DoctorWorkAreaJPanel dwjp = (DoctorWorkAreaJPanel) component;
        dwjp.populateRequestTable();
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);

    }//GEN-LAST:event_btnBackActionPerformed

    private void btnViewProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewProfileActionPerformed
        // TODO add your handling code here:
         CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        userProcessContainer.add("RequestLabTestJPanel", new PatientProfile(userProcessContainer, userAccount, organization, enterprise, patient));
        layout.next(userProcessContainer);

    }//GEN-LAST:event_btnViewProfileActionPerformed

    private void btnAppointmentCompleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAppointmentCompleteActionPerformed
        // TODO add your handling code here:

        if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "Are you sure you want to mark it to complete? NOTE: you wont be able to make any changes again.", "Confirmation Required", JOptionPane.YES_NO_OPTION)) {

            try {
                if (txtFees.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Fees cannot be null!");
                    return;
                }
                int i = Integer.parseInt(txtFees.getText());
                appointment.setAppointmentAmount(i);
            } catch (NumberFormatException e) {
                return;
            }
            HospitalBillingAppointmentWorkRequest req = new HospitalBillingAppointmentWorkRequest();
            req.setAppointment(appointment);
            req.setSender(userAccount);
            req.setRequestDate(new Date());
            req.setStatus("open");

            appointment.setAptStatus(Appointment.AptStatus.COMPLETE);
            
            for(Organization oorg : enterprise.getOrganizationDirectory().getOrganizationList()){
            if(oorg instanceof HospitalBillingOrganization){
                
            oorg.getWorkQueue().getWorkRequestList().add(req);
            }
            }
            
            userProcessContainer.remove(this);
            Component[] componentArray = userProcessContainer.getComponents();
            Component component = componentArray[componentArray.length - 1];
            DoctorWorkAreaJPanel dwjp = (DoctorWorkAreaJPanel) component;
            dwjp.populateRequestTable();
            CardLayout layout = (CardLayout) userProcessContainer.getLayout();
            layout.previous(userProcessContainer);

        }


    }//GEN-LAST:event_btnAppointmentCompleteActionPerformed

    private void btnCreateNewRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateNewRecordActionPerformed
        // TODO add your handling code here:

        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        userProcessContainer.add("RequestLabTestJPanel", new CreateNewMedicalRecordJPanel(userProcessContainer, userAccount, organization, enterprise, patient));
        layout.next(userProcessContainer);

    }//GEN-LAST:event_btnCreateNewRecordActionPerformed

    private void btnCreateNewNote1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateNewNote1ActionPerformed
        // TODO add your handling code here:

        String textNote = txtAreaAddNote.getText();
        Boolean b = chkBoxPatientVisibility.isSelected();
        if (textNote.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Type something in the text area");
            return;
        }
        hiddenMedicalFile.addNotes(Date.from(Instant.now()), textNote, b, userAccount);

        populateNoteTableAsPerRecord(hiddenMedicalFile);
        txtAreaAddNote.setText("");
    }//GEN-LAST:event_btnCreateNewNote1ActionPerformed

    private void txtAreaAddNoteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAreaAddNoteFocusGained
        // TODO add your handling code here:
        if (txtAreaAddNote.getText().equalsIgnoreCase(hiddenInitialNotesTextArea)) {

            txtAreaAddNote.setText("");

        }
    }//GEN-LAST:event_txtAreaAddNoteFocusGained

    private void btnMarkAsCuredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarkAsCuredActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblMedicalRecords.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Please select a medical record from table");
            return;

        }

        hiddenMedicalFile.setIsCured(!hiddenMedicalFile.isIsCured());
        populateMediclRecordsTable();
    }//GEN-LAST:event_btnMarkAsCuredActionPerformed

    private void btnCreateNewPrescriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateNewPrescriptionActionPerformed

        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        userProcessContainer.add("RequestLabTestJPanel", new CreateNewPrescriptionJPanel(userProcessContainer, userAccount, organization, enterprise, patient, hiddenMedicalFile, business));
        layout.next(userProcessContainer);

    }//GEN-LAST:event_btnCreateNewPrescriptionActionPerformed

    private void btnOrderNewLabTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderNewLabTestActionPerformed
        // TODO add your handling code here:
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        userProcessContainer.add("RequestLabTestJPanel", new OrderNewLabTestJPanel(userProcessContainer, userAccount, organization, enterprise, patient, hiddenMedicalFile, business));
        layout.next(userProcessContainer);
    }//GEN-LAST:event_btnOrderNewLabTestActionPerformed

    private void txtFeesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFeesFocusGained
        if (txtFees.getText().equalsIgnoreCase("0")) {

            txtFees.setText("");

        }
    }//GEN-LAST:event_txtFeesFocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAppointmentComplete;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCreateNewNote1;
    private javax.swing.JButton btnCreateNewPrescription;
    private javax.swing.JButton btnCreateNewRecord;
    private javax.swing.JButton btnMarkAsCured;
    private javax.swing.JButton btnOrderNewLabTest;
    private javax.swing.JButton btnViewProfile;
    private javax.swing.JCheckBox chkBoxPatientVisibility;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblFormTitle;
    private javax.swing.JLabel lblHaveBeenDiagBefore;
    private javax.swing.JLabel lblHowLong;
    private javax.swing.JLabel lblPatientName;
    private javax.swing.JLabel lblPhoto;
    private javax.swing.JLabel lblProblemInterferenceDailyLife;
    private javax.swing.JLabel lbltreatementBforeVisit;
    private javax.swing.JList lstAllergies;
    private javax.swing.JTable tblDoctorNotes;
    private javax.swing.JTable tblLabTests;
    private javax.swing.JTable tblMedicalRecords;
    private javax.swing.JTable tblPrescriptionDetails;
    private javax.swing.JTable tblPrescriptions;
    private javax.swing.JTable tblTestDetails;
    private javax.swing.JTextArea txtAreaAddNote;
    private javax.swing.JTextArea txtAreaFamilyHistory;
    private javax.swing.JTextArea txtAreaViewNote;
    private javax.swing.JTextField txtFees;
    // End of variables declaration//GEN-END:variables
}
