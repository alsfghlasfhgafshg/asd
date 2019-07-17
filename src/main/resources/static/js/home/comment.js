

$(function() {

    // 获取评论列表
    function getCommnet(articleId) {

        $.ajax({
            url: '/home/article/comments',
            type: 'GET',
            data:{"articleId":articleId},
            success: function(data){
                $("#mainContainer").html(data);

            },
            error : function() {
                toastr.error("error!");
            }
        });
    }

    // 提交评论
    $(".article-content-container").on("click",".submitcontent", function () {
        // 获取 CSRF Token
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: '/home/article/comments',
            type: 'POST',
            data:{"articleId":articleId, "commentContent":$('#commentContent').val()},
            beforeSend: function(request) {
                request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
            },
            success: function(data){
                if (data.success) {
                    // 清空评论框
                    $('#commentContent').val('');
                    // 获取评论列表
                    getCommnet(articleId);
                } else {
                    toastr.error(data.message);
                }
            },
            error : function() {
                toastr.error("error!");
            }
        });
    });



    // 删除评论
    $(".article-content-container").on("click",".article-delete-comment", function () {
        // 获取 CSRF Token
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: '/home/article/comments/'+$(this).attr("commentId")+'?articleId='+articleId,
            type: 'DELETE',
            beforeSend: function(request) {
                request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
            },
            success: function(data){
                if (data.success) {
                    // 获取评论列表
                    getCommnet(articleId);
                } else {
                    toastr.error(data.message);
                }
            },
            error : function() {
                toastr.error("error!");
            }
        });
    });

    // 初始化 博客评论
    getCommnet(articleId);
});