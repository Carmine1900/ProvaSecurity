package com.prova.security;


import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.prova.key.RsaKeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

//Creo una classe di configurazione della sicurezza aggiunendo l'annotazione sottostante
@Configuration
@EnableWebSecurity
public class SecurityConfig
{

    private RsaKeyProperties keys;

    public SecurityConfig(RsaKeyProperties keysProperties)
    {
        this.keys = keysProperties;
    }


    //Creo un bean he mi restituisce il password encoder
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    //Creo un  gestore di autenticazione
    @Bean
    public AuthenticationManager authManager(UserDetailsService detailsService)
    {
        //imposto un provider di autenticazione
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        //passandogli il mio detail service gli dico in che modo autenticherà l'utente
        daoProvider.setUserDetailsService(detailsService);
        daoProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoProvider);
    }

    @Bean
    //Creo un bean che mi restituisca una security filter chain
    public SecurityFilterChain configureFilterChain(HttpSecurity http) throws Exception
    {
        http
                //Disabilito il csrf tramite una funzione lambda
                .csrf(csfr -> csfr.disable())
                //Definisco quali rihcieste debbano essere autenticate e quali no sempre tramite un espressione lambda
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("user/registerUser").permitAll();
                    auth.requestMatchers("user/loginUser").permitAll();
	//				auth.requestMatchers("user/findAll").hasRole("admin");
	//	            auth.requestMatchers("user/deleteUser").hasRole("user");
                    auth.anyRequest().authenticated();
                });

        http
                .oauth2ResourceServer((rs) -> rs.jwt((jwt) -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));


        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();

    }


    //Bean per JWT
    @Bean
    public JwtDecoder jwtDecoder()
    {
        return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
    }

    @Bean
    public JwtEncoder jwtEncoder()
    {
        JWK jwk = new RSAKey.Builder(keys.getPublicKey()).privateKey(keys.getPrivateKey()).build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<SecurityContext>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }


    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter()
    {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtConverter;


    }

}