package jnpp.service.dto.accounts;

public class CurrentAccountDTO extends MoneyAccountDTO {

    private Double limit;

    public CurrentAccountDTO(String rib, Double money, CurrencyDTO currency,
            Double limit) {
        super(rib, money, currency);
        this.limit = limit;
    }

    @Override
    public Type getType() {
        return AccountDTO.Type.CURRENT;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }

}
