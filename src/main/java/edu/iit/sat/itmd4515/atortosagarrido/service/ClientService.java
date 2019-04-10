/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.service;

import edu.iit.sat.itmd4515.atortosagarrido.model.Client;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author antoniotortosa
 */
@Stateless
public class ClientService extends AbstractService<Client> {
        
    public ClientService() {
        super(Client.class);
    }
    
    @Override
    public void remove(Client c){
        c.getMembership().removeClient(c);
        em.remove(c);
    }
    
    /**
     * Find all clients
     * 
     * @return all the Client in the Data Base
     */
    @Override
    public List<Client> findAll(){
        return em.createNamedQuery("Client.findAll",Client.class)
                .getResultList();
    }
    
}
