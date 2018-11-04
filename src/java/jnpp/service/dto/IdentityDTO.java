package jnpp.service.dto;

public class IdentityDTO {
    
    public enum Gender {
        
        MALE,
        FEMALE;
        
    }   
   
    private IdentityDTO.Gender gender;
    private String firstname;
    private String lastname;

    public IdentityDTO(IdentityDTO.Gender gender, String firstname, String lastname) {
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
        
    @Override
    public String toString() {
        return firstname + " " + lastname;
    }
    
}
