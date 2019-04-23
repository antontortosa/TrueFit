/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.service;

import edu.iit.sat.itmd4515.atortosagarrido.domain.Membership;
import edu.iit.sat.itmd4515.atortosagarrido.domain.security.Group;
import edu.iit.sat.itmd4515.atortosagarrido.domain.security.User;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
    
    @EJB
    protected MembershipService memSv;
    
    @EJB
    protected UserService usrSv;
    
    @EJB
    protected GroupService grpSv;
    
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
        
        User admin = new User("admin", "admin", true);
        Group adminGroup = new Group("ADMIN_ROLE", "This is the admin group");
        admin.addGroup(adminGroup);
        
        grpSv.create(adminGroup);
        usrSv.create(admin);
        
        memSv.create(m1);
        memSv.create(m2);
        memSv.create(m3);
        memSv.create(m4);
    }
}
