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

    // Quando viene creata l'istanza di questa classe, genera in automatico la coppia di chiave (pubblica e privata)
    public SecurityConfig(RsaKeyProperties keysProperties)
    {
        this.keys = keysProperties;
    }


    //Creo un bean he mi restituisce il password encoder: codifica password
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    //Creo un  gestore di autenticazione che viene richiamato nella classe UserServiceImpl
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

        http.oauth2ResourceServer((rs) -> rs.jwt((jwt) -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

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
        // Questo oggetto crea un convertitore che sarà responsabile della conversione delle autorizzazioni (ruoli)
        // presenti nel token JWT.
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // Configurazioni convertitore
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles"); // Qui si sta configurando il convertitore per estrarre le autorizzazioni (ruoli) dal campo "roles" del token JWT.
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_"); // Inoltre, viene specificato un prefisso "ROLE_" che verrà aggiunto a ciascuna autorizzazione estratta. Ad esempio, se il ruolo nel token è "ADMIN", diventerà "ROLE_ADMIN".

        // Viene creato un convertitore aggiuntivo che sarà responsabile della conversione dell'intero token JWT in un oggetto di autenticazione.
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();

        // Invoco il metodo setJwtGrantedAuthoritiesConverter dell'oggetto jwtConverter per settare l'oggetto creato (jwtGrantedAuthoritiesConverter):
        // si imposta il convertitore JwtGrantedAuthoritiesConverter precedentemente configurato all'interno del
        // JwtAuthenticationConverter.
        // Questo indica che durante il processo di autenticazione, le autorizzazioni (ruoli) saranno estratte dal token
        // JWT utilizzando il JwtGrantedAuthoritiesConverter.
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtConverter;
    }

}