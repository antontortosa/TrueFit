/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.service;

import edu.iit.sat.itmd4515.atortosagarrido.domain.Client;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Membership;
import edu.iit.sat.itmd4515.atortosagarrido.domain.security.Group;
import edu.iit.sat.itmd4515.atortosagarrido.domain.security.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

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
    
    @EJB
    protected ClientService clSv;
    
    protected static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    
    public DBFormation() {
    }
    
    @PostConstruct
    private void buildDatabase(){
         //Memberships
         buildMemberships();
         buildUsers();
         buildClients();
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
        Group clientGroup = new Group("CLIENT_GROUP", "This is the clients group");
        Group employeeGroup = new Group("EMPLOYEE_GROUP", "This is the employee group");
        Group trainerGroup = new Group("TRAINER_GROUP", "This is the trainer group");
        User client1 = new User("antontortosa", "pass", true);
        User client2 = new User("emrose", "pass", true);
        User traienr1 = new User("ninatm", "pass", true);
        grpSv.create(adminGroup);
        grpSv.create(clientGroup);
        grpSv.create(trainerGroup);
        grpSv.create(employeeGroup);
        admin.addGroup(adminGroup);
        client1.addGroup(clientGroup);
        client2.addGroup(clientGroup);
        traienr1.addGroup(trainerGroup);
        traienr1.addGroup(employeeGroup);
        usrSv.create(admin);
        usrSv.create(client1);
        usrSv.create(client2);
        usrSv.create(traienr1);
    }

    private void buildClients() {
        try {
            Client c1 = new Client("Antonio",
                    "Tortosa",
                    format.parse("1994-11-17"),
                    1.8,
                    70);
            c1.setUser(usrSv.findByName("antontortosa"));
            c1.setMembership(memSv.findByName("VIP"));
            Client c2 = new Client("Emilia",
                    "Rosales",
                    format.parse("1998-07-10"),
                    1.5,
                    45);
            c2.setUser(usrSv.findByName("emrose"));
            c2.setMembership(memSv.findByName("Gold"));
            clSv.create(c2);
        } catch (ParseException ex) {
            Logger.getLogger(DBFormation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
