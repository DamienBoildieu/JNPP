package jnpp.dao.entities;

import jnpp.dao.entities.Gender;
import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Identity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String firstname;
    private String lastname;

    public Identity() {
        this(null, null, null);
    }
    
    public Identity(Gender gender, String firstname, String lastname) {
        this.gender = gender;
        this.firstname = firstname;
        this.lastname = lastname;
    }
    
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
}
