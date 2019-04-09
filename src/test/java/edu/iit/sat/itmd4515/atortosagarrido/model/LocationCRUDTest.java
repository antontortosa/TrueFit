/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.model;

import static edu.iit.sat.itmd4515.atortosagarrido.model.AbstractJPATest.em;
import java.text.ParseException;
import javax.persistence.RollbackException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author antoniotortosa
 */
public class LocationCRUDTest extends AbstractJPATest{
    
    public LocationCRUDTest() {
    }
    
    @Before
    public void setUp() throws ParseException {
        em = ef.createEntityManager();
        et = em.getTransaction();
        Location l = new Location("Downtown center", "25 E Delaware", (short) 60611);
        et.begin();
        em.persist(l);
        et.commit();
    }

    @After
    public void tearDown() {
        Location l = em.createNamedQuery("Location.findByName",Location.class)
                .setParameter("name", "Downtown center")
                .getSingleResult();
        if (em != null) {
            if (et != null) {   
                et.begin();
                em.remove(l);
                et.commit();  
            }
            em.close();
        }
    }

    @Test
    public void createNewValidLocation() {
        Location l = new Location("Lakeview Center", "1237 W Fullerton", (short) 60614);
        et.begin();
        em.persist(l);
        assertNull("ID should be null before object is commited to the database", l.getId());
        et.commit();
        assertTrue("ID should have a value so far", l.getId() > 0);
        et.begin();
        em.remove(l);
        et.commit();
    }
    
    @Test(expected = RollbackException.class)
    public void createInvalidLocation() {
        Location l = new Location(null, "1237 W Fullerton", (short) 60614);
        et.begin();
        em.persist(l);
        assertNull("ID should be null before object is commited to the database", l.getId());
        et.commit();
    }
    
    @Test
    public void addLocationToClient() throws ParseException{
        Client c = new Client("Antoio", "Tortosa", format.parse("1994-11-17"), 1.8, 77);
        Location l = em.createNamedQuery("Location.findByName",Location.class)
                .setParameter("name", "Downtown center")
                .getSingleResult();
        et.begin();
        em.persist(l);
        em.persist(c);
        c.setMainLocation(l);
        et.commit();
        assertTrue(l.getClients().contains(c));
        et.begin();
        em.remove(c);
        et.commit();
    }
    
    @Test
    public void addLocationToEmployee() throws ParseException{
        Employee e = new Employee("Francisco", "Garcia", format.parse("1995-05-13"));
        Location l = em.createNamedQuery("Location.findByName",Location.class)
                .setParameter("name", "Downtown center")
                .getSingleResult();
        et.begin();
        em.persist(l);
        em.persist(e);
        e.setLocation(l);
        et.commit();
        assertTrue(l.getEmployees().contains(e));
        et.begin();
        em.remove(e);
        et.commit();
    }
}
