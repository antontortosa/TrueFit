/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.service;

import edu.iit.sat.itmd4515.atortosagarrido.domain.Location;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author antoniotortosa
 */

@Stateless
public class LocationService extends AbstractService<Location>{
    
    public LocationService() {
        super(Location.class);
    }
    
    /**
     * Find all locations
     * 
     * @return all the locations in the Data Base
     */
    @Override
    public List<Location> findAll(){
        return em.createNamedQuery("Location.findAll",Location.class)
                .getResultList();
    }

    public Location findByName(String name) {
        return em.createNamedQuery("Location.findByName",Location.class)
                .setParameter("name", name)
                .getSingleResult();
    }
    
}
