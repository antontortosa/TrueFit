/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.web;

import edu.iit.sat.itmd4515.atortosagarrido.domain.Administrative;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Client;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Equipment;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Location;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Technician;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Trainer;
import edu.iit.sat.itmd4515.atortosagarrido.service.ClientService;
import edu.iit.sat.itmd4515.atortosagarrido.service.EmployeeService;
import edu.iit.sat.itmd4515.atortosagarrido.service.EquipmentService;
import edu.iit.sat.itmd4515.atortosagarrido.service.LocationService;
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
    
    private Location location;
    
    private Equipment equipment;
    
    @Inject
    LoginController loginController;
    
    @EJB
    private EmployeeService empSvc;

    @EJB
    private ClientService clSvc;
    
    @EJB
    private LocationService locSvc;
    
    @EJB
    private EquipmentService eqSvc;
    
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
        this.location = new Location();
        this.equipment = new Equipment();
    }
    
    ////////////////////
    //////CLIENTS//////
    ///////////////////
    //PREPARE METHODS
    public String prepareViewClient(Client c){
        LOG.log(Level.INFO, "inside prepareViewClient  with client {0}", c.toString());
        this.client = c;
        return "/admin/clients/viewClient.xhtml";
    }
    
    public String prepareEditClient(Client c){
        LOG.log(Level.INFO, "inside prepareEditClient with client {0}", c.toString());
        this.client = c;
        return "/admin/clients/editClient.xhtml";
    }
    
    public String prepareCreateClient(){
        LOG.log(Level.INFO, "inside prepareCreateClient");
        this.client = new Client();
        return "/admin/clients/editClient.xhtml";
    }
    //SAVE
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
        
        return "/admin/clients/allClients.xhtml";
    }
    //DELETE
    public String doDeleteClient(Client client){
        LOG.log(Level.INFO, "inside doDeleteClient with client {0}", client.toString());
        clSvc.remove(client);
        return "/admin/clients/allClients.xhtml?faces-redirect=true";
    }
    
    ////////////////////
    ///////ADMINS//////
    ///////////////////
    //PREPARE METHODS
    public String prepareViewAdmin(Administrative a){
        LOG.log(Level.INFO, "inside prepareViewAdmin  with admin {0}", a.toString());
        this.otherAdminstrative = a;
        return "/admin/employees/admins/viewAdmin.xhtml";
    }
    
    public String prepareEditAdmin(Administrative a){
        LOG.log(Level.INFO, "inside prepareEditAdmin with admin {0}", a.toString());
        this.otherAdminstrative = a;
        return "/admin/employees/admins/editAdmin.xhtml";
    }
    
    public String prepareCreateAdmin(){
        LOG.log(Level.INFO, "inside prepareCreateAdmin");
        this.otherAdminstrative = new Administrative();
        return "/admin/employees/admins/editAdmin.xhtml";
    }
    //SAVE
    public String doSaveAdmin(){
        LOG.log(Level.INFO, "inside doSaveAdmin with admin {0}", otherAdminstrative.toString());
        
        if(this.otherAdminstrative.getId()!=null){
            //UPDATE
            LOG.log(Level.INFO, "doSaveAdmin is going to call an update with {0}", this.otherAdminstrative.toString());
            empSvc.update(this.otherAdminstrative);
        }else{
            //CREATE
            LOG.log(Level.INFO, "doSaveAdmin is going to call a create with {0}", this.otherAdminstrative.toString());
            empSvc.create(this.otherAdminstrative);
        }
        
        return "/admin/employees/admins/allAdmins.xhtml";
    }
    //DELTE
    public String doDeleteAdmin(Administrative admin){
        LOG.log(Level.INFO, "inside doDeleteAdmin with client {0}", admin.toString());
        empSvc.remove(admin);
        return "/admin/employees/admins/allAdmins.xhtml?faces-redirect=true";
    }
    ////////////////////
    ///////TECHS///////
    ///////////////////
    //PREPARE METHODS
    public String prepareViewTech(Technician t){
        LOG.log(Level.INFO, "inside prepareViewTech  with technician {0}", t.toString());
        this.technician = t;
        return "/admin/employees/techs/viewTech.xhtml";
    }
    
    public String prepareEditTech(Technician t){
        LOG.log(Level.INFO, "inside prepareEditTech with technician {0}", t.toString());
        this.technician = t;
        return "/admin/employees/techs/editTech.xhtml";
    }
    
    public String prepareCreateTech(){
        LOG.log(Level.INFO, "inside prepareCreateTech");
        this.technician = new Technician();
        return "/admin/employees/techs/editTech.xhtml";
    }
    //SAVE
    public String doSaveTech(){
        LOG.log(Level.INFO, "inside doSaveTech with technician {0}", technician.toString());
        
        if(this.technician.getId()!=null){
            //UPDATE
            LOG.log(Level.INFO, "doSaveTech is going to call an update with {0}", this.technician.toString());
            empSvc.update(this.technician);
        }else{
            //CREATE
            LOG.log(Level.INFO, "doSaveTech is going to call a create with {0}", this.technician.toString());
            empSvc.create(this.technician);
        }
        return "/admin/employees/techs/allTechnicians.xhtml";
    }
    //DELTE
    public String doDeleteTech(Technician technician){
        LOG.log(Level.INFO, "inside doDeleteTech with client {0}", technician.toString());
        empSvc.remove(technician);
        return "/admin/employees/techs/allTechnicians.xhtml?faces-redirect=true";
    }
    //OTHERS
    public String doRemoveEquipment(Technician t, Equipment eq){
        t.removeEquipment(eq);
        empSvc.update(t);
        return "/admin/employees/techs/allTechnicians.xhtml?faces-redirect=true";
    }
    /////////////////////
    ///////TRAINERS/////
    ////////////////////
    //PREPARE METHODS
    public String prepareViewTrainer(Trainer t){
        LOG.log(Level.INFO, "inside prepareViewTrainer  with trainer {0}", t.toString());
        this.trainer = t;
        return "/admin/employees/trainers/viewTrainer.xhtml";
    }
    
    public String prepareEditTrainer(Trainer t){
        LOG.log(Level.INFO, "inside prepareEditTrainer with trainer {0}", t.toString());
        this.trainer = t;
        return "/admin/employees/trainers/editTrainer.xhtml";
    }
    
    public String prepareCreateTrainer(){
        LOG.log(Level.INFO, "inside prepareCreateTrainer");
        this.trainer = new Trainer();
        return "/admin/employees/trainers/editTrainer.xhtml";
    }
    //SAVE
    public String doSaveTrainer(){
        LOG.log(Level.INFO, "inside doSaveTrainer with trainer {0}", trainer.toString());
        
        if(this.trainer.getId()!=null){
            //UPDATE
            LOG.log(Level.INFO, "doSaveTrainer is going to call an update with {0}", this.trainer.toString());
            empSvc.update(this.trainer);
        }else{
            //CREATE
            LOG.log(Level.INFO, "doSaveTrainer is going to call a create with {0}", this.trainer.toString());
            empSvc.create(this.trainer);
        }
        
        return "/admin/employees/trainers/allTrainers.xhtml";
    }
    //DELTE
    public String doDeleteTrainer(Trainer trainer){
        LOG.log(Level.INFO, "inside doDeleteClient with trainer {0}", trainer.toString());
        empSvc.remove(trainer);
        return "/admin/employees/trainers/allTrainers.xhtml?faces-redirect=true";
    }
    /////////////////////
    //////LOCATION//////
    ////////////////////
    //PREPARE METHODS
    public String prepareViewLocation(Location l){
        LOG.log(Level.INFO, "inside prepareViewLocation  with location {0}", l.toString());
        this.location = l;
        return "/admin/locations/viewLocation.xhtml";
    }
    
    public String prepareEditLocation(Location l){
        LOG.log(Level.INFO, "inside prepareEditLocation with location {0}", l.toString());
        this.location = l;
        return "/admin/locations/editLocation.xhtml";
    }
    
    public String prepareCreateLocation(){
        LOG.log(Level.INFO, "inside prepareCreateLocation");
        this.location = new Location();
        return "/admin/locations/editLocation.xhtml";
    }
    //SAVE
    public String doSaveLocation(){
        LOG.log(Level.INFO, "inside doSaveLocation with location {0}", location.toString());
        
        if(this.location.getId()!=null){
            //UPDATE
            LOG.log(Level.INFO, "doSaveLocation is going to call an update with {0}", this.location.toString());
            locSvc.update(this.location);
        }else{
            //CREATE
            LOG.log(Level.INFO, "doSaveLocation is going to call a create with {0}", this.location.toString());
            locSvc.create(this.location);
        }
        
        return "/admin/locations/allLocations.xhtml";
    }
    //DELTE
    public String doDeleteLocation(Location location){
        LOG.log(Level.INFO, "inside doDeleteLocation with location {0}", trainer.toString());
        locSvc.remove(location);
        return "/admin/locations/allLocations.xhtml?faces-redirect=true";
    }
    /////////////////////
    //////EQUIPMENT//////
    ////////////////////
    //PREPARE METHODS
    public String prepareViewEquipment(Equipment eq){
        LOG.log(Level.INFO, "inside prepareViewEquiment  with equipment {0}", eq.toString());
        this.equipment = eq;
        return "/admin/equipments/viewEquipment.xhtml";
    }
    
    public String prepareEditEquipment(Equipment eq){
        LOG.log(Level.INFO, "inside prepareEditEquipment with equipment {0}", eq.toString());
        this.equipment = eq;
        return "/admin/equipments/editEquipment.xhtml";
    }
    
    public String prepareCreateEquipment(){
        LOG.log(Level.INFO, "inside prepareCreateLocation");
        this.equipment = new Equipment();
        return "/admin/equipments/editEquipment.xhtml";
    }
    //SAVE
    public String doSaveEquipment(){
        LOG.log(Level.INFO, "inside doSaveEquipment with equipment {0}", location.toString());
        
        if(this.equipment.getId()!=null){
            //UPDATE
            LOG.log(Level.INFO, "doSaveEquipment is going to call an update with {0}", this.equipment.toString());
            eqSvc.update(this.equipment);
        }else{
            //CREATE
            LOG.log(Level.INFO, "doSaveEquipment is going to call a create with {0}", this.equipment.toString());
            eqSvc.create(this.equipment);
        }
        
        return "/admin/equipments/allEquipments.xhtml";
    }
    //DELTE
    public String doDeleteEquipment(Equipment equipment){
        LOG.log(Level.INFO, "inside doDeleteEquipment with equipment {0}", equipment.toString());
        eqSvc.remove(equipment);
        return "/admin/equipments/allEquipments.xhtml?faces-redirect=true";
    }
    
    ///////////////
    //All Admins//
    //////////////
    public List<Administrative> getAllAdministratives(){
        return empSvc.findAllAdmins();
    }
    
    /////////////////////
    //GETTERS & SETTERS//
    ////////////////////
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
    
    /**
     * Get the value of location
     *
     * @return the value of location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Set the value of location
     *
     * @param location new value of location
     */
    public void setLocation(Location location) {
        this.location = location;
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
