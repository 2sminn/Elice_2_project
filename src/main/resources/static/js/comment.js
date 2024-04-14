
//페이지 로드 시에
$(document).ready(function () {
    //댓글 작성
    $("#comment_submit").click(function () {
        comment_create(postId);
    });
    //댓글 수정
    $("#comment_edit").click(function () {
        comment_edit(comment.id);
        $('#comment_content').val(comment.content)
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
        let commentElement = $(`
            <div class="comment-content-container"> <!-- 댓글 1개-->
                <div class="row-box comment-header">
                    <div class="commnet-name-box">
                        <img src ="/static/img/miao.jpg" class="comment-profile">
                        <span class="commnet-name">냔냔펑치</span>
                    </div>
                    <div class="comment-day">${comment.modifiedAt}</div>
                    <img src="/static/img/comment-edit2.png" class="comment_edit" >
                    <img src="/static/img/comment-delete2.png" class="comment_delete">
                </div>
                <div class="row-box comment-article" >
                    <div class="comment_detail_box">
                        <div class="comment_detail">${comment.content}</div>
                    </div>
                </div>
                <div class="row-box comment-footer">
                    <input type="button" value="댓글" class="reply-btn">
                    <input type="button" value="좋아요" class="upvote-btn">
                    <input type="button" value="싫어요" class="downvote-btn">
                </div>
            </div>
        `);
        // 댓글 ID를 댓글 요소에 저장
        commentElement.find('.comment_edit').data('comment_id', comment.id);
        commentElement.find('.comment_delete').data('comment_id', comment.id);
        // 댓글 요소를 목록에 추가
        commentList.append(commentElement);
        });
}

//댓글 수정 버튼 클릭
$(document).on('click', '.comment_edit', function() {
    // 댓글 ID 가져오기
    let commentId = $(this).data('comment_id');
    // 댓글 내용 가져오기
    let commentContent = $(this).closest('.comment-content-container').find('.comment_detail').text();
    // 수정을 위해 댓글 내용을 textarea로 변경
    if($(this).closest('.comment-content-container').find('.comment_edit_textarea').length === 0){
        $(this).closest('.comment-content-container').find('.comment_detail_box')
            .append(`<div class="comment_edit_box"><textarea class="comment_edit_textarea" cols="30" rows="3">${commentContent}</textarea>
                   <button class="comment_edit_btn">등록</button></div>`);
    }else{
        $(this).closest('.comment-content-container').find('.comment_edit_box').empty();
    }


});

// function comment_edit(commentId) {
//     const postId = window.location.pathname.split('/').pop();
//     let data = {content : $('#comment_content').val()}
//     $.ajax({
//         url: '/api/comment' + commentId,
//         type: 'PUT',
//         dataType: 'text',
//         contentType: "application/json",
//         data: JSON.stringify(data),
//         success: function (data) {
//             alert("댓글이 수정되었습니다");
//             location.href = '/community/' + postId;
//         }
//     });
// }

//
// //댓글 수정
// $(document).on('click', '.comment_edit', function() {
//     let commentId = $(this).data('comment_id');
//     comment_edit(commentId);
// });
// function comment_edit() {
//     const postId = window.location.pathname.split('/').pop();
//     let data = {content : $('#comment_content').val()}
//     $.ajax({
//         url: '/api/comment' + commentId,
//         type: 'PUT',
//         dataType: 'text',
//         contentType: "application/json",
//         data: JSON.stringify(data),
//         success: function (data) {
//             alert("댓글이 수정되었습니다");
//             location.href = '/community/' + postId;
//         }
//     });
// }


