/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aaw.jnpp.services;

import org.aaw.jnpp.dao.UserEntity;

/**
 *
 * @author damien
 */
public interface Connect {
  UserEntity connect(String name, String password);
}
