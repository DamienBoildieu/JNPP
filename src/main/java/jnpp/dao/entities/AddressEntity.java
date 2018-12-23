package jnpp.dao.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

import jnpp.service.dto.AddressDTO;

@Embeddable
public class AddressEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer number;
    private String street;
    private String city;
    private String state;

    public AddressEntity(Integer number, String street, String city,
            String state) {
        this.number = number;
        this.street = street;
        this.city = city;
        this.state = state;
    }

    public AddressEntity() {
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

    public AddressDTO toDTO() {
        return new AddressDTO(number, street, city, state);
    }

}
