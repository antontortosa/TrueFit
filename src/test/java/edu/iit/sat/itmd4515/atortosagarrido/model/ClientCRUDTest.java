/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author antoniotortosa
 */
public class ClientCRUDTest extends AbstractJPATest {

    public ClientCRUDTest() {
    }

    @Test
    public void createNewValidClient() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Client c = new Client("Antoio", "Tortosa", format.parse("1994-11-17"), 3, 1.8, 77);
        et.begin();
        em.persist(c);
        assertNull("ID should be null before object is commited to the database", c.getId());
        et.commit();
        assertTrue("ID should have a value so far", c.getId() > 0);
    }

    @Test(expected = RollbackException.class)
    public void createInvalidClient() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Client c = new Client(null, "Tortosa", format.parse("1994-11-17"), 3, 1.8, 77);
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Client c = new Client("Emilia", "Rosales", format.parse("1998-7-10"), 3, 1.51, 43);
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
        et.commit();
    }
}
