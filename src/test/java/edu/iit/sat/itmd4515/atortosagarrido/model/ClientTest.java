/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author antoniotortosa
 */
public class ClientTest extends AbstractJPATest {
    
    public ClientTest() {
    }
    
    @Test
    public void createNewValidClient() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Client c = new Client("Antoio", "Tortosa", format.parse("1994-11-17"), 3, 1.8, 77);
        et.begin();
        em.persist(c);
        assertNull("ID should be null before object is commited to the database", c.getId());
        et.commit();
        assertTrue("ID should have a value so far", c.getId()>0);
    }
}
