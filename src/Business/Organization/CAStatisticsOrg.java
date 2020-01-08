/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Business.Organization;

import Business.Role.CAPatientManagerRole;
import Business.Role.CAStatisticsRole;
import Business.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author darsh
 */
public class CAStatisticsOrg extends Organization{

    public CAStatisticsOrg() {
        super(Organization.Type.Statistics.getValue());
    }

      @Override
    public ArrayList<Role> getSupportedRole() {
         ArrayList<Role> roles = new ArrayList();
        roles.add(new CAStatisticsRole());
        return roles;
    }
    
}
