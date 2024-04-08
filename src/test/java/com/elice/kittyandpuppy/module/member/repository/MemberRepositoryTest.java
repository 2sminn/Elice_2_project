package com.elice.kittyandpuppy.module.member.repository;

import com.elice.kittyandpuppy.module.member.entity.Member;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("입력형식 확인")
    void checkValid() {
        // given
        Member member = new Member("elice", "elice", "1234");

        // when

        // then
        Throwable e = assertThrows(ConstraintViolationException.class, () -> memberRepository.save(member));
        System.out.println("error : " + e.getMessage());
    }

    @Test
    @DisplayName("Member 저장")
    void save() {
        // given
        Member member = new Member("elice", "rabbit@elice.io", "elice123");

        // when
        Member savedMember = memberRepository.save(member);

        // then
        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getName()).isEqualTo("elice");
        assertThat(savedMember.getEmail()).isEqualTo("rabbit@elice.io");
        assertThat(savedMember.getPassword()).isEqualTo("elice123");
    }

    @Test
    @DisplayName("Member findById")
    void findById() {
        // given
        Member member = new Member("elice", "rabbit@elice.io", "elice123");

        // when
        Member savedMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(savedMember.getId()).get();

        // then
        assertThat(findMember.getId()).isEqualTo(savedMember.getId());
        assertThat(findMember.getName()).isEqualTo("elice");
        assertThat(findMember.getEmail()).isEqualTo("rabbit@elice.io");
        assertThat(findMember.getPassword()).isEqualTo("elice123");
    }

    @Test
    @DisplayName("findAll")
    void findAll() {
        // given
        Member member1 = new Member("elice1", "rabbit1@elice.io", "elice1234");
        Member member2 = new Member("elice2", "rabbit2@elice.io", "elice1234");

        // when
        memberRepository.save(member1);
        memberRepository.save(member2);
        List<Member> members = memberRepository.findAll();

        // then
        assertThat(members.size()).isEqualTo(2);
        assertThat(members).contains(member1, member2);

    }

}