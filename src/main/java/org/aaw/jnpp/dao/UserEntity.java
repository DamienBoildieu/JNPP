/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aaw.jnpp.dao;

import java.io.Serializable;

/**
 *
 * @author damien
 */
public class UserEntity implements Serializable {

  private static final long serialVersionUID = 8258197330872114846L;
  
  final private String firstName;
  
  final private String lastName;
  
  public UserEntity(String fName, String lName) {
    this.firstName = fName;
    this.lastName = lName;
  }
  
  public String getFirstName() {
    return this.firstName;
  }
  
  public String getLastName() {
    return this.lastName;
  }
}
