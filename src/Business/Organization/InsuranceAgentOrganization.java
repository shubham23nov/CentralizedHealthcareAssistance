/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Role.InsuranceAgentRole;
import Business.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author Team NullPointerException
 */
public class InsuranceAgentOrganization extends Organization{

    public InsuranceAgentOrganization() {
        super(Organization.Type.InsuranceAgent.getValue());
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new InsuranceAgentRole());
        return roles;
    }
    
}
