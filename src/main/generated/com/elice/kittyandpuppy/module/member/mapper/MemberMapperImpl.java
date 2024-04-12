package com.elice.kittyandpuppy.module.member.mapper;

import com.elice.kittyandpuppy.module.member.dto.MemberSaveDto;
import com.elice.kittyandpuppy.module.member.entity.Member;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
<<<<<<< HEAD
    date = "2024-04-12T10:31:36+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
=======
    date = "2024-04-12T13:20:05+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
>>>>>>> c98af4efe7d19e5b2cb8004b8ffb1fd6276d6d02
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
