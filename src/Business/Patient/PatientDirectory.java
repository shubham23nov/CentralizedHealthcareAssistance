/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Business.Patient;

import java.util.ArrayList;

/**
 *
 * @author darsh
 */
public class PatientDirectory {
    private ArrayList<Patient> patientList;
    
    public PatientDirectory(){
        this.patientList = new ArrayList<>();                
    }

    public ArrayList<Patient> getPatientList() {
        return patientList;
    }

    public void addPatient(Patient patient){
        this.patientList.add(patient);
    }
    
    public void removePatient(Patient patient){
        this.patientList.remove(patient);
    }
    
    public boolean patientExist(int patientId){
        
        for (Patient patient : patientList){
            if (patient.getPatientId() == patientId){
                return true;
            }
        }
        return false;
    }
    
    public Patient getPatient(int patientId){
        for (Patient patient : patientList){
            if (patient.getPatientId() == patientId){
                return patient;
            }
        }
        return null;
    }
        
}
