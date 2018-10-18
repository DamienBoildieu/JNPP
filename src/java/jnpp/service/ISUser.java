package jnpp.service;

public interface ISUser {
    
    public boolean signIn(String login, String password);
    public boolean signOut(String login);
    public boolean signUp(String login, String password);
    
}
