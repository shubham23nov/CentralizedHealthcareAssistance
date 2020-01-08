/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Business.Enterprise.CentralAuthorityEnterprise;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Organization.CAPatientManagementOrganization;
import Business.Organization.Organization;
import Business.Patient.Patient;
import java.util.ArrayList;

/**
 *
 * @author rruch
 */
public class TestClass {

    public static EcoSystem business;
    
    
    public TestClass() {

        business = EcoSystem.getInstance();

    }
    
    public ArrayList<Patient> getAllPatient() {
        
        
        ArrayList<Patient> patientList = new ArrayList<>();
        System.out.println("TestClass.getAllPatient");
        System.out.println(business.getName());
        for (Network network : business.getNetworkList()) {
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
                    }

                }

            }

        }

        return patientList;
    }

}
