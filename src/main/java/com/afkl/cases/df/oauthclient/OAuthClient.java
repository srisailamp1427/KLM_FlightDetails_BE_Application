package com.afkl.cases.df.oauthclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.JSONParser;


/**
 * This class consist of methods related OAuthClient
 * 
 * @author srisailam
 */
public  class OAuthClient {
	private static final Logger logger = LogManager.getLogger(OAuthClient.class);

	/**
	 * This method for get the AccessToken using HttpClient
	 * 
	 * @param oauthClientDetails
	 * @return String
	 */
	public static String getAccessToken(OAuthClientDetails oauthClientDetails) {
		HttpResponse response = null;
		String accessToken = null;
		HttpPost post = new HttpPost(oauthClientDetails.getAuthenticationServerUrl());

		List<BasicNameValuePair> oAuthRequestBody = new ArrayList<BasicNameValuePair>();
		oAuthRequestBody
				.add(new BasicNameValuePair(OAuthClientConstants.GRANT_TYPE, oauthClientDetails.getGrantType()));
		oAuthRequestBody.add(new BasicNameValuePair(OAuthClientConstants.CLIENT_ID, oauthClientDetails.getClientId()));
		oAuthRequestBody
				.add(new BasicNameValuePair(OAuthClientConstants.CLIENT_SECRET, oauthClientDetails.getClientSecret()));

		HttpClient client = HttpClientBuilder.create().build();

		try {
			post.setEntity(new UrlEncodedFormEntity(oAuthRequestBody, "UTF-8"));

			response = client.execute(post);
			int code = response.getStatusLine().getStatusCode();
			if (code == OAuthClientConstants.HTTP_UNAUTHORIZED) {

				// Add Basic Authorization header
				post.addHeader(OAuthClientConstants.AUTHORIZATION, getBasicAuthorizationHeader(
						oauthClientDetails.getClientId(), oauthClientDetails.getClientSecret()));

				post.releaseConnection();
				response = client.execute(post);
				code = response.getStatusLine().getStatusCode();
				if (code == 401 || code == 403) {
					logger.info("HttpClient code.." + code);
					logger.error("not retrived access token ..");
					throw new RuntimeException(" not retrived access token: " + oauthClientDetails.getClientId());
				}

			}

			String contentType = OAuthClientConstants.JSON_CONTENT;
			Map<String, String> map;
			if (response.getEntity().getContentType() != null) {
				contentType = response.getEntity().getContentType().getValue();
			}
			if (contentType.contains(OAuthClientConstants.JSON_CONTENT)
					|| contentType.contains(OAuthClientConstants.HAL_JSON_CONTENT)) {
				map = handleJsonResponse(response);
			} else {
				logger.error("content type could not hadled with json, xml,urlEncoded ");
				throw new RuntimeException(
						"Could not handle " + contentType + " content type.  include JSON, XML and URLEncoded ");
			}

			accessToken = map.get(OAuthClientConstants.ACCESS_TOKEN);
			logger.info("access token" + accessToken);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		return accessToken;
	}

	/**
	 * This method for parse the json based on input httpResponse
	 * 
	 * @param response
	 * @return Map
	 */
	public static Map handleJsonResponse(HttpResponse response) {
		Map<String, String> oauthLoginResponse = null;
		String contentType = response.getEntity().getContentType().getValue();
		try {
			oauthLoginResponse = (Map<String, String>) new JSONParser()
					.parse(EntityUtils.toString(response.getEntity()));
		} catch (ParseException e) {
			logger.error(e.getMessage());
			throw new RuntimeException();
		} catch (org.json.simple.parser.ParseException e) {
			logger.error(e.getMessage());
			throw new RuntimeException();
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new RuntimeException();
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
			throw e;
		}

		return oauthLoginResponse;
	}

	/**
	 * This method for getBasicAuthorizationHeader
	 * 
	 * @param clientId
	 * @param clientSecret
	 * @return String
	 */
	public static String getBasicAuthorizationHeader(String clientId, String clientSecret) {
		return OAuthClientConstants.BASIC + " " + encodeCredentials(clientId, clientSecret);
	}

	/**
	 * This method for encode the Credentials
	 * 
	 * @param clientId
	 * @param clientSecret
	 * @return String
	 */
	public static String encodeCredentials(String clientId, String clientSecret) {
		String cred = clientId + ":" + clientSecret;
		String encodedValue = null;
		byte[] encodedBytes = Base64.encodeBase64(cred.getBytes());
		encodedValue = new String(encodedBytes);
		logger.info("encodedBytes " + new String(encodedBytes));

		byte[] decodedBytes = Base64.decodeBase64(encodedBytes);
		logger.info("decodedBytes " + new String(decodedBytes));

		return encodedValue;

	}

	/**
	 * This method for validating OAuthClientDetails
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isValidInput(OAuthClientDetails input) {

		if (input == null) {
			return false;
		}

		String grantType = input.getGrantType();
		String authenticationServerUrl = input.getAuthenticationServerUrl();
		String clientId = input.getClientId();
		String clientSecret = input.getClientSecret();
		if (!(grantType != null && grantType.trim().length() > 0)) {
			logger.info("Provide the valid grant_type");
			return false;
		}

		if (!(authenticationServerUrl != null && authenticationServerUrl.trim().length() > 0)) {
			logger.info("Provide the valid authentication server url");
			return false;
		}

		if (grantType.equals(OAuthClientConstants.GRANT_TYPE_CLIENT_CREDENTIALS)) {
			if (!(clientId != null && clientId.trim().length() > 0)
					|| (clientSecret != null && clientSecret.trim().length() > 0)) {
				logger.info("Provide the valid client_id , client_secret ");
				return false;
			}
		}

		logger.info("Validated success");
		return true;

	}

}