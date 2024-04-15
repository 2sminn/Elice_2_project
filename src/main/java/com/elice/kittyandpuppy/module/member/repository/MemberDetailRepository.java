package com.elice.kittyandpuppy.module.member.repository;

import com.elice.kittyandpuppy.module.member.entity.Member;
import com.elice.kittyandpuppy.module.member.entity.MemberDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberDetailRepository extends JpaRepository<MemberDetails,Long> {
    Optional<MemberDetails> findByMember(Member member);
}
