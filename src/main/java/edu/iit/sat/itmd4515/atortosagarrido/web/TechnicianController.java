/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.web;

import edu.iit.sat.itmd4515.atortosagarrido.domain.Equipment;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Reparation;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Technician;
import edu.iit.sat.itmd4515.atortosagarrido.domain.security.User;
import edu.iit.sat.itmd4515.atortosagarrido.service.EmployeeService;
import edu.iit.sat.itmd4515.atortosagarrido.service.ReparationService;
import java.time.Instant;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;

/**
 *
 * @author antoniotortosa
 */
@Named
@RequestScoped
public class TechnicianController {

    private static final Logger LOG = Logger.getLogger(TechnicianController.class.getName());
    
    private Technician technician;
    
    private User user;
    
    private Reparation reparation;
    
    @Inject
    private SecurityContext securityContext;
    @Inject
    private LoginController loginController;
    
    @EJB
    private EmployeeService empSvc;
    
    @EJB
    private ReparationService repSvc;

    public TechnicianController() {
    }

    @PostConstruct
    private void postConstruct(){
        LOG.info("TechnicianController -> inside PostConstruct");
        if(loginController.getRemoteUser()!=null && securityContext.isCallerInRole("TECH_ROLE")){
            technician = (Technician)empSvc.findByUsername(loginController.getRemoteUser());
            user = technician.getUser();
        }else{
            user = new User();
            this.technician = new Technician();
        } 
        this.reparation = new Reparation();
    }
    
    /**
     * Method to obtain all the reparations that haven't been finished yet by
     * one technician
     * @return a List with all the reparations still without end date
     */
    public List<Reparation> getAllOnGoingReparationsOfTech(){
        return repSvc.findAllByTecOnGoing(this.technician.getId());
    }
    
    public List<Reparation> getAllReparationsOfTech(){
        return repSvc.findAllByTec(this.technician.getId());
    }
    
    public String startReparation(Equipment equip){
        LOG.log(Level.INFO, "TechnicianController -> inside startReparation with equipment {0} and trainer {1}", new Object[]{equip.getName(),this.technician.getFullName()});
        this.reparation = new Reparation();
        this.reparation.setTechnician(this.technician);
        this.reparation.setEquipment(equip);
        this.reparation.setDateStart(Date.from(Instant.now()));
        repSvc.create(this.reparation);
        return "/employees/technicians/equipments/allequipments.xhtml?faces-redirect=true";
    }
    public String finishReparation(Reparation rep){
        LOG.log(Level.INFO, "TechnicianController -> inside finishReparation with reparation '{'{0}, {1}'}'", 
                new Object[]{rep.getTechnician().getFullName(), rep.getEquipment().getName()});
        this.reparation = rep;
        this.reparation.setDateFinish(Date.from(Instant.now()));
        repSvc.update(this.reparation);
        return "/employees/technicians/equipments/allequipments.xhtml";
    }
    
    public String doSaveTechnician(){
        empSvc.update(this.technician);
        return "/employees/technicians/profile.xhtml";
    }
    
    //Utility
    public List<Technician> getAllTechnicians(){
        return empSvc.findAllTechnicians();
    }
    
    /**
     * Get the value of technician
     *
     * @return the value of technician
     */
    public Technician getTechnician() {
        return technician;
    }

    /**
     * Set the value of technician
     *
     * @param technician new value of technician
     */
    public void setTechnician(Technician technician) {
        this.technician = technician;
    }
    
    /**
     * Get the value of user
     *
     * @return the value of user
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the value of user
     *
     * @param user new value of user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Get the value of reparation
     *
     * @return the value of reparation
     */
    public Reparation getReparation() {
        return reparation;
    }

    /**
     * Set the value of reparation
     *
     * @param reparation new value of reparation
     */
    public void setReparation(Reparation reparation) {
        this.reparation = reparation;
    }
}
