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
    private String label;

    public MovementDTO(Date date, String ribFrom, String label) {
        this.date = date;
        this.ribFrom = ribFrom;
        this.label = label;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
