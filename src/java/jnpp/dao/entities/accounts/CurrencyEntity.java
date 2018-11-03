package jnpp.dao.entities.accounts;

import jnpp.service.dto.accounts.CurrencyDTO;

public enum CurrencyEntity {
    
    EURO;
    
    public CurrencyDTO toDTO() {
        return CurrencyDTO.EURO;
    }
    
    public static CurrencyEntity toEntity(CurrencyDTO dto) {
        switch (dto) {
            case EURO:
                return EURO;
            default:
                return null;
        }
    }
    
    private static double[][] changes = null;
    
    static {
        int n = CurrencyEntity.values().length;
        changes = new double[n][n];
        for (int i = 0; i < n; ++i) {
            changes[i][i] = 1;
            for (int j = i + 1; i < n; ++j) {
                changes[i][j] = 1;
                changes[j][i] = 1;
            }        
        }
    }  
    
    public double convert(double amount, CurrencyEntity currency) {
        return amount * changes[currency.ordinal()][ordinal()];
    }
    
}
