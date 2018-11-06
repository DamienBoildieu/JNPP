package jnpp.service.dto.movements;

import java.util.Date;

public abstract class TradeDTO extends MovementDTO {

    private String ribTo;

    public TradeDTO(Date date, String ribFrom, String ribTo, String label) {
        super(date, ribFrom, label);
        this.ribTo = ribTo;
    }

    public String getRibTo() {
        return ribTo;
    }

    public void setRibTo(String ribTo) {
        this.ribTo = ribTo;
    }

}
