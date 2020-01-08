/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Business.Organization;

import Business.Email;
import Business.Role.CAOTPBot;
import Business.Role.Role;
import Business.WorkQueue.CAFetchPatientRequest;
import Business.WorkQueue.CANewPatientOTPAuthRequest;
import Business.WorkQueue.CANewPatientVerifyRequest;
import Business.WorkQueue.CAOTPAuthorizationWorkRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author darsh
 */
public class CAOTPManagement extends Organization{
    
    private HashMap<Integer, Integer> patientOtpMap;
    private HashMap<String, Integer> emailOtpMap;
    private ArrayList<String> patientOtpLogs;
    private ArrayList<String> emailOtpLogs;
    
    public CAOTPManagement(){
        super(Organization.Type.OTPManagement.getValue());
        this.patientOtpMap = new HashMap<>();
        this.emailOtpMap = new HashMap<>();
        this.patientOtpLogs = new ArrayList<>();
        this.emailOtpLogs = new ArrayList<>();
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new CAOTPBot());
        return roles;
    }
    
    public void generatePatientOtp(CAOTPAuthorizationWorkRequest request, String email){
        this.getWorkQueue().getWorkRequestList().add(request);
        int patientId = request.getPatientId();
        request.setStatus("Pending");
        int otp = generateOTP(email);
        patientOtpMap.put(patientId, otp);
        request.setResolveDate(new Date());
        request.setStatus("Completed");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        String strDate = dateFormat.format(new Date());
        patientOtpLogs.add(strDate + " OTP Generated for PatientID: " + patientId + ". OTP " + otp + " sent on " + email);
    }
    
    public void generateEmailOtp(CANewPatientOTPAuthRequest request){
        this.getWorkQueue().getWorkRequestList().add(request);
        String email = request.getEmail();
        request.setStatus("Pending");
        int otp = generateOTP(email);
        emailOtpMap.put(email, otp);
        request.setStatus("OTP Sent");
        request.setResolveDate(new Date());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        String strDate = dateFormat.format(new Date());
        emailOtpLogs.add(strDate + " OTP Generated for Email ID: " + email  + ". OTP is: " + otp);
    }
    
    public int generateOTP(String email){
            
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        Email.sendEmail(email, "OTP Request Generated", "OTP requested is: " + number);
        return number;
    }
    

    
    public boolean verifyPatientOTP(CAFetchPatientRequest request){
        this.getWorkQueue().getWorkRequestList().add(request);
        int patientID = request.getPatientId();
        int otp = request.getOtp();
        request.setStatus("Pending");
        if (!patientOtpMap.containsKey(patientID)) {
            request.setStatus("Patient Not Found");
            return false;}
        int retriverOtp = patientOtpMap.get(patientID);
        if (otp == retriverOtp) {
            patientOtpMap.remove(patientID);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
            String strDate = dateFormat.format(new Date());
            request.setStatus("OTP Verified");
            request.setResolveDate(new Date());
            patientOtpLogs.add(strDate + ": OTP Verified for PatientID: " + patientID + ". OTP " + otp + ". ");
            return true;
        }
        else {
            request.setStatus("OTP Verification failed");
            return false;  }      
    }
    
    public boolean verifyEmailOTP(CANewPatientVerifyRequest request){
        this.getWorkQueue().getWorkRequestList().add(request);
        request.setStatus("Pending");
        String email = request.getEmailID();
        int otp = request.getOtp();
        
         if (!emailOtpMap.containsKey(email)) {
             request.setStatus("OTP does not exist");
             return false;}
        int retriverOtp = emailOtpMap.get(email);
        
        if (otp == retriverOtp) {
            emailOtpMap.remove(email);
            request.setStatus("OTP Verified");
            return true;}
        else return false;        
    }
    
}
