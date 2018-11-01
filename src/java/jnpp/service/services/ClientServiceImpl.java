package jnpp.service.services;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import jnpp.dao.entities.clients.Address;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.clients.Gender;
import jnpp.dao.entities.clients.Identity;
import jnpp.dao.entities.clients.PrivateEntity;
import jnpp.dao.entities.clients.ProfessionalEntity;
import jnpp.service.exceptions.ClosureException;
import jnpp.service.exceptions.clients.AgeException;
import jnpp.service.exceptions.duplicates.DuplicateClientException;
import jnpp.service.exceptions.clients.InformationException;
import jnpp.service.exceptions.entities.FakeClientException;

import org.springframework.stereotype.Service;
import jnpp.dao.repositories.AccountDAO;
import jnpp.dao.repositories.ClientDAO;
import jnpp.service.dto.clients.ClientDTO;

@Service("ClientService")
public class ClientServiceImpl implements ClientService {

    public static final int OF_AGE = 18;
    public static boolean DEFAULT_NOTIFY = true;    
    
    public static final int LOGIN_LENGTH = 8;
    private static final int LOGIN_MIN = (int) Math.pow(10, LOGIN_LENGTH - 1);
    private static final int LOGIN_RANGE = 9 * LOGIN_MIN;
    
    public static final int PASSWORD_LENGTH = 8;
    private static final String PASSWORD_SALT = 
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    
    @Resource
    ClientDAO clientDAO;
    @Resource
    AccountDAO accountDAO;
    
    private final Random random = new Random();
    
    @Override
    public ClientDTO signIn(String login, String password) {
        if (login == null || password == null) throw new IllegalArgumentException();
        return ClientDTO.newDTO(clientDAO.findByLoginPassword(login, password));
    }

    @Override
    public void signOut(String login) 
            throws FakeClientException {
        if (login == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
    }

    @Override
    public void signUp(Gender gender, String firstname, String lastname, 
            Date birthday, String email, Integer number, String street, 
            String city, String state, String phone) 
            throws DuplicateClientException, AgeException, 
            InformationException {
        if (gender == null || firstname == null || lastname == null || birthday == null || email == null || number == null || 
                street == null || city == null || state == null || phone == null) throw new IllegalArgumentException();
        if (!isOfAge(birthday)) throw new AgeException();      
        if (!isEmailValid(email) || !isAddressValid(number, street, city, state) || !isPhoneValid(phone)) throw new InformationException();
        if (clientDAO.findPrivateByIdentity(gender, firstname, lastname) != null) throw new DuplicateClientException();
        String login = generateNewLogin();
        String password = generateRandomLogin();
        PrivateEntity client = new PrivateEntity(login, password, gender, firstname, lastname, birthday, email, number, street, city, state, phone, DEFAULT_NOTIFY);
        clientDAO.save(client);
    }

    @Override
    public void signUp(String name, Gender ownerGender, 
            String ownerFirstname, String ownerLastname, String email, 
            Integer number, String street, String city, String state, 
            String phone) 
            throws DuplicateClientException, InformationException {     
        if (name == null || ownerGender == null || ownerFirstname == null || 
                ownerLastname == null || email == null || number == null ||
                street == null || city == null || state == null || 
                phone == null) throw new IllegalArgumentException();      
        if (!isEmailValid(email) || !isAddressValid(number, street, city, state) || !isPhoneValid(phone)) throw new InformationException();
        if (clientDAO.findProfessionalByNameIdentity(name, ownerGender, ownerFirstname, ownerLastname) != null) 
            throw new DuplicateClientException();  
        String login = generateNewLogin();
        String password = generateRandomLogin();
        ProfessionalEntity client = new ProfessionalEntity(login, password, name, ownerGender, 
                ownerFirstname, ownerLastname, email, number, street, city, 
                state, phone, DEFAULT_NOTIFY);
        clientDAO.save(client);
    }

    @Override
    public ClientDTO update(String login, String email, 
            Integer number, String street, String city, String state, 
            String phone) 
            throws InformationException, FakeClientException {
        if (login == null || (email == null && number == null && street == null && 
                city == null && state == null && phone == null)) throw new IllegalArgumentException(); 
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        if (!isEmailValid(email) || !isAddressValid(number, street, city, state) || !isPhoneValid(phone)) throw new InformationException();  
        if (email != null) client.setEmail(email);
        Address address = client.getAddress();
        if (number != null) address.setNumber(number);
        if (street != null) address.setStreet(street);
        if (city != null) address.setCity(city);
        if (state != null) address.setState(state);
        if (phone != null) client.setPhone(phone);    
        clientDAO.save(client);
        return ClientDTO.newDTO(client);
    }

    @Override
    public void close(String login, String password) throws ClosureException, FakeClientException {
       if (login == null || password == null) throw new IllegalArgumentException();
       ClientEntity client = clientDAO.findByLoginPassword(login, password);
       if (client == null) throw new FakeClientException();
       if (accountDAO.hasAccount(login)) throw new ClosureException();
       clientDAO.delete(client);
    }


    @Override
    public boolean updatePassword(String login, String oldPassword, String newPassword) throws FakeClientException {
        if (login == null || oldPassword == null || newPassword == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        if (!oldPassword.equals(client.getPassword())) return false;
        client.setPassword(newPassword);
        clientDAO.save(client);
        return true;
    }

    @Override
    public boolean resetPassword(String login, Gender gender, String firstname, 
            String lastname, String email) throws FakeClientException {
        if (login == null || gender == null || firstname == null  
                || lastname == null || email == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        if (!email.equals(client.getEmail())) return false;
        if (client.getType() != ClientEntity.Type.PRIVATE) return false;
        Identity identity = ((PrivateEntity) client).getIdentity();
        if (identity == null) return false;
        if (!gender.equals(identity.getGender()) || !firstname.equals(identity.getFirstname()) 
                || !lastname.equals(identity.getLastname())) return false;
        String password = generateRandomPassword();
        client.setPassword(password);
        clientDAO.save(client);
        return true;    
    }

    @Override
    public boolean resetPassword(String login, String name, Gender ownerGender, 
            String ownerFirstname, String ownerLastname, String email) throws FakeClientException {
        if (login == null || name == null || ownerGender == null 
                || ownerFirstname == null || ownerLastname == null) throw new IllegalArgumentException();
        ClientEntity client = clientDAO.find(login);
        if (client == null) throw new FakeClientException();
        if (!email.equals(client.getEmail())) return false;
        if (client.getType() != ClientEntity.Type.PROFESIONAL) return false;
        Identity identity = ((ProfessionalEntity) client).getOwner();
        if (identity == null) return false;
        if (!ownerGender.equals(identity.getGender()) || !ownerFirstname.equals(identity.getFirstname()) 
                || !ownerLastname.equals(identity.getLastname())) return false;
        if (!name.equals(((ProfessionalEntity) client).getName())) return false;
        String password = generateRandomPassword();
        client.setPassword(password);
        clientDAO.save(client);
        return true;    
    }
    
    private String generateNewLogin() {
        Set<String> logins = new HashSet<String>(clientDAO.findAllLogin());
        String login = generateRandomLogin();
        while (logins.contains(login)) login = generateRandomLogin();
        return login;
    }
    
    private String generateRandomLogin() {
        return "" + (LOGIN_MIN + random.nextInt(LOGIN_RANGE));
    }
    
    private String generateRandomPassword() {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < PASSWORD_LENGTH)
            sb.append(PASSWORD_SALT.
                    charAt((int) (random.nextFloat() * PASSWORD_SALT.length())));
        return sb.toString();
    }
    
    private static boolean isEmailValid(String email) {
        return true;
    }
    
    private static boolean isAddressValid(Integer number, String street, String city, String state) {
        return true;
    }
    
    private static boolean isPhoneValid(String phone) {
        return true;
    }
    
    private static boolean isOfAge(Date birthday) {
        return Period.between(birthday.toInstant().atZone(ZoneId.systemDefault()).
                toLocalDate(), LocalDate.now()).getYears() >= OF_AGE;
    }
    
}
