package com.elice.kittyandpuppy.module.member.mapper;

import com.elice.kittyandpuppy.module.member.entity.Member;
import com.elice.kittyandpuppy.module.member.entity.MemberDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MemberMapper {
    @Mapping(target = "id", ignore = true)
    Member MemberDTOToMember(MemberDto memberDto);
}
