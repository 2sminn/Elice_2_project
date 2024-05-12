const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const animalString = urlParams.get('animal');
const animal = JSON.parse(decodeURIComponent(animalString));


$(document).ready(function () {
    renderDetail();
});

function renderDetail() {

    const kind = animal.kindCd.replace(/^\[.*?\]\s*/, '');

    let age;
    const yearPattern = /\d{4}/g; // 4자리 연도 패턴
    const matches = animal.age.match(yearPattern); // 연도 추출

    if (animal.age.includes("(60일미만)")) {
        age = "<span style=\"color: #F0AB0F\">신생아</span>";
    } else if (matches && matches.length > 0) {
        const birthYear = parseInt(matches[0]); // 출생 연도 추출
        age = `<span style=\"color: #F0AB0F\">${2024 - birthYear + 1}</span>살`;
    } else {
        age = "나이 정보 없음";
    }

    const sex = function() {
        if (animal.sexCd === 'F') {
           return "여자";
        } else {
            return "남자";
        }
    }

    const neuter = function() {
        if (animal.neuterYn === 'Y') {
            return "중성화 수술을 받은적이 있어요.";
        } else {
            return "중성화 수술을 받은적이 없어요.";
        }
    }

    let resultHTML = `
        <div class="item">
          <div class="img">
            <img class="image-thumbnail" src="${animal.popfile}">
          </div>
          <div class="detail">
            <div class="title">
              <p>${kind} | ${age}</p>
            </div>
            <hr style="border: solid 0.5px; color: #E4E4E6; margin: 15px">
            <div class="content">
              <p>저는 ${sex()}이고요, ${neuter()}</p>
              <p>털색은 ${animal.colorCd}이고, 몸무게는 ${animal.weight}이에요.</p>
              <p>특징: ${animal.specialMark}</p>
            </div>
            <hr style="border: solid 0.5px; color: #E4E4E6; margin: 15px">
            <div class="address">
              <p>보호소: ${animal.careNm}</p>
              <p>보호소 연락처: ${animal.careTel}</p>
              <p>보호주소: ${animal.careAddr}</p> 
            </div>
          </div>
        </div>
    `

    document.querySelector("#box").innerHTML = resultHTML;
}