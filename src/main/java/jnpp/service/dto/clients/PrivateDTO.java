package jnpp.service.dto.clients;

import java.util.Date;

import jnpp.service.dto.AddressDTO;
import jnpp.service.dto.IdentityDTO;

public class PrivateDTO extends ClientDTO {

    private IdentityDTO identity;
    private Date birthday;

    public PrivateDTO(String login, IdentityDTO identity, Date birthday,
            String email, AddressDTO address, String phone) {
        super(login, email, address, phone);
        this.identity = identity;
        this.birthday = birthday;
    }

    @Override
    public Type getType() {
        return ClientDTO.Type.PRIVATE;
    }

    @Override
    public String toViewJson() {
        return new ClientViewDTO(this).toJson();
    }
    public IdentityDTO getIdentity() {
        return identity;
    }

    public void setIdentity(IdentityDTO identity) {
        this.identity = identity;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

}
