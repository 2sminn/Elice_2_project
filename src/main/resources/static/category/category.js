document.addEventListener('DOMContentLoaded', () => {
    const parentCategoryButtons = document.querySelectorAll('.parent_category');

    parentCategoryButtons.forEach(button => {
        button.addEventListener('click', (event) => {
            parentCategoryButtons.forEach(btn => btn.classList.remove('selected'));
            event.target.classList.add('selected');

            const categoryType = event.target.textContent;
            loadCategories(categoryType);
        });
    });
});

function loadCategories(categoryType) {
    const categorySection = document.querySelector('.category-section');
    while (categorySection.firstChild) {
        categorySection.removeChild(categorySection.firstChild);
    }

    fetch(`/api/categories/${categoryType}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            Object.keys(data).forEach(key => {
                const category = data[key];
                const button = document.createElement('button');
                button.textContent = category.name;  // 서버에서 카테고리 이름을 반환한다고 가정
                button.className = 'category';
                categorySection.appendChild(button);
            });
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
}

function loadProducts(categoryName) {
    const productSection = document.querySelector('.product-section');
    while (productSection.firstChild) {
        productSection.removeChild(productSection.firstChild);
    }

    fetch(`/api/products/${categoryName}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(products => {
            products.forEach(product => {
                const productDiv = document.createElement('div');
                productDiv.className = 'product-item';
                productDiv.innerHTML = `
                    <img src="${product.image}" alt="${product.name}">
                    <p class="product-name">${product.name}</p>
                    <p class="product-price">${product.price}</p>
                `;
                productSection.appendChild(productDiv);
            });
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
}

document.addEventListener('click', function(event) {
    if (event.target.className === 'category') {
        loadProducts(event.target.textContent);
    }
});