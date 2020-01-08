/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Business.Patient;

import Business.UserAccount.UserAccount;
import java.util.Date;

/**
 *
 * @author darsh
 */
   public class Notes{
        private Date date;
        private String notes;
        private boolean patientVisible;
        private UserAccount doctorName;
        
        public Notes(Date date, String notes, boolean patientVisible, UserAccount doctorName){
            this.date = date;
            this.notes = notes;
            this.patientVisible = patientVisible;
            this.doctorName = doctorName;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

    public boolean isPatientVisible() {
        return patientVisible;
    }

    public void setPatientVisible(boolean patientVisible) {
        this.patientVisible = patientVisible;
    }

    public UserAccount getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(UserAccount doctorName) {
        this.doctorName = doctorName;
    }
        
        

    @Override
    public String toString() {
        return getDate().toString();
    }
        
        
                
    }