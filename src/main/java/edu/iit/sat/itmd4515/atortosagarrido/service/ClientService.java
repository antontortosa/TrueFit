/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.service;

import edu.iit.sat.itmd4515.atortosagarrido.model.Client;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author antoniotortosa
 */
@Stateless
public class ClientService {

    @PersistenceContext(name = "itmd4515PU")
    private EntityManager em;
    
    public ClientService() {
    }
    
    public void create(Client c){
        em.persist(c);
    }
    
    public void update(Client c){
        em.merge(c);
    }
    
    public void remove(Client c){
        c.getMembership().removeClient(c);
        em.remove(c);
    }     
}
