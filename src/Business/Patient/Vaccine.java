/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Business.Patient;

import java.util.Date;

/**
 *
 * @author darsh
 */
    class Vaccine{
        
        private String vaccineName;
        private String vaccineType;
        private Date dateGiven;
        private String administeredBy;
        private Date nextDose;
        private String notes;
        
        public Vaccine(String vaccineName, String vaccineType, Date dateGiven, String administeredBy, Date nextDose, String notes){
            this.vaccineName = vaccineName;
            this.vaccineType = vaccineType;
            this.dateGiven = dateGiven;
            this.administeredBy = administeredBy;
            this.nextDose = nextDose;
            this.notes = notes;
        }

        public String getVaccineName() {
            return vaccineName;
        }

        public void setVaccineName(String vaccineName) {
            this.vaccineName = vaccineName;
        }

        public String getVaccineType() {
            return vaccineType;
        }

        public void setVaccineType(String vaccineType) {
            this.vaccineType = vaccineType;
        }

        public Date getDateGiven() {
            return dateGiven;
        }

        public void setDateGiven(Date dateGiven) {
            this.dateGiven = dateGiven;
        }

        public String getAdministeredBy() {
            return administeredBy;
        }

        public void setAdministeredBy(String administeredBy) {
            this.administeredBy = administeredBy;
        }

        public Date getNextDose() {
            return nextDose;
        }

        public void setNextDose(Date nextDose) {
            this.nextDose = nextDose;
        }   

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }
        
        
    }
