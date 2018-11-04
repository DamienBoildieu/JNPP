package jnpp.dao.entities.clients;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import jnpp.dao.entities.AddressEntity;
import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.dao.entities.advisor.AdvisorEntity;
import jnpp.service.dto.accounts.AccountDTO;
import jnpp.service.dto.clients.ClientDTO;
import jnpp.service.dto.clients.LoginDTO;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@NamedQueries({
    @NamedQuery(
        name = "find_client_by_login_password",
        query = "SELECT c FROM ClientEntity c "
                + "WHERE c.login = :login "
                + "  AND c.password = :password"),
    @NamedQuery(
        name = "find_all_login",
        query = "SELECT c.login FROM ClientEntity c"),
    @NamedQuery(
        name = "find_all_clients",
        query = "SELECT c FROM ClientEntity c")})
public abstract class ClientEntity implements Serializable {

    public static enum Type {
    
        PRIVATE,
        PROFESIONAL;
        
        public static class Values {

            public static final String PRIVATE = "PRIVATE";
            public static final String PROFESSIONAL = "PROFESSIONAL";

            private Values() {}

        }
    
    }
    
    private static final long serialVersionUID = 1L;
    
    @Id
    private String login;
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String email;
    @Embedded
    @Column(nullable = false)
    private AddressEntity address;
    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private Boolean notify;
    
    @ManyToOne
    @JoinColumn(name = "advisor_fk")
    private AdvisorEntity advisor;
    
    public ClientEntity(String login, String password, String email, Integer number, String street, String city, 
            String state, String phone, Boolean notify, AdvisorEntity advisor) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.address = new AddressEntity(number, street, city, state);
        this.phone = phone;
        this.notify = notify;
        this.advisor = advisor;
    }
    
    public ClientEntity() {}
    
    public abstract Type getType();
    
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getNotify() {
        return notify;
    }

    public void setNotify(Boolean notify) {
        this.notify = notify;
    }

    public AdvisorEntity getAdvisor() {
        return advisor;
    }

    public void setAdvisor(AdvisorEntity advisor) {
        this.advisor = advisor;
    }
    
    @Override
    public int hashCode() {
        return login != null ? login.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ClientEntity))
            return false;
        ClientEntity other = (ClientEntity) object;
        return !((this.login == null && other.login != null) 
                || (this.login != null && !this.login.equals(other.login)));
    }
    
    public abstract ClientDTO toDTO();
    
    public abstract LoginDTO toLoginDTO();
    
    public static List<LoginDTO> toLoginDTO(List<ClientEntity> entities) {
        List<LoginDTO> dtos = new ArrayList<LoginDTO>(entities.size());
        Iterator<ClientEntity> it = entities.iterator();
        while (it.hasNext()) dtos.add(it.next().toLoginDTO());
        return dtos;
    }
    
}
