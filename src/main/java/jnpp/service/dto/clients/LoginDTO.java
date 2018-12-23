package jnpp.service.dto.clients;

public class LoginDTO {

    private String login;
    private String password;
    private ClientDTO.Type type;
    private String name;
    private String email;

    public LoginDTO(String login, String password, ClientDTO.Type type, String name, String email) {
        this.login = login;
        this.password = password;
        this.type = type;
        this.name = name;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClientDTO.Type getType() {
        return type;
    }

    public void setType(ClientDTO.Type type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
