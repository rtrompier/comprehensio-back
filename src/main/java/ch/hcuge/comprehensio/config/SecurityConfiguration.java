package ch.hcuge.comprehensio.config;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
	
	private static final String KEYCLOAK_REALM_ACCESS = "realm_access";
	private static final String KEYCLOAK_ROLES = "roles";

	@Bean
	SecurityWebFilterChain configure(ServerHttpSecurity http) {
		// @formatter:off
	    http
	    .cors().disable()
	    .csrf().disable()
	    .httpBasic().disable()
	    .formLogin().disable()
	    	.authorizeExchange()
	    	.anyExchange().permitAll()
	    	.and()
			.oauth2ResourceServer()
			.jwt()
			.jwtAuthenticationConverter(grantedAuthoritiesExtractor())
			
			;
	 // @formatter:on
		return http.build();
	}
	
	Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
	    GrantedAuthoritiesExtractor extractor = new GrantedAuthoritiesExtractor();
	    return new ReactiveJwtAuthenticationConverterAdapter(extractor);
	}
	
	static class GrantedAuthoritiesExtractor extends JwtAuthenticationConverter {
	    protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
	    	// @formatter:off
	    				JSONObject realmAccess = (JSONObject) jwt.getClaims().get(KEYCLOAK_REALM_ACCESS);
	    				JSONArray roles = (JSONArray) realmAccess.get(KEYCLOAK_ROLES);
	    				return roles.stream()
	    						.map(Object::toString)
	    						.map(SimpleGrantedAuthority::new)
	    						.collect(Collectors.toSet());
	    				// @formatter:on
	    }
	}

	
}
