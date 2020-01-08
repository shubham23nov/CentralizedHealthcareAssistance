/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Business.Patient;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author darsh
 */
public class Vaccination {
    
    private ArrayList<Vaccine> vaccineList;
    
    public Vaccination(){
        this.vaccineList = new ArrayList<>();
    }

    public ArrayList<Vaccine> getVaccineList() {
        return vaccineList;
    }

    public void setVaccineList(ArrayList<Vaccine> vaccineList) {
        this.vaccineList = vaccineList;
    }
    
    public void addVaccine(String vaccineName, String vaccineType, Date dateGiven, String administeredBy, Date nextDose, String notes){
        Vaccine vaccine = new Vaccine(vaccineName, vaccineType, dateGiven, administeredBy, nextDose, notes);
        this.vaccineList.add(vaccine);
    }
    
    public void removeVaccine(Vaccine vaccine){
        this.vaccineList.remove(vaccine);
    }
    
}
