/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.web;

import edu.iit.sat.itmd4515.atortosagarrido.domain.Client;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Trainer;
import edu.iit.sat.itmd4515.atortosagarrido.domain.security.User;
import edu.iit.sat.itmd4515.atortosagarrido.service.EmployeeService;
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
public class TrainerController {
    private static final Logger LOG = Logger.getLogger(TechnicianController.class.getName());
    
    private Trainer trainer;
    
    private User user;
    
    private Client client;

    
    @Inject
    private SecurityContext securityContext;
    @Inject
    private LoginController loginController;

    @EJB
    private EmployeeService empSvc;

    public TrainerController() {
    }
    
    @PostConstruct
    private void postConstruct(){
        LOG.info("inside PostConstruct");
        if(loginController.getRemoteUser()!=null && securityContext.isCallerInRole("TRAINER_ROLE")){
            trainer = (Trainer)empSvc.findByUsername(loginController.getRemoteUser());
            user = trainer.getUser();
        }else{
            user = new User();
            this.trainer = new Trainer();
        } 
    }
    
    public String prepareViewClient(Client c){
        LOG.log(Level.INFO, "inside prepareViewClient  with client {0}", c.toString());
        this.client = c;
        return "/employees/trainers/clients/viewclient.xhtml";
    }
    
    public String doSaveTrainer(){
        LOG.log(Level.INFO, "inside doSaveTrainer  with trainer {0}", this.trainer.toString());
        empSvc.update(this.trainer);
        return "/employees/trainers/profile.xhtml";
    }
    
    //Utility
    public List<Trainer> getAllTrainers(){
        return empSvc.findAllTrainers();
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
     * Get the value of trainer
     *
     * @return the value of trainer
     */
    public Trainer getTrainer() {
        return trainer;
    }

    /**
     * Set the value of trainer
     *
     * @param trainer new value of trainer
     */
    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }
    
     /**
     * Get the value of client
     *
     * @return the value of client
     */
    public Client getClient() {
        return client;
    }

    /**
     * Set the value of client
     *
     * @param client new value of client
     */
    public void setClient(Client client) {
        this.client = client;
    }

}
