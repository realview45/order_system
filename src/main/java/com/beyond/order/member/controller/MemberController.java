package com.beyond.order.member.controller;

import com.beyond.order.common.auth.JwtTokenProvider;
import com.beyond.order.member.domain.Member;
import com.beyond.order.member.dtos.MemberCreateDto;
import com.beyond.order.member.dtos.MemberDetailDto;
import com.beyond.order.member.dtos.MemberListDto;
import com.beyond.order.member.dtos.MemberLoginDto;
import com.beyond.order.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    public MemberController(MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    //회원가입
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid MemberCreateDto dto){
        Long id = memberService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
    //유저, 어드민로그인
    @PostMapping("/doLogin")
    public String login(@RequestBody @Valid MemberLoginDto dto){
        Member member = memberService.login(dto);
        String accessToken = jwtTokenProvider.createToken(member);
//        refresh토큰생성
//        MemberLoginResDto memberLoginResDto = MemberLoginResDto.builder()
//                .accessToken(accessToken)
//                .refreshToken(null)
//                .build();
        return accessToken;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public List<MemberListDto> findAll(){
        return memberService.findAll();
    }
    @GetMapping("/myinfo")
    public MemberDetailDto myinfo(){
        return memberService.myinfo();
    }
    @GetMapping("/detail/{id}")
    public MemberDetailDto findById(@PathVariable Long id){
        return memberService.findById(id);
    }
}
