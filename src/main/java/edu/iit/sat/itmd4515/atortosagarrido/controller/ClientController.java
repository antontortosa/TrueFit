/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.controller;

import edu.iit.sat.itmd4515.atortosagarrido.model.Client;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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

    
    public ClientController() {
    }
    
    @PostConstruct
    private void postConstruct(){
        LOG.info("inside PostConstruct");
        client = new Client();
    }
    
    public String executeSaveClient(){
        LOG.info("inside executeSaveClient" + client.toString());
        return "/admin/welcome.xhtml";
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    
    
    
}
