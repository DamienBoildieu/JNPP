package jnpp.service.dto.movements;

import java.util.Date;

public abstract class TradeDTO extends MovementDTO {

    private String ribTo;

    public TradeDTO(Date date, String ribFrom, String ribTo) {
        super(date, ribFrom);
        this.ribTo = ribTo;
    }

    public String getRibTo() {
        return ribTo;
    }

    public void setRibTo(String ribTo) {
        this.ribTo = ribTo;
    }

}
