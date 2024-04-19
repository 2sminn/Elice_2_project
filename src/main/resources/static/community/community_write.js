$(function () {

    $("#submitBtn").click(function() {
        var postData = {
            title: $("#title").val(),
            boardType: $("#boardType").val(),   // 커뮤니티는 1, 콘텐츠는 2
            content: CKEDITOR.instances.content.getData()   // CKEditor에서 데이터 가져오기
        };

        $.ajax({
            type: "POST",
            url: "/api/community/post", // 서버의 API 경로
            data: JSON.stringify(postData),
            contentType: "application/json; charset=utf-8",
            success: function(response) {
                alert("게시글이 성공적으로 등록되었습니다.");
                window.location.href = "/communities"; // 게시글 목록 페이지로 리디렉션
            },
            error: function(xhr, status, error) {
                alert("게시글 등록에 실패하였습니다: " + xhr.responseText);
            }
        });
    });
});