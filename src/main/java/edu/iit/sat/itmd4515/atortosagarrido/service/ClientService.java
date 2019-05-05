/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.service;

import edu.iit.sat.itmd4515.atortosagarrido.domain.Client;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Location;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Membership;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Trainer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author antoniotortosa
 */
@Stateless
public class ClientService extends AbstractService<Client> {

    private static final Logger LOG = Logger.getLogger(ClientService.class.getName());
        
    
    public ClientService() {
        super(Client.class);
    }
    
    @Override
    public void update(Client c){
        Client db = em.getReference(Client.class, c.getId());
        if(c.getName()!=null){
            db.setName(c.getName());
        }
        if(c.getSurname()!=null){
            db.setSurname(c.getSurname());
        }
        if(c.getBirthDate()!=null){
            db.setBirthDate(c.getBirthDate());
        }
        if(c.getSignDate()!=null){
            db.setSignDate(c.getSignDate());
        }
        if(c.getMembership()!=null){
            db.setMembership(c.getMembership());
            em.merge(db.getMembership());
        }
        if(c.getHeight()!=0.0){
            db.setHeight(c.getHeight());
        }
        if(c.getWeight()!=0.0){
            db.setWeight(c.getWeight());
        }
        if(c.getBodyFatPercentage()!=0.0){
            db.setBodyFatPercentage(c.getBodyFatPercentage());
        }
        if(c.getMainLocation()!=null){
            //LOG.log(Level.INFO, "New location as for JSF: {0}", c.getMainLocation().toString());
            db.setMainLocation(c.getMainLocation());
            //LOG.log(Level.INFO, "New location as for database: {0}", db.getMainLocation().toString());
            em.merge(db.getMainLocation());
        }
        if(c.getTrainingFocusId()!=0){
            db.setTrainingFocusId(c.getTrainingFocusId());
        }
        if(c.getTrainer()!=null){
            db.setTrainer(c.getTrainer());
            em.merge(db.getTrainer());
        }
        if(c.getUser()!=null){
            db.setUser(c.getUser());
        }
        em.flush();
    }
    
    @Override
    public void remove(Client c){
        Client cDB = em.getReference(Client.class, c.getId());
        Membership m = cDB.getMembership();
        Location l = cDB.getMainLocation();
        Trainer t = cDB.getTrainer();
        if(l!=null){
            l.removeClient(cDB);
            em.merge(l);
        }
        if(t!=null){
            t.removeClient(cDB);
            em.merge(t);
        }
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
