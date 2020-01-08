/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Hospital;

import Business.Employee.Employee;
import Business.Patient.Patient;
import Business.UserAccount.UserAccount;
import java.util.Date;

/**
 *
 * @author rruch
 */
public class Appointment {
    
    public static int appointment_count = 0;
    public int appointmentAmount;
    
    private String Appointment_id;
    public enum AptStatus {
    INITIAL("INITIAL"),ASSIGNED("ASSIGNED"),ACCEPTED("ACCEPTED"),COMPLETE("COMPLETE"),CANCELLED("CANCELLED");
     private String value;
        
        private AptStatus(String value){
            this.value=value;
        }
        public String getValue() {
            return value;
        }
        @Override
        public String toString(){
        return value;
    }
    }

    public enum Apttime{
    MORNING("MORNING"), AFTERNOON("AFTERNOON"), EVENING("EVENING"), NIGHT("NIGHT");
     private String value;
        
        private Apttime(String value){
            this.value=value;
        }
        public String getValue() {
            return value;
        }
        @Override
        public String toString(){
        return value;
    }
    }
    private AptStatus aptStatus;
    private Apttime aptTime;
    Date appointmentDate;
    UserAccount doctor;
    Patient patient;
    UserAccount receptionist;

    
    public Appointment(Date appointmentDate, Apttime aptTime,UserAccount doctor, Patient patient, UserAccount receptionist,AptStatus aptStatus) {
        this.appointmentDate = appointmentDate;
        this.doctor = doctor;
        this.patient = patient;
        this.receptionist = receptionist;
        Appointment_id = "APT" + ++appointment_count;
        this.aptStatus=aptStatus;
        this.aptTime = aptTime;
    }

    public AptStatus getAptStatus() {
        return aptStatus;
    }

    public void setAptStatus(AptStatus aptStatus) {
        this.aptStatus = aptStatus;
    }

    public Apttime getAptTime() {
        return aptTime;
    }

    public void setAptTime(Apttime aptTime) {
        this.aptTime = aptTime;
    }
    
    public String getAppointment_id() {
        return Appointment_id;
    }

    public void setAppointment_id(String Appointment_id) {
        this.Appointment_id = Appointment_id;
    }
    
    
    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public UserAccount getDoctor() {
        return doctor;
    }

    public void setDoctor(UserAccount doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public UserAccount getReceptionist() {
        return receptionist;
    }

    public void setReceptionist(UserAccount receptionist) {
        this.receptionist = receptionist;
    }

    @Override
    public String toString() {
        return this.Appointment_id;
    }

    public int getAppointmentAmount() {
        return appointmentAmount;
    }

    public void setAppointmentAmount(int appointmentAmount) {
        this.appointmentAmount = appointmentAmount;
    }    
    
 }
