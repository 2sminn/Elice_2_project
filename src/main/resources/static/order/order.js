let token; // 사용자 token
let orderItemsId; // 주문 상품 Ids
let deliveryId; // 배송지 Id
let orderId; // 주문 Id
let payBy; // 결제 방법
let orderTotalPrice = 0; // 총 금액
let requestsCompleted = 0; // 총 주문 건수

// 주문 페이지 전환 후 자동으로 사용자 주소 가져오기
$(document).ready(function () {

    token = localStorage.getItem("token");

    orderItemsId = localStorage.getItem('orderItemIds');

    if (orderItemsId !== null) {
        orderItemsId = JSON.parse(orderItemsId);
    } else {
        orderItemsId = [];
    }

    getOrderItems();
    getMemberInfo();

});

// 주소 찾기
function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if (data.userSelectedType === 'R') {
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('zipCode').value = data.zonecode;
            document.getElementById("street").value = addr + extraAddr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detail").focus();
        }
    }).open();
}

// 사용자 정보 불러오기
function getMemberInfo() {
    $.ajax({
        url: '/api/address/member',
        type: 'POST',
        async: false,
        data: JSON.stringify({
            token: token
        }),
        contentType: 'application/json',
        success: function (response) {
            document.getElementById('zipCode').value = response.address.zipCode;
            document.getElementById('street').value = response.address.street;
            document.getElementById('detail').value = response.address.detail;
            document.getElementById('receiverName').value = response.name;
            document.getElementById('receiverPhone').value = response.phone;
        }
    });
}

// 주문 상품 불러오기
function getOrderItems() {
    let resultHTML = "";

    // orderItemsId가 존재하고 비어 있지 않은 경우에만 요청을 보냄
    if (orderItemsId && orderItemsId.length > 0) {
        orderItemsId.forEach(function (orderItemId) {
            // 서버에 GET 요청 보내기
            $.ajax({
                url: '/api/orderItem/' + orderItemId,
                type: 'GET',
                async: false,
                success: function (data) {
                    // 성공적으로 데이터를 받아온 경우 처리
                    var item = data; // 받아온 데이터

                    resultHTML += `<div class="item">
                         <div class="img">
                           <img class="image-thumbnail" src="${item.imageUrl}">
                         </div>
                         <div class="detail">
                           <p class="font-middle">${item.name}</p>
                           <p class="font-middle">수량 ${item.amount}개</p>
                           <p class="font-middle">${new Intl.NumberFormat("ko-KR").format(item.totalPrice)}원</p>
                         </div>
                       </div>`

                    requestsCompleted++;
                    orderTotalPrice += item.totalPrice;

                    // 모든 요청이 완료되었을 때 결과를 출력합니다.
                    if (requestsCompleted === orderItemsId.length) {
                        document.querySelector("#orderItemBox").innerHTML = resultHTML;
                        document.querySelector("#totalPrice").innerHTML = `<p class="font-middle">${new Intl.NumberFormat("ko-KR").format(orderTotalPrice)}원</p>`;
                        document.querySelector("#count").innerHTML = `<p style="margin-right: 10px;">총 <span style="color: #F0AB0F;" id="orderItemCount">${requestsCompleted}</span>건</p>`;
                        document.querySelector("#result").innerHTML = `<p><span style="color: #F0AB0F;" id="resultPrice">${new Intl.NumberFormat("ko-KR").format(orderTotalPrice)}</span>원</p>`;
                    }
                },
                error: function (xhr, status, error) {
                    // 요청이 실패한 경우 콘솔에 오류 메시지 출력
                    console.error('GET 요청 실패:', status, error);
                }
            });
        });
    } else {
        console.log('orderItemsId가 존재하지 않거나 비어 있습니다.');
    }
}

// 결제하기
function order() {

    // 결제 방법 선택
    try {
        payBy = document.querySelector('input[name="payBy"]:checked').value;
    } catch (err) {
        alert("결제 방식을 선택해주세요.");
        return;
    }

    // 체크박스가 체크되어 있으면 회원 배송지 정보 업데이트 하기
    let checkbox = document.getElementById("deliveryCheckbox");

    if (checkbox.checked) {
        $.ajax({
            url: '/api/details',
            type: 'POST',
            async: false,
            data: JSON.stringify({
                address: {
                    zipCode: document.getElementById('zipCode').value,
                    street: document.getElementById('street').value,
                    detail: document.getElementById('detail').value
                },
                name: document.getElementById('receiverName').value,
                phone: document.getElementById('receiverPhone').value,
                token: token
            }),
            contentType: 'application/json',
            success: function (response) {
                if (response === true) {
                    console.log('API 호출 성공');
                }
            },
            error: function (xhr, status, error) {
                console.error('API 호출 실패:', status, error);
            }
        });
    }

    // 배송지 정보 저장
    $.ajax({
        url: '/api/address/delivery',
        type: 'POST',
        async: false,
        data: JSON.stringify({
            address: {
                zipCode: document.getElementById('zipCode').value,
                street: document.getElementById('street').value,
                detail: document.getElementById('detail').value
            },
            deliveryName: document.getElementById('receiverName').value,
            deliveryPhone: document.getElementById('receiverPhone').value
        }),
        contentType: 'application/json',
        success: function (response) {
            deliveryId = response;
        },
        error: function (xhr, status, error) {
            console.error('API 호출 실패:', status, error);
        }
    });

    // 주문 정보 저장
    $.ajax({
        url: '/api/order',
        type: 'POST',
        async: false,
        data: JSON.stringify({
            token: token,
            deliveryId: deliveryId,
            orderItemIds: orderItemsId,
            payment: payBy
        }),
        contentType: 'application/json',
        success: function (response) {
            orderId = response;
        },
        error: function (xhr, status, error) {
            console.error('API 호출 실패:', status, error);
        }
    });

    localStorage.removeItem('orderItemIds');

    if (payBy === 'vbank') {
        AUTHNICE.requestPay({
            clientId: 'S2_3c0c9aed5cd44acb9d4a155c9bb74371',
            method: payBy,
            orderId: orderId,
            amount: orderTotalPrice,
            vbankHolder: '구해줘멍냥',
            goodsName: '구해줘멍냥',
            returnUrl: 'http://localhost:8080/nicepay', //API를 호출할 Endpoint 입력
            fnError: function (result) {
                alert('개발자확인용 : ' + result.errorMsg + '')
            }
        });
    } else {
        AUTHNICE.requestPay({
            clientId: 'S2_3c0c9aed5cd44acb9d4a155c9bb74371',
            method: payBy,
            orderId: orderId,
            amount: orderTotalPrice,
            goodsName: '구해줘멍냥',
            returnUrl: 'http://localhost:8080/nicepay', //API를 호출할 Endpoint 입력
            fnError: function (result) {
                alert('개발자확인용 : ' + result.errorMsg + '')
            }
        });
    }

}

// 결제 승인창에서 정상 결제시 메세지를 전송함
window.addEventListener("message", receiveMessage, false);
function receiveMessage(event) {
    console.log(event);
    var receivedData = JSON.parse(event.data);
    var type = receivedData.type;
    if (type === 'error') {
        location.replace("/order/fail" + "?orderId=" + orderId);
    } else {
        location.replace("/order/success" + "?orderId=" + orderId);
    }
}
