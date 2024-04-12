package com.elice.kittyandpuppy.module.member.entity;

import com.elice.kittyandpuppy.global.Address;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@NoArgsConstructor
@Table(name = "member_details")
public class MemberDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Embedded
    private Address address;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public MemberDetails(Member member) {
        this.member = member;
    }
}
