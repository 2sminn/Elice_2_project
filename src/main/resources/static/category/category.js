$(document).ready(function() {
    // 페이지 로드 시 기본적으로 강아지 카테고리(예시로 id=1)의 상품 로드
    loadProductsByParentId(1);

    // 강아지와 고양이 버튼에 클릭 이벤트 바인딩
    $('.parent_category').click(function() {
        var parentCategory = $(this).hasClass('dog') ? 'dog' : 'cat';
        var parentCategoryId = $(this).hasClass('dog') ? 1 : 2;

        $('.parent_category').removeClass('selected');
        $(this).addClass('selected');
        updateCategoryButtons(parentCategory);
        loadProductsByParentId(parentCategoryId);
    });


    // 하위 카테고리 버튼에 클릭 이벤트 바인딩
    $('.category').click(function() {
        $('.category').removeClass('selected');
        $(this).addClass('selected');

        const categoryId = $(this).data('category-id');
        loadProducts(categoryId);
    });

    // 정렬 버튼에 클릭 이벤트 바인딩
    $('.sort-btn').click(function() {
        const sortType = $(this).attr('id');
        sortProducts(sortType);
    });

    // 상품 클릭 시 상세 페이지로 이동
    $(document).on('click', '.product-item', function() {
        const productId = $(this).data('product-id');
        window.location.href = `/product/${productId}`;
    });
});
function updateCategoryButtons(parentCategory) {
    // 상위 카테고리에 따라 하위 카테고리 버튼의 ID를 업데이트합니다.
    var categoryMappings = {
        'dog': { '간식': 3, '매트': 4, '사료': 5, '패션': 6, '장난감': 7 },
        'cat': { '간식': 8, '매트': 9, '사료': 10, '패션': 11, '장난감': 12 }
    };

    $('.category').each(function() {
        var categoryName = $(this).text();
        $(this).data('category-id', categoryMappings[parentCategory][categoryName]);
    });
}
function loadProductsByParentId(parentId) {
    $.ajax({
        url: `/categories/${parentId}/subcategories`, // 올바른 엔드포인트로 GET 요청
        type: 'GET',
        dataType: 'json',
        success: function(response) {
            const allProducts = [];
            // 모든 하위 카테고리의 상품들을 하나의 배열로 합칩니다.
            response.forEach(subCategory => {
                allProducts.push(...subCategory.products);
            });
            // 상품 섹션을 업데이트 합니다.
            updateProductSection(allProducts);
            },
            error: function(error) {
                console.error('Error fetching products:', error);
            }
    });
}

function loadProducts(categoryId) {
    // 특정 카테고리의 상품 데이터 로드
    $.ajax({
        url: `/categories/${categoryId}/products`,
        type: 'GET',
        dataType: 'json',
        success: function(products) {
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
                <div class="img">
                    <img class="image-thumbnail" src="${product.imageUrl}" alt="${product.name}">
                </div>
                <div class="detail">
                    <h4 class="product-name">${product.name}</h4>
                    <p class="product-price">${product.price}</p>
                </div>
            </div>
        `;
        productSection.append(productHtml);
    });
}

function sortProducts(sortType) {
    const products = $('.product-section .product-item').get();
    switch (sortType) {
        case 'popularity':
            products.sort((a, b) => $(b).data('popularity') - $(a).data('popularity'));
            break;
        case 'latest':
            products.sort((a, b) => $(b).data('date') - $(a).data('date'));
            break;
        case 'low-to-high':
            products.sort((a, b) => $(a).data('price') - $(b).data('price'));
            break;
        case 'high-to-low':
            products.sort((a, b) => $(b).data('price') - $(a).data('price'));
            break;
        default:
            break;
    }
    $('.product-section').empty().append(products);
}