$(document).ready(function() {
    // URL에서 게시글 ID 추출
    const postId = getUrlParameter("postId");

    // 게시글 데이터 가져오기
    $.ajax({
        url: `/api/community/${postId}`, // 게시글 ID를 포함한 API URL
        type: 'GET',
        dataType: 'json',
        success: function(response) {
            const post = response.post; // 응답에서 post 객체 추출
            $('#post-title').html(post.title);
            $('#post-content').html(post.content);
            $('#post-views').html(post.views);
            $('#post-created').html(formatDate(post.createdAt));
            $('#post-modifiedAt').html(formatDate(post.modifiedAt));
            $('#likeCount').html(response.likeCount); // 좋아요 개수 업데이트
        },
        error: function() {
            alert('게시글을 불러오는데 실패했습니다.');
        }
    });

    // 좋아요 상태 가져오기
    var token = localStorage.getItem('token');
    if (token) {
        $.ajax({
            url: `/api/community/post/${postId}/like-status`,
            type: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            },
            success: function(response) {
                updateLikeButton(response.isLiked); // 좋아요 상태 업데이트
            },
            error: function() {
                alert('좋아요 상태를 불러오는데 실패했습니다.');
            }
        });
    }

    // 수정 버튼 클릭 이벤트 핸들러 추가
    $('#editBtn').click(function() {
        window.location.href = `/community/update?postId=${postId}`; // 수정 페이지로 리디렉션
    });

    // 삭제 버튼 클릭 이벤트 핸들러 추가
    $('#deleteBtn').click(function() {
        if (confirm('이 게시글을 삭제하시겠습니까?')) {
            $.ajax({
                url: `/api/community/post/${postId}`,
                type: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${token}`
                },
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

    // 좋아요 버튼 토글 기능 추가
    $("#likeBtn").click(function() {
        var icon = $(this).find("i");
        var likeCount = $("#likeCount");
        icon.toggleClass("far fa-heart");
        icon.toggleClass("fas fa-heart");

        if (!token) {
            alert('로그인이 필요합니다.');
            return;
        }

        // 좋아요 상태를 서버에 업데이트
        $.ajax({
            url: `/api/community/post/${postId}/like`,
            type: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`
            },
            success: function(response) {
                console.log('좋아요 상태가 업데이트되었습니다.');
                likeCount.html(response.likeCount); // 좋아요 개수 업데이트
            },
            error: function() {
                alert('좋아요 상태 업데이트에 실패했습니다.');
                // 실패 시 아이콘 상태를 다시 원래대로 돌리기
                icon.toggleClass("far fa-heart");
                icon.toggleClass("fas fa-heart");
            }
        });
    });
});

function updateLikeButton(isLiked) {
    var icon = $("#likeBtn").find("i");
    if (isLiked) {
        icon.removeClass("far fa-heart");
        icon.addClass("fas fa-heart");
    } else {
        icon.removeClass("fas fa-heart");
        icon.addClass("far fa-heart");
    }
}

function getUrlParameter(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    var results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}

function formatDate(dateArray) {
    if (!dateArray || dateArray.length < 3) {
        return "잘못된 날짜입니다.";
    }
    var date = new Date(dateArray[0], dateArray[1] - 1, dateArray[2], dateArray[3] || 0, dateArray[4] || 0, dateArray[5] || 0, dateArray[6] || 0);
    return date.toLocaleString();
}
