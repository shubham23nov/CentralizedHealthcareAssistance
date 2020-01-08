/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Business.Patient;

import Business.UserAccount.UserAccount;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author darsh
 */
public class LabTest {
    
    private int testId;
    private static int count = 1;
    private UserAccount refDoctorname;
    private UserAccount labAssistant;
    private String verifiedByDoctor;
    private Date date;
    private ArrayList<TestDetails> testDetails;
    private String status;
    
    
    public LabTest( UserAccount refDoctorName, Date date){
        testDetails = new ArrayList<>();
        this.refDoctorname = refDoctorName;
        this.date = date;
        this.status = "New";
        this.testId = count;
        count++;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTestId() {
        return testId;
    }




    public UserAccount getRefDoctorname() {
        return refDoctorname;
    }

    public void setRefDoctorname(UserAccount refDoctorname) {
        this.refDoctorname = refDoctorname;
    }

    public UserAccount getLabAssistant() {
        return labAssistant;
    }

    public void setLabAssistant(UserAccount labAssistant) {
        this.labAssistant = labAssistant;
    }

    public String getVerifiedByDoctor() {
        return verifiedByDoctor;
    }

    public void setVerifiedByDoctor(String verifiedByDoctor) {
        this.verifiedByDoctor = verifiedByDoctor;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<TestDetails> getTestDetails() {
        return testDetails;
    }

    public void setTestDetails(ArrayList<TestDetails> testDetails) {
        this.testDetails = testDetails;
    }
    
    public void addTestDetails(String testName){
        TestDetails testDetail = new TestDetails(testName);
        testDetails.add(testDetail);
    }

    @Override
    public String toString() {
        return getTestId()+"";
    }
    
    
}
