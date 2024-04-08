package com.elice.kittyandpuppy.module.member.service;

import com.elice.kittyandpuppy.module.member.entity.Member;
import com.elice.kittyandpuppy.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Member join(Member member) {
        Member savedMember = memberRepository.save(member);
        return savedMember;
    }

    @Transactional(readOnly = true)
    public Optional<Member> findMemberById(Long id) {
        Optional<Member> findMember = memberRepository.findById(id);
        return findMember;
    }

    @Transactional(readOnly = true)
    public List<Member> findAll() {
        List<Member> members = memberRepository.findAll();
        return members;
    }

    @Transactional
    public Member editMember(Long id, Member member) {
        Member findMember = memberRepository.findById(id).get();
        findMember.setName(member.getName());
        return findMember;
    }

    @Transactional(readOnly = true)
    public boolean checkEmail(String email){
        Member member = memberRepository.findByEmail(email).orElse(null);
        if(member==null) return false;
        return true;
    }

    @Transactional(readOnly = true)
    public boolean checkName(String name) {
        Member member = memberRepository.findByName(name).orElse(null);
        if(member==null) return false;
        return true;
    }
}
