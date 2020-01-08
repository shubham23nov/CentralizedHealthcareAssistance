/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Business;

/**
 *
 * @author darsh
 */
public class Disease {
    
    private String name;
    private boolean criticalIllness;
    private boolean chronicDisease;
    private boolean communicable;
    private boolean lifestyleDisease;
    private boolean lifelongDisease;
    public Disease(String name, boolean criticalIllness, boolean chronicDisease, boolean communicable, boolean lifestyleDisease, boolean lifelongDisease){
        this.name = name;
        this.criticalIllness = criticalIllness;
        this.chronicDisease = chronicDisease;
        this.communicable = communicable;
        this.lifestyleDisease = lifestyleDisease;
        this.lifelongDisease = lifelongDisease;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCriticalIllness() {
        return criticalIllness;
    }

    public void setCriticalIllness(boolean criticalIllness) {
        this.criticalIllness = criticalIllness;
    }

    public boolean isChronicDisease() {
        return chronicDisease;
    }

    public void setChronicDisease(boolean chronicDisease) {
        this.chronicDisease = chronicDisease;
    }

    public boolean isCommunicable() {
        return communicable;
    }

    public void setCommunicable(boolean communicable) {
        this.communicable = communicable;
    }

    public boolean isLifestyleDisease() {
        return lifestyleDisease;
    }

    public void setLifestyleDisease(boolean lifestyleDisease) {
        this.lifestyleDisease = lifestyleDisease;
    }

    public boolean isLifelongDisease() {
        return lifelongDisease;
    }

    public void setLifelongDisease(boolean lifelongDisease) {
        this.lifelongDisease = lifelongDisease;
    }
    
    
    
    @Override
    public String toString(){
        return this.name;
    }
}
