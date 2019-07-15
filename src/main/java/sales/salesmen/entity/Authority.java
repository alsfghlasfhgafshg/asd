package sales.salesmen.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public Authority(Long id, String name, String authorities) {
        this.id = id;
        this.name = name;
        this.authorities = authorities;
    }

    public Authority(String name, String authorities) {
        this.name = name;
        this.authorities = authorities;
    }

    public Authority() {
    }

    @Column(nullable = false)
    private String authorities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getAuthority() {
        return authorities;
    }
}
