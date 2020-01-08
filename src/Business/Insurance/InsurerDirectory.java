/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Insurance;

import Business.Patient.Patient;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Team NullPointerException <>
 */
public class InsurerDirectory {
    private ArrayList<Insurer> insurerList;
    
    public InsurerDirectory(){
        this.insurerList = new ArrayList<Insurer>();                
    }

    public ArrayList<Insurer> getInsurerList() {
        return insurerList;
    }

    public void addInsurer(Patient patient, int id, String folioNum, double insuredAmount, double premiumPerMonth, Date dateOFInsurance, Date dateOfExpiration){
        Insurer insurer = new Insurer(patient, id, folioNum, insuredAmount, premiumPerMonth, dateOFInsurance, dateOfExpiration);
        this.insurerList.add(insurer);
    }
    
    public void removeInsurer(Insurer insurer){
        this.insurerList.remove(insurer);
    }
}
