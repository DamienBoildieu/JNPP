package jnpp.service.dto.movements;

import java.util.Date;

import jnpp.service.dto.accounts.ShareDTO;

public class PurchaseDTO extends ShareTradeDTO {

    public PurchaseDTO(Date date, String ribFrom, String ribTo, Integer amount,
            ShareDTO share, String label) {
        super(date, ribFrom, ribTo, amount, share, label);
    }

    @Override
    public MovementDTO.Type getType() {
        return MovementDTO.Type.PURCHASE;
    }

}
