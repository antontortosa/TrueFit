/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.web;

import edu.iit.sat.itmd4515.atortosagarrido.service.GroupService;
import edu.iit.sat.itmd4515.atortosagarrido.service.UserService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author antoniotortosa
 */
@Named
@RequestScoped
public class LoginController {

    //login form fields
    @NotBlank(message = "Username can't be left in blank")
    @NotEmpty(message = "Username can't be empty")
    private String username;
    @NotBlank(message = "Password can't be left in blank")
    @NotEmpty(message = "Password can't be empty")
    private String password;

    //logger
    private static final Logger LOG = Logger.getLogger(LoginController.class.getName());

    //resurces injected
    @Inject
    private SecurityContext securityContext;
    @Inject
    private FacesContext facesContext;
    
    @EJB 
    private UserService usrSvc;
    
    @EJB 
    private GroupService grpSvc;
    
    public LoginController() {
    }

    //Utlity methods
    
    /**
     * Returns the authenticated username
     * @return the username of the authenticated user
     */
    public String getRemoteUser(){
        return facesContext.getExternalContext().getRemoteUser();
    }
    
    public boolean isAdmin() {
         return securityContext.isCallerInRole("ADMIN_ROLE");
    }
    
    public boolean isClient() {
         return securityContext.isCallerInRole("CLIENT_ROLE");
    }
    
    public boolean isTrainer() {
         return securityContext.isCallerInRole("TRAINER_ROLE");
    }
    
    public boolean isTech() {
         return securityContext.isCallerInRole("TECH_ROLE");
    }
    
    //Action methods
    
    /**
     * Logic of the login procedure
     * @return the welcome page if login is successful. Error if fails
     */
    public String doLogin() {
        LOG.info("Inside doLogin()");
        Credential credential = new UsernamePasswordCredential(username, new Password(password));
        AuthenticationStatus authStatus = securityContext.authenticate((HttpServletRequest) facesContext.getExternalContext().getRequest(),
                (HttpServletResponse) facesContext.getExternalContext().getResponse(),
                AuthenticationParameters.withParams().credential(credential)
        );
        LOG.log(Level.INFO, "Authentication status is: {0}", authStatus.toString());

        switch (authStatus) {
            case SEND_CONTINUE:
                LOG.info("SEND_CONTINUE on Login");
                break;
            case SEND_FAILURE:
                LOG.info("SEND_FAILURE on Login");
                return "/error.xhtml";
            case SUCCESS:
                LOG.info("SUCCESS on Login");
                break;
            case NOT_DONE:
                LOG.info("NOT_DONE on Login");
                return "/error.xhtml";
        }
        if(isAdmin()){
            return "employees/admin/welcome.xhtml?faces-redirect=true";
        }
        else if(isClient()){
            return "clients/user/welcome.xhtml?faces-redirect=true";
        }
        else if(isTrainer()){
         return "employees/train/welcome.xhtml?faces-redirect=true";
        }
        else{
          return "employees/tech/welcome.xhtml?faces-redirect=true";
        }
    }
    
    public String doLogout(){
        try {
            HttpServletRequest req = (HttpServletRequest)facesContext.getExternalContext().getRequest();
            req.logout();
        } catch (ServletException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        LOG.info("Logged out user and redirecting to login page");
        return "/login.xhtml?faces-redirect=true";
    }

    /**
     * Get the value of password
     *
     * @return the value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the value of password
     *
     * @param password new value of password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the value of username
     *
     * @return the value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the value of username
     *
     * @param username new value of username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    

}
