//페이지 로드 시에
$(document).ready(function () {
    //댓글 작성
    $("#comment_submit").click(function () {
        comment_create(postId);
    });

    //댓글 조회
    const postId = window.location.pathname.split('/').pop();
    $.ajax({
        url: `/api/comment/${postId}`,
        type: 'GET',
        dataType: 'json',
        success: function (comments) {
            displayComments(comments);
        }
    });

});

//댓글 작성
function comment_create(postId) {
    let data = {content : $('#comment_content').val(),
        postId : postId}
    $.ajax({
        url: '/api/comment',
        type: 'POST',
        dataType: 'text',
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (data) {
            alert("댓글이 작성되었습니다");
            location.href = '/community/' + postId;
        }
    });
}


//댓글 조회
function displayComments(comments) {
    let commentList = $('#comment_list');
    commentList.empty();
    comments.forEach(function(comment) {
            commentList.append(`
            <div class="comment-content-container"> <!-- 댓글 1개-->
                <div class="row-box comment-header">
                    <div class="commnet-name-box">
                        <img src ="/static/img/miao.jpg" class="comment-profile">
                        <span class="commnet-name">냔냔펑치</span>
                    </div>
                    <div class="comment-day">${comment.modifiedAt}</div>
                </div>
                <div class="row-box comment-article" >
                    <div>${comment.content}</div>
                </div>
                <div class="row-box comment-footer">
                    <input type="button" value="댓글" class="reply-btn">
                    <input type="button" value="좋아요" class="upvote-btn">
                    <input type="button" value="싫어요" class="downvote-btn">
                </div>
            </div>
        `)
        });
}
