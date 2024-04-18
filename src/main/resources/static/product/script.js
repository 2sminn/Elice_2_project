$(document).ready(function () {
    loadProduct();
    $("#add-to-cart").click(function () {
        const productId = $('#product-name').data('productId');
        comment_create(productId);
    });
});

function loadProduct(){
    //추후에 사용
    const productId = window.location.pathname.split('/').pop();
    $.ajax({
        //TODO 추후에 productId를 받을수 있다면 수정할 예정입니다.
        url: '/api/product/1',
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
    // productName.empty();
    // productName.append(`${product.name}`);
}

//장바구니 버튼을 눌렀을 때
function addToCart(productId){
    function addProductToList(productId) {
        // 로컬 스토리지에서 리스트 가져오기
        let cartList = JSON.parse(window.localStorage.getItem("cartList")) || [];
        // 상품 ID를 리스트에 추가
        cartList.push(productId);
        // 업데이트된 리스트를 다시 로컬 스토리지에 저장
        window.localStorage.setItem("cartList", JSON.stringify(cartList));
    }
}