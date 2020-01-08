/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface.DoctorRole;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Hospital.Appointment;
import Business.Organization.HospitalDoctorOrganization;
import Business.Patient.Patient;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.DoctorAppointmentWorkRequest;
import Business.WorkQueue.LabTestWorkRequest;
import Business.WorkQueue.WorkRequest;
import java.awt.CardLayout;
import java.time.Instant;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import userinterface.PatientHistory.PatientHistoryJPanel;

/**
 *
 * @author Team NullPointerException
 */
public class DoctorWorkAreaJPanel extends javax.swing.JPanel {

    private JPanel userProcessContainer;
    private HospitalDoctorOrganization organization;
    private Enterprise enterprise;
    private UserAccount userAccount;
    EcoSystem business;

    /**
     * Creates new form DoctorWorkAreaJPanel
     */
    public DoctorWorkAreaJPanel(JPanel userProcessContainer, UserAccount account, HospitalDoctorOrganization organization, Enterprise enterprise,EcoSystem business) {
        initComponents();

        this.userProcessContainer = userProcessContainer;
        this.organization = organization;
        this.enterprise = enterprise;
        this.userAccount = account;
        this.business=business;
        populateRequestTable();

        //listener of the table
        tableListener();
    }

    public void populateRequestTable() {
        DefaultTableModel model = (DefaultTableModel) workRequestJTable.getModel();

        model.setRowCount(0);
        for (WorkRequest request : organization.getWorkQueue().getWorkRequestList()) {

            Appointment appointment = ((DoctorAppointmentWorkRequest) request).getAppointment();
            Appointment.AptStatus appointmentStatus = appointment.getAptStatus();
            Date apptDate = appointment.getAppointmentDate();
            Date now = Date.from(Instant.now());
            int compareResult = apptDate.compareTo(now);
//            System.out.println("compareResult -  " + compareResult);
            if ((request.getReceiver() != userAccount) ||compareResult < 0 || (appointmentStatus.equals(Appointment.AptStatus.CANCELLED)) || (appointmentStatus.equals(Appointment.AptStatus.COMPLETE))) {
                continue;
            }
            Object[] row = new Object[7];
            row[0] = appointment;
            row[1] = appointment.getAppointmentDate();
            row[2] = appointment.getAptTime();
            row[3] = appointment.getPatient();
            row[4] = request.getSender();
            row[5] = request.getReceiver();
            row[6] = appointmentStatus;

            model.addRow(row);
        }

    }

    public void tableListener() {

        workRequestJTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
            if (workRequestJTable.getSelectedRow() > -1) {
                int selectedRow = workRequestJTable.getSelectedRow();

//                Appointment appointment = (Appointment) workRequestJTable.getValueAt(selectedRow, 0);
//                Date date = (Date) workRequestJTable.getValueAt(selectedRow, 1);
//                Appointment.Apttime status = (Appointment.Apttime) workRequestJTable.getValueAt(selectedRow, 2);
//                Patient myPatient = (Patient) workRequestJTable.getValueAt(selectedRow, 3);
//                UserAccount receptionistRequestor = (UserAccount) workRequestJTable.getValueAt(selectedRow, 4);


                UserAccount docAcceptor = (UserAccount) workRequestJTable.getValueAt(selectedRow, 5);
                Appointment.AptStatus aptStatus = (Appointment.AptStatus) workRequestJTable.getValueAt(selectedRow, 6);

                //if appointment is cancelled
                if (aptStatus.equals(Appointment.AptStatus.CANCELLED)) {
                    btnCancelAppointment.setEnabled(false);
                    btnAcceptAppointment.setEnabled(false);
                    btnViewPatient.setEnabled(false);

                } else {
                    // its not cancelled
                    btnCancelAppointment.setEnabled(true);

                    if (docAcceptor == null) {
                        //doctor has not accepted
                        btnAcceptAppointment.setEnabled(true);
                        btnViewPatient.setEnabled(false);

                    } else {
                        //doctor has accepted
                        btnAcceptAppointment.setEnabled(false);
                        btnViewPatient.setEnabled(true);
                    }

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

        jScrollPane1 = new javax.swing.JScrollPane();
        workRequestJTable = new javax.swing.JTable();
        refreshTestJButton = new javax.swing.JButton();
        btnViewPatient = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnAcceptAppointment = new javax.swing.JButton();
        btnCancelAppointment = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        lblFormTitle = new javax.swing.JLabel();

        workRequestJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Appointment Id", "Date", "Time", "Patient", "Requestor", "Doctor", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(workRequestJTable);

        refreshTestJButton.setText("Refresh");
        refreshTestJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshTestJButtonActionPerformed(evt);
            }
        });

        btnViewPatient.setText("View Patient");
        btnViewPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewPatientActionPerformed(evt);
            }
        });

        jLabel1.setText("My Appointments");

        btnAcceptAppointment.setText("Accept Appointment");
        btnAcceptAppointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcceptAppointmentActionPerformed(evt);
            }
        });

        btnCancelAppointment.setText("Cancel Appointment");
        btnCancelAppointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelAppointmentActionPerformed(evt);
            }
        });

        lblFormTitle.setFont(new java.awt.Font("Calibri", 3, 36)); // NOI18N
        lblFormTitle.setText("Appointment Schedule");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(438, 438, 438)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnViewPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(refreshTestJButton))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(btnCancelAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnAcceptAppointment)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(563, 563, 563)
                        .addComponent(lblFormTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(640, Short.MAX_VALUE))
            .addComponent(jSeparator1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblFormTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(refreshTestJButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelAppointment)
                    .addComponent(btnAcceptAppointment))
                .addGap(18, 18, 18)
                .addComponent(btnViewPatient)
                .addContainerGap(457, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void refreshTestJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshTestJButtonActionPerformed

        populateRequestTable();

    }//GEN-LAST:event_refreshTestJButtonActionPerformed

    private void btnViewPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewPatientActionPerformed

        int selectedRow = workRequestJTable.getSelectedRow();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Please select atleast one row.");
            return;
        }

       Appointment apt = (Appointment) workRequestJTable.getValueAt(selectedRow, 0);
       Patient myPatient = (Patient) workRequestJTable.getValueAt(selectedRow, 3);
       CardLayout layout = (CardLayout) userProcessContainer.getLayout();
       PatientHistoryJPanel patientHistoryPanel = new PatientHistoryJPanel(userProcessContainer, userAccount, organization, enterprise, myPatient,apt,business);
       userProcessContainer.add("PatientHistoryJPanel", patientHistoryPanel);
       layout.next(userProcessContainer);
       
    }//GEN-LAST:event_btnViewPatientActionPerformed

    private void btnAcceptAppointmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcceptAppointmentActionPerformed
        // TODO add your handling code here:
        
        int selectedRow = workRequestJTable.getSelectedRow();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Please select atleast one row.");
            return;
        }
        
        
        
        
        
        
    }//GEN-LAST:event_btnAcceptAppointmentActionPerformed

    private void btnCancelAppointmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelAppointmentActionPerformed
        // TODO add your handling code here:
        
        int selectedRow = workRequestJTable.getSelectedRow();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Please select atleast one row.");
            return;
        }
        Appointment appointment = (Appointment) workRequestJTable.getValueAt(selectedRow, 0);
        appointment.setAptStatus(Appointment.AptStatus.CANCELLED);
        
        populateRequestTable();
        
        
        
    }//GEN-LAST:event_btnCancelAppointmentActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAcceptAppointment;
    private javax.swing.JButton btnCancelAppointment;
    private javax.swing.JButton btnViewPatient;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblFormTitle;
    private javax.swing.JButton refreshTestJButton;
    private javax.swing.JTable workRequestJTable;
    // End of variables declaration//GEN-END:variables
}
