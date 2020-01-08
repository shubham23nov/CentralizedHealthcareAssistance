/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;

/**
 *
 * @author Team NullPointerException
 */
public abstract class Role {
    
    public enum RoleType{
        Admin("Admin"),
        Doctor("Doctor"),
        LabAssistant("Lab Assistant"),
        Receptionist("Receptionist"),
        CAPatientManager("Central Authority Patient Manager"),
        Pharmacist("Pharmacist"),
        InsuranceAgent("Insurance Agent"),
        Underwriter("Underwriter"),
        FinanceManager("Finance Manager"),
        InventoryManager("Inventory Manager"),
        CAAdmin("Aentral Authority Administrator"),
        CADrugInspector("Central Authority Drug Inspector"),
        Patient("Patient");
        
        private String value;
        private RoleType(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
    
    public abstract JPanel createWorkArea(JPanel userProcessContainer, 
            UserAccount account, 
            Organization organization, 
            Enterprise enterprise, 
            EcoSystem business);

    @Override
    public String toString() {
        return this.getClass().getName();
    }
    
    
}