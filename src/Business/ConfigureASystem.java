package Business;

import Business.Employee.Employee;
import Business.Enterprise.CentralAuthorityEnterprise;
import Business.Enterprise.Enterprise;
import Business.Enterprise.HospitalEnterprise;
import Business.Enterprise.InsuranceSystemEnterprise;
import Business.Enterprise.PharmacyEnterprise;
import Business.Network.Network;
import Business.Organization.CAPatientAuthorization;
import Business.Organization.CAPatientManagementOrganization;
import Business.Organization.DrugPharmacistOrganization;
import Business.Organization.HospitalDoctorOrganization;
import Business.Organization.HospitalLabOrganization;
import Business.Organization.HospitalReceptionOrganization;
import Business.Organization.InsuranceAgentOrganization;
import Business.Organization.InsuranceFinanceOrganization;
import Business.Organization.InsuranceUnderwriterOrganization;
import Business.Organization.Organization;
import Business.Role.Role;
import Business.Role.SystemAdminRole;
import Business.UserAccount.UserAccount;
import java.util.ArrayList;

/**
 *
 * @author Team NullPointerException
 */
public class ConfigureASystem {
    
    public static EcoSystem configure(){
        
        EcoSystem system = EcoSystem.getInstance();
        
        //Create a network
//        
//        Network boston = new Network();
//        boston.setName("Boston");
//        
//        
//        Network nyc = new Network();
//        nyc.setName("nyc");
//        
//        
//        //create an enterprise
//        HospitalEnterprise bosHosp = new HospitalEnterprise("Boston Hospital") ;
//        HospitalEnterprise nycHosp = new HospitalEnterprise("Nyc Hospital") ;
//        CentralAuthorityEnterprise bosCA = new CentralAuthorityEnterprise("Boston CA");
//        CentralAuthorityEnterprise nycCA = new CentralAuthorityEnterprise("Nyc CA");
//        PharmacyEnterprise bosPharm = new PharmacyEnterprise("Boston Pharmacist");
//        PharmacyEnterprise nycPharm = new PharmacyEnterprise("Nyc Pharmacist");
//        InsuranceSystemEnterprise bosInc = new InsuranceSystemEnterprise("Boston Insurance");
//        InsuranceSystemEnterprise nycInc = new InsuranceSystemEnterprise("Nyc Insurance");
//        
//        //initialize some organizations
//        
//        HospitalDoctorOrganization bosdoc = new HospitalDoctorOrganization();
//        HospitalDoctorOrganization nycdoc = new HospitalDoctorOrganization();
//        
//        HospitalLabOrganization boslab = new HospitalLabOrganization();
//        HospitalLabOrganization nyclab = new HospitalLabOrganization();
//        
//        HospitalReceptionOrganization bosrec = new HospitalReceptionOrganization();
//        HospitalReceptionOrganization nycrec = new HospitalReceptionOrganization();
//        
//        DrugPharmacistOrganization bospharmorg = new DrugPharmacistOrganization();
//        DrugPharmacistOrganization nycpharmorg = new DrugPharmacistOrganization();
//        
//        InsuranceFinanceOrganization bosincfin = new InsuranceFinanceOrganization();
//        InsuranceFinanceOrganization nycincfin = new InsuranceFinanceOrganization();
//        
//        InsuranceAgentOrganization bosincag = new InsuranceAgentOrganization();
//        InsuranceAgentOrganization nycincag = new InsuranceAgentOrganization();
//        
//        InsuranceUnderwriterOrganization bosincund = new InsuranceUnderwriterOrganization();
//        InsuranceUnderwriterOrganization nycincund = new InsuranceUnderwriterOrganization();
//        
//        CAPatientAuthorization boscapa = new CAPatientAuthorization();
//        CAPatientAuthorization nyccapa = new CAPatientAuthorization();
//        
//        CAPatientManagementOrganization boscapm = new CAPatientManagementOrganization();
//        CAPatientManagementOrganization nyccapm = new CAPatientManagementOrganization();
//        
        
        //have some employees 
        
        
        
        //create user account
        
        
        Employee employee = system.getEmployeeDirectory().createEmployee("sysadmin");
        
        UserAccount ua = system.getUserAccountDirectory().createUserAccount("sysadmin", "sysadmin", employee, new SystemAdminRole());
        
        return system;
    }
    
}
