/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.service;

import edu.iit.sat.itmd4515.atortosagarrido.domain.Administrative;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Employee;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Technician;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Trainer;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author antoniotortosa
 */

@Stateless
public class EmployeeService extends AbstractService<Employee> {
   
    public EmployeeService() {
        super(Employee.class);
    }
    
    /**
     * Find all employees
     * 
     * @return all the Emmployees in the Data Base
     */
    @Override
    public List<Employee> findAll(){
        return em.createNamedQuery("Employee.findAll",Employee.class)
                .getResultList();
    }
    
    public List<Technician> findAllTechnicians(){
        return em.createNamedQuery("Technician.findAll",Technician.class)
                .getResultList();
    }

    public Employee findByUsername(String username) {
        return em.createNamedQuery("Employee.findByUsername",Employee.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    public List<Administrative> findAllAdmins() {
        return em.createNamedQuery("Administrative.findAll",Administrative.class)
                .getResultList();
    }
    
    public List<Trainer> findAllTrainers() {
        return em.createNamedQuery("Trainer.findAll",Trainer.class)
                .getResultList();
    }
    
}
