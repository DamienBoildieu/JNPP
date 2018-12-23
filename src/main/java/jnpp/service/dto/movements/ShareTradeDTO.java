package jnpp.service.dto.movements;

import java.util.Date;

import jnpp.service.dto.accounts.ShareDTO;

public abstract class ShareTradeDTO extends TradeDTO {

    private Integer amount;
    private ShareDTO share;

    public ShareTradeDTO(Date date, String ribFrom, String ribTo,
            Integer amount, ShareDTO share, String label) {
        super(date, ribFrom, ribTo, label);
        this.amount = amount;
        this.share = share;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public ShareDTO getShare() {
        return share;
    }

    public void setShare(ShareDTO share) {
        this.share = share;
    }

}
