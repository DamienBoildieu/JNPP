package jnpp.service.dto;

public class AddressDTO extends AbstractDTO {

    private Integer number;
    private String street;
    private String city;
    private String state;

    public AddressDTO(Integer number, String street, String city,
            String state) {
        this.number = number;
        this.street = street;
        this.city = city;
        this.state = state;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return number + " " + street + ", " + city + ", " + state;
    }

}
