package es.code.urjc.periftech;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.code.urjc.periftech.models.Cliente;
import es.code.urjc.periftech.repositories.ClienteRepository;

@Service
public class RepositoryUserDetailsService implements UserDetailsService{
	@Autowired
	private ClienteRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Cliente user = userRepository.findByNombreUsuario(username);
			

		List<GrantedAuthority> roles = new ArrayList<>();
		for (String role : user.getRoles()) {
			roles.add(new SimpleGrantedAuthority("ROLE_" + role));
		}

		return new org.springframework.security.core.userdetails.User(user.getNombreUsuario(), 
				user.getEncodedPassword(), roles);

	}
}