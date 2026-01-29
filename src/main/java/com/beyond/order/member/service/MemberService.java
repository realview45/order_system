package com.beyond.order.member.service;

import com.beyond.order.member.domain.Member;
import com.beyond.order.member.dtos.MemberCreateDto;
import com.beyond.order.member.dtos.MemberDetailDto;
import com.beyond.order.member.dtos.MemberListDto;
import com.beyond.order.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void create(MemberCreateDto dto) {
        memberRepository.save(dto.toEntity());
    }

    public List<MemberListDto> findAll() {
        return memberRepository.findAll().stream().map(m->MemberListDto.fromEntity(m)).collect(Collectors.toList());
    }

    public String login() {
        return "";
    }

    public MemberDetailDto findById(Long id) {
        Member member =memberRepository.findById(id).orElseThrow(()->new EntityNotFoundException("엔티티가 없습니다."));
        return MemberDetailDto.fromEntity(member);
    }


//    public MemberDetailDto myinfo() {
//        return
//    }

}
