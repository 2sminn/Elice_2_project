package com.elice.kittyandpuppy.module.member.mapper;

import com.elice.kittyandpuppy.module.member.dto.MemberSaveDto;
import com.elice.kittyandpuppy.module.member.entity.Member;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-09T21:31:41+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member MemberDTOToMember(MemberSaveDto memberSaveDto) {
        if ( memberSaveDto == null ) {
            return null;
        }

        Member member = new Member();

        member.setEmail( memberSaveDto.getEmail() );
        member.setName( memberSaveDto.getName() );
        member.setPassword( memberSaveDto.getPassword() );

        return member;
    }
}
