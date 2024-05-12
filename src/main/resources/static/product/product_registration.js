$(document).ready(function() {
    $('.productPrice').on('input', function() {
        var value = $(this).val();
        var messageSpan = $('#priceMessage1');

        // 숫자만 있는지 확인
        if (!/^\d+$/.test(value) && value !== '') {
            messageSpan.text('가격: 가격은 숫자만 입력해야 합니다.'); // 메시지 표시
            $(this).css('color', 'red');

        } else {
            messageSpan.text(''); // 메시지 제거
            $(this).css('color', 'black');
        }
    });

    $('.productDiscount').on('input', function() {
        var value = $(this).val();
        var messageSpan = $('#priceMessage2');

        // 숫자만 있는지 확인
        if (!/^\d+$/.test(value) && value !== '') {
            messageSpan.text('할인율: 할인율은 숫자만 입력해야 합니다'); // 메시지 표시
            $(this).css('color', 'red');
        } else if(value < 0 || value > 100){
            messageSpan.text('할인율: 할인율은 0과 100사이만 입력이 가능합니다'); // 메시지 표시
            $(this).css('color', 'red');
        } else {
            messageSpan.text(''); // 메시지 제거
            $(this).css('color', 'black');
        }
    });

});