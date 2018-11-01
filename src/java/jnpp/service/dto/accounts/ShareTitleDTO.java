package jnpp.service.dto.accounts;

import jnpp.dao.entities.accounts.ShareTitleEntity;

public class ShareTitleDTO {
    
    private final Integer amount;
    private final ShareDTO share;
    
    public ShareTitleDTO(ShareTitleEntity shareTitle) {
        amount = shareTitle.getAmount();
        share = new ShareDTO(shareTitle.getShare());
    }

    public Integer getAmount() {
        return amount;
    }

    public ShareDTO getShare() {
        return share;
    }
    
}
