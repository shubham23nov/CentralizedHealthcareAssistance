/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Business.Patient;

import Business.Disease;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author darsh
 */
public class Patient {
    private String name;
    private String address;
    private String city;
    private String state;
    private int zip;
    private String cellNo;
    private Date dob;
    private String sex;
    private String email;
    private String id;  //Govt ID;
    private String emerName1;
    private String emerRelationship1;
    private String emerNumber1;
    private String emerName2;
    private String emerRelationship2;
    private String emerNumber2;
    private BufferedImage photo;
    private boolean caffinatedDrink;
    private int noOfCups;
    private String alcoholFrequency;
    private boolean smoke;
    private int packsPerDay;
    private int exercisePerWeek;
    private String typeOfExercise;
    private String familyMedicalHistory;
    private String dietaryPreference;
    private int patientId;
    private String status;
    private ArrayList<MedicalFile> medicalFile;
    private Vaccination vacinationList;
    private ArrayList<String> allergies;
    private ArrayList<Disease> previousDisease;
    
    public Patient(){
        this.allergies = new ArrayList<>();
        this.vacinationList = new Vaccination();
        this.previousDisease = new ArrayList<Disease>();
        this.medicalFile = new ArrayList<>();
       
    }
    
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<MedicalFile> getMedicalFile() {
        return medicalFile;
    }

    public void setMedicalFile(ArrayList<MedicalFile> medicalFile) {
        this.medicalFile = medicalFile;
    } 

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }


    public String getCellNo() {
        return cellNo;
    }

    public void setCellNo(String cellNo) {
        this.cellNo = cellNo;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmerName1() {
        return emerName1;
    }

    public void setEmerName1(String emerName1) {
        this.emerName1 = emerName1;
    }

    public String getEmerRelationship1() {
        return emerRelationship1;
    }

    public void setEmerRelationship1(String emerRelationship1) {
        this.emerRelationship1 = emerRelationship1;
    }

    public String getEmerNumber1() {
        return emerNumber1;
    }

    public void setEmerNumber1(String emerNumber1) {
        this.emerNumber1 = emerNumber1;
    }

    public String getEmerName2() {
        return emerName2;
    }

    public void setEmerName2(String emerName2) {
        this.emerName2 = emerName2;
    }

    public String getEmerRelationship2() {
        return emerRelationship2;
    }

    public void setEmerRelationship2(String emerRelationship2) {
        this.emerRelationship2 = emerRelationship2;
    }

    public String getEmerNumber2() {
        return emerNumber2;
    }

    public void setEmerNumber2(String emerNumber2) {
        this.emerNumber2 = emerNumber2;
    }

    public BufferedImage getPhoto() {
        return photo;
    }

    public void setPhoto(BufferedImage photo) {
        this.photo = photo;
    }

    public boolean isCaffinatedDrink() {
        return caffinatedDrink;
    }

    public void setCaffinatedDrink(boolean caffinatedDrink) {
        this.caffinatedDrink = caffinatedDrink;
    }

    public int getNoOfCups() {
        return noOfCups;
    }

    public void setNoOfCups(int noOfCups) {
        this.noOfCups = noOfCups;
    }

    public String getAlcoholFrequency() {
        return alcoholFrequency;
    }

    public void setAlcoholFrequency(String alcoholFrequency) {
        this.alcoholFrequency = alcoholFrequency;
    }

    public boolean isSmoke() {
        return smoke;
    }

    public void setSmoke(boolean smoke) {
        this.smoke = smoke;
    }

    public int getPacksPerDay() {
        return packsPerDay;
    }

    public void setPacksPerDay(int packsPerDay) {
        this.packsPerDay = packsPerDay;
    }

    public int getExercisePerWeek() {
        return exercisePerWeek;
    }

    public void setExercisePerWeek(int exercisePerWeek) {
        this.exercisePerWeek = exercisePerWeek;
    }

    public String getTypeOfExercise() {
        return typeOfExercise;
    }

    public void setTypeOfExercise(String typeOfExercise) {
        this.typeOfExercise = typeOfExercise;
    }

    public String getFamilyMedicalHistory() {
        return familyMedicalHistory;
    }

    public void setFamilyMedicalHistory(String familyMedicalHistory) {
        this.familyMedicalHistory = familyMedicalHistory;
    }

    public String getDietaryPreference() {
        return dietaryPreference;
    }

    public void setDietaryPreference(String dietaryPreference) {
        this.dietaryPreference = dietaryPreference;
    }

    public Vaccination getVacinationList() {
        return vacinationList;
    }

    public void setVacinationList(Vaccination vacinationList) {
        this.vacinationList = vacinationList;
    }

    public ArrayList<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(ArrayList<String> allergies) {
        this.allergies = allergies;
    }

    public ArrayList<Disease> getPreviousDisease() {
        return previousDisease;
    }

    public void setPreviousDisease(ArrayList<Disease> previousDisease) {
        this.previousDisease = previousDisease;
    }

    @Override
    public String toString() {
        return getName();
    }
    
    
    
}
