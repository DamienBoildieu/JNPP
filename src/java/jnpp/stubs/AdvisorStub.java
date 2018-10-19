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
public class AdvisorStub {
    private String firstName;
    private String lastName;
    private String agency;
    private String phone;
    
    public AdvisorStub(String fName, String lName, String agen, String pho) {
        this.firstName = fName;
        this.lastName = lName;
        this.agency = agen;
        this.phone = pho;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
