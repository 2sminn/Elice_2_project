document.addEventListener('DOMContentLoaded', () => {
    const parentCategoryButtons = document.querySelectorAll('.parent_category');

    parentCategoryButtons.forEach(button => {
        button.addEventListener('click', (event) => {
            parentCategoryButtons.forEach(btn => btn.classList.remove('selected'));
            event.target.classList.add('selected');

            const animalType = event.target.textContent;
            loadCategories(animalType);
        });
    });
});

function loadCategories(animalType) {
    const categorySection = document.querySelector('.category-section');
    while (categorySection.firstChild) {
        categorySection.removeChild(categorySection.firstChild);
    }

    fetch(`/api/categories/${animalType}`)
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