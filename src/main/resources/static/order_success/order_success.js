// URL에서 쿼리 매개변수를 가져오는 함수
function getUrlParameter(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    var results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}

$(document).ready(function () {
    var orderId = getUrlParameter('orderId');
    let orderHTML = ""; // Initialize as an empty string
    let paymentHTML = ""; // Initialize as an empty string
    let orderItemsHTML = ""; // Initialize as an empty string
    console.log(orderId);
    $.ajax({
        url: '/api/order/' + orderId,
        type: 'GET',
        async: false,
        contentType: 'application/json',
        success: function (response) {
            console.log(response)
            var orderId = response.id;
            var orderItems = response.orderItems;
            var deliveryAddress = response.deliveryAddress;
            var totalPrice = response.totalPrice; // Corrected property name
            var payment = response.payment;
            var date = response.date;

            if (payment === "card") {
                payment = "신용·체크카드";
            } else if (payment === "kakao") {
                payment = "카카오 페이";
            }

            // 결제 상품
            if (orderItems && orderItems.length > 0) {
                orderItems.forEach(function (orderItem) {
                    orderItemsHTML += `
                        <div class="item">
                          <div class="img">
                            <img class="image-thumbnail" src="${orderItem.imageUrl}">
                          </div>
                          <div class="detail">
                           <p class="font-middle" style="margin-top: 25px">${orderItem.name}</p>
                           <p class="font-middle" style="margin-top: 25px">수량 ${orderItem.amount}개</p>
                           <p class="font-middle" style="margin-top: 25px">${new Intl.NumberFormat("ko-KR").format(orderItem.totalPrice)}원</p>
                          </div>
                       </div>`
                });
            }

            // 결제 정보
            orderHTML += `
                        <div class="column">
                          <p class="font-middle">주문 번호</p>
                          <p class="font-middle" style="position: absolute; left: 100px">${orderId}</p>
                        </div>
                        <div class="column">
                          <p class="font-middle">주문 날짜</p>
                          <p class="font-middle" style="position: absolute; left: 100px">${date}</p>
                        </div>
                        <div class="column">
                          <p class="font-middle">받는 사람</p>
                          <p class="font-middle" style="position: absolute; left: 100px">${deliveryAddress.receiverName}</p>
                        </div>
                        <div class="column">
                          <p class="font-middle">연락처</p>
                          <p class="font-middle" style="position: absolute; left: 100px">${deliveryAddress.receiverPhone}</p>
                        </div>
                        <div class="column">
                          <p class="font-middle">주소</p>
                          <p class="font-middle" style="position: absolute; left: 100px">${deliveryAddress.address.zipCode}<br>${deliveryAddress.address.street}<br>${deliveryAddress.address.detail}</p>
                        </div>`

            document.querySelector("#orderHistory").innerHTML = orderHTML;

            // 결제 상품 추가
            document.querySelector("#order-items-box").innerHTML = orderItemsHTML;

            // 결제 상세
            paymentHTML += `
                        <p class="font-middle">${new Intl.NumberFormat("ko-KR").format(totalPrice)}원</p>
                        <p class="font-middle">${payment}</p>`

            document.querySelector("#payment").innerHTML = paymentHTML;

        },
        error: function (xhr, status, error) {
            // 요청이 실패한 경우 콘솔에 오류 메시지 출력
            console.error('GET 요청 실패:', status, error);
        }
    });
});
