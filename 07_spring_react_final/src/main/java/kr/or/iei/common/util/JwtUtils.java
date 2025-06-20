package kr.or.iei.common.util;

import java.util.Calendar;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	
	//applicatio.properties에 작성된 값 읽어오기
	@Value("${jwt.sevre-key}")
	private String jwtSevreKey;
	
	@Value("${jwt.expire-minute}")
	private int jwtExpireMinute;
	
	@Value("${jwt.expire-hour-refresh}")
	private String jwtExpireHourRefresh;
	
	//AccessToken 발급 메소드
	public String createAccessToken(String memberId, int memberLevel) {
		//1. 내부에서 사용할 방식으로, 정의한 key 반환
		SecretKey key = Keys.hmacShaKeyFor(jwtSevreKey.getBytes());
		
		//2. 토큰 생성시간 및 만료시간 설정
		
		Calendar calendar = Calendar.getInstance();					//현재시간
		Date startTimeDate = calendar.getTime();					//현재시간 == 유효 시작시간
		calendar.add(Calendar.MINUTE, jwtExpireMinute); 			//현재시간 + 10분 == 유효 만료 시간
		
		Date expireTime = calendar.getTime();						//만료시간
		
		//3. 토큰 생성
		String accessToken = Jwts.builder()							//builder를 이용해 토큰 생성
								 .issuedAt(startTimeDate)			//시작시간
								 .expiration(expireTime)			//만료시간
								 .signWith(key)						//암호화 서명
								 .claim("memberId", memberId)		//토큰 포함 정보(key ~ value 형태)
								 .claim("memberLevel", memberLevel) //토큰 포함 정보(key ~ value 형태)
								 .compact();						//생성
		
		return accessToken;
	}
	
	//RefreshToken 발급 메소드
		public String createRefreshToken(String memberId, int memberLevel) {
			//1. 내부에서 사용할 방식으로, 정의한 key 반환
			SecretKey key = Keys.hmacShaKeyFor(jwtSevreKey.getBytes());
			
			//2. 토큰 생성시간 및 만료시간 설정
			
			Calendar calendar = Calendar.getInstance();					//현재시간
			Date startTimeDate = calendar.getTime();					//현재시간 == 유효 시작시간
			calendar.add(Calendar.HOUR, jwtExpireMinute); 				//현재시간 + 10분 == 유효 만료 시간
			
			Date expireTime = calendar.getTime();						//만료시간
			
			//3. 토큰 생성
			String refreshToken = Jwts.builder()						//builder를 이용해 토큰 생성
									 .issuedAt(startTimeDate)			//시작시간
									 .expiration(expireTime)			//만료시간
									 .signWith(key)						//암호화 서명
									 .claim("memberId", memberId)		//토큰 포함 정보(key ~ value 형태)
									 .claim("memberLevel", memberLevel) //토큰 포함 정보(key ~ value 형태)
									 .compact();						//생성
			
			return refreshToken;
		}	
}
