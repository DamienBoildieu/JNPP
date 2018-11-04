package jnpp.service.services;

import java.util.Date;
import jnpp.service.dto.IdentityDTO;
import jnpp.service.dto.clients.ClientDTO;
import jnpp.service.exceptions.ClosureException;
import jnpp.service.exceptions.clients.AgeException;
import jnpp.service.exceptions.clients.InformationException;
import jnpp.service.exceptions.duplicates.DuplicateClientException;
import jnpp.service.exceptions.entities.FakeClientException;

public interface ClientService {
    
    ClientDTO signIn(String login, String password);
    void signOut(String login)
            throws FakeClientException;
    void signUp(IdentityDTO.Gender gender, String firstname, String lastname,
            Date birthday, String email, Integer number, String street, 
            String city, String state, String phone) 
            throws DuplicateClientException, AgeException,
            InformationException;
    ClientDTO update(String login, String email, 
            Integer number, String street, String city, String state, 
            String phone) 
            throws InformationException, FakeClientException;
    void signUp(String name, IdentityDTO.Gender ownerGender, 
            String ownerFirstname, String ownerLastname, String email, 
            Integer number, String street, String city, String state, 
            String phone) 
            throws DuplicateClientException, InformationException; 
    void close(String login, String password) throws ClosureException, FakeClientException;
    boolean updatePassword(String login, String oldPassword, 
            String newPassword) throws FakeClientException;
    
    boolean resetPassword(String login, IdentityDTO.Gender gender, String firstname, 
            String lastname, String email) throws FakeClientException;
    boolean resetPassword(String login, String name, IdentityDTO.Gender ownerGender,
            String ownerFirstname, String ownerLastname, String email) throws FakeClientException;
    
}
