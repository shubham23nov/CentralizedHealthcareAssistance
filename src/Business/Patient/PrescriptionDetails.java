/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Patient;

/**
 *
 * @author darsh
 */
public class PrescriptionDetails {

    private String medicineName; 
    private String dose;    // 25 mg, 30 mg
    private String frequency; // 2 times a day, or morning,evening
    private String condition; // headache, pain, or any disease
    private int noOfDays; // 3 days
    private String notes; // write something note

    public PrescriptionDetails(String medicineName, String dose, String frequency, String condition, int noOfDays, String notes) {
        this.medicineName = medicineName;
        this.dose = dose;
        this.condition = condition;
        this.frequency = frequency;
        this.noOfDays = noOfDays;
        this.notes = notes;
    }

    ;

        public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String MedicineName) {
        this.medicineName = MedicineName;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(int noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public String toString(){
        return this.getMedicineName();
    }
}
