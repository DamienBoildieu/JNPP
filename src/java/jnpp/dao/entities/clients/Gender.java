package jnpp.dao.entities.clients;

public enum Gender {
    
    MALE("Homme"),
    FEMALE("Femme");
    
    private final String french;
    
    Gender(String french) {
        this.french = french;
    }

    public String getFrench() {
        return french;
    }
}
