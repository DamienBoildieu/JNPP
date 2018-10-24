package jnpp.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import jnpp.dao.old.EUser;
import jnpp.dao.old.IDUser;

@Service
public class SUser implements ISUser {

    @Resource
    IDUser dao;
    
    @Override
    public boolean signIn(String login, String password) {
        EUser user = dao.findByLogin(login);
        if (user == null) return false;
        return user.getPassword().equals(password);
    }

    @Override
    public boolean signOut(String login) {
        return true;
    }
    
    @Override
    public boolean signUp(String login, String password) {
        EUser user = dao.findByLogin(login);
        if (user != null) return false;
        dao.save(new EUser(login, password));
        return true;
    }
    
}
