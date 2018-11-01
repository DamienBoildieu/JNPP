package jnpp.service.dto.clients;

import jnpp.dao.entities.Address;
import jnpp.dao.entities.Identity;

public class ProfessionalDTO extends ClientDTO {

    private String name;
    private Identity owner;

    public ProfessionalDTO(String login, String name, Identity owner, String email, Address address, String phone) {
        super(login, email, address, phone);
        this.name = name;
        this.owner = owner;
    }
        
    @Override
    public Type getType() {
        return ClientDTO.Type.PROFESIONAL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Identity getOwner() {
        return owner;
    }

    public void setOwner(Identity owner) {
        this.owner = owner;
    }
    
}
