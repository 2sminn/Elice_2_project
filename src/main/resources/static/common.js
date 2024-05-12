function getId() {
    let data = {token: localStorage.getItem("token")}
    $.ajax({
        url: '/member/id',
        data: JSON.stringify(data),
        type: 'POST',
        contentType: "application/json",
        dataType: 'json',
        async: false,
        success: function (data) {
            return data.id;
        }
    });
}
