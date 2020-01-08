/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Insurance.InsurerDirectory;
import Business.Patient.Patient;
import Business.Role.FinanceManagerRole;
import Business.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author Team NullPointerException
 */
public class InsuranceFinanceOrganization extends Organization {

     private InsurerDirectory insurerDirectory;
    
    public InsuranceFinanceOrganization() {
        super(Organization.Type.InsuranceFinance.getValue());
        this.insurerDirectory = new InsurerDirectory();
    }

    public void setInsurerDirectory(InsurerDirectory insurerDirectory) {
        this.insurerDirectory = insurerDirectory;
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> role = new ArrayList<>();
        role.add(new FinanceManagerRole());
        return role;
    }
    
    public InsurerDirectory getInsurerDirectory(){
        return this.insurerDirectory;
    }
    
}
