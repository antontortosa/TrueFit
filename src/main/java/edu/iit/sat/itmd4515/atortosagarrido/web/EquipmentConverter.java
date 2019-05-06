/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.web;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Equipment;
import edu.iit.sat.itmd4515.atortosagarrido.service.EquipmentService;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private static final Logger LOG = Logger.getLogger(EquipmentConverter.class.getName());
    
    @EJB
    private EquipmentService eqSvc;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        LOG.log(Level.INFO, "EquipmentConverter getAsObject with value {0}", value);
        return eqSvc.findByName(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        LOG.log(Level.INFO, "EquipmentConverter getAsString with Object {0}", value);
        return ((Equipment)value).getName();   
    }
    
}
