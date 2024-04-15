$(document).ready(function () {
    $("#execPostcode").click(function () {
        execPostcode();
    })
})


function execPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if (data.userSelectedType === 'R') {
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            $('#zipCode').val(data.zonecode);
            $('#street').val(addr+extraAddr);
        }
    }).open();
}

let id;

window.onload = function () {
    getDetail();
}

function getDetail() {
    let data = {token: localStorage.getItem("token")}
    $.ajax({
        url: '/api/address/member',
        data: JSON.stringify(data),
        type: 'POST',
        contentType: "application/json",
        dataType: 'json',
        async: false,
        success: function (data) {
            $("#email").text(data.member.email);
            $("#nickname").val(data.member.name);
            id=data.member.id;
            if(data.name!=null){
                $("#name").val(data.name);
            }
            if(data.phone!=null){
                $("#phone").val(data.phone);
            }
            if(data.address!=null){
                $("#zipCode").val(data.address.zipCode);
                $("#street").val(data.address.street);
                $("#detail").val(data.address.detail);
            }
        }
    });
}

$(document).ready(function () {
    $("#nicknameChange").click(function () {
        changeNickName();
    })
})

function changeNickName() {
    const btnElement = document.getElementById("nicknameChange");
    if(btnElement.innerText==="변경"){
        $("#nickname").attr("readOnly",false);
        btnElement.innerText="저장";
    }else {
        nameFormCheck();
    }
}

function nameFormCheck(){
    if(!nameCheck()){
        return;
    }
    let name = $("#nickname").val();

    //Ajax로 전송
    $.ajax({
        url : '/api/member/validation/name?name='+name,
        data : {},
        type : 'GET',
        success : function(result) {
            if (result) {
                if(confirm("사용 가능한 닉네임입니다.\n입력하신 닉네임로 결정하시겠습니까?\n")){
                    $("#nickname").attr("readOnly",true);
                    let data = {
                        name:name,
                        token:localStorage.getItem("token")
                    }
                    $.ajax({
                        url : '/api/member',
                        data : JSON.stringify(data),
                        type : 'PUT',
                        contentType: "application/json",
                        success: function (){
                            const btnElement = document.getElementById("nicknameChange");
                            btnElement.innerText="변경";
                        }
                    });
                }
            }else{
                $("#error").removeClass("visible");
                $("#nickname").val(name);
            }
        }
    });
}

// 닉네임 형식 체크
$(document).ready(function () {
    $("#nickname").on("focusout",function(){
        nameCheck();
    });
});

function nameCheck() {
    let name = $("#nickname").val();

    if(name == '' || name.length == 0 || isBlank(name)) {
        $("#error").removeClass("visible");
        $("#nicknameChange").attr("disabled",true);
        return false;
    }
    else{
        if (name.length>=2) {
            $("#error").addClass("visible");
            $("#nicknameChange").attr("disabled",false)
            return true;
        } else {
            $("#error").removeClass("visible");
            $("#nickname").val(name);
            $("#nicknameChange").attr("disabled",true)
            return false;
        }
    }
}

function isBlank(param) {
    let blank = new RegExp(" ");
    return blank.test(param);
}

//--------------- 회원 정보 변경 ----------------------

$(document).ready(function () {
    $("#change-info").click(function () {
        changeDetails();
    })
})

function changeDetails() {
    const btnElement = document.getElementById("change-info");
    if(btnElement.innerText==="회원 정보 변경"){
        $("#execPostcode").removeClass("visible");
        $("#execPostcode").attr("disabled",false);
        $("#name").attr("readOnly",false);
        $("#phone").attr("readOnly",false);
        $("#detail").attr("readOnly",false);
        btnElement.innerText="회원 정보 저장";
    }else {
        if($("#zipCode").val()==="" || $("#name").val()==="" || $("#phone").val()=="="){
            alert("공백은 사용할 수 없습니다.");
            return;
        }
        let data = {
            name:$("#name").val(),
            phone:$("#phone").val(),
            address:{
                zipCode:$("#zipCode").val(),
                street:$("#street").val(),
                detail:$("#detail").val(),
            },
            token:localStorage.getItem("token")
        }
        $.ajax({
            url : '/api/details',
            data : JSON.stringify(data),
            type : 'POST',
            contentType: "application/json",
            success: function (){
                location.href = location.href;
            }
        });
    }
}
