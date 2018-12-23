package jnpp.service.dto.accounts;

public class ShareDTO {

    private String name;
    private Double value;
    private CurrencyDTO currency;

    public ShareDTO(String name, Double value, CurrencyDTO currency) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public CurrencyDTO getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyDTO currency) {
        this.currency = currency;
    }

}
