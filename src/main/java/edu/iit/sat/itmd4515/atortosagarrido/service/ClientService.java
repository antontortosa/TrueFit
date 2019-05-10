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
    public void create(Client c){
        //Call super to persist the Employee itself
        super.create(c);
        //If it comes with a location we update that location object so it refelects
        //the new employee
        if(c.getMainLocation() != null){
            Location lDB = em.getReference(Location.class, c.getMainLocation().getId());
            lDB.addClient(c);
        }
        em.flush();
    }
    
    @Override
    public void update(Client c){
        Client db = em.getReference(Client.class, c.getId());
        LOG.log(Level.INFO, "Client Service Updating client {0}", db.toString());
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
        if(c.getHeight()!=null){
            db.setHeight(c.getHeight());
        }
        if(c.getWeight()!=null){
            db.setWeight(c.getWeight());
        }
        if(c.getBodyFatPercentage()!=null){
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
            //LOG.log(Level.INFO, "UPDATE CLIENT: New trainer {0} for the client {1}", new Object[]{c.getTrainer().getFullName(), c.toString()});
            Trainer dbT_old = null;            
            Trainer dbT_new = em.getReference(Trainer.class, c.getTrainer().getId());
            //LOG.log(Level.INFO, "UPDATE CLIENT: The new trainer in the DB is: {0}", dbT_new.toString());
            if(db.getTrainer()!=null){
                dbT_old = em.getReference(Trainer.class, db.getTrainer().getId());
                //LOG.log(Level.INFO, "UPDATE CLIENT: The db Client had a trainer who is : {0}", dbT_old.getFullName());
            }
            db.setTrainer(c.getTrainer());
            //LOG.log(Level.INFO, "UPDATE CLIENT: The client {0} has the new trainer {1}", new Object[]{db.toString(), db.getTrainer().getFullName()});
            if(dbT_old!=null){
                for(Client caux : dbT_old.getClients()){
                    //LOG.log(Level.INFO, "UPDATE CLIENT: The old trainer has {0} as a client",caux.getFullName()) ;
                }
                //LOG.log(Level.INFO, "UPDATE CLIENT: The old trainer is merged : {0}" , dbT_old.getFullName());
                em.merge(dbT_old);
            }
            //LOG.log(Level.INFO, "UPDATE CLIENT: The new trainer is merged : {0}", db.getTrainer().getFullName());
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
