/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.model;


import java.text.SimpleDateFormat;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 *
 * @author antoniotortosa
 */
public class AbstractJPATest {

    protected static EntityManagerFactory ef;
    protected static EntityManager em;
    protected static EntityTransaction et;
    protected static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

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
}
