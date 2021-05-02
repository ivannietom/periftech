package es.code.urjc.periftech.security;


import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	@Autowired
	RepositoryUserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10, new SecureRandom());
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Public pages
		http.authorizeRequests().antMatchers("/").permitAll();
		http.authorizeRequests().antMatchers("/login").permitAll();
		http.authorizeRequests().antMatchers("/register").permitAll();
		http.authorizeRequests().antMatchers("/registro").permitAll();
		http.authorizeRequests().antMatchers("/logout").permitAll();

		// Private pages (all other pages)
		http.authorizeRequests().antMatchers("/categorias").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/cart").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/eliminarProductoCarro/{id}").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/nueva-categoria").hasAnyRole("ADMIN");
		http.authorizeRequests().antMatchers("/agregarCategoria").hasAnyRole("ADMIN");
		http.authorizeRequests().antMatchers("/categoria/{id}").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/eliminarCategoria/{id}").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/mi-perfil").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/realizarPedido").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/categoria/producto/agregarProducto{id}").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/nuevo-producto").hasAnyRole("ADMIN");
		http.authorizeRequests().antMatchers("/agregarProducto").hasAnyRole("ADMIN");
		http.authorizeRequests().antMatchers("/eliminarProducto/{id}").hasAnyRole("ADMIN");
		http.authorizeRequests().antMatchers("/categoria/producto/{id}").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/busqueda").hasAnyRole("USER");
		
		// Login form
		http.formLogin().loginPage("/login"); 
		http.formLogin().usernameParameter("nombreUsuario");
		http.formLogin().passwordParameter("password");
		http.formLogin().defaultSuccessUrl("/");
		http.formLogin().failureUrl("/");

		// Logout
		http.logout().logoutUrl("/logout");
		http.logout().logoutSuccessUrl("/");

		// Disable CSRF at the moment
		http.csrf().disable();
	}
}