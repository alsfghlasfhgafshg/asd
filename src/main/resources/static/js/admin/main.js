$(function () {


    $(".blog-menu .list-group-item").click(function () {

        var url = $(this).attr("url");

        $("#titleid").data("rightContainerurl", url)

        $(".blog-menu .list-group-item").removeClass("active");
        $(this).addClass("active");

        showright(url)

    });


    $(".blog-menu .list-group-item:first").trigger("click");

});

function showright(url) {
    $.ajax({
        url: url,
        success: function (data) {
            $("#rightContainer").html(data);
        },
        error: function () {
            alert("error");
        }
    });
}