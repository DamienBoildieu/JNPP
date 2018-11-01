package jnpp.service.dto.clients;

import jnpp.dao.entities.Address;

public abstract class ClientDTO {
    
    public static enum Type {
    
        PRIVATE,
        PROFESIONAL;
        
    }
    
    private String login;
    private String email;
    private Address address;
    private String phone;

    public ClientDTO(String login, String email, Address address, String phone) {
        this.login = login;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }
             
    public abstract Type getType();

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
}
