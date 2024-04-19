$(document).ready(function() {
    // 페이지 로드 시 기본적으로 강아지 카테고리(예시로 id=1)의 상품 로드
    loadProducts(3);

    // 강아지와 고양이 버튼에 클릭 이벤트 바인딩
    $('.parent_category').click(function() {
        $('.parent_category').removeClass('selected');
        $(this).addClass('selected');

        const parentId = $(this).hasClass('dog') ? 1 : 2;
        loadProducts(parentId);
    });

    // 하위 카테고리 버튼에 클릭 이벤트 바인딩
    $('.category').click(function() {
        $('.category').removeClass('selected');
        $(this).addClass('selected');

        const categoryId = $(this).data('category-id');
        loadProducts(categoryId);
    });

    // 상품 클릭 시 상세 페이지로 이동
    $(document).on('click', '.product-item', function() {
        const productId = $(this).data('product-id');
        window.location.href = `/product/${productId}`;
    });
});

function loadProducts(categoryId) {
    $.ajax({
        url: `/categories/${categoryId}/products`, // 올바른 엔드포인트로 GET 요청
        type: 'GET',
        dataType: 'json',
        success: function(products) {
            console.log(products);
            updateProductSection(products);
        },
        error: function(error) {
            console.error('Error fetching products:', error);
        }
    });
}

function updateProductSection(products) {
    const productSection = $('.product-section');
    productSection.empty();

    // 서버로부터 받은 상품 데이터로 상품 요소 생성 후 페이지에 추가
    products.forEach(product => {
        const productHtml = `
            <div class="product-item" data-product-id="${product.id}">
                <img src="${product.imageUrl}" alt="${product.name}">
                <h4 class="product-name">${product.name}</h4>
                <p class="product-price">${product.price}</p>
            </div>
        `;
        productSection.append(productHtml);
    });
}
