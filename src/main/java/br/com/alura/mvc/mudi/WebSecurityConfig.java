package br.com.alura.mvc.mudi;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/home/**")	// EXCEÇÃO DE ACESSO
			.permitAll()			// A PÁGINA HOME E QUALQUER EM SEGUIDA SEM LOGAR
			.anyRequest().authenticated() // TODA REQUISIÇÃO AUTENTICAR
		.and()
			.formLogin( form -> form
					.loginPage("/login")
					.defaultSuccessUrl("/usuario/pedido", true)
					.permitAll()
					)
					.logout(logout -> {
						logout.logoutUrl("/logout")
						.logoutSuccessUrl("/homme");
					}).csrf().disable();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		// CRIA UM USUÁRIO
//		UserDetails user =
//				 User.builder()
//					.username("lucelia")
//					.password(encoder.encode("123"))
//					.roles("ADM")
//					.build();
		
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.passwordEncoder(encoder);
//			.withUser(user);
	}
	

}
