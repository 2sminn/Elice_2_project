$(document).ready(function() {
    loadPosts(0); // 초기 페이지 로드

    $('#search-btn').click(function() {
        var searchTerm = $('.search-bar input[type="search"]').val(); // 검색창에서 검색어 가져오기
        loadPosts(0, searchTerm); // 검색어와 함께 게시글 로드
    });

    function loadPosts(pageNumber, searchTerm = '') {
        $('#data-table tbody').html('<tr><td colspan="4">Loading...</td></tr>'); // 로딩 메시지 표시
        $.ajax({
            url: `/api/communities?page=${pageNumber}&search=${encodeURIComponent(searchTerm)}`,
            type: 'GET',
            dataType: 'json',
            success: function(response) {
                displayPosts(response.content); // 게시글 표시
                setupPagination(response, searchTerm); // 페이지네이션 설정, 검색어 전달
                if (response.content.length === 0) {
                    $('#data-table tbody').html('<tr><td colspan="4">No posts found.</td></tr>'); // 검색 결과가 없음
                }
            },
            error: function() {
                $('#data-table tbody').html('<tr><td colspan="4">Failed to load data. Please check your connection and try again.</td></tr>'); // 에러 메시지 강화
            }
        });
    }

    function displayPosts(posts) {
        const tbody = $('#data-table tbody');
        tbody.empty();
        posts.forEach(post => {
            const row = `
                <tr>
                    <td>${post.id}</td>
                    <td><a href="/community/${post.id}" class="post-link">${post.title}</a></td>
                    <td>${formatDate(post.createdAt)}</td>
                </tr>`;
            tbody.append(row);
        });
    }

    function setupPagination(pageData, searchTerm) {
        const pagination = $('.pagination');
        const maxPagesToShow = 10; // 최대 표시할 페이지 수
        let currentPage = pageData.number;
        let startPage, endPage;

        if (pageData.totalPages <= maxPagesToShow) {
            startPage = 0;
            endPage = pageData.totalPages;
        } else {
            const maxPagesBeforeCurrentPage = Math.floor(maxPagesToShow / 2);
            const maxPagesAfterCurrentPage = Math.ceil(maxPagesToShow / 2) - 1;
            if (currentPage <= maxPagesBeforeCurrentPage) {
                startPage = 0;
                endPage = maxPagesToShow;
            } else if (currentPage + maxPagesAfterCurrentPage >= pageData.totalPages) {
                startPage = pageData.totalPages - maxPagesToShow;
                endPage = pageData.totalPages;
            } else {
                startPage = currentPage - maxPagesBeforeCurrentPage;
                endPage = currentPage + maxPagesAfterCurrentPage + 1;
            }
        }

        pagination.empty(); // 페이지네이션 초기화

        for (let i = startPage; i < endPage; i++) {
            const isActive = currentPage === i ? 'active' : '';
            pagination.append(`<li class="page-item ${isActive}"><a class="page-link" href="#" data-page="${i}">${i + 1}</a></li>`);
        }

        $('.page-link').on('click', function(e) {
            e.preventDefault();
            const pageNum = $(this).data('page');
            loadPosts(pageNum, searchTerm); // 검색어를 유지하면서 페이지 로드
        });
    }

    function formatDate(dateArray) {
        var date = new Date(dateArray[0], dateArray[1] - 1, dateArray[2], dateArray[3], dateArray[4], dateArray[5], dateArray[6] / 1000000);
        return date.toLocaleString();
    }
});