package com.elice.kittyandpuppy.module.member.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveDto {
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$")
    private String email;

    @NotNull
    @Size(min = 2, max = 10)
    private String name;

    @NotNull
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z]).{8,20}")
    private String password;
}
