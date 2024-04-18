// 여기에 실제 API에서 상품 데이터를 불러오는 코드를 추가할 수 있습니다.

function buyNow() {
    var quantity = document.getElementById('product-quantity').value;
    alert('구매하기를 클릭하셨습니다. 수량: ' + quantity);
    // 구매 관련 로직을 구현해야 합니다. 예: API 호출
}

function addToCart() {
    var quantity = document.getElementById('product-quantity').value;
    alert('장바구니에 추가하셨습니다. 수량: ' + quantity);
    // 장바구니 추가 로직을 구현해야 합니다. 예: API 호출
}

// 페이지가 로드될 때 실행되는 함수
document.addEventListener('DOMContentLoaded', function() {
    // 상품 ID를 URL에서 가져옵니다.
    // 예: 'http://example.com/product.html?id=1' 에서 ID를 추출하는 예시입니다.
    const urlParams = new URLSearchParams(window.location.search);
    const productId = urlParams.get('id');

    if (productId) {
        loadProductDetails(productId);
    } else {
        alert('상품 ID가 URL에 포함되어 있지 않습니다.');
    }
});

function loadProductDetails(productId) {
    // 여기에 productId를 사용하여 상품 상세 정보를 가져오는 API 요청을 수행합니다.
    // fetch API나 XMLHttpRequest를 사용할 수 있습니다.
    // 예시를 위한 가짜 데이터
    const productDetails = {
        name: '샘플 상품',
        description: '이것은 상품의 설명입니다.',
        imageUrl: 'path-to-your-image.jpg'
    };

    // 상품 정보를 페이지에 채웁니다.
    document.getElementById('product-image').src = productDetails.imageUrl;
    document.getElementById('product-name').textContent = productDetails.name;
    document.getElementById('product-description').textContent = productDetails.description;
}
