package jnpp.controller.views;

import java.util.HashMap;
import java.util.Map;
import jnpp.service.dto.IdentityDTO;
import jnpp.service.dto.accounts.AccountDTO;
import jnpp.service.dto.accounts.CurrencyDTO;
import jnpp.service.dto.accounts.SavingBookDTO;
import jnpp.service.dto.paymentmeans.PaymentMeanDTO;


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
    
    public Map<CurrencyDTO,String> translateCurrency(Language lang) {
        switch (lang) {
            case FR:    
                return currencyToFrench();
            default:
                throw new AssertionError(lang.name());
        }
    }
    
    private Map<CurrencyDTO,String> currencyToFrench() {
        Map<CurrencyDTO,String> map = new HashMap<CurrencyDTO, String>();
        for (CurrencyDTO currency : CurrencyDTO.values()) {
            switch (currency) {
                case EURO:
                    map.put(currency, "EUR");
                    break;
                default:
                    throw new AssertionError(currency.name());               
            }
        }
        return map;
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
                    map.put(type, "Compte titres");
                    break;
                default:
                    throw new AssertionError(type.name());  
            }
        }
        return map;
    }
    
    public Map<PaymentMeanDTO.Type,String> translatePaymentMean(Language lang) {
        switch (lang) {
            case FR:    
                return paymentMeanToFrench();
            default:
                throw new AssertionError(lang.name());
        }
    }
    
    private Map<PaymentMeanDTO.Type, String> paymentMeanToFrench() {
        Map<PaymentMeanDTO.Type, String> map = new HashMap<PaymentMeanDTO.Type, String>();
        for (PaymentMeanDTO.Type type : PaymentMeanDTO.Type.values()) {
            switch (type) {
                case BANKCARD:
                    map.put(type, "carte bancaire");
                    break;
                case CHECKBOOK:
                    map.put(type, "chéquier");
                    break;
                default:
                    throw new AssertionError(type.name());                
            }
        }
        return map;
    }
    
    public Map<PaymentMeanDTO.Status, String> translatePaymentMeanStatus(Language lang) {
        switch (lang) {
            case FR:    
                return paymentMeanStatusToFrench();
            default:
                throw new AssertionError(lang.name());
        } 
    }
    
    private Map<PaymentMeanDTO.Status, String> paymentMeanStatusToFrench() {
        Map<PaymentMeanDTO.Status, String> map = new HashMap<PaymentMeanDTO.Status, String>();
        for (PaymentMeanDTO.Status status : PaymentMeanDTO.Status.values()) {
            switch (status) {
                case ORDERED:
                    map.put(status,"Commandé");
                    break;
                case ARRIVED:
                    map.put(status,"Arrivé");
                    break;
                case DELIVERED:
                    map.put(status,"Récupéré");
                    break;
                default:
                    throw new AssertionError(status.name());
}
        }
        return map;
    }
}
