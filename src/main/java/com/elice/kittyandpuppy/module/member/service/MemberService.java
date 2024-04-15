package com.elice.kittyandpuppy.module.member.service;

import com.elice.kittyandpuppy.global.jwt.TokenProvider;
import com.elice.kittyandpuppy.module.member.entity.Member;
import com.elice.kittyandpuppy.module.member.entity.MemberDetails;
import com.elice.kittyandpuppy.module.member.repository.MemberDetailRepository;
import com.elice.kittyandpuppy.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberDetailRepository memberDetailRepository;
    private final TokenProvider tokenProvider;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private String key = "elice";
    @Transactional
    public Member join(Member member) {
        // password encoding
        String password = passwordEncoder.encode(member.getPassword()+key);

        member.setPassword(password);
        Member savedMember = memberRepository.save(member);
        MemberDetails memberDetails = new MemberDetails(savedMember);
        memberDetailRepository.save(memberDetails);

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

    @Transactional(readOnly = true)
    public String loginMember(String email,String password){
        Member member = memberRepository.findByEmail(email).orElse(null);
        if(member!=null){
            if(passwordEncoder.matches(password+key,member.getPassword())){
                String jwt = tokenProvider.generateToken(member, Duration.ofHours(1));
                return jwt;
            }
        }
        return null;
    }

    @Transactional
    public Member editMember(Long id, String name) {
        Member findMember = memberRepository.findById(id).get();
        findMember.setName(name);
        return findMember;
    }

    @Transactional(readOnly = true)
    public boolean checkEmail(String email){
        Member member = memberRepository.findByEmail(email).orElse(null);
        if(member==null) return true;
        return false;
    }

    @Transactional(readOnly = true)
    public boolean checkName(String name) {
        Member member = memberRepository.findByName(name).orElse(null);
        if(member==null) return true;
        return false;
    }
}
