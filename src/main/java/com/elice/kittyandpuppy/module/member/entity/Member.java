package com.elice.kittyandpuppy.module.member.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true,nullable = false)
    private String email;

    @Column(name = "name", unique = true , length = 10, nullable = false)
    private String name;    // 유저 닉네임

    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @NotNull
    private String deleted = "N";

    // TODO: redis를 사용해서 일시적으로 저장하고 삭제하도록 변경해야 함
    @Column(name = "tid")
    private String tid;

    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void updateTid(String tid) {
        this.tid = tid;
    }
}
