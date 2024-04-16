package com.elice.kittyandpuppy.module.member.dto;

import com.elice.kittyandpuppy.global.Address;
import com.elice.kittyandpuppy.module.member.entity.MemberDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDetailsDto {
    private String name;
    private String phone;
    private Address address;
    private String token;

    public MemberDetails toEntity() {
        MemberDetails memberDetails = new MemberDetails();
        memberDetails.setPhone(this.phone);
        memberDetails.setName(this.name);
        memberDetails.setAddress(this.address);
        return memberDetails;
    }
}
