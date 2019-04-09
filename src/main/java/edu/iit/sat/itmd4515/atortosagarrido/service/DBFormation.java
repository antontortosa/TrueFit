/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.service;

import edu.iit.sat.itmd4515.atortosagarrido.model.Membership;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author antoniotortosa
 */
@Startup
@Singleton
public class DBFormation {

    @PersistenceContext(name = "itmd4515PU")
    private EntityManager em;
    
    public DBFormation() {
    }
    
    @PostConstruct
    private void buildDatabase(){
         //Memberships
         buildMemberships();
    }

    private void buildMemberships() {
        Membership m1  = new Membership("Standard", 50.0);
        Membership m2  = new Membership("Premium", 75.0);
        Membership m3  = new Membership("Gold", 100.0);
        Membership m4  = new Membership("VIP", 150.0);
        em.persist(m1);
        em.persist(m2);
        em.persist(m3);
        em.persist(m4);
    }
}
