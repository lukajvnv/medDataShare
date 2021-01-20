package rs.ac.uns.ftn.medDataShare.security.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class JwtResponse {
    private String token;
    private String username;
	private Collection<? extends GrantedAuthority> roles;
	private Long user_id;

	public JwtResponse(String accessToken, String username, Collection<? extends GrantedAuthority> authorities) {
		this.token = accessToken;
		this.username = username;
		this.roles = authorities;
	}

}
