/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.web;

import edu.iit.sat.itmd4515.atortosagarrido.domain.Membership;
import edu.iit.sat.itmd4515.atortosagarrido.service.MembershipService;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

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
public class MembershipConverter implements Converter {
    
    @EJB
    private MembershipService memSvc;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value.equals("ERROR")){
            return null;
        }else{
            return memSvc.findByName(value);
        }     
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value!=null){
            return ((Membership)value).getName();
        }else{
            return "ERROR";
        }
        
    }
    
}
