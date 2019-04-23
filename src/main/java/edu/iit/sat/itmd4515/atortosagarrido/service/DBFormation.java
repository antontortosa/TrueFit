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
         buildUsers();
    }

    private void buildMemberships() {
        Membership m1  = new Membership("Standard", 50.0);
        Membership m2  = new Membership("Premium", 75.0);
        Membership m3  = new Membership("Gold", 100.0);
        Membership m4  = new Membership("VIP", 150.0);
        
        memSv.create(m1);
        memSv.create(m2);
        memSv.create(m3);
        memSv.create(m4);
    }

    private void buildUsers() {
        User admin = new User("admin", "admin", true);
        Group adminGroup = new Group("ADMIN_GROUP", "This is the admin group");
        admin.addGroup(adminGroup);
        
        Group clientGroup = new Group("CLIENT_GROUP", "This is the clients group");
        Group employeeGroup = new Group("EMPLOYEE_GROUP", "This is the employee group");
        Group trainerGroup = new Group("TRAINER_GROUP", "This is the trainer group");
        User client1 = new User("antontortosa", "pass", true);
        User client2 = new User("emrose", "pass", true);
        User traienr1 = new User("ninatm", "pass", true);
        client1.addGroup(clientGroup);
        client2.addGroup(clientGroup);
        traienr1.addGroup(trainerGroup);
        traienr1.addGroup(employeeGroup);
        
        grpSv.create(adminGroup);
        grpSv.create(clientGroup);
        grpSv.create(trainerGroup);
        grpSv.create(employeeGroup);
        usrSv.create(admin);
        usrSv.create(client1);
        usrSv.create(client2);
        usrSv.create(traienr1);
    }
}
