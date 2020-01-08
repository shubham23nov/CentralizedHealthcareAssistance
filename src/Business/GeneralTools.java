/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Business.Enterprise.CentralAuthorityEnterprise;
import Business.Enterprise.Enterprise;
import Business.Enterprise.InsuranceSystemEnterprise;
import Business.Insurance.Insurer;
import Business.Insurance.InsurerDirectory;
import Business.Network.Network;
import Business.Organization.CAPatientManagementOrganization;
import Business.Organization.InsuranceFinanceOrganization;
import Business.Organization.InsuranceUnderwriterOrganization;
import Business.Organization.Organization;
import Business.Patient.Patient;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.UnderwriterPaymentAuthorizationWorkRequest;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author rruch
 */
public class GeneralTools {

    public GeneralTools() {

    }

    public static Network getMyNetwork(Organization organization, EcoSystem eco) {

        Network myNetwork = null;
        Enterprise myEnterprise = null;
        Organization parentOrganization = null;
        Organization myOrganization = null;

        for (Network network : eco.getNetworkList()) {
            //Step 2.a: check against each enterprise

            for (Enterprise enterprise : network.getEnterpriseDirectory().getEnterpriseList()) {

                for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {

                    if (org == organization) {
                        myEnterprise = enterprise;
                        myNetwork = network;
                        myOrganization = organization;

                    }

                }

            }

        }

        return myNetwork;
    }

    public static Enterprise getMyEnterprise(Organization organization, EcoSystem eco) {

        Network myNetwork = null;
        Enterprise myEnterprise = null;
        Organization parentOrganization = null;
        Organization myOrganization = null;

        for (Network network : eco.getNetworkList()) {
            //Step 2.a: check against each enterprise

            for (Enterprise enterprise : network.getEnterpriseDirectory().getEnterpriseList()) {

                for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {

                    if (org == organization) {
                        myEnterprise = enterprise;
                        myNetwork = network;
                        myOrganization = organization;

                    }

                }

            }

        }

        return myEnterprise;
    }

    public static Enterprise getMyEnterprise(UserAccount userAccount, EcoSystem eco) {

        Network myNetwork = null;
        Enterprise myEnterprise = null;
        Organization parentOrganization = null;
        Organization myOrganization = null;

        for (Network network : eco.getNetworkList()) {
            //Step 2.a: check against each enterprise

            for (Enterprise enterprise : network.getEnterpriseDirectory().getEnterpriseList()) {

                for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {

                    for (UserAccount user : org.getUserAccountDirectory().getUserAccountList()) {

                        if (user == userAccount) {

                            myEnterprise = enterprise;

                        }
                    }

                }

            }

        }

        return myEnterprise;
    }

    public static Network getMyNetwork(UserAccount userAccount, EcoSystem eco) {

        Network myNetwork = null;
        Enterprise myEnterprise = null;
        Organization parentOrganization = null;
        Organization myOrganization = null;

        for (Network network : eco.getNetworkList()) {
            //Step 2.a: check against each enterprise

            for (Enterprise enterprise : network.getEnterpriseDirectory().getEnterpriseList()) {

                for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {

                    for (UserAccount user : org.getUserAccountDirectory().getUserAccountList()) {

                        if (user == userAccount) {

                            myNetwork = network;

                        }
                    }

                }

            }

        }

        return myNetwork;
    }

    public static ArrayList<Patient> getAllPatient(EcoSystem eco) {

        ArrayList<Patient> patientList = new ArrayList<>();
        System.out.println("TestClass.getAllPatient");
        System.out.println("this ----- " + eco.getName());
        for (Network network : eco.getNetworkList()) {
            //Step 2.a: check against each enterprise
            System.out.println(network.getName());
            for (Enterprise enterprise : network.getEnterpriseDirectory().getEnterpriseList()) {

                if (!(enterprise instanceof CentralAuthorityEnterprise)) {
                    continue;
                } // only take CA enterprise
                System.out.println(enterprise.getName());
                for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {

                    if (!(org instanceof CAPatientManagementOrganization)) {
                        continue;
                    } // only take CA patient management org

                    System.out.println(org.getName());
                    for (Patient patient : ((CAPatientManagementOrganization) org).getPatientDirectory().getPatientList()) {
                        System.out.println(patient.getName());
                        patientList.add(patient);
                        System.out.println(" --- while adding patient -- general tools -- get all patient  -- loop -- patient  -- " + patient.getName() + " " + patient.getPatientId());
                    }

                }

            }

        }

        return patientList;
    }
    
    
    public static InsuranceFinanceOrganization searchForInsuranceCompany(Patient patient, EcoSystem eco) {
        
        int patientId  = patient.getPatientId();
        InsuranceFinanceOrganization org = null;
        for (Network city : eco.getNetworkList()) {
            for (Enterprise ep : city.getEnterpriseDirectory().getEnterpriseList()) {
                for (Organization organization : ep.getOrganizationDirectory().getOrganizationList()) {
                    if (organization instanceof InsuranceFinanceOrganization) {
                        for (Insurer pt : ((InsuranceFinanceOrganization)org).getInsurerDirectory().getInsurerList()) {
                            if (pt.getInsurerId() == patientId) {
                                org = (InsuranceFinanceOrganization) organization;
                                return org;
                            }
                        }
                        break;
                    }
                }
            }
        }

        return org;

    }
    
    public static boolean InsuranceUnderWritingRequestGen(Patient patient, String claimType, Double amount, UserAccount account, EcoSystem business){
    
        UnderwriterPaymentAuthorizationWorkRequest request = new UnderwriterPaymentAuthorizationWorkRequest();
        request.setClaimType(claimType);
        request.setPtient(patient);
        request.setAmount(amount);
        request.setSender(account);
        request.setStatus("Initiated");
        
        for (Network net: business.getNetworkList()){
            for (Enterprise ent : net.getEnterpriseDirectory().getEnterpriseList()){
                if (ent instanceof InsuranceSystemEnterprise){
                    
                    for (Organization org : ent.getOrganizationDirectory().getOrganizationList()){
                        if (org instanceof InsuranceFinanceOrganization){
                            for ( Insurer ins :  ((InsuranceFinanceOrganization)org).getInsurerDirectory().getInsurerList()){
                                 if (ins.getPatient() == patient){
                                     for (Organization org1 : ent.getOrganizationDirectory().getOrganizationList()){
                                         if (org1 instanceof InsuranceUnderwriterOrganization){
                                             ((InsuranceUnderwriterOrganization)org1).getWorkQueue().getWorkRequestList().add(request);
                                             return true;
                                         }
                                     }
                                 }
                            }
                        }
                    }
                    
                }
            }
        }
      return false;  
    }
//     public static InsuranceUnderwriterOrganization searchForUnderwriter(Patient patient, EcoSystem eco) {
//        
//        int patientId  = patient.getPatientId();
//        InsuranceUnderwriterOrganization org = null;
//        for (Network city : eco.getNetworkList()) {
//            for (Enterprise ep : city.getEnterpriseDirectory().getEnterpriseList()) {
//                for (Organization organization : ep.getOrganizationDirectory().getOrganizationList()) {
//                    if (organization instanceof InsuranceUnderwriterOrganization) {
//                        for (Insurer pt : ((InsuranceUnderwriterOrganization)org).getInsurerDirectory().getInsurerList()) {
//                            if (pt.getInsurerId() == patientId) {
//                                org = (InsuranceUnderwriterOrganization) organization;
//                                return org;
//                            }
//                        }
//                        break;
//                    }
//                }
//            }
//        }
//
//        return org;
//
//    }
     
     public static Insurer searchForInsurer(Patient patient, InsuranceFinanceOrganization org) {
        
        int patientId  = patient.getPatientId();
        Insurer insure = null;
                        for (Insurer pt : ((InsuranceFinanceOrganization)org).getInsurerDirectory().getInsurerList()) {
                            if (pt.getInsurerId() == patientId) {
                                insure = pt;
                                return pt;
                            }
                        }

        return insure;

    }
     
     
     public static boolean isPatientInsured(Patient insurePatient,EcoSystem eco){
         
    for( Network network : eco.getNetworkList()){
    
        for( Enterprise enterprise : network.getEnterpriseDirectory().getEnterpriseList() ){
        
            for(Organization organization : enterprise.getOrganizationDirectory().getOrganizationList()){
            
                if( organization instanceof InsuranceFinanceOrganization){
                    InsurerDirectory dir = ((InsuranceFinanceOrganization)organization).getInsurerDirectory();
                    System.out.println(" generaltools -- ispatientinsured -- dir -- " + dir);
                    ArrayList<Insurer> insurerList = dir.getInsurerList();
                    System.out.println("Business.GeneralTools.isPatientInsured() ---- list -- " + insurerList);
                    
                    for (Insurer insurer : insurerList){
                        
                        if(insurer.getInsurerId() == insurePatient.getPatientId()){
                        
                            return true;
                            
                        }
                    
                    }
                
                }
            
            }
        
        }
    
    }
    
    return false;
     
     }

}
