package jnpp.service.dto.clients;

import java.util.Date;
import jnpp.dao.entities.Identity;
import jnpp.dao.entities.clients.PrivateEntity;

public class PrivateDTO extends ClientDTO {
    
    private final Identity identity;
    private final Date birthday;
    
    public PrivateDTO(PrivateEntity client) {
        super(client);
        identity = client.getIdentity();
        birthday = client.getBirthday();
    }
    
    @Override
    public Type getType() {
        return ClientDTO.Type.PRIVATE;
    }

    public Identity getIdentity() {
        return identity;
    }

    public Date getBirthday() {
        return birthday;
    }
    
}
