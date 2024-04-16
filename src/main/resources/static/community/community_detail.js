$(document).ready(function() {
    // URL에서 게시글 ID 추출
    const postId = window.location.pathname.split('/').pop();

    // AJAX 요청으로 게시글 데이터 가져오기
    $.ajax({
        url: `/api/community/${postId}`, // 게시글 ID를 포함한 API URL
        type: 'GET',
        dataType: 'json',
        success: function(post) {
            $('#post-title').html(post.title);
            $('#post-content').html(post.content);
            $('#post-views').html(post.views);
            $('#post-created').html(formatDate(post.createdAt));
            $('#post-modifiedAt').html(formatDate(post.modifiedAt));
        },
        error: function() {
            alert('게시글을 불러오는데 실패했습니다.');
        }
    });

    // 수정 버튼 클릭 이벤트 핸들러 추가
    $('#editBtn').click(function() {
        window.location.href = `/community/update/${postId}`; // 수정 페이지로 리디렉션
    });

    // 삭제 버튼 클릭 이벤트 핸들러 추가
    $('#deleteBtn').click(function() {
        if (confirm('이 게시글을 삭제하시겠습니까?')) {
            $.ajax({
                url: `/api/community/post/${postId}`,
                type: 'DELETE',
                success: function() {
                    alert('게시글이 삭제되었습니다.');
                    window.location.href = '/communities'; // 삭제 후 이동할 페이지
                },
                error: function() {
                    alert('게시글 삭제에 실패했습니다.');
                }
            });
        }
    });
});

function formatDate(dateArray) {
    var date = new Date(dateArray[0], dateArray[1] - 1, dateArray[2], dateArray[3], dateArray[4], dateArray[5], dateArray[6] / 1000000);
    return date.toLocaleString();
}