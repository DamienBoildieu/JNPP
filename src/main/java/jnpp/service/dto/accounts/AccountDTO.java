package jnpp.service.dto.accounts;

import jnpp.service.dto.AbstractDTO;

public abstract class AccountDTO extends AbstractDTO {

    public static enum Type {

        CURRENT, JOINT, SAVING, SHARE;

    }

    private String rib;

    public AccountDTO(String rib) {
        this.rib = rib;
    }

    public abstract Type getType();

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

}
