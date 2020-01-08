/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Business.OTPVerification;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Organization.CAOTPManagement;
import Business.Organization.CAPatientManagementOrganization;
import Business.Organization.Organization;
import Business.Patient.Patient;
import Business.WorkQueue.CAFetchPatientRequest;
import Business.WorkQueue.CAOTPAuthorizationWorkRequest;

/**
 *
 * @author darsh
 */
public class OTPVerification {
    
    
    public static boolean verifyPatientEmail(EcoSystem business, CAOTPAuthorizationWorkRequest request){
    
       String email = ""; 
       
       for (Network network:  business.getNetworkList()){
           
           for (Enterprise enterprise: network.getEnterpriseDirectory().getEnterpriseList()){
           
               for (Organization organization: enterprise.getOrganizationDirectory().getOrganizationList()){
               
                   if (organization instanceof CAPatientManagementOrganization){
                       if (((CAPatientManagementOrganization)organization).getPatientDirectory().patientExist(request.getPatientId())){
                           email =  ((CAPatientManagementOrganization)organization).getPatientDirectory().getPatient(request.getPatientId()).getEmail();
                           for (Organization org: enterprise.getOrganizationDirectory().getOrganizationList()){
                               if (org instanceof CAOTPManagement){
                                   ((CAOTPManagement)org).generatePatientOtp(request, email);
                                   return true;
                               }
                           }
                       }
                   }
               
               }
           
           }
       
       }
       request.setStatus("fail");
       return false;
    }
    
    public static boolean fetchPatient(EcoSystem business, CAFetchPatientRequest request){
    
        
       
       for (Network network:  business.getNetworkList()){
           
           for (Enterprise enterprise: network.getEnterpriseDirectory().getEnterpriseList()){
           
               for (Organization organization: enterprise.getOrganizationDirectory().getOrganizationList()){
               
                   if (organization instanceof CAPatientManagementOrganization){
                       if (((CAPatientManagementOrganization)organization).getPatientDirectory().patientExist(request.getPatientId())){
                         Patient patient =  ((CAPatientManagementOrganization)organization).getPatientDirectory().getPatient(request.getPatientId());
                           for (Organization org: enterprise.getOrganizationDirectory().getOrganizationList()){
                               if (org instanceof CAOTPManagement){
                                   if (((CAOTPManagement)org).verifyPatientOTP(request)){
                                       request.setPatient(patient);
                                       return true;
                                   }else
                                   {
                                       request.setPatient(null);
                                       return false;
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
    
    
}
