package jnpp.service.dto.clients;

import jnpp.dao.entities.clients.Address;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.clients.PrivateEntity;
import jnpp.dao.entities.clients.ProfessionalEntity;

public abstract class ClientDTO {
    
    public static enum Type {
    
        PRIVATE,
        PROFESIONAL;
        
    }
    
    private final String login;
    private final String email;
    private final Address address;
    private final String phone;
    
    public ClientDTO(ClientEntity client) {
        login = client.getLogin();
        email = client.getEmail();
        address = client.getAddress();
        phone = client.getPhone();
    }
    
    public abstract Type getType();

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
        
    public static ClientDTO newDTO(ClientEntity client) {
        switch (client.getType()) {
            case PRIVATE:
                return new PrivateDTO((PrivateEntity) client);
            case PROFESIONAL:
                return new ProfessionalDTO((ProfessionalEntity) client);
        }
        return null;
    }
    
}
