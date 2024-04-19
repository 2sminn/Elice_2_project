let orderId;

// URL에서 쿼리 매개변수를 가져오는 함수
function getUrlParameter(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    var results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}


$(document).ready(function () {
    orderId = getUrlParameter('orderId');
    orderRetry();
    location.replace('/order');

});


function orderRetry() {
    $.ajax({
        url: '/api/order/' + orderId,
        type: 'GET',
        async: false,
        contentType: 'application/json',
        success: function (response) {
            localStorage.setItem("orderItemsId", JSON.stringify(response.orderItemIds));
        },
        error: function (xhr, status, error) {
            console.error('API 호출 실패:', status, error);
        }
    });
}