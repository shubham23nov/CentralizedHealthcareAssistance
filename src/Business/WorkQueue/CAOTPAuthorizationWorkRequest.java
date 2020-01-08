/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Business.WorkQueue;

/**
 *
 * @author darsh
 */
public class CAOTPAuthorizationWorkRequest extends WorkRequest{
        
    
        private RequestType requestType;
        private int patientId;
    
        public CAOTPAuthorizationWorkRequest(RequestType requestType, int patientId){
            this.requestType = requestType;
            this.patientId = patientId;
        }
    
        public enum RequestType{
        Initial("Initial"),
        Emergency("Emergency"),
        Normal("Normal");
        
        private String value;
        
        private RequestType(String value){
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

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }


    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    
    
}
