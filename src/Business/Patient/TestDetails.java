/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Business.Patient;

/**
 *
 * @author darsh
 */
public class TestDetails {
        private String testName;
        private String result;
        private String refRange;
        private String unit;
        private String observation;
        
        public TestDetails(String testName){
            this.testName = testName;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
        
        

        public String getTestName() {
            return testName;
        }

        public void setTestName(String testName) {
            this.testName = testName;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getRefRange() {
            return refRange;
        }

        public void setRefRange(String refRange) {
            this.refRange = refRange;
        }

        public String getObservation() {
            return observation;
        }

        public void setObservation(String observation) {
            this.observation = observation;
        }

    @Override
    public String toString() {
        return getTestName();
    }
        
        
           
}
