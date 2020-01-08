/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.WorkQueue;

import Business.Patient.Patient;

/**
 *
 * @author Team NullPointerException <>
 */
public class FinanceManagerApprovalForNewInsuranceWorkRequest extends WorkRequest{
    private Patient insurePatient;
    private double InsuranceAmount;
    private int duration;
    
    public Patient getInsurePatient() {
        return insurePatient;
    }

    public void setInsurePatient(Patient insurePatient) {
        this.insurePatient = insurePatient;
    }

    public double getInsuranceAmount() {
        return InsuranceAmount;
    }

    public void setInsuranceAmount(double InsuranceAmount) {
        this.InsuranceAmount = InsuranceAmount;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    @Override
    public String toString(){
        return String.valueOf(insurePatient.getPatientId());
    }
    
}
