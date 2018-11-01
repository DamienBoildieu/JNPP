package jnpp.service.dto.clients;

import jnpp.dao.entities.Identity;
import jnpp.dao.entities.clients.ProfessionalEntity;

public class ProfessionalDTO extends ClientDTO {

    private final String name;
    private final Identity owner;
    
    public ProfessionalDTO(ProfessionalEntity client) {
        super(client);
        name = client.getName();
        owner = client.getOwner();
    }
    
    @Override
    public Type getType() {
        return ClientDTO.Type.PROFESIONAL;
    }

    public String getName() {
        return name;
    }

    public Identity getOwner() {
        return owner;
    }
    
}
