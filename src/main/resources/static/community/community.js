// $(document).ready(function() {
//     loadPosts(0); // 초기 페이지 로드
//
//     function loadPosts(pageNumber) {
//         $('#data-table tbody').html('<tr><td colspan="3">Loading...</td></tr>'); // 로딩 메시지 추가
//         $.ajax({
//             url: `/api/communities?page=${pageNumber}`,
//             type: 'GET',
//             dataType: 'json',
//             success: function(response) {
//                 displayPosts(response.content); // 게시글 표시
//                 setupPagination(response); // 페이지네이션 설정
//             },
//             error: function() {
//                 alert('데이터 로드에 실패했습니다.');
//                 $('#data-table tbody').html('<tr><td colspan="3">Failed to load data. Please try again.</td></tr>'); // 에러 메시지 강화
//             }
//         });
//     }
//
//     function displayPosts(posts) {
//         const tbody = $('#data-table tbody');
//         tbody.empty();
//         posts.forEach(post => {
//             tbody.append(
//                 `<tr>
//                     <td>${post.id}</td>
//                     <td><a href="#" class="post-link" data-id="${post.id}">${post.title}</a></td>
//                     <td>${formatDate(post.createdAt)}</td>
//                     <td id="views-${post.id}">${post.views}</td>
//                 </tr>`
//             );
//         });
//     }
//
//     function setupPagination(pageData) {
//         const pagination = $('.pagination');
//         const maxPagesToShow = 10; // 최대 표시할 페이지 수
//         let currentPage = pageData.number;
//         let startPage, endPage;
//
//         if (pageData.totalPages <= maxPagesToShow) {
//             // 전체 페이지 수가 maxPagesToShow보다 작거나 같으면 모든 페이지 표시
//             startPage = 0;
//             endPage = pageData.totalPages;
//         } else {
//             // 전체 페이지 수가 더 많은 경우
//             const maxPagesBeforeCurrentPage = Math.floor(maxPagesToShow / 2);
//             const maxPagesAfterCurrentPage = Math.ceil(maxPagesToShow / 2) - 1;
//             if (currentPage <= maxPagesBeforeCurrentPage) {
//                 // 현재 페이지가 시작 근처에 있을 때
//                 startPage = 0;
//                 endPage = maxPagesToShow;
//             } else if (currentPage + maxPagesAfterCurrentPage >= pageData.totalPages) {
//                 // 현재 페이지가 끝 근처에 있을 때
//                 startPage = pageData.totalPages - maxPagesToShow;
//                 endPage = pageData.totalPages;
//             } else {
//                 // 현재 페이지가 중간에 있을 때
//                 startPage = currentPage - maxPagesBeforeCurrentPage;
//                 endPage = currentPage + maxPagesAfterCurrentPage + 1;
//             }
//         }
//
//         pagination.empty(); // 페이지네이션 초기화
//
//         // '이전' 페이지 버튼
//         if (currentPage > 0) {
//             pagination.append(`<li class="page-item"><a class="page-link" href="#" data-page="${currentPage - 1}">&laquo;</a></li>`);
//         }
//
//         for (let i = startPage; i < endPage; i++) {
//             const isActive = currentPage === i ? 'active' : '';
//             pagination.append(`<li class="page-item ${isActive}"><a class="page-link" href="#" data-page="${i}">${i + 1}</a></li>`);
//         }
//
//         // '다음' 페이지 버튼
//         if (currentPage < pageData.totalPages - 1) {
//             pagination.append(`<li class="page-item"><a class="page-link" href="#" data-page="${currentPage + 1}">&raquo;</a></li>`);
//         }
//
//         // 페이지 링크에 이벤트 바인딩
//         $('.page-link').on('click', function(e) {
//             e.preventDefault();
//             const pageNum = $(this).data('page');
//             loadPosts(pageNum);
//         });
//     }
//
// });
//
// function formatDate(dateArray) {
//     var date = new Date(dateArray[0], dateArray[1] - 1, dateArray[2], dateArray[3], dateArray[4], dateArray[5], dateArray[6] / 1000000);
//     return date.toLocaleString();
// }

$(document).ready(function() {
    loadPosts(0); // 초기 페이지 로드

    function loadPosts(pageNumber) {
        $('#data-table tbody').html('<tr><td colspan="4">Loading...</td></tr>'); // 로딩 메시지 추가
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
                $('#data-table tbody').html('<tr><td colspan="4">Failed to load data. Please try again.</td></tr>'); // 에러 메시지 강화
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
                    <td><a href="#" class="post-link" data-id="${post.id}">${post.title}</a></td>
                    <td>${formatDate(post.createdAt)}</td>
                    <td id="views-${post.id}">${post.views}</td>
                </tr>`;
            tbody.append(row);
        });

        // 게시글 링크에 조회수 업데이트 이벤트 바인딩
        $('.post-link').on('click', function(e) {
            e.preventDefault(); // 기본 이벤트 방지
            const postId = $(this).data('id');
            updateViews(postId); // 조회수 업데이트 함수 호출
        });
    }

    function updateViews(postId) {
        $.ajax({
            url: `/api/community/${postId}/increment-views`,
            type: 'POST',
            success: function() {
                const currentViews = parseInt($(`#views-${postId}`).text());
                $(`#views-${postId}`).text(currentViews + 1); // 클라이언트 측에서 조회수 업데이트

                // 게시글 상세 페이지로 이동
                window.location.href = `/community/${postId}`;
            },
            error: function() {
                alert('조회수를 업데이트하는 데 실패했습니다.');
            }
        });
    }

    function setupPagination(pageData) {
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
            loadPosts(pageNum);
        });
    }

    function formatDate(dateArray) {
        var date = new Date(dateArray[0], dateArray[1] - 1, dateArray[2], dateArray[3], dateArray[4], dateArray[5], dateArray[6] / 1000000);
        return date.toLocaleString();
    }
});