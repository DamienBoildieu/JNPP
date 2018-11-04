package jnpp.service.services;

import java.util.List;
import jnpp.service.dto.IdentityDTO;
import jnpp.service.dto.accounts.CurrencyDTO;
import jnpp.service.dto.accounts.SavingBookDTO;
import jnpp.service.dto.accounts.ShareDTO;
import jnpp.service.dto.advisor.AdvisorDTO;
import jnpp.service.dto.clients.LoginDTO;
import jnpp.service.exceptions.duplicates.DuplicateAdvisorException;
import jnpp.service.exceptions.duplicates.DuplicateSavingbookException;
import jnpp.service.exceptions.duplicates.DuplicateShareException;

public interface BankerService {

    ShareDTO addShare(String name, Double value, CurrencyDTO currency)
            throws DuplicateShareException;
    SavingBookDTO addSavingbook(String name, Double moneyRate, Double timeRate)
            throws DuplicateSavingbookException;
    List<LoginDTO> getClientLogins();
    List<AdvisorDTO> getAdvisors();
    AdvisorDTO addAdvisor(IdentityDTO.Gender gender, String firstname, 
            String lastname, String email, String phone, Integer number, 
            String street, String city, String state)
            throws DuplicateAdvisorException;
    
}
