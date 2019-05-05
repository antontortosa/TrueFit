/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.web;

import edu.iit.sat.itmd4515.atortosagarrido.domain.Location;
import edu.iit.sat.itmd4515.atortosagarrido.service.LocationService;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author antoniotortosa
 */
@Named
@RequestScoped
public class LocationController {

    private static final Logger LOG = Logger.getLogger(LocationController.class.getName());
    
    private Location location;
    
    @EJB
    private LocationService locSvc;

    public LocationController() {
    }
    
    @PostConstruct
    private void postConstruct(){
        LOG.info("inside PostConstruct");
        location = new Location();
    }
    
    public List<Location> getAllLocations(){
        return locSvc.findAll();
    }
    
    /**
     * Get the value of location
     *
     * @return the value of location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Set the value of location
     *
     * @param location new value of location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

}
