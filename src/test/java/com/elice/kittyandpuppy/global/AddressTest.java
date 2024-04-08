package com.elice.kittyandpuppy.global;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

// TODO: 커스텀 예외 처리시 Exception class 를 바꿔야 함
public class AddressTest {

    @DisplayName("주소 객체 생성 테스트")
    @Test
    public void addAddressTest() {
        // given
        String zipCode = "12345";
        String street = "서울로 123";
        String detail = "상세주소";

        // when
        Address address = new Address(zipCode, street, detail);

        // then
        assertEquals(address.getZipCode(), zipCode);
        assertEquals(address.getStreet(), street);
        assertEquals(address.getDetail(), detail);
    }

    @DisplayName("우편번호가 문자인 경우 실패 테스트")
    @Test
    public void zipCodeValidatorTest1() {
        String zipCode = "우편번호";
        String street = "서울로 123";
        String detail = "상세주소";

        assertThatThrownBy(() -> new Address(zipCode, street, detail))
                .isInstanceOf(RuntimeException.class);
    }

    @DisplayName("우편번호가 5자리 숫자가 아닌 경우 실패 테스트")
    @Test
    public void zipCodeValidatorTest2() {
        String zipCodeShort = "123";
        String zipCodeLong = "123456";
        String street = "서울로 123";
        String detail = "상세주소";

        assertThatThrownBy(() -> new Address(zipCodeShort, street, detail))
                .isInstanceOf(RuntimeException.class);

        assertThatThrownBy(() -> new Address(zipCodeLong, street, detail))
                .isInstanceOf(RuntimeException.class);
    }

    @DisplayName("주소가 비어있는 경우 실패 테스트")
    @Test
    public void emptyValidatorTest() {
        String zipCode = "우편번호";
        String street = "서울로 123";
        String detail = "상세주소";

        assertThatThrownBy(() -> new Address(zipCode, "", detail))
                .isInstanceOf(RuntimeException.class);

        assertThatThrownBy(() -> new Address(zipCode, street, ""))
                .isInstanceOf(RuntimeException.class);
    }
}
