/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Insurance.InsurerDirectory;
import Business.Role.Role;
import Business.Role.UnderwriterRole;
import java.util.ArrayList;

/**
 *
 * @author Team NullPointerException
 */
public class InsuranceUnderwriterOrganization extends Organization{


    public InsuranceUnderwriterOrganization() {
        super(Organization.Type.InsuranceUnderwriter.getValue());
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> role = new ArrayList<>();
        role.add(new UnderwriterRole());
        return role;
    }

}
