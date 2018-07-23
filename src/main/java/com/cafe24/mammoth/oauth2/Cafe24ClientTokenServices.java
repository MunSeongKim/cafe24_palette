package com.cafe24.mammoth.oauth2;

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
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.Assert;

/**
 * Cafe24OAuth2AccessToken과 Authentication 객체에 대한 영속성 관리를 위한 서비스<br>
 * <hr>
 * Cafe24ClientTokenServices(Datasource): 빈 등록때 실행되어 DB에 테이블 생성 및 외래키 설정 실행<br>
 * String getMallIdForKey(OAuth2ProtectedResourceDetails, Authentication):
 * Authentication 또는 resource에서 mall_id를 추출<br>
 * boolean isExistsAccessToken(String): AccessToken의 존재 여부 반환<br>
 * void setAuthenticationToSecurityContext(String): 해당 mall_id를 가진
 * Authentication을 찾아 SecurityContext에 등록<br>
 * OAuth2Authentication getAuthentication(String): 해당 mall_id를 가진 Authentication
 * 객체 반환
 * 
 * @since 18-07-19
 * @author MoonStar
 *
 */
public class Cafe24ClientTokenServices implements ClientTokenServices {
	private static final Log LOG = LogFactory.getLog(Cafe24ClientTokenServices.class);

	private final JdbcTemplate jdbcTemplate;

	private final String insertAccessTokenSql = "INSERT INTO authentication (mall_id, access_token, client_id, access_token_object, authentication_object) VALUES (?, ?, ?, ?, ?)";
	private final String selectAccessTokenSql = "SELECT access_token, access_token_object FROM authentication WHERE mall_id = ?";
	private final String deleteAccessTokenSql = "DELETE FROM authentication WHERE mall_id = ?";
	private final String updateAuthenticationSql = "UPDATE authentication SET authentication_object = ? WHERE mall_id = ?";
	private final String selectAuthenticationSql = "SELECT authentication_object FROM authentication WHERE mall_id = ?";
	private final String selectExistTokenSql = "SELECT IF(COUNT(*) = 1, true, false) FROM authentication WHERE mall_id = ?";

	public Cafe24ClientTokenServices(DataSource dataSource) {
		Assert.notNull(dataSource, "DataSource required");
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		try {
			jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS authentication("
					+ "	mall_id VARCHAR(50) NOT NULL PRIMARY KEY," + "	access_token VARCHAR(100),"
					+ "	client_id VARCHAR(100)," + "	access_token_object BLOB," + "	authentication_object BLOB,"
					+ "   CONSTRAINT fk_member_to_authentication FOREIGN KEY ( mall_id ) REFERENCES member ( mall_id ) ON DELETE CASCADE"
					+ ")");
			// jdbcTemplate.execute("ALTER TABLE member ADD CONSTRAINT FOREIGN KEY (mall_id)
			// REFERENCES authentication (mall_id) ON DELETE CASCADE");
		} catch (DataAccessException e) {
			LOG.error("Can not create table in database.", e);
		}
	}

	// Authentication accessing
	public void saveAuthentication(Authentication authentication) {
		String mallId = getMallIdForKey(null, authentication);
		OAuth2Authentication oauth2Authentication = (OAuth2Authentication) authentication;
		jdbcTemplate.update(updateAuthenticationSql,
				new Object[] { new SqlLobValue(SerializationUtils.serialize(oauth2Authentication)), mallId },
				new int[] { Types.BLOB, Types.VARCHAR });
	}

	public OAuth2Authentication getAuthentication(String mallId) {
		OAuth2Authentication storedAuthentication = jdbcTemplate.queryForObject(selectAuthenticationSql,
				new RowMapper<OAuth2Authentication>() {
					public OAuth2Authentication mapRow(ResultSet rs, int rowNum) throws SQLException {
						return SerializationUtils.deserialize(rs.getBytes(1));
					}
				}, mallId);
		return storedAuthentication;
	}

	// SecurityContextHolder에 Authentication 객체를 교체
	// OAuth2 필터를 거치지 않으면 AnonymousAuthenticationToken 객체가 생성 이를 인증된 객체로 변경해줘야
	// RestTemplate이 정상 동작.
	// 토근 발급 시 저장해놓은 Authentication 객체를 DB에서 가져옴
	// AnonymousAuthenticationToken 객체를 Token 발급 후 생성된 인증 객체인
	// UsernamePasswordAuthenticationToken 변경
	public void setAuthenticationToSecurityContext(String mallId) {
		SecurityContextHolder.getContext().setAuthentication(getAuthentication(mallId));
	}

	// AccessToken processing
	@Override
	public OAuth2AccessToken getAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {

		OAuth2AccessToken accessToken = null;
		try {
			accessToken = jdbcTemplate.queryForObject(selectAccessTokenSql, new RowMapper<Cafe24OAuth2AccessToken>() {
				public Cafe24OAuth2AccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {
					return SerializationUtils.deserialize(rs.getBytes(2));
				}
			}, getMallIdForKey(resource, authentication));
		} catch (EmptyResultDataAccessException e) {
			if (LOG.isInfoEnabled()) {
				LOG.debug("Failed to find access token for authentication " + authentication);
			}
		}
		return accessToken;
	}

	@Override
	public void saveAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication,
			OAuth2AccessToken accessToken) {
		removeAccessToken(resource, authentication);
		Cafe24OAuth2AccessToken cafe24Token = (Cafe24OAuth2AccessToken) accessToken;
		OAuth2Authentication oauth2Authentication = (OAuth2Authentication) authentication;
		jdbcTemplate.update(insertAccessTokenSql,
				new Object[] { cafe24Token.getMallId(), cafe24Token.getValue(), resource.getClientId(),
						new SqlLobValue(SerializationUtils.serialize(cafe24Token)),
						new SqlLobValue(SerializationUtils.serialize(oauth2Authentication)) },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BLOB, Types.BLOB });
	}

	@Override
	public void removeAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
		String mallId = getMallIdForKey(resource, authentication);
		jdbcTemplate.update(deleteAccessTokenSql, mallId);
	}

	public boolean isExistsAccessToken(String mallId) {
		return jdbcTemplate.queryForObject(selectExistTokenSql, new Object[] { mallId }, Boolean.class);
	}

	@SuppressWarnings("unchecked")
	private String getMallIdForKey(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
		if (authentication == null) {
			return ((Cafe24AuthorizationCodeResourceDetails) resource).getMallId();
		} else {
			Authentication userAuthentication = ((OAuth2Authentication) authentication).getUserAuthentication();
			Map<String, Object> storeDetails = (Map<String, Object>) ((Map<String, Object>) userAuthentication
					.getDetails()).get("store");
			return (String) storeDetails.get("mall_id");
		}
	}

}
