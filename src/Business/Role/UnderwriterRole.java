/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import static Business.Role.Role.RoleType.Underwriter;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;
import userinterface.UnderwriterRole.UnderwriterWorkAreaJPanel;

/**
 *
 * @author Team NullPointerException
 */
public class UnderwriterRole extends Role{

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
        return new UnderwriterWorkAreaJPanel(userProcessContainer, account, organization, enterprise, business);
    }
    
}
