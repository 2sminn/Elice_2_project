$(document).ready(function() {
    // URL에서 게시글 ID 추출
    const postId = window.location.pathname.split('/').pop();

    // 게시글 데이터 가져오기
    $.ajax({
        url: `/api/community/${postId}`, // 게시글 ID를 포함한 API URL
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            // 받아온 데이터로 폼 필드 채우기
            console.log(data);
            $('#title').val(data.title);
            $('#boardType').val(data.type);     // 멍청하게 오타 문제로 삽질 했었음
            CKEDITOR.instances.content.setData(data.content);
        },
        error: function() {
            alert('게시글 정보를 불러오는데 실패했습니다.');
        }
    });

    // 폼 제출 처리
    $('#submitBtn').click(function() {
        const updatedData = {
            title: $('#title').val(),
            boardType: $('#boardType').val(),
            content: CKEDITOR.instances.content.getData()
        };

        $.ajax({
            url: `/api/community/post/${postId}`,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(updatedData),
            success: function(response) {
                alert('게시글이 성공적으로 수정되었습니다.');
                window.location.href = '/communities'; // 수정 후 페이지 리다이렉션
            },
            error: function() {
                alert('게시글 수정에 실패했습니다.');
            }
        });
    });
});