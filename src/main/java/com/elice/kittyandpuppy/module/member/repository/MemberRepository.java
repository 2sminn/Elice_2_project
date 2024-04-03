package com.elice.kittyandpuppy.module.member.repository;

import com.elice.kittyandpuppy.module.member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

}
