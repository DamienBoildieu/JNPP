package jnpp.service.dto.accounts;

import jnpp.service.dto.AbstractDTO;

public class DebitAuthorizationDTO extends AbstractDTO {

    private String ribFrom;
    private String ribTo;

    public DebitAuthorizationDTO(String ribFrom, String ribTo) {
        this.ribFrom = ribFrom;
        this.ribTo = ribTo;
    }

    public String getRibFrom() {
        return ribFrom;
    }

    public void setRibFrom(String ribFrom) {
        this.ribFrom = ribFrom;
    }

    public String getRibTo() {
        return ribTo;
    }

    public void setRibTo(String ribTo) {
        this.ribTo = ribTo;
    }

}
