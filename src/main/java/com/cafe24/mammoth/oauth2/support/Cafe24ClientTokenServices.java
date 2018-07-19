package com.cafe24.mammoth.oauth2.support;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.client.token.JdbcClientTokenServices;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.Assert;

import com.cafe24.mammoth.oauth2.Cafe24AuthorizationCodeResourceDetails;
import com.cafe24.mammoth.oauth2.Cafe24OAuth2AccessToken;

public class Cafe24ClientTokenServices implements ClientTokenServices {
	private static final Log LOG = LogFactory.getLog(JdbcClientTokenServices.class);

	private final JdbcTemplate jdbcTemplate;
	
	private final String insertAccessTokenSql = "INSERT INTO authentication (mall_id, access_token, client_id, access_token_object, authentication_object) VALUES (?, ?, ?, ?, ?)";
	private final String selectAccessTokenSql = "SELECT access_token, access_token_object FROM authentication WHERE mall_id = ?";
	private final String deleteAccessTokenSql = "DELETE FROM authentication WHERE mall_id = ?";
	private final String selectAuthenticationSql = "SELECT authentication_object FROM authentication WHERE mall_id = ?"; 
	private final String selectExistTokenSql = "SELECT IF(COUNT(*) = 1, true, false) FROM authentication WHERE mall_id = ?";
	
	public Cafe24ClientTokenServices(DataSource dataSource) {
		Assert.notNull(dataSource, "DataSource required");
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		try {
			jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS authentication(" + 
				"	mall_id VARCHAR(50) NOT NULL PRIMARY KEY," + 
				"	access_token VARCHAR(100)," + 
				"	client_id VARCHAR(100)," + 
				"	access_token_object BLOB," + 
				"	authentication_object BLOB," +
				"   CONSTRAINT fk_member_to_authentication FOREIGN KEY ( mall_id ) REFERENCES member ( mall_id ) ON DELETE CASCADE" +
				")");
//			jdbcTemplate.execute("ALTER TABLE member ADD CONSTRAINT FOREIGN KEY (mall_id) REFERENCES authentication (mall_id) ON DELETE CASCADE");
		} catch(DataAccessException e) {
			LOG.error("Can not create table in database.", e);
		}
	}

	@SuppressWarnings("unchecked")
	private String getMallIdForKey(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
		if(authentication == null) {
			return ((Cafe24AuthorizationCodeResourceDetails) resource).getMallId();
		} else {
			Authentication userAuthentication = ((OAuth2Authentication) authentication).getUserAuthentication();
			Map<String, Object> storeDetails = (Map<String, Object>) ((Map<String, Object>) userAuthentication.getDetails()).get("store");
			return (String) storeDetails.get("mall_id"); 
		}
	}
	
	public boolean isExistsAccessToken(String mallId) {
		return jdbcTemplate.queryForObject(selectExistTokenSql, new Object[] {mallId}, Boolean.class);
	}
	
	public OAuth2Authentication getAuthentication(String mallId) {
		
		OAuth2Authentication storedAuthentication = null;
		
		storedAuthentication = jdbcTemplate.queryForObject(selectAuthenticationSql , new RowMapper<OAuth2Authentication>() {
			public OAuth2Authentication mapRow(ResultSet rs, int rowNum) throws SQLException {
				return SerializationUtils.deserialize(rs.getBytes(1));
			}
		}, mallId);
		
		return storedAuthentication;
	}
	
	// SecurityContextHolder에 Authentication 객체를 교체
	// OAuth2 필터를 거치지 않으면 AnonymousAuthenticationToken 객체가 생성 이를 인증된 객체로 변경해줘야 RestTemplate이 정상 동작.
	// 토근 발급 시 저장해놓은 Authentication 객체를 DB에서 가져옴
	// AnonymousAuthenticationToken 객체를 Token 발급 후 생성된 인증 객체인 UsernamePasswordAuthenticationToken 변경 
	public void setAuthenticationToSecurityContext(String mallId) {
		SecurityContextHolder.getContext().setAuthentication(getAuthentication(mallId));
	}
	
	
	@Override
	public OAuth2AccessToken getAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {

		OAuth2AccessToken accessToken = null;
		
		System.out.println("==================== Cafe24ClientTokenServices.getAccessToken() =================");
		System.out.println("Authentication: " + authentication);
		System.out.println("---------------------------------------------------------------------------------");
		
		String mallId = getMallIdForKey(resource, authentication);
		
		try {
			accessToken = jdbcTemplate.queryForObject(selectAccessTokenSql, new RowMapper<OAuth2AccessToken>() {
				public OAuth2AccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {
					return SerializationUtils.deserialize(rs.getBytes(2));
				}
			}, mallId);
			System.out.println("accessToken extends " + accessToken.getClass());
			System.out.println("accessToken: " + accessToken);
			System.out.println("---------------------------------------------------------------------------------");
			System.out.println("accessToken.getValue(): " + accessToken.getValue());
			System.out.println("---------------------------------------------------------------------------------");
			System.out.println("accessToken.isExpired():" + accessToken.isExpired());
			System.out.println("---------------------------------------------------------------------------------");
			System.out.println("accessToken.getAdditionalInformation():" + accessToken.getAdditionalInformation());
		}
		catch (EmptyResultDataAccessException e) {
			if (LOG.isInfoEnabled()) {
				LOG.debug("Failed to find access token for authentication " + authentication);
			}
		}
		System.out.println("================================================================");
		return accessToken;
	}
	
	@Override
	public void saveAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication,
			OAuth2AccessToken accessToken) {
		removeAccessToken(resource, authentication);
		System.out.println("==================== Cafe24ClientTokenServices.saveAccessToken() =================");
		System.out.println("Authentication: " + authentication);
		Cafe24OAuth2AccessToken cafe24Token = (Cafe24OAuth2AccessToken) accessToken;
		//DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(accessToken);
		// String name = authentication==null ? null : authentication.getName();
		jdbcTemplate.update(
				insertAccessTokenSql,
				new Object[] { cafe24Token.getMallId(), cafe24Token.getValue(), resource.getClientId(),
						new SqlLobValue(SerializationUtils.serialize(accessToken)),
						new SqlLobValue(SerializationUtils.serialize(authentication)) },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BLOB, Types.BLOB });
		System.out.println("================================================================");
	}

	@Override
	public void removeAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
		System.out.println("==================== removing token =====================");
		String mallId = getMallIdForKey(resource, authentication);
		jdbcTemplate.update(deleteAccessTokenSql, mallId);
	}

}
