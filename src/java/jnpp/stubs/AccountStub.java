/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jnpp.stubs;

/**
 *
 * @author damien
 */
public class AccountStub {
    private String ref;
    private String type;
    private long amount;
    
    public AccountStub(String reference, String accountType, long accountAmount) {
        this.ref = reference;
        this.type = accountType;
        this.amount = accountAmount;
    }
    
    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
