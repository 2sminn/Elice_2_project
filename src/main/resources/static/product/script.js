$(document).ready(function () {
    loadProduct();
    $("#add-to-cart").click(function () {
        const productId = $('#product-name').data('productId');
        addToCart(productId);
    });
    $("#buy-now").click(function () {
        const productId = $('#product-name').data('productId');
        buyProduct(productId);
    });
});

function loadProduct(){
    //추후에 사용
    const productId = window.location.pathname.split('/').pop();
    $.ajax({
        //TODO 추후에 productId를 받을수 있다면 수정할 예정입니다.
        url: `/api/product/${productId}`,
        type: 'GET',
        dataType: 'json',
        success: function (product) {
            displayProduct(product);
            $('#product-name').data('productId', product.id);
            // window.localStorage.setItem("productId", product.id);
        }
    });
}
function displayProduct(product){
    $('#product-name').html(product.name);
    $('#product-price').html(product.price);
    $('#product-description').html(product.description);
    $('#image-box').append(`<img id="product-image" src="${product.imageUrl}" alt="상품 이미지">`);

}

//장바구니 버튼을 눌렀을 때
function addToCart(productId){
    $.ajax({
        url:`/api/orderItem/${productId}`,
        type: 'POST',
        dataType: 'json',
        success: function(orderItem){
            alert("장바구니에 담겼습니다.");
            if(localStorage.getItem('orderItemIds')!= null){
                let orderItems = JSON.parse(localStorage.orderItems);
                orderItems.push(orderItem.id);
                localStorage.setItem('orderItemIds',JSON.stringify(orderItems));

            } else{
                localStorage.setItem('orderItemIds',JSON.stringify([orderItem.id]));
            }
        },
        error: function(xhr, status, error) {
            console.error('장바구니를 담는 중 오류 발생:', status, error);
        }
    });


    console.log(window.localStorage.getItem("orderItemIds"));
}

//구매하기 버튼 눌렀을 때
function buyProduct(productId){
    $.ajax({
        url: `/api/orderItem/${productId}`,
        type: 'POST',
        dataType: 'json',
        success: function (orderItem) {
            alert("장바구니에 담겼습니다.");
            if(localStorage.getItem('orderItemIds')!= null){
                let orderItems = JSON.parse(localStorage.orderItems);
                orderItems.push(orderItem.id);
                localStorage.setItem('orderItemIds',JSON.stringify(orderItems));

            } else{
                localStorage.setItem('orderItemIds',JSON.stringify([orderItem.id]));
            }
            location.href = '/cart';
        }
    });
}
