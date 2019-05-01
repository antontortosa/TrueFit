/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.web;

import edu.iit.sat.itmd4515.atortosagarrido.domain.Administrative;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Client;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Technician;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Trainer;
import edu.iit.sat.itmd4515.atortosagarrido.service.ClientService;
import edu.iit.sat.itmd4515.atortosagarrido.service.EmployeeService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author antoniotortosa
 */
@Named
@RequestScoped
public class AdministrativeController {

    private static final Logger LOG = Logger.getLogger(AdministrativeController.class.getName());
    
    private Administrative adminstrative;
    
    private Administrative otherAdminstrative;

    private Client client;
    
    private Trainer trainer;
    
    private Technician technician;
    
    @Inject
    LoginController loginController;
    
    @EJB
    private EmployeeService empSvc;

    @EJB
    private ClientService clSvc;
    
    public AdministrativeController() {
    }
    
    @PostConstruct
    private void postConstruct(){
        LOG.log(Level.INFO, "inside postConstruct  with user {0}", loginController.getRemoteUser());
        this.adminstrative = (Administrative) empSvc.findByUsername(loginController.getRemoteUser());
        this.client = new Client();
        this.technician = new Technician();
        this.trainer = new Trainer();
        this.otherAdminstrative = new Administrative();
    }
    
    //Prepare Clients
    public String prepareViewClient(Client c){
        LOG.log(Level.INFO, "inside prepareViewClient  with client {0}", c.toString());
        this.client = c;
        return "/employees/admin/viewClient.xhtml";
    }
    
    public String prepareEditClient(Client c){
        LOG.log(Level.INFO, "inside prepareEditClient with client {0}", c.toString());
        this.client = c;
        return "/employees/admin/editClient.xhtml";
    }
    
    public String prepareCreateClient(){
        LOG.log(Level.INFO, "inside prepareCreateClient");
        this.client = new Client();
        return "/employees/admin/editClient.xhtml";
    }
    
    //Prepare Trainers
    public String prepareViewTrainer(Trainer t){
        LOG.log(Level.INFO, "inside prepareViewTrainer  with trainer {0}", t.toString());
        this.trainer = t;
        return "/employees/admin/viewTrainer.xhtml";
    }
    
    public String prepareEditTrainer(Trainer t){
        LOG.log(Level.INFO, "inside prepareEditTrainer with trainer {0}", t.toString());
        this.trainer = t;
        return "/employees/admin/editTrainer.xhtml";
    }
    
    public String prepareCreateTrainer(){
        LOG.log(Level.INFO, "inside prepareCreateTrainer");
        this.trainer = new Trainer();
        return "/employees/admin/editTrainer.xhtml";
    }
    
     //Prepare Technicians
    public String prepareViewTech(Technician t){
        LOG.log(Level.INFO, "inside prepareViewTech  with technician {0}", t.toString());
        this.technician = t;
        return "/employees/admin/viewTech.xhtml";
    }
    
    public String prepareEditTech(Technician t){
        LOG.log(Level.INFO, "inside prepareEditTech with technician {0}", t.toString());
        this.technician = t;
        return "/employees/admin/editTech.xhtml";
    }
    
    public String prepareCreateTech(){
        LOG.log(Level.INFO, "inside prepareCreateTech");
        this.technician = new Technician();
        return "/employees/admin/editTech.xhtml";
    }
    
     //Prepare Admins
    public String prepareViewAdmin(Administrative a){
        LOG.log(Level.INFO, "inside prepareViewAdmin  with admin {0}", a.toString());
        this.otherAdminstrative = a;
        return "/employees/admin/viewAdmin.xhtml";
    }
    
    public String prepareEditAdmin(Administrative a){
        LOG.log(Level.INFO, "inside prepareEditAdmin with admin {0}", a.toString());
        this.otherAdminstrative = a;
        return "/employees/admin/editAdmin.xhtml";
    }
    
    public String prepareCreateAdmin(){
        LOG.log(Level.INFO, "inside prepareCreateAdmin");
        this.otherAdminstrative = new Administrative();
        return "/employees/admin/editAdmin.xhtml";
    }
    
    
    //action methods
    public String doCreateClient(){
        return "";
    }
    
    public String doSaveClient(){
        LOG.log(Level.INFO, "inside doSaveClient with client {0}", client.toString());
        
        if(this.client.getId()!=null){
            //UPDATE
            LOG.log(Level.INFO, "doSaveClient is going to call an update with {0}", this.client.toString());
            clSvc.update(this.client);
        }else{
            //CREATE
            LOG.log(Level.INFO, "doSaveClient is going to call a create with {0}", this.client.toString());
            clSvc.create(this.client);
        }
        
        return "allClients.xhtml";
    }
    
    public String doDeleteClient(Client client){
        LOG.log(Level.INFO, "inside doDeleteClient with client {0}", client.toString());
        clSvc.remove(client);
        return "allClients.xhtml?faces-redirect=true";
    }
    
    public String doDeleteTrainer(Trainer trainer){
        LOG.log(Level.INFO, "inside doDeleteClient with client {0}", client.toString());
        empSvc.remove(trainer);
        return "allClients.xhtml?faces-redirect=true";
    }
    
    //All Admins
    public List<Administrative> getAllAdministratives(){
        return empSvc.findAllAdmins();
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
    
    /**
     * Get the value of adminstrative
     *
     * @return the value of adminstrative
     */
    public Administrative getAdminstrative() {
        return adminstrative;
    }

    /**
     * Set the value of adminstrative
     *
     * @param adminstrative new value of adminstrative
     */
    public void setAdminstrative(Administrative adminstrative) {
        this.adminstrative = adminstrative;
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
     * Get the value of otherAdminstrative
     *
     * @return the value of otherAdminstrative
     */
    public Administrative getOtherAdminstrative() {
        return otherAdminstrative;
    }

    /**
     * Set the value of otherAdminstrative
     *
     * @param otherAdminstrative new value of otherAdminstrative
     */
    public void setOtherAdminstrative(Administrative otherAdminstrative) {
        this.otherAdminstrative = otherAdminstrative;
    }
    
}
