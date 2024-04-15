$(document).ready(function () {
    //TODO JWT 토큰으로 멤버 id를 받을수 있게된다면 이부분 수정
    $.ajax({
        url: '/api/orders/1',
        type: 'GET',
        dataType: 'json',
        success: function (orders) {
            displayOrderItems(orders);
        }
    });
    $('.check_all').change(function() {
        let isChecked = $(this).prop('checked');
        $('.product-check').prop('checked', isChecked);
    });
});

// 장바구니 조회
function displayOrderItems(orders) {
    // List<OrderResponse>에서 List<OrderItemResponse> orderItems; 를 가져와야 하기 때문에
    // (= 리스트 속에 객체 속에 리스트를 받아와야 함)
    // 일단 orders.orderItems[0]를 사용하여 받아옴.
    // 장바구니 모양을 클릭핶을때 orderResponse 의 index번호를 잘 받아올수 있다면 아래코드 수정하면 됨
    //TODO 추후 OrderResponse의 index 번호를 받아올수 있다면 밑에 코드를 수정해주면 되겠다!
    let orderList = orders[0].orderItems;
    let cartList = $('#cart_list');
    cartList.empty();
    orderList.forEach(function(orderItem) {
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
                        <div><span class="detail-price">${orderItem.price}</span>원</div>
                    </div>
                </div>
            </div>
        `);
        cartElement.find('.detail-price').data('price', orderItem.price);
        cartElement.find('.product-count').data('count', orderItem.amount);
        cartList.append(cartElement);
    });
}

//