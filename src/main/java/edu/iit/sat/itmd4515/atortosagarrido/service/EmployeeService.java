/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.service;

import edu.iit.sat.itmd4515.atortosagarrido.domain.Employee;
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
    
}
