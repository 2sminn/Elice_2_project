package com.elice.kittyandpuppy.module.member.repository;

import com.elice.kittyandpuppy.module.member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Page<Member> findAll(Pageable pageable);
    Optional<Member> findByName(String name);
    Optional<Member> findByEmail(String email);
}
