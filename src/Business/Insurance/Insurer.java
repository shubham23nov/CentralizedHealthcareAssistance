/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Insurance;

import Business.Patient.Patient;
import java.util.Date;

/**
 *
 * @author Team NullPointerException <>
 */
public class Insurer {

    private Patient patient;

    private int insurerId;
    private String folioNumber;
    private double insuredAmount;
    private double premiumPerMonth;
    private Date dateOfInsurance;
    private Date dateOfExpiration;

    public Insurer(Patient patient, int insurerId, String folioNumber, double insuredAmount, double premiumPerMonth, Date dateOFInsurance, Date dateOfExpiration) {
        this.patient = patient;
        this.insurerId = insurerId;
        this.folioNumber = folioNumber;
        this.insuredAmount = insuredAmount;
        this.premiumPerMonth = premiumPerMonth;
        this.dateOfExpiration = dateOfExpiration;
        this.dateOfInsurance = dateOFInsurance;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public int getInsurerId() {
        return insurerId;
    }

    public String getFolioNumber() {
        return folioNumber;
    }

    public void setFolioNumber(String folioNumber) {
        this.folioNumber = folioNumber;
    }

    public double getInsuredAmount() {
        return insuredAmount;
    }

    public void setInsuredAmount(double insuredAmount) {
        this.insuredAmount = insuredAmount;
    }

    public double getPremiumPerMonth() {
        return premiumPerMonth;
    }

    public void setPremiumPerMonth(double premiumPerMonth) {
        this.premiumPerMonth = premiumPerMonth;
    }

    public Date getDateOfInsurance() {
        return dateOfInsurance;
    }

    public void setDateOfInsurance(Date dateOfInsurance) {
        this.dateOfInsurance = dateOfInsurance;
    }

    public Date getDateOfExpiration() {
        return dateOfExpiration;
    }

    public void setDateOfExpiration(Date dateOfExpiration) {
        this.dateOfExpiration = dateOfExpiration;
    }
 
    
    @Override
    public String toString(){
        return this.folioNumber;
    }
}
