 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.config;

import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;


/**
 *
 * @author antoniotortosa
 */
@Named
@ApplicationScoped
@DeclareRoles({"ADMIN_ROLE","CLIENT_ROLE","TRAINER_ROLE","TECH_ROLE"})
public class SecurityConfig {
    
}
