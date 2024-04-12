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
    // 이 함수는 animalType에 따라 category-section의 내용을 갱신합니다.
    // 실제 애플리케이션에서는 이 부분에서 서버에 요청을 보내거나
    // 미리 정의된 데이터를 사용하여 섹션을 업데이트할 수 있습니다.
    // 예시를 위해 정적인 데이터를 사용합니다.

    const categorySection = document.querySelector('.category-section');
    while (categorySection.firstChild) {
        categorySection.removeChild(categorySection.firstChild);
    }

    let categories;
    if (animalType === '강아지') {
        categories = ['뼈다귀', '목줄', '옷', '장난감', '간식'];
    } else if (animalType === '고양이') {
        categories = ['캣타워', '긁기판', '옷', '장난감', '간식'];
    }

    categories.forEach(category => {
        const button = document.createElement('button');
        button.textContent = category;
        button.className = 'category';
        categorySection.appendChild(button);
    });
}