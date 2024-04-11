$(document).ready(function () {
    $("#login").click(function(){
        login();
    });
});

// 회원 가입 요청
function login() {
    let data = {
        email : $("#email").val(),
        password : $("#password").val()
    };
    //Ajax로 전송
    $.ajax({
        url : '/api/member/login',
        data : JSON.stringify(data),
        type : 'POST',
        contentType: "application/json ; charset=UTF-8",
        dataType : 'json',
        success : function(result) {
                console.log(result);
            $("#error").addClass("visually-hidden");
                alert("로그인에 성공하였습니다!!");
        },
        error:function(request){
            alert("로그인에 실패하였습니다!!");
            $("#error").removeClass("visually-hidden");
        }
    }); //End Ajax
}