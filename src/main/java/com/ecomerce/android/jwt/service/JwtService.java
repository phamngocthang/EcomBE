package com.ecomerce.android.jwt.service;

import java.util.Date;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEDecrypter;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.JWEDecryptionKeySelector;
import com.nimbusds.jose.proc.JWEKeySelector;
import com.nimbusds.jose.proc.SimpleSecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;

@Service
public class JwtService {
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String ROLE = "role";
	public static final String SECRET_KEY = "11111111111111111111111111111111";
	public static final int EXPIRE_TIME = 86400000;
	private static final String BEARER_PREFIX = "Bearer ";
	
	
	private String stripBearerToken(String token) {
		if (token != null)
			token = token.startsWith(BEARER_PREFIX) ? token.substring(BEARER_PREFIX.length()) : token;
		return token;
	}
	
	//JWS
//	public String generateTokenLogin(String username) {
//	    String token = null;
//	    try {
//	      // Create HMAC signer
//	      JWSSigner signer = new MACSigner(generateShareSecret());
//	      // Trình tạo để xây dựng các bộ xác nhận quyền sở hữu JSON Web Token (JWT).
//	      JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
//	      builder.claim(USERNAME, username);
//	      builder.expirationTime(generateExpirationDate());
//	      JWTClaimsSet claimsSet = builder.build();
//	      /** Tạo mã JWT mới được ký với header mã hóa bằng HS256 
//	       *  cùng với các xác nhận quyền sở hữu đã chỉ định trong claimsSet. Trạng thái ban đầu sẽ là unsigned.
//	       */
//	      SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
//	      // Apply the HMAC protection: Tạo chữ ký  và kiểm chứng token 
//	      signedJWT.sign(signer);
//	      token = signedJWT.serialize();
//	    } catch (Exception e) {
//	      e.printStackTrace();
//	    }
//	    return token;
//	}
	
	//JWE
	public String generateTokenLogin(String username, String role) {
	    String token = null;
	    try {
	      // Create HMAC signer
	      //JWSSigner signer = new MACSigner(generateShareSecret());
	      // Trình tạo để xây dựng các bộ xác nhận quyền sở hữu JSON Web Token (JWT).
	      JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
	      builder.claim(USERNAME, username);
	      builder.claim(ROLE, role);
	      builder.expirationTime(generateExpirationDate());
	      JWTClaimsSet claimsSet = builder.build();
	      JWEHeader header = new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256);
	      DirectEncrypter encrypter = new DirectEncrypter(generateShareSecret());
	      Payload payload = new Payload(claimsSet.toJSONObject());
	      System.out.println(payload);
	      JWEObject jweObject = new JWEObject(header, payload);
	      jweObject.encrypt(encrypter);
	      token = jweObject.serialize();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return token;
	}
	
//	/* JWS: Lấy các thông tin trong Claims đã được lưu trong token
	
//	private JWTClaimsSet getClaimsFromToken(String token) {
//		token = stripBearerToken(token);
//		JWTClaimsSet claims = null;
//		try {
//			SignedJWT signedJWT = SignedJWT.parse(token);
//			JWSVerifier verifier = new MACVerifier(generateShareSecret());
//			if (signedJWT.verify(verifier)) {
//				claims = signedJWT.getJWTClaimsSet();
//				//System.out.println(claims);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return claims;
//	}
	
	//JWE
	private JWTClaimsSet getClaimsFromToken(String token) {
		ConfigurableJWTProcessor<SimpleSecurityContext> jwtProcessor = new DefaultJWTProcessor<SimpleSecurityContext>();
		JWKSource<SimpleSecurityContext> jweKeySource = new ImmutableSecret<SimpleSecurityContext>(generateShareSecret());
		JWEKeySelector<SimpleSecurityContext> jweKeySelector =
		    new JWEDecryptionKeySelector<SimpleSecurityContext>(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256, jweKeySource);
		jwtProcessor.setJWEKeySelector(jweKeySelector);
		token = stripBearerToken(token);
		JWTClaimsSet claims = null;
		try {
//			JWEObject jweObject = JWEObject.parse(token);
//			//DirectDecrypter verifier = new DirectDecrypter(generateShareSecret());
//			jweObject.decrypt(new DirectDecrypter(generateShareSecret()));
//			System.out.println(token);
//			System.out.println(jweObject.getPayload());
//			SignedJWT signedJWT = jweObject.getPayload().toSignedJWT();
//			if (signedJWT.verify(new MACVerifier(generateShareSecret()))) {
//				claims = signedJWT.getJWTClaimsSet();
//			}
			claims = jwtProcessor.process(token, null);
			System.out.println(claims);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return claims;
	}

	// Gen thời hạn cho SecretKey
	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + EXPIRE_TIME);
	}
	
	// Từ token lấy ra Date
	private Date getExpirationDateFromToken(String token) {
		token = stripBearerToken(token);
		Date expiration = null;
		JWTClaimsSet claims = getClaimsFromToken(token);
		expiration = claims.getExpirationTime();
		return expiration;
	}
  
	// Từ token lấy ra Username
	public String getUsernameFromToken(String token) {
		token = stripBearerToken(token);
		String username = null;
		try {
			JWTClaimsSet claims = getClaimsFromToken(token);
			username = claims.getStringClaim(USERNAME);
			String role = claims.getStringClaim(ROLE);
			System.out.print("ROLE" + role);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return username;
	}
  
	// Gen khóa secret thành 32byte
	private byte[] generateShareSecret() {
		// Generate 256-bit (32-byte) shared secret
		byte[] sharedSecret = new byte[32];
		sharedSecret = SECRET_KEY.getBytes();
		return sharedSecret;
	}
	
	// Kiểm tra token có hết hạn hay không
	private Boolean isTokenExpired(String token) {
		token = stripBearerToken(token);
		Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
  
	// Kiểm tra token có hợp lệ không
	public Boolean validateTokenLogin(String token) {
		token = stripBearerToken(token);
	    if (token == null || token.trim().length() == 0) {
	      return false;
	    }
	    String username = getUsernameFromToken(token);
	    if (username == null || username.isEmpty()) {
	      return false;
	    }
	    if (isTokenExpired(token)) {
	      return false;
	    }
	    return true;
	}
}

