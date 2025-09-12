package com.EasyTable.Security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import static org.springframework.security.config.Customizer.withDefaults;

import com.EasyTable.Service.UserDetailsServiceImpl;
/**
 * Configurazione di sicurezza per l'applicazione EasyTable
 * gestisce: 
 * autenticazione e autorizzazione degli utenti tramite UserDetailsServiceImpl
 * cifratura delle password con BCryptPasswordEncoder
 * configurazione CORS per consetire richieste dai client specificati
 * protezione degli endpoint con ruoli specifici "ROLE_USER" e "ROLE_ADMIN" 
 * disabilitazione del CSRF 
 * gestione della sessione
 * logout con invalidazione della sessione e rimozione dei cookie
 * gli endpoint pubblici sono: 
 * login e register 
 * tutte le altre richiedono autenticazione
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class EasyTableSecurity {

	private final UserDetailsServiceImpl userDetailsServiceImpl;

	public EasyTableSecurity(UserDetailsServiceImpl userDetailsServiceImpl) {
		this.userDetailsServiceImpl = userDetailsServiceImpl;
	}
	
	@Bean
	 PasswordEncoder passwordEncoder() { 
		return new BCryptPasswordEncoder(); 
	}
	
	  @Bean
	     AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
	        return authConfig.getAuthenticationManager();
	    }
	  

	  
	    @Bean
	    CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration config = new CorsConfiguration();

	        
	        config.setAllowedOrigins(List.of("http://127.0.0.1:5500", "http://localhost:5500"));

	        
	        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

	        
	        config.setAllowedHeaders(List.of("Content-Type", "Authorization", "X-Requested-With", "Accept"));


	        config.setAllowCredentials(true);

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", config);
	        return source;
	    }
	  
	  
	  @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { 
	    	http
	        .csrf(csrf -> csrf.disable())  
	        .cors(withDefaults())
	        .authorizeHttpRequests(auth -> auth
	        				.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
	        				.requestMatchers("/auth/login", "/auth/register").permitAll()
	        				.requestMatchers("/api/mostraListaPrenotazioniUtente", "/api/salvaPrenotazione", "/api/eliminaPrenotazioneUtente/**", "/api/cercaPrenotazioneOraGiorno/**",  "/api/modificaPrenotazione/**").hasAuthority("ROLE_USER")
	        				.requestMatchers("/api/mostraListaPrenotazioniAdmin", "/api/eliminaPrenotazioneAdmin/**").hasAuthority("ROLE_ADMIN")
	                        .anyRequest().authenticated()
	        )
	        .formLogin().disable() 
	        .logout(logout -> logout
	        		.logoutUrl("/logout")
	        		.logoutSuccessUrl("/login")
	        		.invalidateHttpSession(true)
	        		.deleteCookies("JSESSIONID")
	        		.permitAll()
	        		)
	        .sessionManagement(session -> session 
	        		.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
	        		)
	        .userDetailsService(userDetailsServiceImpl); 
	        

	return http.build();
	}
}
