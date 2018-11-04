package jnpp.service.services.banker;

import javax.annotation.Resource;
import jnpp.dao.entities.accounts.CurrencyEntity;
import jnpp.dao.entities.accounts.SavingBookEntity;
import jnpp.dao.entities.accounts.ShareEntity;
import jnpp.dao.repositories.SavingBookDAO;
import jnpp.dao.repositories.ShareDAO;
import jnpp.service.dto.accounts.CurrencyDTO;
import jnpp.service.dto.accounts.SavingBookDTO;
import jnpp.service.dto.accounts.ShareDTO;
import jnpp.service.exceptions.duplicates.DuplicateSavingbookException;
import jnpp.service.exceptions.duplicates.DuplicateShareException;
import org.springframework.stereotype.Service;

@Service("BankerService")
public class BankerServiceImpl implements BankerService {

    @Resource
    ShareDAO shareDAO;
    @Resource
    SavingBookDAO savingBookDAO;
    
    @Override
    public ShareDTO addShare(String name, Double value, CurrencyDTO currency) 
            throws DuplicateShareException {
        if (name == null || value == null || value <= 0 || currency == null)
            throw new IllegalArgumentException();
        ShareEntity share = shareDAO.findByName(name);
        if (share != null) throw new DuplicateShareException();
        share = new ShareEntity(name, value, CurrencyEntity.toEntity(currency));
        shareDAO.save(share);
        return share.toDTO();
    }
    
    @Override
    public SavingBookDTO addSavingbook(String name, Double moneyRate, Double timeRate)
            throws DuplicateSavingbookException {
        if (name == null || moneyRate == null || moneyRate <= 0 
                || timeRate == null || timeRate <= 0)
            throw new IllegalArgumentException();
        
        SavingBookEntity savingbook = savingBookDAO.findByName(name);
        if (savingbook != null) throw new DuplicateSavingbookException();
        savingbook = new SavingBookEntity(name, moneyRate, timeRate);
        savingBookDAO.save(savingbook);
        return savingbook.toDTO();
    }
    
}
