package jnpp.service.dto.accounts;

public abstract class MoneyAccountDTO extends AccountDTO {

    private Double money;
    private CurrencyDTO currency;

    public MoneyAccountDTO(String rib, Double money, CurrencyDTO currency) {
        super(rib);
        this.money = money;
        this.currency = currency;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public CurrencyDTO getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyDTO currency) {
        this.currency = currency;
    }

}
