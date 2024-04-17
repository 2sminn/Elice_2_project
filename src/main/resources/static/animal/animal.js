const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
let totalCount = 0;
let page = parseInt(urlParams.get('page'));
let buttons;
const maxButton= 5;
const maxPage = Math.ceil(12 / totalCount);
let response;

$(document).ready(function () {
    if (isNaN(page) || page < 1 || page > maxPage) {
        page = 1;
    }

    renderAnimal();
    renderButton();

});

function renderButton() {
    if (page < 3) {
        buttons = [1, 2, 3, 4, 5];
    } else if (page + 2 > maxPage) {
        buttons = [maxPage - 4, maxPage - 3, maxPage - 2, maxPage - 1, maxPage];
    } else {
        buttons = [page - 2, page - 1, page, page+1, page+2];
    }

    var buttonHTML = '';
    buttons.forEach(function(num) {
        if(num === page) {
            buttonHTML += `<a class="button" style="color: #F0AB0F;" onclick="changePage(${num})">${num}</a>`
        } else {
            buttonHTML += `<a class="button" onclick="changePage(${num})">${num}</a>`
        }
    });

    document.querySelector("#buttons").innerHTML = buttonHTML;
}

function getDate() {
    var currentDate = new Date();

    var year = currentDate.getFullYear().toString();
    var month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
    var day = currentDate.getDate().toString().padStart(2, '0');

    return year + month + day;
}

function renderAnimal() {
    var formattedDate = getDate();

    var apiUrl = 'https://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic';
    var params = {
        serviceKey: 'mRbgEkHOWYZFJrZEDYNcJNYBoRvP1yuMsuVJrZknnJ0XbGU0gedzG5OIVq43YSSyxgmv9KUSr8y8LNWd1JtV9Q==',
        bgnde: '20231201',
        endde: formattedDate,
        state : 'protect',
        pageNo: page,
        numOfRows: 12
    };

    let resultHTML = "";

    $.ajax({
        url: apiUrl,
        type: 'GET',
        data: params,
        dataType: 'xml',
        async: false,
        success: function (xml) {
            response = removeText(xmlToJson(xml));
            let items = response.response.body.items.item;
            totalCount = response.response.body.totalCount;
            items.forEach(function(item, index) {

                const kind = item.kindCd.replace(/^\[.*?\]\s*/, '');

                let age;
                const yearPattern = /\d{4}/g; // 4자리 연도 패턴
                const matches = item.age.match(yearPattern); // 연도 추출

                if (matches && matches.length > 0) {
                    const birthYear = parseInt(matches[0]); // 출생 연도 추출
                    age = `${2024 - birthYear + 1}살`;
                } else if (item.age.includes("(60일미만)")) {
                    age = "신생아";
                } else {
                    age = "나이 정보 없음";
                }

                const sex = function() {
                    if (item.sexCd === 'F') {
                        return "여아";
                    } else {
                        return "남아";
                    }
                }

                resultHTML += `<div class="box" onclick="goToDetailPage(${index})">
                       <div class="item">
                         <div class="img">
                           <img class="image-thumbnail" src="${item.filename}">
                         </div>
                         <div class="detail">
                           <p class="font-middle">품종: ${kind}</p>
                           <p class="font-middle">색상: ${item.colorCd}</p>
                           <p class="font-middle">성별: ${sex()}</p>
                           <p class="font-middle">나이: ${age}</p>
                         </div>
                       </div>
                       <hr style="border: solid 0.5px; color: #E4E4E6; margin: 15px">
                       <span class="font-middle" style="color: gray;">지역: ${item.orgNm}</span></div>`
            });

        },
        error: function (xhr, status, error) {
            // 요청이 실패한 경우 콘솔에 오류 메시지 출력
            console.error('GET 요청 실패:', status, error);
        }
    });

    document.querySelector("#item-box").innerHTML = resultHTML;
}

function xmlToJson(xml) {
    // Create the return object
    var obj = {};

    if (xml.nodeType == 1) {
        // element
        // do attributes
        if (xml.attributes.length > 0) {
            obj["@attributes"] = {};
            for (var j = 0; j < xml.attributes.length; j++) {
                var attribute = xml.attributes.item(j);
                obj["@attributes"][attribute.nodeName] = attribute.nodeValue;
            }
        }
    } else if (xml.nodeType == 3) {
        // text
        obj = xml.nodeValue;
    }

    // do children
    // If all text nodes inside, get concatenated text from them.
    var textNodes = [].slice.call(xml.childNodes).filter(function(node) {
        return node.nodeType === 3;
    });
    if (xml.hasChildNodes() && xml.childNodes.length === textNodes.length) {
        obj = [].slice.call(xml.childNodes).reduce(function(text, node) {
            return text + node.nodeValue;
        }, "");
    } else if (xml.hasChildNodes()) {
        for (var i = 0; i < xml.childNodes.length; i++) {
            var item = xml.childNodes.item(i);
            var nodeName = item.nodeName;
            if (typeof obj[nodeName] == "undefined") {
                obj[nodeName] = xmlToJson(item);
            } else {
                if (typeof obj[nodeName].push == "undefined") {
                    var old = obj[nodeName];
                    obj[nodeName] = [];
                    obj[nodeName].push(old);
                }
                obj[nodeName].push(xmlToJson(item));
            }
        }
    }
    return obj;
}

function removeText(jsonObj) {
    if (typeof jsonObj !== 'object') {
        return jsonObj;
    }
    for (var key in jsonObj) {
        if (key === '#text') {
            // "#text" 키가 발견되면 삭제
            delete jsonObj[key];
        } else {
            // 재귀적으로 객체의 속성들을 처리
            jsonObj[key] = removeText(jsonObj[key]);
        }
    }
    return jsonObj;
}

function goToDetailPage(index) {
    const animalString = encodeURIComponent(JSON.stringify(response.response.body.items.item[index]));
    window.location.href = `/animal/detail?animal=${animalString}`;
}

function changePage(num) {
    window.location.href = `/animal?page=${num}`;
}
