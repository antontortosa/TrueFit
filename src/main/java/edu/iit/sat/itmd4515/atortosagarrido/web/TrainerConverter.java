/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.web;

import edu.iit.sat.itmd4515.atortosagarrido.domain.Employee;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Location;
import edu.iit.sat.itmd4515.atortosagarrido.service.EmployeeService;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

/**
 *
 * @author antoniotortosa
 */
@ManagedBean
@Named
//@RequestScoped
public class TrainerConverter implements Converter {
    
    @EJB
    private EmployeeService empSvc;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value.equals("NO_TRAINER")){
            return null;
        }
        return empSvc.findByFullName(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value != null){
            return ((Employee)value).getFullName();   
        }else{
            return "NO_TRAINER";
        }
    }
    
}
