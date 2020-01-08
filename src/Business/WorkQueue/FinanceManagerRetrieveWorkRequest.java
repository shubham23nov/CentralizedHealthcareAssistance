/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.WorkQueue;

import Business.Insurance.Insurer;
import Business.Patient.Patient;
import Business.Patient.Prescription;

/**
 *
 * @author Team NullPointerException <>
 */
public class FinanceManagerRetrieveWorkRequest extends WorkRequest {

    private Insurer insurer;
    private Prescription prescription;

    public Insurer getInsurer() {
        return insurer;
    }

    public void setInsurer(Insurer insurer) {
        this.insurer = insurer;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }
    
    @Override
    public String toString(){
        return String.valueOf(this.getInsurer().getInsurerId());
    }
}
