// const headerFragment = `
// <a href="#"><p>홈</p></a>
// <a href="#"><p>쇼핑</p></a>
// <a href="#"><p>콘텐츠</p></a>
// <a href="#"><p>커뮤니티</p></a>
// <a href="#" class="write-button"><p>글쓰기</p></a>
// `;
//
// document.getElementById('header-fragment').innerHTML += headerFragment;
window.onload=function (){
    alert("우럭 ㄱ?");
    const token = localStorage.getItem("token");
    if(token==null){
        alert("asdfasdf");
        return;
    }
    $("#login").text("마이 페이지");
    // const loginBtn = document.getElementById("login");
    // loginBtn.text("마이 페이지");
    alert("성공");
}
