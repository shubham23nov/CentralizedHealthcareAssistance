/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Enterprise;

import Business.Role.FinanceManagerRole;
import Business.Role.InsuranceAgentRole;
import Business.Role.Role;
import Business.Role.UnderwriterRole;
import java.util.ArrayList;

/**
 *
 * @author Team NullPointerException
 */
public class InsuranceSystemEnterprise extends Enterprise {

    public InsuranceSystemEnterprise(String name) {
        super(name, EnterpriseType.InsuranceSystem);
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new InsuranceAgentRole());
        roles.add(new UnderwriterRole());
        roles.add(new FinanceManagerRole());
        return roles;
    }
    
}
