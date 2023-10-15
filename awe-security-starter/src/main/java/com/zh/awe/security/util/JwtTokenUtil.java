package com.zh.awe.security.util;

import com.zh.awe.security.config.WebSecurityProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

public class JwtTokenUtil {

	private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

	private static final String ISS = "leopard";

	public static String createToken(String id, String user, WebSecurityProperties securityProperties) {
		JwtBuilder jwtBuilder = Jwts.builder();
		String jwtSignWith = securityProperties.getJwtSignWith();
		if (jwtSignWith == null || !jwtSignWith.equals("RSA")) {
			byte[] keyBytes = Decoders.BASE64.decode(securityProperties.getJwtSecret());
			Key key = Keys.hmacShaKeyFor(keyBytes);
			jwtBuilder.signWith(key,SignatureAlgorithm.HS512);
		} else {
			jwtBuilder.signWith(securityProperties.getPrivateKey(),SignatureAlgorithm.RS512);
		}
		jwtBuilder.setSubject(user);
		jwtBuilder.setIssuer(ISS);
		jwtBuilder.setId(id);
		jwtBuilder.setIssuedAt(new Date());
		jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + securityProperties.getTime() * 1000));
		return jwtBuilder.compact();
	}

	/**
	 * 从token中获取用户名
	 */
	public static String getUser(WebSecurityProperties securityProperties, String token) {
		Claims claims = getTokenBody(securityProperties, token);
		if (claims != null) {
			return claims.getSubject();
		} else {
			return null;
		}
	}

	/**
	 * 从token中获取ID，同时做解密处理
	 */
	public static String getUserId(WebSecurityProperties securityProperties, String token) {
		Claims claims = getTokenBody(securityProperties, token);
		if (claims != null) {
			return claims.getId();
		} else {
			return null;
		}
	}

	/**
	 * 设置失效
	 *
	 */
	public static void setJwtExpiration(WebSecurityProperties securityProperties, String token) {
		Claims claims = getTokenBody(securityProperties, token);
		if (claims != null) {
			claims.clear();
		}
	}

	/**
	 * 获取token信息，同时也做校验处理
	 */
	public static Claims getTokenBody(WebSecurityProperties securityProperties, String token) {
		try {
			if (token == null || token.equals("")) {
				return null;
			}
			String jwtSignWith = securityProperties.getJwtSignWith();
			if (jwtSignWith == null || !jwtSignWith.equals("RSA")) {
				return getTokenBody(securityProperties.getJwtSecret(), token);
			} else {
				return getTokenBody(securityProperties.getPublicKey(), token);
			}
		} catch (ExpiredJwtException expired) {
			logger.error("token[{}]已过期", token);
			return null;
		} catch (MalformedJwtException e) {
			logger.error("token[{}]无效", token);
			return null;
		}
	}

	public static Claims getTokenBody(String key, String token) {
		try {
			if (token == null || token.equals("")) {
				return null;
			}
			return Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token)
					.getBody();
		} catch (ExpiredJwtException expired) {
			logger.error("token[{}]已过期", token);
			return null;
		} catch (MalformedJwtException malformedJwt) {
			logger.error("token[{}]无效", token);
			return null;
		}
	}

	public static Claims getTokenBody(PublicKey key, String token) {
		if (token == null || token.equals("")) {
			return null;
		}
		try {
			return Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token)
					.getBody();
		} catch (ExpiredJwtException expired) {
			logger.error("token[{}]已过期", token);
			return null;
		} catch (MalformedJwtException malformedJwt) {
			logger.error("token[{}]无效", token);
			return null;
		}
	}

	public static String createRsaToken(String id, String user, long expiration, PrivateKey key) {
		JwtBuilder jwtBuilder = Jwts.builder();
		jwtBuilder.signWith(key,SignatureAlgorithm.RS256);
		jwtBuilder.setSubject(user);
		jwtBuilder.setIssuer(ISS);
		jwtBuilder.setId(id);
		jwtBuilder.setIssuedAt(new Date());
		jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + expiration * 1000));
		return jwtBuilder.compact();
	}

}
