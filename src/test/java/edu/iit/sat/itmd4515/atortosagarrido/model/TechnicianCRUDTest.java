/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.model;

import edu.iit.sat.itmd4515.atortosagarrido.domain.Position;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Employee;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Technician;
import edu.iit.sat.itmd4515.atortosagarrido.domain.EqStatus;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Equipment;
import static edu.iit.sat.itmd4515.atortosagarrido.model.AbstractJPATest.et;
import java.text.ParseException;
import javax.persistence.RollbackException;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author antoniotortosa
 */
public class TechnicianCRUDTest extends AbstractJPATest {
    
    public TechnicianCRUDTest() {
    }
    
    @Before
    public void setUp() throws ParseException {
        em = ef.createEntityManager();
        et = em.getTransaction();
        Technician t = new Technician("Paul", "Fixthings", format.parse("1982-07-14"));
        et.begin();
        em.persist(t);
        et.commit();
    }

    @After
    public void tearDown() {
        Employee t = em.createNamedQuery("Employee.findByFullName",Employee.class)
                .setParameter("name", "Paul")
                .setParameter("surname", "Fixthings")
                .getSingleResult();
        if (em != null) {
            if (et != null) {   
                et.begin();
                em.remove(t);
                et.commit();  
            }
            em.close();
        }
    }
    
    @Test
    public void createNewValidTechnician() throws ParseException {
        Technician t = new Technician("Charles", "Fixable", format.parse("1992-10-29"));
        et.begin();
        em.persist(t);
        assertNull("ID should be null before object is commited to the database", t.getId());
        et.commit();
        assertTrue("ID should have a value so far", t.getId() > 0);
        et.begin();
        em.remove(t);
        et.commit();
    }
    
    @Test(expected = RollbackException.class)
    public void createInvalidTechnician() throws ParseException {
        Technician t = new Technician(null, "Fixable", format.parse("1992-10-29"));
        et.begin();
        em.persist(t);
        et.commit();
        //assertTrue("ID should have a value so far", c.getId()>0);
    }
    
    @Test
    public void addPositionToTechnician() throws ParseException{
        Technician t = new Technician("Charles", "Fixable", format.parse("1992-10-29"));
        Position p = new Position(50000.00, "Chief Technician");
        et.begin();
        em.persist(t);
        em.persist(p);
        t.setPosition(p);
        et.commit();
        p = em.createNamedQuery("Position.findByName",Position.class)
                .setParameter("name", "Chief Technician")
                .getSingleResult();
        assertTrue(p.getEmployees().contains(t));
        et.begin();
        em.remove(t);
        em.remove(p);
        et.commit();
    }
    
    @Test
    public void linkTechnicianToEquipment() throws ParseException {
        Technician t = new Technician("Charles", "Fixable", format.parse("1992-10-29"));
        Equipment eq = new Equipment(EqStatus.BROKEN, "Rack_1");
        t.addEquipment(eq);
        et.begin();
        em.persist(eq);
        em.persist(t);
        et.commit();
        eq = em.createNamedQuery("Equipment.findByName",Equipment.class)
                .setParameter("name", "Rack_1")
                .getSingleResult();
        assertTrue(eq.getTechnicians().contains(t));
        et.begin();
        em.remove(t);
        em.remove(eq);
        et.commit();
    }
    
}
