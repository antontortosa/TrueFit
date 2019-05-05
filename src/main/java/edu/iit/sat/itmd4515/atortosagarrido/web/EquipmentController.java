/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.web;

import edu.iit.sat.itmd4515.atortosagarrido.domain.EqStatus;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Equipment;
import edu.iit.sat.itmd4515.atortosagarrido.service.EquipmentService;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author antoniotortosa
 */
@Named
@RequestScoped
public class EquipmentController {

    private static final Logger LOG = Logger.getLogger(EquipmentController.class.getName());
    
    private Equipment equipment;

    @EJB
    private EquipmentService eqSvc;
    
    public EquipmentController() {
    }
    
    public EqStatus[] getEquipmentStatus(){
        return EqStatus.values();
    }
    
    public List<Equipment> getAllEquipments(){
        return eqSvc.findAll();
    }

    /**
     * Get the value of equipment
     *
     * @return the value of equipment
     */
    public Equipment getEquipment() {
        return equipment;
    }

    /**
     * Set the value of equipment
     *
     * @param equipment new value of equipment
     */
    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

}
