package iu.iu.spring_app.api.users.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "iuser", schema = "public")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(nullable = false, unique = true)
    @JsonIgnore
    private String email;

    @Column(name ="username", nullable = false, unique = true)
    private String name;

    @Column(name = "password_hash", nullable = false)
    @JsonIgnore
    private String password;

    @ManyToOne
    @JoinColumn(name = "avatar_id", referencedColumnName = "avatar_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Avatar avatar;

    @Column(nullable = false)
    private Boolean admin = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = admin ? "ROLE_ADMIN" : "ROLE_USER";
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}