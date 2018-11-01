package jnpp.service.dto;

import jnpp.dao.entities.Address;
import jnpp.dao.entities.AdvisorEntity;
import jnpp.dao.entities.Identity;

public class AdvisorDTO {
    
    private final Identity identity;
    private final String email;
    private final String phone;
    private final Address officeAdress;
    
    public AdvisorDTO(AdvisorEntity advisor) {
        identity = advisor.getIdentity();
        email = advisor.getEmail();
        phone = advisor.getPhone();
        officeAdress = advisor.getOfficeAdress();
    }

    public Identity getIdentity() {
        return identity;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Address getOfficeAdress() {
        return officeAdress;
    }
    
}
