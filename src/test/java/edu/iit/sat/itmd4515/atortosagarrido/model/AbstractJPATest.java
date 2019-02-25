/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author antoniotortosa
 */
public class AbstractJPATest {

    private static EntityManagerFactory ef;
    protected static EntityManager em;
    protected static EntityTransaction et;

    @BeforeClass
    public static void setUpClass() {
        ef = Persistence.createEntityManagerFactory("itmd4515testPU");
    }

    @AfterClass
    public static void tearDownClass() {
        if (ef != null) {
            ef.close();
        }
    }

    @Before
    public void setUp() throws ParseException {
        em = ef.createEntityManager();
        et = em.getTransaction();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Client c = new Client("Iam", "Nobody", format.parse("1944-6-6"), 1, 1.76, 80);
        et.begin();
        em.persist(c);
        et.commit();
    }

    @After
    public void tearDown() {
        Client c = em.createNamedQuery("Client.findByFullName",Client.class)
                .setParameter("name", "Iam")
                .setParameter("surname", "Nobody")
                .getSingleResult();
        if (em != null) {
            if (et != null) {   
                et.begin();
                em.remove(c);
                et.commit();  
            }
            em.close();
        }
    }
}
