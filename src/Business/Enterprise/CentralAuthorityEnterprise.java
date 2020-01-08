/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Enterprise;

import Business.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author Team NullPointerException
 */
public class CentralAuthorityEnterprise extends Enterprise{

    public CentralAuthorityEnterprise(String name) {
        super(name, EnterpriseType.CentralAuthority);
        this.getOrganizationDirectory().createOrganization(Type.OTPManagement);
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
