/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Insurance;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Enterprise.InsuranceSystemEnterprise;
import Business.GeneralTools;
import Business.Network.Network;
import Business.Organization.InsuranceFinanceOrganization;
import Business.Organization.InsuranceUnderwriterOrganization;
import Business.Organization.Organization;
import Business.Patient.Patient;
import Business.WorkQueue.FinanceManagerRetrieveWorkRequest;
import Business.WorkQueue.UnderwriterPaymentAuthorizationWorkRequest;
import Business.WorkQueue.WorkRequest;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Team NullPointerException <>
 */
public class InsuranceClaims {

    private int patientId;
    private double claimAmount;
    private ClaimType claimType;
//    private InsuranceUnderwriterOrganization underwriterOrganization;
    private EcoSystem business;

//    private InsuranceUnderwriterOrganization getUnderwriterOrg() {
//        InsuranceUnderwriterOrganization underwriterOrg = null;
//        for (Network cities : business.getNetworkList()) {
//            for (Enterprise ep : cities.getEnterpriseDirectory().getEnterpriseList()) {
//                if (ep instanceof InsuranceSystemEnterprise) {
//                    for (Organization org : ep.getOrganizationDirectory().getOrganizationList()) {
//                        if (org instanceof InsuranceUnderwriterOrganization) {
//                            underwriterOrg = (InsuranceUnderwriterOrganization) org;
//                        }
//                    }
//                }
//            }
//        }
//        return underwriterOrg;
//    }
//    private InsuranceFinanceOrganization getFinanceMgrOrg() {
//        InsuranceFinanceOrganization finOrg = null;
//        for (Network cities : business.getNetworkList()) {
//            for (Enterprise ep : cities.getEnterpriseDirectory().getEnterpriseList()) {
//                for (Organization org : ep.getOrganizationDirectory().getOrganizationList()) {
//                    if (org instanceof InsuranceFinanceOrganization) {
//                        finOrg = (InsuranceFinanceOrganization) org;
//                    }
//                }
//            }
//        }
//        return finOrg;
//    }
    public enum ClaimType {
        Hospitalization("Hospitalization"),
        Surgery("Surgery"),
        Consultation("Consultation"),
        MedicineBill("MedicineBill");
        private String value;

        private ClaimType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public InsuranceClaims(int patientId, double claimAmount, ClaimType claimType, EcoSystem business) {
        this.patientId = patientId;
        this.claimAmount = claimAmount;
        this.claimType = claimType;
        this.business = business;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public double getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(double claimAmount) {
        this.claimAmount = claimAmount;
    }

    public ClaimType getClaimType() {
        return claimType;
    }

    public void setClaimType(ClaimType claimType) {
        this.claimType = claimType;
    }

    public void retrievePharmacyClaimRequest(Patient patient, InsuranceUnderwriterOrganization unOrganization) {
        ArrayList<InsuranceClaims> claimList = new ArrayList();
        for (WorkRequest request : unOrganization.getWorkQueue().getWorkRequestList()) {
            if (request instanceof UnderwriterPaymentAuthorizationWorkRequest) {
              //  claimList.add(((UnderwriterPaymentAuthorizationWorkRequest) request).getClaims());
            }
        }
        double totalClaims = 0.0;
        if (claimList != null) {
//            int patientId = claims.getPatientId();
            for (InsuranceClaims claims : claimList) {
                totalClaims = totalClaims + claims.getClaimAmount();
                this.setClaimAmount(totalClaims);
            }
            String message = "Amount " + totalClaims + " is requested from Insurance Company.";
            FinanceManagerRetrieveWorkRequest request = new FinanceManagerRetrieveWorkRequest();

            request.setMessage(message);
            request.setStatus("Sent");

            InsuranceFinanceOrganization org = GeneralTools.searchForInsuranceCompany(patient, business);
//            for (Network city : business.getNetworkList()) {
//                for (Enterprise ep : city.getEnterpriseDirectory().getEnterpriseList()) {
//                    for (Organization organization : ep.getOrganizationDirectory().getOrganizationList()) {
//                        if (organization instanceof InsuranceFinanceOrganization) {
//                            for (Insurer patient : ((InsuranceFinanceOrganization) organization).getInsurerDirectory().getInsurerList()) {
//                                if (patient.getInsurerId() == patientId) {
//                                    org = (InsuranceFinanceOrganization) organization;
//                                    request.setInsurer(patient);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
            if (org != null) {
                org.getWorkQueue().getWorkRequestList().add(request);
            }
            Insurer insurer = retrieveInsurerDetailsRequest(patient, org);
            if (insurer.getInsuredAmount() > this.getClaimAmount() && insurer.getDateOfExpiration().after(new Date()) && insurer.getDateOfInsurance().before(new Date())) {
                insurer.setInsuredAmount(insurer.getInsuredAmount() - claimAmount);
                request.setStatus("Approved");
            }
        }
    }

    public Insurer retrieveInsurerDetailsRequest(Patient patient, InsuranceFinanceOrganization org) {
        
        return GeneralTools.searchForInsurer(patient, org);
    }
}
