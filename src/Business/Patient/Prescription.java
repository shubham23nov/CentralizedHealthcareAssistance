/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Business.Patient;

import Business.UserAccount.UserAccount;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author darsh
 */
public class Prescription {
    
    private Date date;
    private int prescriptionId;
    private String name; 
    private UserAccount doctorName;
    private boolean status;  //Sold or not
    private ArrayList<PrescriptionDetails> medicineDetails;
    
    public Prescription(Date date, UserAccount  doctorName,int PrescriptionID){
        medicineDetails = new ArrayList<>();
        this.date = date;
        this.doctorName = doctorName;
        this.status = false;
        this.prescriptionId = PrescriptionID;

    }

    public int getPrescriptionId() {
        return prescriptionId;
    }
 

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public UserAccount getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(UserAccount doctorName) {
        this.doctorName = doctorName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<PrescriptionDetails> getMedicineDetails() {
        return medicineDetails;
    }

    public void setMedicineDetails(ArrayList<PrescriptionDetails> medicineDetails) {
        this.medicineDetails = medicineDetails;
    }
    
    public void addMedicine(String medicineName, String dose, String frequency, String condition, int noOfDays, String notes){
         PrescriptionDetails  medicine = new PrescriptionDetails(medicineName, dose, frequency, condition, noOfDays, notes);
         this.medicineDetails.add(medicine);
         
    };

        @Override
        public String toString()
        {
            return String.valueOf(this.getPrescriptionId());
        }

}
