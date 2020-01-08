/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Organization.Organization.Type;
import java.util.ArrayList;

/**
 *
 * @author Team NullPointerException
 */
public class OrganizationDirectory {
    
    private ArrayList<Organization> organizationList;

    public OrganizationDirectory() {
        organizationList = new ArrayList();
    }

    public ArrayList<Organization> getOrganizationList() {
        return organizationList;
    }
    
    public Organization createOrganization(Type type){
        Organization organization = null;
        if (type.getValue().equals(Type.Doctor.getValue())){
            organization = new HospitalDoctorOrganization();
            organizationList.add(organization);
        }
        else if (type.getValue().equals(Type.Lab.getValue())){
            organization = new HospitalLabOrganization();
            organizationList.add(organization);
        }

        else if (type.getValue().equals(Type.PatientManagement.getValue())){
            organization = new CAPatientManagementOrganization();
            organizationList.add(organization);}
        
        else if (type.getValue().equals(Type.OTPManagement.getValue())){
            organization = new CAOTPManagement();
            organizationList.add(organization);
        }

        else if (type.getValue().equals(Type.PatientAuthorization.getValue())){
            organization = new CAPatientAuthorization();
            organizationList.add(organization);
        }
        
        else if (type.getValue().equals(Type.Statistics.getValue())){
            organization = new CAStatisticsOrg();
            organizationList.add(organization);
        }        
        
        else if (type.getValue().equals(Type.Reception.getValue())){
            organization = new HospitalReceptionOrganization();

            organizationList.add(organization);
        }
        else if (type.getValue().equals(Type.HospitalBilling.getValue())){
            organization = new HospitalBillingOrganization();

            organizationList.add(organization);
        }
        else if (type.getValue().equals(Type.Pharmacist.getValue())){
            organization = new DrugPharmacistOrganization();                    // Shubham Jain
            organizationList.add(organization);
        }
        else if (type.getValue().equals(Type.InsuranceFinance.getValue())){
            organization = new InsuranceFinanceOrganization();                    // Shubham Jain
            organizationList.add(organization);
        }
        else if (type.getValue().equals(Type.InsuranceUnderwriter.getValue())){
            organization = new InsuranceUnderwriterOrganization();                    // Shubham Jain
            organizationList.add(organization);
        }
        else if (type.getValue().equals(Type.InsuranceAgent.getValue())){
            organization = new InsuranceAgentOrganization();                    // Shubham Jain
            organizationList.add(organization);
        }
        return organization;
    }
}