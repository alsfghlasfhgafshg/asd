$(function () {
    var _pageSize;

    function getArticles(pageIndex, pageSize) {
        $.ajax({
            url: "/articles",
            contentType: 'application/json',
            data: {
                "async": true,
                "pageIndex": pageIndex,
                "pageSize": pageSize,
            },
            success: function (data) {
                $("#mainContainer").html(data);
            },
            error: function () {
                toastr.error("访问articles失败");
            }
        })
    };

    $.tbpage("#mainContainer", function (pageIndex, pageSize) {
        getUsersByName(pageIndex, pageSize);
        _pageSize = pageSize;
    });

    $("#rightContainer").on("click", "#addArticle", function () {
        $.ajax({
            url: "/articles/add",
            success: function (data) {
                $("#articleFormContainer").html(data);
            },
            error: function () {
                toastr.error("error!");
            }
        })
    });

    $("#rightContainer").on("click", ".blog-edit-article", function () {
        $.ajax({
            url: "/articles/edit/" + $(this).attr("articleId"),
            success: function (data) {
                $("#articleFormContainer").html(data);
            },
            error: function () {
                toastr.error("error!");
            }
        })
    });

    $("#submitEdit").click(function () {
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");

        var editor = window.editor;
        var bid = $("#articleId").val();
        var htmlContent = editor.getData();
        var title = $("#title").val();
        var author = $("#author").val();
        var cid = $("#catalog").val();
        var data= {"id":bid,"title":title,"author":author,"htmlContent":htmlContent,"cid":cid};
        $.ajax({
            url: "/articles",
            type: 'post',
            beforeSend: function (request) {
                request.setRequestHeader(csrfHeader, csrfToken);
            },
            async:true,
            contentType:"application/json;charset=utf-8",
            data: JSON.stringify(data),
            success: function (da) {
                $('#articleForm')[0].reset();
                if (da.success) {
                    getArticles(0, _pageSize);
                } else {
                    toastr.error(data.message);
                }
            }
        })
    });

    $("#rightContainer").on("click", ".blog-delete-article", function () {

        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: "/articles/" + $(this).attr("articleId"),
            type: 'delete',
            beforeSend: function (request) {
                request.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                if (data.success) {
                    getArticles(0, _pageSize);
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error("error!");
            }
        });
    });
});