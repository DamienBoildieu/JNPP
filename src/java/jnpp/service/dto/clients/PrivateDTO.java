package jnpp.service.dto.clients;

import java.util.Date;
import jnpp.dao.entities.Address;
import jnpp.dao.entities.Identity;
import jnpp.dao.entities.clients.PrivateEntity;

public class PrivateDTO extends ClientDTO {
    
    private Identity identity;
    private Date birthday;

    public PrivateDTO(String login, Identity identity, Date birthday, String email, Address address, String phone) {
        super(login, email, address, phone);
        this.identity = identity;
        this.birthday = birthday;
    }
    
    @Override
    public Type getType() {
        return ClientDTO.Type.PRIVATE;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
}
