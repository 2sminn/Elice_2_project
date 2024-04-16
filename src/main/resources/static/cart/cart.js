$(document).ready(function () {
    loadCartList();
    $("#product-order").click(function () {
        order();
    });
});

//장바구니 조회
function loadCartList() {
    //TODO JWT 토큰으로 멤버 id를 받을수 있게된다면 이부분 수정
    $.ajax({
        url: '/api/orders/1',
        type: 'GET',
        dataType: 'json',
        success: function (orders) {
            displayOrderItems(orders);
        }
    });
}

// 장바구니 아이템 조회
function displayOrderItems(orders) {
    // List<OrderResponse>에서 List<OrderItemResponse> orderItems; 를 가져와야 하기 때문에
    // (= 리스트 속에 객체 속에 리스트를 받아와야 함)
    // 일단 orders.orderItems[0]를 사용하여 받아옴.
    // 장바구니 모양을 클릭했을때 orderResponse 의 index번호를 잘 받아올수 있다면 아래코드 수정하면 됨
    //TODO 추후 OrderResponse의 index 번호를 받아올수 있다면 밑에 코드를 수정해주면 되겠다!
    let orderList = orders[0].orderItems;
    let cartList = $('#cart_list');
    cartList.empty();
    orderList.forEach(function (orderItem) {
        let price = calPrice(orderItem.amount, orderItem.price)
        let cartElement = $(`
            <div class="product-box"> <!--상품 1개-->
                <div class="product-item">
                    <input type="checkbox" class="product-check">
                    <div><img src="/static/order/test_item.png" class="product-img"></div>
                    <div class="product-detail-box">
                        <div class="detail-name">${orderItem.name}</div>
                        <div class="detail-checkbox">
                            <button class="product-minus">-</button>
                            <div class="product-count">
                                ${orderItem.amount}
                            </div>
                            <button class="product-plus">+</button>
                        </div>
                        <div><span class="detail-price">${price}</span>원</div>
                    </div>
                    <img src="/static/img/comment-delete2.png" class="product-delete">
                </div>
            </div>
        `);
        cartElement.find('.product-item').data('id', orderItem.id);
        cartElement.find('.detail-price').data('price', orderItem.price);
        cartElement.find('.product-count').data('count', orderItem.amount);
        cartList.append(cartElement);
    });
}

//주문하기
function order(){
    $.ajax({
        //TODO 모든 api와 view의 연결작업이 끝나고 url(localhost:8080/cart)에는
        // 쿼리파라미터로 {orderId}가 들어갔을테니 그 때 수정하자
        url:'/api/order/1',
        type: 'GET',
        data: 'text',
        success: function (response) {
            location.href = "/order";
        }
    })
}

//수량에 비례하여 가격 조정하는 메소드
function calPrice(amount, price){
    return amount*price;
}
//삭제
$(document).on('click', '.product-delete', function() {
    let id = $(this).closest('.product-box').find('.product-item').data('id');
    $.ajax(
        {
            url: `/api/orderItem/${id}`,
            type: 'DELETE',
            dataType: 'text',
            success: function (response) {
                loadCartList();
            }
        }
    );
});


//수량 감소
$(document).on('click', '.product-minus', function () {
    let id = $(this).closest('.product-box').find('.product-item').data('id');
    let countElement = $(this).siblings('.product-count');
    let amount = parseInt(countElement.text());
    if (amount > 1) {
        amount--;
        changeCount(id, amount);
    }
});
//수량 증가
$(document).on('click', '.product-plus', function () {
    let id = $(this).closest('.product-box').find('.product-item').data('id');
    let countElement = $(this).siblings('.product-count');
    let amount = parseInt(countElement.text());
    amount++;
    changeCount(id, amount);

});
//수량 변경
function changeCount(id, amount) {
    let requestData = {amount: amount};
    $.ajax({
        url: `/api/orderItem/${id}`,
        type: 'PUT',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(requestData),
        success: function (orderItemResponse) {
            loadCartList();
        }
    })
}

