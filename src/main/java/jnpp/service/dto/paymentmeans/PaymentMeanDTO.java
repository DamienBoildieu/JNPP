package jnpp.service.dto.paymentmeans;

public abstract class PaymentMeanDTO {

    public static enum Type {

        BANKCARD, CHECKBOOK;

    }

    public static enum Status {

        ORDERED, ARRIVED, DELIVERED;

        public Status next() {
            Status[] status = values();
            return status[(ordinal() + 1) % status.length];
        }

    }

    private String id;
    private String login;
    private String rib;
    private Status status;

    public PaymentMeanDTO(String id, String login, String rib, Status status) {
        this.id = id;
        this.login = login;
        this.rib = rib;
        this.status = status;
    }

    public abstract Type getType();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
