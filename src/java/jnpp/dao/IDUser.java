package jnpp.dao;

import java.util.List;

public interface IDUser {
    
    public void save(EUser user);
    public void update(EUser user);
    public void delete(EUser user);
    public EUser find(long id);
    public List<EUser> findAll();
    public EUser findByLogin(String login);
    
}
