/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Role.PharmacistRole;
import Business.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author Team NullPointerException
 */
public class DrugPharmacistOrganization extends Organization{

    public DrugPharmacistOrganization() {
        super(Organization.Type.Pharmacist.getValue());
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> role = new ArrayList<>();
        role.add(new PharmacistRole());
        return role;
    }
    
}
