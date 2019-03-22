package ch.hcuge.comprehensio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

	@Bean
	SecurityWebFilterChain configure(ServerHttpSecurity http) {
		// @formatter:off
	    http
	    .httpBasic().disable()
	    .formLogin().disable()
	    	.authorizeExchange()
	    	.anyExchange().authenticated()
	    	.and()
			.oauth2ResourceServer()
			.jwt();
	 // @formatter:on
		return http.build();
	}
}
