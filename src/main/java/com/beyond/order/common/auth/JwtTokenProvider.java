package com.beyond.order.common.auth;

import com.beyond.order.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secretKey}")
    private String st_secret_key;
    private Key secret_key;
    //서버키 디코딩 PostConstruct를 이용해 Value 초기화보다 늦은 순서 보장
    @PostConstruct
    public void init(){
        secret_key = new SecretKeySpec(Base64.getDecoder().decode(st_secret_key),
                SignatureAlgorithm.HS512.getJcaName());
    }
    public String createToken(Member member){
//        sub : abc@naver.com 형태
        Claims claims = Jwts.claims().setSubject(member.getEmail());
//        주된 키값을 제외한 나머지 정보는 put을 사용하여 key:value세팅
        claims.put("role", member.getRole().toString());
//        ex)claims.put("age", member.getAge()); 형태가능

        Date now = new Date();
//        토큰의 구성요소 : 헤더, 페이로드, 시그니처(서명부)
        String token = Jwts.builder()
//                아래 3가지 요소는 페이로드
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+30*60*1000L))//30분:30*60초*1000밀리초 : 밀리초형태로 변환
//              secret키를 통해 서명값(signature) 생성
                .signWith(secret_key)
                .compact();
        return token;
    }
}
