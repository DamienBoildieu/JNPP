package jnpp.service.dto.accounts;

import java.util.ArrayList;
import java.util.List;

public class ShareAccountDTO extends AccountDTO {

    private List<ShareTitleDTO> shareTitles;

    public ShareAccountDTO(String rib, List<ShareTitleDTO> shareTitles) {
        super(rib);
        this.shareTitles = new ArrayList<ShareTitleDTO>(shareTitles);
    }

    @Override
    public Type getType() {
        return AccountDTO.Type.SHARE;
    }

    public List<ShareTitleDTO> getShareTitles() {
        return shareTitles;
    }

    public void setShareTitles(List<ShareTitleDTO> shareTitles) {
        this.shareTitles = shareTitles;
    }

}
