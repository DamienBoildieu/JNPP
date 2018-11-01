package jnpp.service.dto.advisor;

import jnpp.dao.entities.Address;
import jnpp.dao.entities.advisor.AdvisorEntity;
import jnpp.dao.entities.Identity;

public class AdvisorDTO {
    
    private Identity identity;
    private String email;
    private String phone;
    private Address officeAdress;
    
    public AdvisorDTO(Identity identity, String email, String phone, Address officeAdress) {
        this.identity = identity;
        this.email = email;
        this.phone = phone;
        this.officeAdress = officeAdress;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
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

    public Address getOfficeAdress() {
        return officeAdress;
    }

    public void setOfficeAdress(Address officeAdress) {
        this.officeAdress = officeAdress;
    }
    
}
