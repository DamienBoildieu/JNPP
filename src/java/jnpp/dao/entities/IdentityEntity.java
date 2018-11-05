package jnpp.dao.entities;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import jnpp.service.dto.IdentityDTO;

@Embeddable
public class IdentityEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public enum Gender {
        
        MALE,
        FEMALE;
        
        public IdentityDTO.Gender toDTO() {
            switch (ordinal()) {
                case 0:
                    return IdentityDTO.Gender.MALE;
                case 1:
                    return IdentityDTO.Gender.FEMALE;
                default:
                    return null;
            }
        }
        
        public static Gender toEntity(IdentityDTO.Gender gender) {
            switch (gender) {
                case FEMALE:
                    return FEMALE;
                case MALE:
                    return MALE;
                default:
                    return null;
            }
        }
        
    }    
    
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String firstname;
    private String lastname;

    public IdentityEntity() {
        this(null, null, null);
    }
    
    public IdentityEntity(Gender gender, String firstname, String lastname) {
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
    
    public IdentityDTO toDTO() {
        return new IdentityDTO(gender.toDTO(), firstname, lastname);
    }
    
    @Override
    public String toString() {
        return firstname + " " + lastname;
    }
 
    public static IdentityEntity toEntity(IdentityDTO dto) {
        return new IdentityEntity(Gender.toEntity(dto.getGender()), 
                dto.getFirstname(), dto.getLastname());
    }
    
}
