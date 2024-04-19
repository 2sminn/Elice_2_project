$(document).ready(function() {
    // 페이지 로드 시 기본적으로 강아지 카테고리의 상품 로드
    loadProducts(1);

    // 강아지와 고양이 버튼에 클릭 이벤트 바인딩
    $('.parent_category').click(function() {
        // 모든 버튼의 'selected' 클래스 제거 후 현재 버튼에 'selected' 클래스 추가
        $('.parent_category').removeClass('selected');
        $(this).addClass('selected');

        // 강아지 또는 고양이 카테고리에 따른 상품 데이터 로드
        const categoryId = $(this).hasClass('dog') ? 1 : 2;
        loadProducts(categoryId);
    });

    // 하위 카테고리 버튼에 클릭 이벤트 바인딩
    $('.category').click(function() {
        // 모든 카테고리 버튼의 'selected' 클래스 제거 후 현재 버튼에만 'selected' 클래스 추가
        $('.category').removeClass('selected');
        $(this).addClass('selected');

        // 클릭된 버튼의 data-category-id 값을 가져와 해당 카테고리의 상품 데이터 로드
        const categoryId = $(this).data('category-id');
        loadProducts(categoryId);
    });

    // 상품 클릭 이벤트 바인딩
    $(document).on('click', '.product-item', function() {
        const productId = $(this).data('product-id');
        window.location.href = `/product/${productId}`; // 상품 상세 페이지로 이동
    });
});

function loadProducts(categoryId) {
    $.ajax({
        url: `/api/categories/${categoryId}`, // 해당 카테고리id에 있는 상품데이터 갖고오기 <GET 요청>
        type: 'GET',
        dataType: 'json',
        success: function(products) {
            updateProductSection(products); // 상품 섹션 업데이트
        },
        error: function(error) {
            console.error('Error fetching products:', error);
        }
    });

    $.ajax({
        url: `/api/categories/${parentId}/subcategories`, // 서버의 해당 엔드포인트로 GET 요청
        type: 'GET',
        dataType: 'json',
        success: function(products) {
            updateProductSection(products); // 상품 섹션 업데이트
        },
        error: function(error) {
            console.error('Error fetching products:', error);
        }
    });

    $.ajax({
        url: `/api/categories/${categoryId}/products`, // 서버의 해당 엔드포인트로 GET 요청
        type: 'GET',
        dataType: 'json',
        success: function(products) {
            updateProductSection(products); // 상품 섹션 업데이트
        },
        error: function(error) {
            console.error('Error fetching products:', error);
        }
    });
}

function updateProductSection(products) {
    const productSection = $('.product-section');
    productSection.empty(); // 상품 섹션 초기화

    // 가져온 상품 데이터를 바탕으로 상품 요소 생성 후 상품 섹션에 추가
    products.forEach(product => {
        const productHtml = `
            <div class="product-item">
                <img src="${product.image_url}" alt="${product.name}">
                <h4 class="product-name">${product.name}</h4>
                <p class="product-price">${product.price}</p>
            </div>
        `;
        productSection.append(productHtml);
    });
}