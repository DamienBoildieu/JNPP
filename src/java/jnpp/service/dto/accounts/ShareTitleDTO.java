package jnpp.service.dto.accounts;

public class ShareTitleDTO {
    
    private Integer amount;
    private ShareDTO share;
    
    public ShareTitleDTO(Integer amount, ShareDTO share) {
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
