package com.elice.kittyandpuppy.global;

import lombok.Builder;
import lombok.Getter;

/**
 * Address 객체
 *
 * <p> 우편번호 예외 메세지 <br>
 * - 우편번호가 숫자가 아닌 경우: "우편번호는 숫자로 이뤄져있습니다." <br>
 * - 우편번호가 5자리가 아닌 경우: "우편번호는 5자리 입니다."
 *
 * @zipCode: 우편번호
 * @street: 도로명 주소
 * @detail: 상세 주소
 *
 */
@Getter
public class Address {

    private String zipCode;
    private String street;
    private String detail;

    @Builder
    public Address(String zipCode, String street, String detail) {

        zipCodeValidator(zipCode);

        this.zipCode = zipCode;
        this.street = street;
        this.detail = detail;
    }

    private void zipCodeValidator(String zipCode) {
        try {
            Integer.parseInt(zipCode);
        } catch (NumberFormatException exception) {
            throw new RuntimeException("우편번호는 숫자로 이뤄져있습니다.");
        }

        if (zipCode.length() != 5) {
            throw new RuntimeException("우편번호는 5자리 입니다.");
        }
    }
}
