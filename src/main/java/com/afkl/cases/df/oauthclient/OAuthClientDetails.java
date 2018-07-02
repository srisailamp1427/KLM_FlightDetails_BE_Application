package com.afkl.cases.df.oauthclient;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * This model class for OAuthClient
 * 
 * @author srisailam
 */

@Getter
@Setter
@Component
@PropertySource("classpath:Oauth2.properties")
@ConfigurationProperties
public class OAuthClientDetails {
	String clientSecret;
	String refreshToken;
	String authenticationServerUrl;
	String grantType;
	String clientId;
}
