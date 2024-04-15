package com.elice.kittyandpuppy.module.member.service;

import com.elice.kittyandpuppy.module.member.entity.Member;
import com.elice.kittyandpuppy.module.member.entity.MemberDetails;
import com.elice.kittyandpuppy.module.member.repository.MemberDetailRepository;
import com.elice.kittyandpuppy.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberDetailService {
    private final MemberDetailRepository memberDetailRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public MemberDetails findByMemberID(Long id){
        Member findMember = memberRepository.findById(id).orElse(null);
        if(findMember!=null){
            MemberDetails memberDetails = memberDetailRepository.findByMember(findMember).orElse(null);
            return memberDetails;
        }
        return null;
    }

    @Transactional
    public MemberDetails update(Long id, MemberDetails memberDetails){
        MemberDetails findMemberDetails = findByMemberID(id);
        if(findMemberDetails!=null){
            findMemberDetails.setName(memberDetails.getName());
            findMemberDetails.setPhone(memberDetails.getPhone());
            findMemberDetails.setAddress(memberDetails.getAddress());

            return findMemberDetails;
        }
        return null;
    }
}
