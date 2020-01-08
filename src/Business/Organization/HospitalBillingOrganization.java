/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Role.AccountManagerRole;
import Business.Role.LabAssistantRole;
import Business.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author Team NullPointerException
 */
public class HospitalBillingOrganization  extends Organization{

    public HospitalBillingOrganization() {
        super(Organization.Type.HospitalBilling.getValue());
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new AccountManagerRole());
        return roles;
    }
     
   
    
    
}

