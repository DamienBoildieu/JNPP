package jnpp.service.dto.advisor;

import jnpp.service.dto.AbstractDTO;
import jnpp.service.dto.AddressDTO;
import jnpp.service.dto.IdentityDTO;

public class AdvisorDTO extends AbstractDTO {

    private IdentityDTO identity;
    private String email;
    private String phone;
    private AddressDTO officeAddress;

    public AdvisorDTO(IdentityDTO identity, String email, String phone,
            AddressDTO officeAddress) {
        this.identity = identity;
        this.email = email;
        this.phone = phone;
        this.officeAddress = officeAddress;
    }

    public IdentityDTO getIdentity() {
        return identity;
    }

    public void setIdentity(IdentityDTO identity) {
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

    public AddressDTO getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(AddressDTO officeAddress) {
        this.officeAddress = officeAddress;
    }

}
