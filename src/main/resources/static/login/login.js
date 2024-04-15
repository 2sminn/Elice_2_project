$(document).ready(function () {
    $("#login").click(function(){
        login();
    });
});

function login() {
    let data = {
        email : $("#email").val(),
        password : $("#password").val()
    };
    let url;
    //Ajax로 전송
    url=$.ajax({
        url : '/api/member/login',
        data : JSON.stringify(data),
        type : 'POST',
        contentType: "application/json ; charset=UTF-8",
        dataType : 'json',
        success : function(data) {
            if(data){
                localStorage.setItem("token",url.getResponseHeader("token"));
                alert("로그인에 성공하였습니다!!");
                window.location.href="/";
            }else{
                alert("로그인에 실패하였습니다!!");
                $("#error").removeClass("visually-hidden");
            }
        }
    });
}