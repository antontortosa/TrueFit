/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.web;

import edu.iit.sat.itmd4515.atortosagarrido.domain.Technician;
import edu.iit.sat.itmd4515.atortosagarrido.domain.security.User;
import edu.iit.sat.itmd4515.atortosagarrido.service.EmployeeService;
import java.util.List;
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
    
    @Inject
    private SecurityContext securityContext;
    @Inject
    private LoginController loginController;
    
    @EJB
    private EmployeeService empSvc;

    public TechnicianController() {
    }

    @PostConstruct
    private void postConstruct(){
        LOG.info("inside PostConstruct");
        if(loginController.getRemoteUser()!=null && securityContext.isCallerInRole("TECH_ROLE")){
            technician = (Technician)empSvc.findByUsername(loginController.getRemoteUser());
            user = technician.getUser();
        }else{
            user = new User();
            this.technician = new Technician();
        } 
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

}
