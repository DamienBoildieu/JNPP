package jnpp.service.dto.advisor;

import jnpp.dao.entities.AddressEntity;
import jnpp.dao.entities.IdentityEntity;

public class AdvisorDTO {
    
    private IdentityEntity identity;
    private String email;
    private String phone;
    private AddressEntity officeAdress;
    
    public AdvisorDTO(IdentityEntity identity, String email, String phone, AddressEntity officeAdress) {
        this.identity = identity;
        this.email = email;
        this.phone = phone;
        this.officeAdress = officeAdress;
    }

    public IdentityEntity getIdentity() {
        return identity;
    }

    public void setIdentity(IdentityEntity identity) {
        this.identity = identity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AddressEntity getOfficeAdress() {
        return officeAdress;
    }

    public void setOfficeAdress(AddressEntity officeAdress) {
        this.officeAdress = officeAdress;
    }
    
}
