/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Patient.PatientDirectory;
import Business.Role.CAPatientManagerRole;
import Business.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author Team NullPointerException
 */
public class CAPatientManagementOrganization extends Organization{
    
    private PatientDirectory patientDirectory;

    public CAPatientManagementOrganization() {
        super(Organization.Type.PatientManagement.getValue());
        this.patientDirectory = new PatientDirectory();
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
         ArrayList<Role> roles = new ArrayList();
        roles.add(new CAPatientManagerRole());
        return roles;
    }
    
    public PatientDirectory getPatientDirectory(){
        return this.patientDirectory;
    }
    
}
