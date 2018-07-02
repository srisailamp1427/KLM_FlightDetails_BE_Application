package com.afkl.cases.df.service.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afkl.cases.df.oauthclient.OAuthClient;
import com.afkl.cases.df.oauthclient.OAuthClientDetails;
import com.afkl.cases.df.service.OAuthService;

/**
 * This class to get the OAuthClientToken
 * 
 * @author srisailam
 */
@Component
public class OAuthServiceImpl implements OAuthService {
	private static final Logger logger = LogManager.getLogger(OAuthServiceImpl.class);
	@Autowired
	private OAuthClientDetails oAuthDetails;

	@Override
	public String getOAuthClientToken() throws Exception {
		String token = null;

		if (!OAuthClient.isValidInput(oAuthDetails)) {
			logger.info("OAuth details  not valid, please check the config properties");
		}
		// Generate new Access token
		String accessToken = OAuthClient.getAccessToken(oAuthDetails);

		if ((accessToken!=null&&accessToken.trim().length()>0)) {
			token = accessToken;
			logger.info("accessToken.." + accessToken);
		} else {
			logger.info("Access token not generated");
		}
		return token;
	}
}
