//   요청한 데이터 : {"member_list":[
//    {"id":"aa1","pw":"bb","addr":"cc","tel":"dd"},
//    {"id":"aa2","pw":"bb","addr":"cc","tel":"dd"},
//    {"id":"aa3","pw":"bb","addr":"cc","tel":"dd"}
//  ]}
$(document).ready(function(){
    $("#listButton").click(getMemberList); //id="listButton"인 태그에 click하면 function getMemberList() 실행
});
function getMemberList(){
    $.ajax({
        url:"list.jsp",                    //list.jsp에 AJAX요청
        success:function(data){
            let obj=JSON.parse(data);      //data를 받아와서 JSON형태로 변환
            let array=["<ol>"];
            obj["member_list"].forEach(
                member =>  array.push("<li>"+member.id+"</li>")
            //JSON에 있는 member.id의 value를 li태그에 넣어서 array에 넣어줌
            );
            array.push("</ol>");
            $("#result").html(array.join(""));
            //array의 요소들을 다 합쳐서 하나로 만든후 id="result"인 태그에 html로 출력
        }
    });
}