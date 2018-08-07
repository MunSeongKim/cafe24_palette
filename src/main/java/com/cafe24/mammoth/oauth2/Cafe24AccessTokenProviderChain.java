package com.cafe24.mammoth.oauth2;

import java.util.Collections;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.client.token.OAuth2AccessTokenSupport;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import com.cafe24.mammoth.oauth2.support.Cafe24APIGenerater;

/**
 * {@link AccessTokenProviderChain}을 복사한 클래스<br>
 * Token refreshing 과정에서 로직 수행 할 수 있도록 하는 조건과 Refreshing 완료 후 발급하는 AccessToken 객체를 Cafe24AccessToken 객체로 변경<br>
 * 
 * @since 18-07-23
 * @author MoonStar
 *
 */
public class Cafe24AccessTokenProviderChain extends OAuth2AccessTokenSupport implements AccessTokenProvider {

	private final List<AccessTokenProvider> chain;

	private ClientTokenServices clientTokenServices;

	public Cafe24AccessTokenProviderChain(List<? extends AccessTokenProvider> chain) {
		this.chain = chain == null ? Collections.<AccessTokenProvider>emptyList() : Collections.unmodifiableList(chain);
	}

	/**
	 * Token services for long-term persistence of access tokens.
	 *
	 * @param clientTokenServices
	 *            the clientTokenServices to set
	 */
	public void setClientTokenServices(ClientTokenServices clientTokenServices) {
		this.clientTokenServices = clientTokenServices;
	}

	public boolean supportsResource(OAuth2ProtectedResourceDetails resource) {
		for (AccessTokenProvider tokenProvider : chain) {
			if (tokenProvider.supportsResource(resource)) {
				return true;
			}
		}
		return false;
	}

	public boolean supportsRefresh(OAuth2ProtectedResourceDetails resource) {
		for (AccessTokenProvider tokenProvider : chain) {
			if (tokenProvider.supportsRefresh(resource)) {
				return true;
			}
		}
		return false;
	}

	public OAuth2AccessToken obtainAccessToken(OAuth2ProtectedResourceDetails resource, AccessTokenRequest request)
			throws UserRedirectRequiredException, AccessDeniedException {
		System.out.println("===================================== obtainAccessToken =============================");
		OAuth2AccessToken accessToken = null;
		OAuth2AccessToken existingToken = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Cafe24APIGenerater.setMallIdInResourceDetails(resource, auth);
		
		if (auth instanceof AnonymousAuthenticationToken) {
			if (!resource.isClientOnly()) {
				throw new InsufficientAuthenticationException(
						"Authentication is required to obtain an access token (anonymous not allowed)");
			}
		}

		if (resource.isClientOnly() || (auth != null && auth.isAuthenticated())) {
			existingToken = request.getExistingToken();
			System.out.println(existingToken);
			if (existingToken == null && clientTokenServices != null) {
				existingToken = clientTokenServices.getAccessToken(resource, auth);
				//System.out.println(existingToken.getExpiration());
			}

			if (existingToken != null) {
				if (existingToken.isExpired()) {
					System.out.println("Expired");
					if (clientTokenServices != null) {
						clientTokenServices.removeAccessToken(resource, auth);
					}
					OAuth2RefreshToken refreshToken = existingToken.getRefreshToken();
					if (refreshToken != null && resource.isClientOnly()) {
						accessToken = refreshAccessToken(resource, refreshToken, request);
						System.out.println("accessToken: " + accessToken);
					}
				} else {
					System.out.println("No expired");
					accessToken = existingToken;
				}
			}
		}
		// Give unauthenticated users a chance to get a token and be redirected

		if (accessToken == null) {
			// looks like we need to try to obtain a new token.
			accessToken = obtainNewAccessTokenInternal(resource, request);

			if (accessToken == null) {
				throw new IllegalStateException("An OAuth 2 access token must be obtained or an exception thrown.");
			}
		}

		if (clientTokenServices != null && (resource.isClientOnly() || auth != null && auth.isAuthenticated())) {
			clientTokenServices.saveAccessToken(resource, auth, accessToken);
		}

		return accessToken;
	}

	protected OAuth2AccessToken obtainNewAccessTokenInternal(OAuth2ProtectedResourceDetails details,
			AccessTokenRequest request) throws UserRedirectRequiredException, AccessDeniedException {

		if (request.isError()) {
			// there was an oauth error...
			throw OAuth2Exception.valueOf(request.toSingleValueMap());
		}

		for (AccessTokenProvider tokenProvider : chain) {
			if (tokenProvider.supportsResource(details)) {
				return tokenProvider.obtainAccessToken(details, request);
			}
		}

		throw new OAuth2AccessDeniedException("Unable to obtain a new access token for resource '" + details.getId()
				+ "'. The provider manager is not configured to support it.", details);
	}

	/**
	 * Obtain a new access token for the specified resource using the refresh token.
	 *
	 * @param resource
	 *            The resource.
	 * @param refreshToken
	 *            The refresh token.
	 * @return The access token, or null if failed.
	 * @throws UserRedirectRequiredException
	 */
	public OAuth2AccessToken refreshAccessToken(OAuth2ProtectedResourceDetails resource,
			OAuth2RefreshToken refreshToken, AccessTokenRequest request) throws UserRedirectRequiredException {
		for (AccessTokenProvider tokenProvider : chain) {
			if (tokenProvider.supportsRefresh(resource)) {
				Cafe24OAuth2AccessToken refreshedAccessToken = new Cafe24OAuth2AccessToken(
						tokenProvider.refreshAccessToken(resource, refreshToken, request));
				if (refreshedAccessToken.getRefreshToken() == null) {
					// Fixes gh-712
					refreshedAccessToken.setRefreshToken(refreshToken);
				}
				return refreshedAccessToken;
			}
		}
		throw new OAuth2AccessDeniedException("Unable to obtain a new access token for resource '" + resource.getId()
				+ "'. The provider manager is not configured to support it.", resource);
	}

}