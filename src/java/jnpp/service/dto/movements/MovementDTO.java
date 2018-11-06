package jnpp.service.dto.movements;

import java.util.Date;

public abstract class MovementDTO {

    public static enum Type {

        TRANSFERT,
        DEBIT,
        PURCHASE,
        SALE,
        WITHDRAW,
        PAYMENT,
        DEPOSIT;

    }

    private Date date;
    private String ribFrom;

    public MovementDTO(Date date, String ribFrom) {
        this.date = date;
        this.ribFrom = ribFrom;
    }

    public abstract Type getType();

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRibFrom() {
        return ribFrom;
    }

    public void setRibFrom(String ribFrom) {
        this.ribFrom = ribFrom;
    }
}
