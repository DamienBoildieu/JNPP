package jnpp.controller.views;

import java.util.HashMap;
import java.util.Map;
import jnpp.service.dto.IdentityDTO;
import jnpp.service.dto.accounts.AccountDTO;
import jnpp.service.dto.accounts.CurrencyDTO;
import jnpp.service.dto.paymentmeans.PaymentMeanDTO;

/**
 * Classe singleton de création de strings associées à des valeurs d'enum selon la langue
 * @author damien
 */
public class Translator {
    /**
     * Constructeur privé
     */
    private Translator() {}
    /**
     * Enum des langues
     */
    public static enum Language {
        FR;
    }
    /**
     * Propriétaire du singleton
     */
    private static class TranslatorHolder
    {   
        /**
         * L'instance de Translator
         */
        private final static Translator instance = new Translator();
    }
    /**
     * Récupère le singleton
     * @return le singleton
     */
    public static Translator getInstance()
    {
        return TranslatorHolder.instance;
    }
    /**
     * Créé la map des devises
     * @param lang la langue
     * @return la map des devises avec les strings associées
     */
    public Map<CurrencyDTO,String> translateCurrency(Language lang) {
        switch (lang) {
            case FR:    
                return currencyToFrench();
            default:
                throw new AssertionError(lang.name());
        }
    }
    /**
     * Traduit les devises en français
     * @return la map des devises avec les strings associées
     */
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
    /**
     * Créé la map des sexes
     * @param lang la langue
     * @return la map des sexes avec les strings associées
     */
    public Map<IdentityDTO.Gender,String> translateGenders(Language lang) {
        switch (lang) {
            case FR:    
                return gendersToFrench();
            default:
                throw new AssertionError(lang.name());
        }
    }
    /**
     * Traduit les sexes en français
     * @return la map des sexes avec les strings associées
     */
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
    /**
     * Créé la map des types de comptes
     * @param lang la langue
     * @return la map des types de comptes avec les strings associées
     */
    public Map<AccountDTO.Type,String> translateAccounts(Language lang) {
        switch (lang) {
            case FR:    
                return accountsToFrench();
            default:
                throw new AssertionError(lang.name());
        }
    }
    /**
     * Traduit les types de compte en français
     * @return la map des types de compte avec les strings associées
     */
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
    /**
     * Créé la map des moyens de paiement
     * @param lang la langue
     * @return la map des moyens de paiement avec les strings associées
     */
    public Map<PaymentMeanDTO.Type,String> translatePaymentMean(Language lang) {
        switch (lang) {
            case FR:    
                return paymentMeanToFrench();
            default:
                throw new AssertionError(lang.name());
        }
    }
    /**
     * Traduit les moyens de paiement en français
     * @return la map des moyens de paiement avec les strings associées
     */
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
    /**
     * Créé la map des statuts des commandes
     * @param lang la langue
     * @return la map des statuts des commandes avec les strings associées
     */
    public Map<PaymentMeanDTO.Status, String> translatePaymentMeanStatus(Language lang) {
        switch (lang) {
            case FR:    
                return paymentMeanStatusToFrench();
            default:
                throw new AssertionError(lang.name());
        } 
    }
    /**
     * Traduit les statuts des commandes en français
     * @return la map des statuts des commandes avec les strings associées
     */
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
