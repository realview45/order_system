package com.beyond.order.member.controller;

import com.beyond.order.member.dtos.MemberCreateDto;
import com.beyond.order.member.dtos.MemberDetailDto;
import com.beyond.order.member.dtos.MemberListDto;
import com.beyond.order.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    //회원가입
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid MemberCreateDto dto){
        memberService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("ok");
    }
    //유저, 어드민로그인
    @PostMapping("/doLogin")
    public void login(){
        memberService.login();
    }
    @GetMapping("/list")
    public List<MemberListDto> findAll(){
        return memberService.findAll();
    }
//    @GetMapping("/myinfo")
//    public MemberDetailDto myinfo(){
//        return memberService.myinfo();
//    }
    @GetMapping("/detail/{id}")
    public MemberDetailDto findById(@PathVariable Long id){
        return memberService.findById(id);
    }
}
