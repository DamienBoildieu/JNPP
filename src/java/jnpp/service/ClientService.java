package jnpp.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
import jnpp.service.exceptions.clients.InvalidUpdateException;
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
    
    private Random random = new Random();
    
    @Override
    public Client signIn(String login, String password) {
        Identifier identifier = identifierDAO.find(login, password);
        return identifier.getClient();
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
    public Private signUp(Gender gender, String firstname, String lastname, 
            Date birthday, String email, Integer number, String street, 
            String city, String state, String phone) 
            throws DuplicatedClientException, BeOfAgeException, 
            InvalidInformationException {
        if (gender == null || firstname == null || lastname == null ||
                birthday == null || email == null || number == null || 
                street == null || city == null || state == null ||
                phone == null) throw new IllegalArgumentException();
        if (computeAge(birthday) < 18) throw new BeOfAgeException();
        if (!(validEmail(email) && 
                validAddress(new Address(number, street, city, state)) && 
                validPhone(phone))) throw new InvalidInformationException();
        if (clientDAO.privateExist(gender, firstname, lastname)) 
            throw new DuplicatedClientException();
        Private client = new Private(gender, firstname, lastname, birthday, 
                email, number, street, city, state, phone, false);
        client = (Private) clientDAO.save(client);
        Identifier identifier = generateNewIdentifier();
        identifier.setClient(client);
        identifierDAO.save(identifier);
        return client;
    }

    @Override
    public Professional signUp(String name, Gender ownerGender, 
            String ownerFirstname, String ownerLastname, String email, 
            Integer number, String street, String city, String state, 
            String phone) 
            throws DuplicatedClientException, InvalidInformationException {     
        if (name == null || ownerGender == null || ownerFirstname == null || 
                ownerLastname == null || email == null || number == null ||
                street == null || city == null || state == null || 
                phone == null) throw new IllegalArgumentException();      
        if (!(validEmail(email) && 
                validAddress(new Address(number, street, city, state)) && 
                validPhone(phone))) throw new InvalidInformationException();    
        if (clientDAO.professionalExist(name)) 
            throw new DuplicatedClientException();  
        Professional client = new Professional(name, ownerGender, 
                ownerFirstname, ownerLastname, email, number, street, city, 
                state, phone);
        client = (Professional) clientDAO.save(client);
        Identifier identifier = generateNewIdentifier();
        identifier.setClient(client);
        identifierDAO.save(identifier);
        return client;
    }

    @Override
    public Private update(Private client, Private information) 
            throws DuplicatedClientException, InvalidInformationException, 
            InvalidUpdateException, FakeClientException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Professional update(Professional client, Professional information) 
            throws DuplicatedClientException, InvalidInformationException, 
            InvalidUpdateException, FakeClientException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close(Client client) 
            throws ClosureException, FakeClientException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getLogin(Client client) throws FakeClientException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean updatePassword(Client client, String oldPassword, 
            String newPassword) throws FakeClientException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean resetPassword(String login, String firstname, 
            String lastname, String email) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean resetPassword(String login, String name, 
            String ownerFirstname, String ownerLastname, String email) {
        throw new UnsupportedOperationException();
    }
    
    private Identifier generateNewIdentifier() {
        List<String> logins = identifierDAO.findAllLogin();
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
    
    private static boolean validEmail(String email) {
        return true;
    }
    
    private static boolean validAddress(Address address) {
        return true;
    }
    
    private static boolean validPhone(String phone) {
        return true;
    }
    
    private static int computeAge(Date date) {
        return Period.between(date.toInstant().atZone(ZoneId.systemDefault()).
                toLocalDate(), LocalDate.now()).getYears();
    }
    
}
