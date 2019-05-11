/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.web;

import edu.iit.sat.itmd4515.atortosagarrido.domain.Membership;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author antoniotortosa
 */
@FacesValidator("membershipValidator")
public class membershipValidator implements Validator{

    private static final Logger LOG = Logger.getLogger(membershipValidator.class.getName());

    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String errorMessage = null;
        if (!(value instanceof Membership)) {
            errorMessage = "No membership selected.";
        }
        if (errorMessage != null) {
            FacesMessage msg =
                    new FacesMessage("Invalid Employee Input", errorMessage);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
    
}
