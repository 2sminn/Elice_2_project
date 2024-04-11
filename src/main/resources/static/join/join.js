// 이메일 중복 체크
$(document).ready(function() {
    $("#emailCheck").click(function() {
        if(!emailCheck()){
            return;
        }
        let email = $("#email").val();

        $.ajax({
            url: '/api/member/validation/email?email='+email,
            data: {},
            type: 'GET',
            // contentType: "application/json; charset=UTF-8",
            success: function (result) {
                if(result) {
                    if (confirm("사용 가능한 이메일입니다.\n입력하신 이메일로 결정하시겠습니까?\n")) {
                        $("#email-label").addClass("visually-hidden");
                        $("#email-form").css("--bs-btn-border-color", "#6D6045");
                        $("#email").css("color", "#6D6045");
                        $("#email").attr("readOnly", true);
                    }
                }else {
                    $("#email-label").removeClass("visually-hidden");
                    $("#email-label").css("color", "red").text("이메일: 사용할 수 없는 이메일 입니다. 다른 이메일을 입력해 주세요");
                    $("#email").css("--bs-btn-border-color", "red");
                    $("#email").css("color", "red");
                    $(this).parent().val(email);
                }
            }
        }); //End Ajax
    });
});

// 이메일 형식 체크
$(document).ready(function () {
    $("#email").on("focusout",function(){
        emailCheck();
    });
});

// 닉네임 중복 체크
$(document).ready(function() {
    $("#nameCheck").click(function() {
        if(!nameCheck()){
            return;
        }
        let name = $("#name").val();

        //Ajax로 전송
        $.ajax({
            url : '/api/member/validation/name?name='+name,
            data : {},
            type : 'GET',
            success : function(result) {
                if (result) {
                    if(confirm("사용 가능한 닉네임입니다.\n입력하신 닉네임로 결정하시겠습니까?\n")){
                        $("#emailForm").css("boarder-color", "black");
                        $("#name").attr("readOnly",true);
                    }
                }else{
                    $("#name-label").removeClass("visually-hidden");
                    $("#name-label").css("color", "red").text("닉네임: 사용할 수 없는 닉네임 입니다. 다른 닉네임을 입력해 주세요");
                    $("#name").css("boarder-color", "red");
                    $("#name").css("color", "red");
                    $(this).parent().val(name);
                }
            }
        }); //End Ajax
    });
})

// 닉네임 형식 체크
$(document).ready(function () {
    $("#name").on("focusout",function(){
        nameCheck();
    });
});

// password 체크
$(document).ready(function () {
    $("#password").on("focusout",function(){
        let password = $("#password").val();

        if(password == '' || password.length == 0 || isBlank(password)) {
            $("#password-label").removeClass("visually-hidden");
            $("#password-label").css("color", "red").text("비밀번호: 공백은 사용할 수 없습니다.");
            $("#password").attr("required",true);
            return;
        }
        else{
            let regex = new RegExp("(?=.*[0-9])(?=.*[a-zA-Z]).{8,20}");
            let check = regex.test(password);
            if (check) {
                $("#password-label").addClass("visually-hidden");
                $("#password-form").css("--bs-btn-border-color", "#6D6045");
                $("#password").css("color", "#6D6045");
                $("#password").attr("required",false);
                passwordCheck();
            } else {
                $("#password-label").removeClass("visually-hidden");
                $("#password-label").css("color", "red").text("비밀번호: 8~16자의 영문 대/소문자, 숫자를 사용해 주세요.");
                $("#password").css("--bs-btn-border-color", "red");
                $("#password").css("color", "red");
                $("#password").attr("required",true);
                $(this).val(password);
            }
        }
    });
});

// password-check
$(document).ready(function () {
    $("#password-check").on("focusout",function(){
        passwordCheck();
    });
});

// 회원 가입 클릭
$(document).ready(function () {
    $("#join").click(function(){
        if(!$("#email").is("[readOnly]")) {
            alert("이메일: 이메일 중복 체크를 하셔야 합니다.");
        }else{
            if(!$("#name").is("[readOnly]")){
                alert("닉네임: 닉네임 중복 체크를 하셔야 합니다.");
            }else{
                if(!$("#password").is("[required]")){
                    if(!$("#password-check").is("[required]")){
                        join();
                    }
                    else{
                        alert("비밀번호: 비밀번호를 확인하셔야 합니다.");
                    }
                }
                else{
                    alert("비밀번호: 필수 정보입니다.");
                }
            }
        }
    });
});

// 회원 가입 요청
function join() {
    let data = {
        email : $("#email").val(),
        name : $("#name").val(),
        password : $("#password").val()
    };
    //Ajax로 전송
    $.ajax({
        url : '/api/member',
        data : JSON.stringify(data),
        type : 'POST',
        contentType: "application/json ; charset=UTF-8",
        dataType : 'json',
        success : function(data) {
            if(data){
                alert("회원 가입에 성공하였습니다!!");
                window.location.href="/member/login";
            }else{
                alert("회원 가입에 실패하였습니다...");
            }
        }
    }); //End Ajax
}

// email Check 함수
function emailCheck() {
    let email = $("#email").val();

    if(email == '' || email.length == 0 || isBlank(email)) {
        $("#email-label").removeClass("visually-hidden");
        $("#email-label").css("color", "red").text("이메일: 공백은 사용할 수 없습니다.");
        return false;
    }
    else{
        let regex = new RegExp("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$");
        let check = regex.test(email);
        if (check) {
            $("#email-label").addClass("visually-hidden");
            $("#email-form").css("--bs-btn-border-color", "#6D6045");
            $("#email").css("color", "#6D6045");
            $("#email").attr("required",false);
            return true;
        } else {
            $("#email-label").removeClass("visually-hidden");
            $("#email-label").css("color", "red").text("이메일: 이메일 형식이 맞지 않습니다.");
            $("#email").css("--bs-btn-border-color", "red");
            $("#email").css("color", "red");
            $("#email").attr("required",true);
            $(this).val(email);
            return false;
        }
    }
}

// name check 함수
function nameCheck() {
    let name = $("#name").val();

    if(name == '' || name.length == 0 || isBlank(name)) {
        $("#name-label").removeClass("visually-hidden");
        $("#name-label").css("color", "red").text("닉네임: 공백은 사용할 수 없습니다.");
        return false;
    }
    else{
        if (name.length>=2) {
            $("#name-label").addClass("visually-hidden");
            $("#name-form").css("--bs-btn-border-color", "#6D6045");
            $("#name").css("color", "#6D6045");
            return true;
        } else {
            $("#name-label").removeClass("visually-hidden");
            $("#name-label").css("color", "red").text("닉네임: 닉네임은 두 글자 이상이어야 합니다.");
            $("#name").css("--bs-btn-border-color", "red");
            $("#name").css("color", "red");
            $(this).val(name);
            return false;
        }
    }
}

// password check 함수
function passwordCheck() {
    let password = $("#password").val();
    let password_check = $("#password-check").val();

    if(password==password_check) {
        $("#password-check-label").addClass("visually-hidden");
        $("#password-check-form").css("--bs-btn-border-color", "#6D6045");
        $("#password-check").css("color", "#6D6045");
        $("#password-check").attr("required",false);
    }
    else{
        $("#password-check-label").removeClass("visually-hidden");
        $("#password-check-label").css("color", "red").text("비밀번호: 비밀번호가 일치하지 않습니다.");
        $("#password-check").css("--bs-btn-border-color", "red");
        $("#password-check").css("color", "red");
        $("#password-check").attr("required",true);
        $(this).val(password);
    }
}

// 공백 확인 함수
function isBlank(param) {
    let blank = new RegExp(" ");
    return blank.test(param);
}