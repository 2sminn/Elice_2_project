$(document).ready(function () {
    loadProduct();
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
            //상품 ID 로컬스토리지 저장
            window.localStorage.setItem("productId", product.id);
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