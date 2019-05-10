/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.web;

import edu.iit.sat.itmd4515.atortosagarrido.domain.Client;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Location;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Membership;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Trainer;
import edu.iit.sat.itmd4515.atortosagarrido.domain.security.User;
import edu.iit.sat.itmd4515.atortosagarrido.service.ClientService;
import edu.iit.sat.itmd4515.atortosagarrido.service.GroupService;
import edu.iit.sat.itmd4515.atortosagarrido.service.MembershipService;
import edu.iit.sat.itmd4515.atortosagarrido.service.UserService;
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
public class ClientController {

    private static final Logger LOG = Logger.getLogger(ClientController.class.getName());

    private Client client;

    private User user;
    
    private Trainer trainer;

    @Inject
    private LoginController loginController;

    @Inject
    private SecurityContext securityContext;

    @EJB
    private MembershipService memSvc;

    @EJB
    private ClientService clSvc;

    @EJB
    private UserService usrSvc;

    @EJB
    private GroupService grpSvc;

    public ClientController() {
    }

    @PostConstruct
    private void postConstruct() {
        LOG.info("inside PostConstruct");
        if (loginController.getRemoteUser() != null && securityContext.isCallerInRole("CLIENT_ROLE")) {
            client = clSvc.findByUsername(loginController.getRemoteUser());
            user = client.getUser();
            trainer = client.getTrainer()!=null?client.getTrainer():new Trainer();
        } else {
            user = new User();
            client = new Client();
            trainer = new Trainer();
        }
    }

    public String clientCreatesClient() {
        LOG.log(Level.INFO, "inside executeSaveClient{0}", client.toString());
        //HttpServletRequest req =(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        //username  = facesContext.getExternalContext().getRequestParameterMap().get("user");
        user.addGroup(grpSvc.findByName("CLIENT_GROUP"));
        usrSvc.create(user);
        client.setUser(user);
        clSvc.create(client);
        return "clientok.xhtml";
    }

    //SAVE
    public String doSaveClient() {
        LOG.log(Level.INFO, "inside doSaveClient with client {0}", client.toString());

        //UPDATE
        LOG.log(Level.INFO, "doSaveClient is going to call an update with client {0}", this.client.toString());
        clSvc.update(this.client);

        return "/clients/user/profile.xhtml";
    }
    
    public String doSelectTrainer(Trainer t){
        LOG.log(Level.INFO, "inside doSelectTraienr with trainer {0}", t!=null?t.getFullName():"no trainer");
        this.trainer = t;
        this.client.setTrainer(this.trainer);
        clSvc.update(this.client);
        return "/clients/user/training/trainerhome.xhtml";
    }
    
    public String doSelectLocation(Location l){
        LOG.log(Level.INFO, "inside doSelectLocation with location {0}", l!=null?l.getName():"no location");
        this.client.setMainLocation(l);
        clSvc.update(this.client);
        return "/clients/user/training/locationhome.xhtml";
    }

    public List<Membership> getMemberships() {
        return memSvc.findAll();
    }

    public List<Client> getAllClients() {
        return clSvc.findAll();
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
}
