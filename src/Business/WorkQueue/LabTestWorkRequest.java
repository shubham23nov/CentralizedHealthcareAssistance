/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.WorkQueue;

import Business.Patient.LabTest;

/**
 *
 * @author Team NullPointerException
 */
public class LabTestWorkRequest extends WorkRequest{
    
    private LabTest labTest;
    

    public LabTest getLabTest() {
        return labTest;
    }

    public void setLabTest(LabTest labTest) {
        this.labTest = labTest;
    }

    @Override
    public String toString() {
        return getRequestDate().toString();
    }

    
}
