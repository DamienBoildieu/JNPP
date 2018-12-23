package jnpp.service.dto.movements;

import java.util.Date;

import jnpp.service.dto.accounts.ShareDTO;

public class SaleDTO extends ShareTradeDTO {

    public SaleDTO(Date date, String ribFrom, String ribTo, Integer amount,
            ShareDTO share, String label) {
        super(date, ribFrom, ribTo, amount, share, label);
    }

    @Override
    public MovementDTO.Type getType() {
        return MovementDTO.Type.SALE;
    }

}
