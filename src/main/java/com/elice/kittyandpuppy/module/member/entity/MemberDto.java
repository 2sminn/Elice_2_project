package com.elice.kittyandpuppy.module.member.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class MemberDto {
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$")
    private String email;

    @NotNull
    @Size(min = 2, max = 8)
    private String name;

    @NotNull
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z]).{8,20}")
    private String password;
}
