 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.config;

import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;


/**
 *
 * @author antoniotortosa
 */
@Named
@ApplicationScoped
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/itmd4515DS",
        callerQuery = "select password from sec_user where username = ?",
        groupsQuery = "select groupname from sec_user_groups where username = ?"
)
@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/login.xhtml",
                errorPage = "/error.xhtml")
)
@DeclareRoles({"ADMIN_ROLE","CLIENT_ROLE","TRAINER_ROLE","TECH_ROLE"})
public class SecurityConfig {
    
}
