$(document).ready(function() {
    // URL에서 게시글 ID 추출
    const postId = window.location.pathname.split('/').pop();

    // AJAX 요청으로 게시글 데이터 가져오기
    $.ajax({
        url: `/api/community/${postId}`, // 게시글 ID를 포함한 API URL
        type: 'GET',
        dataType: 'json',
        success: function(post) {
            $('#post-title').text(post.title);
            $('#post-content').text(post.content);
            $('#post-created').text(formatDate(post.createdAt));
            $('#post-modifiedAt').text(formatDate(post.modifiedAt));
        },
        error: function() {
            alert('게시글을 불러오는데 실패했습니다.');
        }
    });
});

function formatDate(dateArray) {
    var date = new Date(dateArray[0], dateArray[1] - 1, dateArray[2], dateArray[3], dateArray[4], dateArray[5], dateArray[6] / 1000000);
    return date.toLocaleString();
}