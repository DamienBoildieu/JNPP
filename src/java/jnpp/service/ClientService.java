package jnpp.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import jnpp.dao.entities.clients.Address;
import jnpp.dao.entities.clients.Client;
import jnpp.dao.entities.clients.Gender;
import jnpp.dao.entities.clients.Identifier;
import jnpp.dao.entities.clients.Private;
import jnpp.dao.entities.clients.Professional;
import jnpp.dao.repositories.IClientDAO;
import jnpp.dao.repositories.IIdentifierDAO;
import jnpp.service.exceptions.ClosureException;
import jnpp.service.exceptions.clients.BeOfAgeException;
import jnpp.service.exceptions.clients.DuplicatedClientException;
import jnpp.service.exceptions.clients.InvalidInformationException;
import jnpp.service.exceptions.entities.FakeClientException;

import org.springframework.stereotype.Service;

@Service("ClientService")
public class ClientService implements IClientService {

    private static final String LOGIN_FORMAT = "%08d";
    
    private static final int PASSWORD_LENGTH = 8;
    private static final String PASSWORD_SALT = 
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    
    @Resource
    IClientDAO clientDAO;
    @Resource
    IIdentifierDAO identifierDAO;
    
    private final Random random = new Random();
    
    @Override
    public Client signIn(String login, String password) {
        return identifierDAO.find(login, password);
    }

    @Override
    public void signOut(Client client) 
            throws FakeClientException {
        if (client == null) throw new IllegalArgumentException();
        if (CHECK_FAKE_ENTITY)
            if (client.getId() == null || 
                    clientDAO.find(client.getId()) == null) 
                throw new FakeClientException();
    }

    @Override
    public void signUp(Gender gender, String firstname, String lastname, 
            Date birthday, String email, Integer number, String street, 
            String city, String state, String phone) 
            throws DuplicatedClientException, BeOfAgeException, 
            InvalidInformationException {
        if (gender == null || firstname == null || lastname == null ||
                birthday == null || email == null || number == null || 
                street == null || city == null || state == null ||
                phone == null) throw new IllegalArgumentException();
        if (computeAge(birthday) < 18) throw new BeOfAgeException();      
        validEmail(email);
        validAddress(new Address(number, street, city, state));
        validPhone(phone);
        if (clientDAO.privateExist(gender, firstname, lastname)) 
            throw new DuplicatedClientException();
        Private client = new Private(gender, firstname, lastname, birthday, 
                email, number, street, city, state, phone, false);
        client = (Private) clientDAO.save(client);
        Identifier identifier = generateNewIdentifier();
        identifier.setClient(client);
        identifierDAO.save(identifier);
    }

    @Override
    public void signUp(String name, Gender ownerGender, 
            String ownerFirstname, String ownerLastname, String email, 
            Integer number, String street, String city, String state, 
            String phone) 
            throws DuplicatedClientException, InvalidInformationException {     
        if (name == null || ownerGender == null || ownerFirstname == null || 
                ownerLastname == null || email == null || number == null ||
                street == null || city == null || state == null || 
                phone == null) throw new IllegalArgumentException();      
        validEmail(email);
        validAddress(new Address(number, street, city, state));
        validPhone(phone);    
        if (clientDAO.professionalExist(name)) 
            throw new DuplicatedClientException();  
        Professional client = new Professional(name, ownerGender, 
                ownerFirstname, ownerLastname, email, number, street, city, 
                state, phone);
        client = (Professional) clientDAO.save(client);
        Identifier identifier = generateNewIdentifier();
        identifier.setClient(client);
        identifierDAO.save(identifier);
    }

    @Override
    public Client update(Client client, String email, 
            Integer number, String street, String city, String state, 
            String phone) 
            throws InvalidInformationException, FakeClientException {
        if (client == null) throw new IllegalArgumentException();
        checkFake(client);
        if (email == null && number == null && street == null && 
                city == null && state == null && phone == null) return client;
        if (email != null) validEmail(email);
        Address newAddress = null;
        if (number != null || street != null || city != null || state != null) {
            Address oldAddress = client.getAddress();
            newAddress = new Address(number, street, city, state);
            if (number == null) newAddress.setNumber(oldAddress.getNumber());
            if (street == null) newAddress.setStreet(oldAddress.getStreet());
            if (city == null) newAddress.setCity(oldAddress.getCity());
            if (state == null) newAddress.setState(oldAddress.getState());
            validAddress(newAddress);
        }
        if (phone != null) validPhone(phone);    
        if (email != null) client.setEmail(email);
        if (newAddress != null) client.setAddress(newAddress);
        if (phone != null) client.setPhone(phone);  
        clientDAO.update(client);
        return client;
    }

    @Override
    public void close(Client client) throws ClosureException, FakeClientException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getLogin(Client client) throws FakeClientException {
        if (client == null) throw new IllegalArgumentException();
        checkFake(client);
        return identifierDAO.findLogin(client.getId());
    }

    @Override
    public boolean updatePassword(Client client, String oldPassword, String newPassword) throws FakeClientException {
        checkFake(client);
        Identifier identifier = identifierDAO.find(client.getId());
        if (!identifier.getPassword().equals(oldPassword)) return false;
        identifier.setPassword(newPassword);
        identifierDAO.save(identifier);
        return true;
    }

    @Override
    public boolean resetPassword(String login, String firstname, 
            String lastname, String email) {
        Identifier identifier = identifierDAO.findPrivate(login, 
                firstname, lastname, email);
        if (identifier == null) return false;
        String password = generateRandomPassword();
        identifier.setPassword(password);
        identifierDAO.save(identifier);
        return true;    
    }

    @Override
    public boolean resetPassword(String login, String name, 
            String ownerFirstname, String ownerLastname, String email) {
        Identifier identifier = identifierDAO.findProfessional(login, name, 
                ownerFirstname, ownerLastname, email);
        if (identifier == null) return false;
        String password = generateRandomPassword();
        identifier.setPassword(password);
        identifierDAO.save(identifier);
        return true;
    }
    
    private Identifier generateNewIdentifier() {
        Set<String> logins = new HashSet<String>(identifierDAO.findAllLogin());
        String login = generateRandomLogin();
        while (logins.contains(login)) login = generateRandomLogin();
        return new Identifier(login, generateRandomPassword());
    }
    
    private String generateRandomLogin() {
        int i = random.nextInt();
        if (i < 0) i = -i;
        return String.format(LOGIN_FORMAT, i);
    }
    
    private String generateRandomPassword() {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < PASSWORD_LENGTH)
            sb.append(PASSWORD_SALT.
                    charAt((int) (random.nextFloat() * PASSWORD_SALT.length())));
        return sb.toString();
    }
    
    private void checkFake(Client client) throws FakeClientException {
        if (CHECK_FAKE_ENTITY && clientDAO.isFake(client)) 
            throw new FakeClientException();
    }
    
    private static void validEmail(String email) 
            throws InvalidInformationException {}
    
    private static void validAddress(Address address) 
            throws InvalidInformationException {}
    
    private static void validPhone(String phone) 
            throws InvalidInformationException {}
    
    private static int computeAge(Date date) {
        return Period.between(date.toInstant().atZone(ZoneId.systemDefault()).
                toLocalDate(), LocalDate.now()).getYears();
    }
    
}
