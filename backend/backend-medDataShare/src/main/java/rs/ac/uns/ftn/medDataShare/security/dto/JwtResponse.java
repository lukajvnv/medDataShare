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

	public JwtResponse(String accessToken, String username, Collection<? extends GrantedAuthority> authorities, Long user_id) {
		this.token = accessToken;
		this.username = username;
		this.roles = authorities;
		this.user_id = user_id;
	}

	public JwtResponse(String accessToken, String username, Collection<? extends GrantedAuthority> authorities, String user_id) {
		this.token = accessToken;
		this.username = username;
		this.roles = authorities;
	}

}
