package jnpp.dao.entities.clients;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Address implements Serializable {

    private Integer number;
    private String street;
    private String city;
    private String state;

    public Address(Integer number, String street, String city, String state) {
        this.number = number;
        this.street = street;
        this.city = city;
        this.state = state;
    }
    
    public Address() {
        this(null, null, null, null);
    }
    
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
}
