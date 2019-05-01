/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.service;

import edu.iit.sat.itmd4515.atortosagarrido.domain.Client;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Membership;
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
        Client cDB = em.getReference(Client.class, c.getId());
        Membership m = cDB.getMembership();
        m.removeClient(cDB);
        em.merge(m);
        em.remove(cDB);
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
    
    public Client findByUsername(String username) {
        return em.createNamedQuery("Client.findByUsername",Client.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}
