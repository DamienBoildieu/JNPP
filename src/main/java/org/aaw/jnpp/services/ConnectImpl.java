/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aaw.jnpp.services;

import org.aaw.jnpp.dao.UserEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author damien
 */
@Service("Connect") 
public class ConnectImpl implements Connect {

  @Override
  public UserEntity connect(String name, String password) {
    return new UserEntity(name, password); //To change body of generated methods, choose Tools | Templates.
  }
  
}
