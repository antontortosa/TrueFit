/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.web;

import edu.iit.sat.itmd4515.atortosagarrido.domain.Administrative;
import edu.iit.sat.itmd4515.atortosagarrido.service.EmployeeService;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author antoniotortosa
 */
@Named
@RequestScoped
public class AdministrativeController {

    private static final Logger LOG = Logger.getLogger(AdministrativeController.class.getName());
    
    private Administrative adminstrative;
    
    @EJB
    private EmployeeService empSvc;

    /**
     * Get the value of adminstrative
     *
     * @return the value of adminstrative
     */
    public Administrative getAdminstrative() {
        return adminstrative;
    }

    /**
     * Set the value of adminstrative
     *
     * @param adminstrative new value of adminstrative
     */
    public void setAdminstrative(Administrative adminstrative) {
        this.adminstrative = adminstrative;
    }

    
}
