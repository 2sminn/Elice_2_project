$(document).ready(function () {
    loadCartList();
    //
    // $("#product-order").click(function () {
    //     order();
    // });

});

//장바구니 조회
function loadCartList() {
    //로컬스토리지에서 장바구니 아이템(productId가 단긴 리스트 객체) 리스트 받아옴
    let cart = getCart(); //해당 코드는 let cart = [ 1, 2, 3, ... ] 과 같은 리스트 이다.
    cart = cart.filter(item => item !== '');
    $.ajax({
        url:'/api/product/cart',
        type:'GET',
        data: { cartList: cart },
        dataType: 'JSON',
        success: function(cartItems){
            displayOrderItems(cartItems);
        },
        error: function(xhr, status, error) {
            console.error('장바구니를 불러오는 중 오류가 발생했습니다:', status, error);
        }
    })
}
function getCart() {
    return JSON.parse(window.localStorage.getItem("cartList")) || [];
}

// 장바구니 아이템 조회
function displayOrderItems(cartItems) {
    // html #cart_list 요소 선택
    let cartList = $('#cart_list');
    cartList.empty();
    cartItems.forEach(function (orderItem) {
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
//
// // 주문하기 버튼
// function order(){
//     $.ajax({
//         //TODO 모든 api와 view의 연결작업이 끝나고 url(localhost:8080/cart)에는
//         // 쿼리파라미터로 {orderId}가 들어갔을테니 그 때 수정하자
//         url:'/api/order/1',
//         type: 'GET',
//         data: 'text',
//         success: function (response) {
//             //장바구니 삭제
//             window.localStorage.removeItem("cartList");
//             location.href = "/order";
//         }
//     })
// }
//수량에 비례하여 가격 조정하는 메소드
function calPrice(amount, price){
    return amount*price;
}
//삭제
$(document).on('click', '.product-delete', function() {
    let id = $(this).closest('.product-box').find('.product-item').data('id');
    removeProductFromList(id);
    loadCartList();
    // $.ajax(
    //     {
    //         url: `/api/orderItem/${id}`,
    //         type: 'DELETE',
    //         dataType: 'text',
    //         success: function (response) {
    //             loadCartList();
    //         }
    //     }
    // );
});
function removeProductFromList(productId) {
    // 로컬 스토리지에서 리스트 가져오기
    let productList = JSON.parse(window.localStorage.getItem("productList")) || [];
    // 상품 ID를 리스트에서 제거
    const index = productList.indexOf(productId);
    if (index !== -1) {
        productList.splice(index, 1);
    }
    // 업데이트된 리스트를 다시 로컬 스토리지에 저장
    window.localStorage.setItem("productList", JSON.stringify(productList));
}


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

