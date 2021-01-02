package rs.ac.uns.ftn.medDataShare.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rs.ac.uns.ftn.medDataShare.model.user.Admin;
import rs.ac.uns.ftn.medDataShare.model.user.CommonUser;
import rs.ac.uns.ftn.medDataShare.model.user.MedWorker;
import rs.ac.uns.ftn.medDataShare.model.user.User;
import rs.ac.uns.ftn.medDataShare.util.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class UserPrinciple implements UserDetails {
    private static final long serialVersionUID = 1L;

    private String id;
    private String email;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrinciple(String id,
                         String email, String password,
                         Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrinciple build(Object obj) {
        List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();

        String userRole = ((User) obj).getRole();
        auth.add(new SimpleGrantedAuthority(userRole));

        return new UserPrinciple(
                ((User) obj).getId(),
                ((User) obj).getEmail(),
                ((User) obj).getPassword(),
                auth);
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
