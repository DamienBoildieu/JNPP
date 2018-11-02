package jnpp.service.dto.clients;

import jnpp.service.dto.AddressDTO;
import jnpp.service.dto.IdentityDTO;

public class ProfessionalDTO extends ClientDTO {

    private String name;
    private IdentityDTO owner;

    public ProfessionalDTO(String login, String name, IdentityDTO owner, String email, AddressDTO address, String phone) {
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

    public IdentityDTO getOwner() {
        return owner;
    }

    public void setOwner(IdentityDTO owner) {
        this.owner = owner;
    }
    
}
