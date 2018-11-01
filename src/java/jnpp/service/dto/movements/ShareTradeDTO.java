package jnpp.service.dto.movements;

import jnpp.dao.entities.movements.ShareTradeEntity;
import jnpp.service.dto.accounts.ShareDTO;

public abstract class ShareTradeDTO extends TradeDTO {
    
    private final Integer amount;
    private final ShareDTO share;
    
    public ShareTradeDTO(ShareTradeEntity movement) {
        super(movement);
        amount = movement.getAmount();
        share = new ShareDTO((movement.getShare()));
    }

    public Integer getAmount() {
        return amount;
    }

    public ShareDTO getShare() {
        return share;
    }

}
