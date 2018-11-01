package jnpp.service.dto.accounts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jnpp.dao.entities.accounts.ShareAccountEntity;
import jnpp.dao.entities.accounts.ShareTitleEntity;

public class ShareAccountDTO extends AccountDTO {
    
    private final List<ShareTitleDTO> shareTitles;
    
    public ShareAccountDTO(ShareAccountEntity account) {
        super(account);
        List<ShareTitleEntity> entities = account.getShareTitles();
        shareTitles = new ArrayList<ShareTitleDTO>(entities.size());
        Iterator<ShareTitleEntity> it = entities.iterator();
        while (it.hasNext()) shareTitles.add(new ShareTitleDTO(it.next()));
    }

    @Override
    public Type getType() {
        return AccountDTO.Type.SHARE;
    }
    
    public List<ShareTitleDTO> getShareTitles() {
        return shareTitles;
    }
    
}
