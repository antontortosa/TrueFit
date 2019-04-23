/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.domain.security;

import javax.inject.Inject;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

/**
 *
 * @author antoniotortosa
 */
public class UserListener {
    
    @Inject
    private Pbkdf2PasswordHash pswdHash;
    
    @PrePersist
    @PreUpdate
    private void hashPassword(User u){
         u.setPassword(pswdHash.generate(u.getPassword().toCharArray()));
    }
}
