/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.web;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Equipment;
import edu.iit.sat.itmd4515.atortosagarrido.service.EquipmentService;
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
public class EquipmentConverter implements Converter {
    
    @EJB
    private EquipmentService eqSvc;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return eqSvc.findByName(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Equipment)value).getName();   
    }
    
}
