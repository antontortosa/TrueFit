/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.model;

/**
 *
 * @author antoniotortosa
 */
public enum EqStatus {
    
    BROKEN("Broken"),
    FIXING("Fixing"),
    ONSERVICE("On Service");
    
    private String label;

    private EqStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    
}
