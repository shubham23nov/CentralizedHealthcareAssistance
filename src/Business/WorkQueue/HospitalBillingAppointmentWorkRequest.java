/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.WorkQueue;

import Business.Hospital.Appointment;
import Business.UserAccount.UserAccount;

/**
 *
 * @author rruch
 */
public class HospitalBillingAppointmentWorkRequest extends WorkRequest{
    
    Appointment appointment;
    UserAccount secondSender;
    UserAccount secondReceiver;

    public UserAccount getSecondReceiver() {
        return secondReceiver;
    }

    public void setSecondReceiver(UserAccount secondReceiver) {
        this.secondReceiver = secondReceiver;
    }

    public UserAccount getSecondSender() {
        return secondSender;
    }

    public void setSecondSender(UserAccount secondSender) {
        this.secondSender = secondSender;
    }
    
    
    

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
    
    
       @Override
    public String toString() {
        return getRequestDate().toString();
    }
    
}
