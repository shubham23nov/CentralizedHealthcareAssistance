/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.WorkQueue;

import Business.Insurance.InsuranceClaims;
import Business.Patient.Patient;

/**
 *
 * @author Team NullPointerException <>
 */
public class UnderwriterPaymentAuthorizationWorkRequest extends WorkRequest{
    private Patient patient;

    private String claimType;
    private Double amount;

    public Patient getPatient() {
        return patient;
    }

    public void setPtient(Patient ptient) {
        this.patient = ptient;
    }
    

    public String getClaimType() {
        return claimType;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return patient.getName();
    }

    
    
}
