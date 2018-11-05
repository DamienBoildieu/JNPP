package jnpp.controller.views;

import java.util.HashMap;
import java.util.Map;
import jnpp.service.dto.IdentityDTO;
import jnpp.service.dto.accounts.AccountDTO;
import jnpp.service.dto.accounts.SavingBookDTO;


public class Translator {
    private Translator() {}
    
    public static enum Language {
        FR;
    }
    
    private static class TranslatorHolder
    {       
        private final static Translator instance = new Translator();
    }
 
    public static Translator getInstance()
    {
        return TranslatorHolder.instance;
    }
    
    public Map<IdentityDTO.Gender,String> translateGenders(Language lang) {
        switch (lang) {
            case FR:    
                return gendersToFrench();
            default:
                throw new AssertionError(lang.name());
        }
    }
    
    private Map<IdentityDTO.Gender, String> gendersToFrench() {
        Map<IdentityDTO.Gender, String> map = new HashMap<IdentityDTO.Gender, String>();
        for (IdentityDTO.Gender gender : IdentityDTO.Gender.values()) {
            switch (gender) {
                case MALE:
                    map.put(gender, "Homme");
                    break;
                case FEMALE:
                    map.put(gender, "Femme");
                    break;
                default:
                    throw new AssertionError(gender.name());
            }
        }
        return map;
    }
    
    public Map<AccountDTO.Type,String> translateAccounts(Language lang) {
        switch (lang) {
            case FR:    
                return accountsToFrench();
            default:
                throw new AssertionError(lang.name());
        }
    }
    
    private Map<AccountDTO.Type, String> accountsToFrench() {
        Map<AccountDTO.Type, String> map = new HashMap<AccountDTO.Type, String>();
        for (AccountDTO.Type type : AccountDTO.Type.values()) {
            switch (type) {
                case CURRENT:
                    map.put(type, "Compte courrant");
                    break;
                case JOINT:
                    map.put(type, "Compte joint");
                    break;
                case SAVING:
                    map.put(type, "Compte dépôt");
                    break;
                case SHARE:
                    map.put(type, "Compte titre");
                    break;
                default:
                    throw new AssertionError(type.name());  
            }
        }
        return map;
    }
}
