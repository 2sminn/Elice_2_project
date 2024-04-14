//페이지 로드 시에
$(document).ready(function () {
    //댓글 작성
    $("#comment_submit").click(function () {
        comment_create();
    });
    //댓글 조회
    const postId = window.location.pathname.split('/').pop();
    loadComments(postId);

});

//댓글 작성
function comment_create() {
    let data = {
        content : $('#comment_content').val()
    }
    $.ajax({
        url: '/api/comment',
        type: 'POST',
        dataType: 'text',
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (data) {
            alert("댓글이 작성되었습니다");
            location.href = '/comment';
        }
    });


}
//댓글 조회 테스트
function loadComments(postId) {
    $.ajax({
        url: '/api/comment/' + postId,
        type: 'GET',
        dataType: 'json',
        success: function (post) {
            displayComments(post);
        },
        error: function(xhr, status, error) {
            console.error('Error fetching comments:', error);
        }
    });
}
function displayComments(post) {
    let comments = post.comments;

    let commentList = $('#comment_list');
    commentList.empty();

    if (comments.length === 0) {
        commentList.append('<p>No comments yet.</p>');
    } else {
        comments.forEach(function(comment) {
            let commentItem = "";
            commentItem.append(`<div class="comment-content-container" id="comment_detail">
                <div class="row-box comment-header">
                    <div class="commnet-name-box">
                        <img src ="/img/miao.jpg" class="comment-profile">
                        <span class="commnet-name">냔냔펑치</span>
                    </div>
                    <div class="comment-day">comment.modifiedAt</div>
                </div>
                <div class="row-box comment-article">
                    <div> ${comment.content} </div>
                </div>
                <div class="row-box comment-footer">
                    <input type="button" value="댓글" class="reply-btn">
                    <input type="button" value="좋아요" class="upvote-btn">
                    <input type="button" value="싫어요" class="downvote-btn"></div>
            </div>`)
            commentList.append(commentItem);
        });
    }
}


//
// //댓글 조회
// function loadComments(postId) {
//     $.ajax({
//         url: '/api/comment/' + postId,
//         type: 'GET',
//         dataType: 'json',
//         success: function (data) {
//             displayComments(data);
//         },
//         error: function(xhr, status, error) {
//             console.error('Error fetching comments:', error);
//         }
//     });
// }
//
//
// function displayComments(post) {
//     let comments = post.getComment; // post 로부터 comment 리스트를 받아와야 하는데 ~...
//     let commentList = $('#comment_list');
//     commentList.empty();
//
//     if (comments.length === 0) {
//         commentList.append('<p>No comments yet.</p>');
//     } else {
//         comments.forEach(function(comment) {
//             let commentItem = "";
//             commentItem += `<div class="comment-content-container" id="comment_detail">
//                 <div class="row-box comment-header">
//                     <div class="commnet-name-box">
//                         <img src ="/img/miao.jpg" class="comment-profile">
//                         <span class="commnet-name">냔냔펑치</span>
//                     </div>
//                     <div class="comment-day">2024.03.09</div>
//                 </div>
//                 <div class="row-box comment-article" >
//                     <div>` +comment.getContent+`</div> // comment 객체로부터 content도 받아와야 하고..
//                 </div>
//                 <div class="row-box comment-footer">
//                     <input type="button" value="댓글" class="reply-btn">
//                     <input type="button" value="좋아요" class="upvote-btn">
//                     <input type="button" value="싫어요" class="downvote-btn">
//                 </div>
//             </div>`
//             commentList.append(commentItem);
//         });
//     }
// }
//commit