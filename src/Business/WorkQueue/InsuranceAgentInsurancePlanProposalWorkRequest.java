/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.WorkQueue;

import Business.Insurance.Insurer;

/**
 *
 * @author Team NullPointerException <>
 */
public class InsuranceAgentInsurancePlanProposalWorkRequest extends WorkRequest{
    Insurer insuredPatient;

    public Insurer getInsuredPatient() {
        return insuredPatient;
    }

    public void setInsuredPatient(Insurer insuredPatient) {
        this.insuredPatient = insuredPatient;
    }
    
    @Override
    public String toString(){
        return String.valueOf(this.getInsuredPatient().getInsurerId());
    }
}
