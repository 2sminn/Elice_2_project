$(document).ready(function() {
    loadPosts(0); // 초기 페이지 로드

    function loadPosts(pageNumber) {
        $('#data-table tbody').html('<tr><td colspan="3">Loading...</td></tr>'); // 로딩 메시지 추가
        $.ajax({
            url: `/api/communities?page=${pageNumber}`,
            type: 'GET',
            dataType: 'json',
            success: function(response) {
                displayPosts(response.content); // 게시글 표시
                setupPagination(response); // 페이지네이션 설정
            },
            error: function() {
                alert('데이터 로드에 실패했습니다.');
                $('#data-table tbody').html('<tr><td colspan="3">Failed to load data. Please try again.</td></tr>'); // 에러 메시지 강화
            }
        });
    }

    function displayPosts(posts) {
        const tbody = $('#data-table tbody');
        tbody.empty();
        posts.forEach(post => {
            tbody.append(
                `<tr>
                    <td>${post.id}</td>
                    <td><a href="/community/${post.id}" class="post-link">${post.content}</a></td>
<!--                    나중에 작성자로 보여주는 것으로 변경할 것-->
<!--                    <td>${post.content}</td>--> 
                    <td>${formatDate(post.createdAt)}</td>
                </tr>`
            );
        });
    }

    function setupPagination(pageData) {
        const pagination = $('.pagination');
        pagination.empty(); // 페이지네이션 초기화

        for (let i = 0; i < pageData.totalPages; i++) {
            const isActive = pageData.number === i ? 'active' : '';
            pagination.append(
                `<li class="page-item ${isActive}">
                    <a class="page-link" href="#" data-page="${i}">${i + 1}</a>
                </li>`
            );
        }

        // Setup pagination click events with jQuery
        $('.page-link').on('click', function(e) {
            e.preventDefault();
            var pageNum = $(this).data('page');
            loadPosts(pageNum);
        });
    }
});

function formatDate(dateArray) {
    var date = new Date(dateArray[0], dateArray[1] - 1, dateArray[2], dateArray[3], dateArray[4], dateArray[5], dateArray[6] / 1000000);
    return date.toLocaleString();
}