package com.project.oauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	
	@Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        clients.inMemory().withClient("thirdPartyClient").secret(encoder.encode("secret"))
        .authorizedGrantTypes("authorization_code").redirectUris("http://localhost:8090/showEmployees")
            .scopes("read").authorities("CLIENT");
    }

}
