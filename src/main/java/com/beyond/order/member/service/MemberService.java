package com.beyond.order.member.service;

import com.beyond.order.member.domain.Member;
import com.beyond.order.member.dtos.MemberCreateDto;
import com.beyond.order.member.dtos.MemberDetailDto;
import com.beyond.order.member.dtos.MemberListDto;
import com.beyond.order.member.dtos.MemberLoginDto;
import com.beyond.order.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void create(MemberCreateDto dto) {
        memberRepository.save(dto.toEntity(passwordEncoder.encode(dto.getPassword())));
    }

    public List<MemberListDto> findAll() {
        return memberRepository.findAll().stream().map(m->MemberListDto.fromEntity(m)).collect(Collectors.toList());
    }

    public Member login(MemberLoginDto dto) {
        Optional<Member> member = memberRepository.findByEmail(dto.getEmail());
        boolean login = true;
        if(member.isPresent()){
            if(!passwordEncoder.matches(dto.getPassword(), member.get().getPassword())) {
                login = false;
            }
        }
        else{
            login=false;
        }
        if(!login){
            throw new EntityNotFoundException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }
        return member.get();
    }

    public MemberDetailDto findById(Long id) {
        Member member =memberRepository.findById(id).orElseThrow(()->new EntityNotFoundException("엔티티가 없습니다."));
        return MemberDetailDto.fromEntity(member);
    }


//    public MemberDetailDto myinfo() {
//        return
//    }

}
