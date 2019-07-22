$(function () {
    var _pageSize;

    function getArticles(pageIndex, pageSize) {
        $.ajax({
            url: "/admins/articles",
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
        getArticles(pageIndex, pageSize);
        _pageSize = pageSize;
    });

    $("#rightContainer").on("click", "#addArticle", function () {
        $.ajax({
            url: "/admins/articles/add",
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
            url: "/admins/articles/edit/" + $(this).attr("articleId"),
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
        var content = $(".ck-content").text();
        var htmlContent = editor.getData();
        var title = $("#title").val();
        var author = $("#author").val();
        var cid = $("#catalog").val();
        var data= {"id":bid,"title":title,"author":author,"htmlContent":htmlContent,"cid":cid};
        var filename;



        var formdata = new FormData();
        formdata.append("id",bid);
        formdata.append("title",title);
        formdata.append("author",author);
        formdata.append("htmlContent",htmlContent);
        formdata.append("Content",content);
        formdata.append("cid",cid);

        if ($("#picinput")[0].files[0]!=null){
            filename = $("#picinput")[0].files[0].name;
            formdata.append("avatar",$("#picinput")[0].files[0],filename);
        }

        $.ajax({
            url: "/admins/articles",
            type: 'post',
            beforeSend: function (request) {
                request.setRequestHeader(csrfHeader, csrfToken);
            },
            async:true,
            processData:false,
            contentType:false,
            data: formdata,
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
            url: "/admins/articles/" + $(this).attr("articleId"),
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