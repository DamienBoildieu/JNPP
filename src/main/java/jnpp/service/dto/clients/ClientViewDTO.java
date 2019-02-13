/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jnpp.service.dto.clients;

import jnpp.service.dto.AbstractDTO;
import jnpp.service.dto.IdentityDTO;

public class ClientViewDTO extends AbstractDTO {
    private final ClientDTO.Type type;
    private final String login;
    private final String name;
    
    public ClientViewDTO(PrivateDTO client) {
        this.type = client.getType();
        this.login = client.getLogin();
        IdentityDTO identity = client.getIdentity();
        this.name = identity.getFirstname() + " " + identity.getLastname();
    }
    
    public ClientViewDTO(ProfessionalDTO client) {
        this.type = client.getType();
        this.login = client.getLogin();
        this.name = client.getName();
    }
    
    public ClientDTO.Type getType() {
        return this.type;
    }
    
    public String getLogin() {
        return this.login;
    }
    
    public String getName() {
        return this.name;
    }
}
