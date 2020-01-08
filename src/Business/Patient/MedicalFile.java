/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Business.Patient;

import Business.Disease;
import Business.UserAccount.UserAccount;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author darsh
 */
public class MedicalFile {
    private Disease problemDiseases;
    private String howLongProblemBegan;
    private String problemInteferenceWithDailyActivities;
    private String haveBeenDiagnosedBefore;
    private String kindOfTreatmentTried;
    private ArrayList<Notes> notesList;
    private UserAccount doctorName;
    private Date createdDate;
    private boolean isCured; 
    
    private ArrayList<Prescription> prescription;
    private ArrayList<LabTest> labTest;
    
    
    public MedicalFile(UserAccount doctorName, Date createdDate){
        this.notesList = new ArrayList<>();
        this.prescription = new ArrayList<>();
        this.labTest = new ArrayList<>();
        this.doctorName = doctorName;
        this.createdDate = createdDate;
    }

    
    
    public boolean isIsCured() {
        return isCured;
    }

    public void setIsCured(boolean isCured) {
        this.isCured = isCured;
    }

    public ArrayList<Notes> getNotesList() {
        return notesList;
    }

    public void setNotesList(ArrayList<Notes> notesList) {
        this.notesList = notesList;
    }

    public UserAccount getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(UserAccount doctorName) {
        this.doctorName = doctorName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public ArrayList<LabTest> getLabTest() {
        return labTest;
    }

    public void setLabTest(ArrayList<LabTest> labTest) {
        this.labTest = labTest;
    }
    
    
    
    public void addNotes(Date date, String notes, boolean patientVisible, UserAccount doctorName){
        Notes note = new Notes(date, notes, patientVisible, doctorName);
        this.notesList.add(note);
    }
    
    
    public void deleteNote(Notes note){
        this.notesList.remove(note);
    }
    
    public Disease getProblemDiseases() {
        return problemDiseases;
    }

    public void setProblemDiseases(Disease problemDiseases) {
        this.problemDiseases = problemDiseases;
    }

    public String getHowLongProblemBegan() {
        return howLongProblemBegan;
    }

    public void setHowLongProblemBegan(String howLongProblemBegan) {
        this.howLongProblemBegan = howLongProblemBegan;
    }

    public String getProblemInteferenceWithDailyActivities() {
        return problemInteferenceWithDailyActivities;
    }

    public void setProblemInteferenceWithDailyActivities(String problemInteferenceWithDailyActivities) {
        this.problemInteferenceWithDailyActivities = problemInteferenceWithDailyActivities;
    }

    public String getHaveBeenDiagnosedBefore() {
        return haveBeenDiagnosedBefore;
    }

    public void setHaveBeenDiagnosedBefore(String haveBeenDiagnosedBefore) {
        this.haveBeenDiagnosedBefore = haveBeenDiagnosedBefore;
    }

    public String getKindOfTreatmentTried() {
        return kindOfTreatmentTried;
    }

    public void setKindOfTreatmentTried(String kindOfTreatmentTried) {
        this.kindOfTreatmentTried = kindOfTreatmentTried;
    }

    public ArrayList<Prescription> getPrescription() {
        return prescription;
    }
    
    public void addPrescription(Prescription prescription){
    this.prescription.add(prescription);
    }
}
