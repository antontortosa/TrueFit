/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.web;

import edu.iit.sat.itmd4515.atortosagarrido.domain.Client;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Membership;
import edu.iit.sat.itmd4515.atortosagarrido.service.ClientService;
import edu.iit.sat.itmd4515.atortosagarrido.service.MembershipService;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author antoniotortosa
 */
@Named
@RequestScoped
public class ClientController {

    private static final Logger LOG = Logger.getLogger(ClientController.class.getName());

    private Client client;

    @EJB 
    private MembershipService memSvc;
    
    @EJB 
    private ClientService clSvc;
    
    public ClientController() {
    }
    
    @PostConstruct
    private void postConstruct(){
        LOG.info("inside PostConstruct");
        client = new Client();
    }
    
    public String executeSaveClient(){
        LOG.info("inside executeSaveClient" + client.toString());
        client.setSignDate(Date.from(Instant.now()));
        clSvc.create(client);
        return "/employees/clientok.xhtml";
    }

    public List<Membership> getMemberships(){
        return memSvc.findAll();
    }
    
    public List<Client> getAllClients(){
        return clSvc.findAll();
    }
    
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    
    
    
}
