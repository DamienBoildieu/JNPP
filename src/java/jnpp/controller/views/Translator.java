package jnpp.controller.views;

import java.util.HashMap;
import java.util.Map;
import jnpp.dao.entities.Gender;
import jnpp.dao.entities.accounts.AccountEntity;


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
    
    public Map<Gender,String> translateGenders(Language lang) {
        switch (lang) {
            case FR:    
                return gendersToFrench();
            default:
                throw new AssertionError(lang.name());
        }
    }
    
    private Map<Gender, String> gendersToFrench() {
        Map<Gender, String> map = new HashMap<Gender, String>();
        for (Gender gender : Gender.values()) {
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
    
    public Map<AccountEntity.Type,String> translateAccounts(Language lang) {
        switch (lang) {
            case FR:    
                return accountsToFrench();
            default:
                throw new AssertionError(lang.name());
        }
    }
    
    private Map<AccountEntity.Type, String> accountsToFrench() {
        Map<AccountEntity.Type, String> map = new HashMap<AccountEntity.Type, String>();
        for (AccountEntity.Type type : AccountEntity.Type.values()) {
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
