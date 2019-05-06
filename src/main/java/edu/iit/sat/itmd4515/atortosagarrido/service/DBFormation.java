/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.service;

import edu.iit.sat.itmd4515.atortosagarrido.domain.Administrative;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Client;
import edu.iit.sat.itmd4515.atortosagarrido.domain.EqStatus;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Equipment;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Location;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Membership;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Technician;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Trainer;
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

    private static final Logger LOG = Logger.getLogger(DBFormation.class.getName());

    @EJB
    protected MembershipService memSv;

    @EJB
    protected UserService usrSv;

    @EJB
    protected GroupService grpSv;

    @EJB
    protected ClientService clSv;

    @EJB
    protected EmployeeService empSv;

    @EJB
    protected LocationService locSv;
    
    @EJB
    protected EquipmentService eqcSv;

    protected static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public DBFormation() {
    }

    @PostConstruct
    private void buildDatabase() {
        buildLocations();
        buildEquipment();
        buildMemberships();
        buildUsers();
        buildClients();
        buildAdministrators();
        buildTechnicians();
        buildTrainers();
    }

    private void buildMemberships() {
        Membership m1 = new Membership("Standard", 50.0);
        Membership m2 = new Membership("Premium", 75.0);
        Membership m3 = new Membership("Gold", 100.0);
        Membership m4 = new Membership("VIP", 150.0);

        memSv.create(m1);
        memSv.create(m2);
        memSv.create(m3);
        memSv.create(m4);
    }

    private void buildUsers() {
        //Create groups
        Group adminGroup = new Group("ADMIN_GROUP", "This is the admin group");
        Group clientGroup = new Group("CLIENT_GROUP", "This is the clients group");
        Group employeeGroup = new Group("EMPLOYEE_GROUP", "This is the employee group");
        Group trainerGroup = new Group("TRAINER_GROUP", "This is the trainer group");
        Group techGroup = new Group("TECH_GROUP", "This is the technician group");
        //Create users
        User admin1 = new User("admin", "admin", true);
        User admin2 = new User("sauron", "pass", true);
        User client1 = new User("antontortosa", "pass", true);
        User client2 = new User("emrose", "pass", true);
        User traienr1 = new User("ramonf", "pass", true);
        User tech1 = new User("davidgl", "pass", true);
        //Persist groups in DB
        grpSv.create(adminGroup);
        grpSv.create(clientGroup);
        grpSv.create(trainerGroup);
        grpSv.create(employeeGroup);
        grpSv.create(techGroup);
        //Add the groups to the corresponding users
        admin1.addGroup(adminGroup);
        admin2.addGroup(adminGroup);
        client1.addGroup(clientGroup);
        client2.addGroup(clientGroup);
        traienr1.addGroup(trainerGroup);
        traienr1.addGroup(employeeGroup);
        tech1.addGroup(employeeGroup);
        tech1.addGroup(techGroup);
        //Persist the users in the DB
        usrSv.create(admin1);
        usrSv.create(admin2);
        usrSv.create(client1);
        usrSv.create(client2);
        usrSv.create(traienr1);
        usrSv.create(tech1);
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
            c1.setMainLocation(locSv.findByName("Chicago Lake View"));
            Client c2 = new Client("Emilia",
                    "Rosales",
                    format.parse("1998-07-10"),
                    1.5,
                    45);
            c2.setUser(usrSv.findByName("emrose"));
            c2.setMembership(memSv.findByName("Gold"));
            c2.setMainLocation(locSv.findByName("Chicago Downtown"));
            LOG.log(Level.INFO, "DBFormation is trying to persist Client {0}", c1.toString());
            clSv.create(c1);
            LOG.log(Level.INFO, "DBFormation is trying to persist Client {0}", c2.toString());
            clSv.create(c2);
        } catch (ParseException ex) {
            Logger.getLogger(DBFormation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buildAdministrators() {
        try {
            Administrative e1 = new Administrative("Gandalf",
                    "The Grey",
                    format.parse("1000-02-14"));
            e1.setUser(usrSv.findByName("admin"));
            LOG.log(Level.INFO, "DBFormation is trying to persist Admin {0}", e1.toString());
            empSv.create(e1);
            Administrative e2 = new Administrative("Sauron",
                    "The Great",
                    format.parse("1000-03-25"));
            e2.setUser(usrSv.findByName("sauron"));
            LOG.log(Level.INFO, "DBFormation is trying to persist Admin {0}", e2.toString());
            empSv.create(e2);
        } catch (ParseException ex) {
            Logger.getLogger(DBFormation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buildTechnicians() {
        try {
            Technician tch1 = new Technician("David",
                    "Galan",
                    format.parse("1994-03-20"));
            tch1.setUser(usrSv.findByName("davidgl"));
            tch1.setLocation(locSv.findByName("Chicago Lake View"));
            tch1.addEquipment(eqcSv.find(1));
            LOG.log(Level.INFO, "DBFormation is trying to persist Tech {0}", tch1.toString());
            empSv.create(tch1);
        } catch (ParseException ex) {
            Logger.getLogger(DBFormation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buildTrainers() {
        try {
            Trainer t1 = new Trainer("Ramon",
                    "Fernandez",
                    format.parse("1994-03-21"),
                    50);
            t1.setUser(usrSv.findByName("ramonf"));
            t1.setLocation(locSv.findByName("Chicago Downtown"));
            LOG.log(Level.INFO, "DBFormation is trying to persist Trainer {0}", t1.toString());
            empSv.create(t1);
        } catch (ParseException ex) {
            Logger.getLogger(DBFormation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buildLocations() {
        Location mainLoc = new Location("Chicago Lake View", "1237 W Fullerton Ave, Chicago, IL", 60614);
        //mainLoc.setAddress("1237 W Fullerton Ave, Chicago, IL");
        LOG.log(Level.INFO, "DBFormation is trying to persist Location {0}", mainLoc.toString());
        locSv.create(mainLoc);
        Location secondLoc = new Location("Chicago Downtown", "79 W Monroe St, Chicago, IL", 60603);
        //mainLoc.setAddress("1237 W Fullerton Ave, Chicago, IL");
        LOG.log(Level.INFO, "DBFormation is trying to persist Location {0}", secondLoc.toString());
        locSv.create(secondLoc);
    }
    
    private void buildEquipment(){
        Equipment eq = new Equipment(EqStatus.BROKEN, "Bench 1");
        eq.setLocation(locSv.findByName("Chicago Lake View"));
        LOG.log(Level.INFO, "DBFormation is trying to persist Equipment {0}", eq.toString());
        eqcSv.create(eq);
    }
}
