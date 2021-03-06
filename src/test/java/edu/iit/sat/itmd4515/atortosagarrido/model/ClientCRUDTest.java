/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.model;

import edu.iit.sat.itmd4515.atortosagarrido.domain.Client;
import static edu.iit.sat.itmd4515.atortosagarrido.model.AbstractJPATest.em;
//import edu.iit.sat.itmd4515.atortosagarrido.service.ClientService;
import java.text.ParseException;
//import javax.ejb.EJB;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author antoniotortosa
 */
public class ClientCRUDTest extends AbstractJPATest {
    
    //@EJB
    //private ClientService clSv;
    
    public ClientCRUDTest() {
    }
    
    @Before
    public void setUp() throws ParseException {
        em = ef.createEntityManager();
        et = em.getTransaction();
        Client c = new Client("Iam", "Nobody", format.parse("1944-6-6"), 1.76, Double.valueOf(80));
        et.begin();
        em.persist(c);
        et.commit();
    }

    @After
    public void tearDown() {
        et.begin();
        Client c = em.createNamedQuery("Client.findByFullName",Client.class)
                .setParameter("name", "Iam")
                .setParameter("surname", "Nobody")
                .getSingleResult();
        em.remove(c);
        et.commit();
        if (em != null) {
            em.close();
        }
    }

    @Test
    public void createNewValidClient() throws ParseException {
        Client c = new Client("Antoio", "Tortosa", format.parse("1994-11-17"), 1.8, Double.valueOf(77));
        et.begin();
        em.persist(c);
        assertNull("ID should be null before object is commited to the database", c.getId());
        et.commit();
        assertTrue("ID should have a value so far", c.getId() > 0);
    }

    @Test(expected = RollbackException.class)
    public void createInvalidClient() throws ParseException {
        Client c = new Client(null, "Tortosa", format.parse("1994-11-17"), 1.8, Double.valueOf(77));
        et.begin();
        em.persist(c);
        et.commit();
        //assertTrue("ID should have a value so far", c.getId()>0);
    }

    @Test
    public void testFindExistingClient() {
        Client c = em.createNamedQuery("Client.findByFullName", Client.class)
                .setParameter("name", "Iam")
                .setParameter("surname", "Nobody")
                .getSingleResult();
        assertEquals("Name of client retrieved should match", "Iam Nobody", c.getFullName());
    }

    @Test(expected = NoResultException.class)
    @SuppressWarnings("UnusedAssignment")
    public void testFindNonExistingClient() {
        Client c = em.createNamedQuery("Client.findByFullName", Client.class)
                .setParameter("name", "Yosoy")
                .setParameter("surname", "Inexistente")
                .getSingleResult();
        //assertEquals("Name of client retrieved should match", "Iam Nobody", c.getFullName());
    }

    @Test(expected = NoResultException.class)
    @SuppressWarnings("UnusedAssignment")
    public void testRemoveExistingClient() throws ParseException {
        Client c = new Client("Emilia", "Rosales", format.parse("1998-7-10"), 1.51, Double.valueOf(43));
        et.begin();
        em.persist(c);
        et.commit();
        assertTrue("ID should have a value so far", c.getId() > 0);
        et.begin();
        em.remove(c);
        et.commit();
        c = em.createNamedQuery("Client.findByFullName", Client.class)
                .setParameter("name", "Emilia")
                .setParameter("surname", "Rosales")
                .getSingleResult();
    }

    @Test
    public void testUpdateExistingClient() {
        String oldSurname;
        String newSurname = "Still Nobody";
        Client c = em.createNamedQuery("Client.findByFullName", Client.class)
                .setParameter("name", "Iam")
                .setParameter("surname", "Nobody")
                .getSingleResult();
        assertEquals("Name of client retrieved should match", "Iam Nobody", c.getFullName());
        oldSurname = c.getSurname();
        et.begin();
        em.persist(c);
        c.setSurname(newSurname);
        et.commit();
        c = em.createNamedQuery("Client.findByFullName", Client.class)
                .setParameter("name", "Iam")
                .setParameter("surname", newSurname)
                .getSingleResult();
        assertEquals("The new surname has to be the updated one", newSurname, c.getSurname());
        et.begin();
        em.persist(c);
        c.setSurname(oldSurname);
        em.merge(c);
        et.commit();
    }
}
