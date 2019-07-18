$(function() {

    var _pageSize=10; // 存储用于搜索

    function getArticlesByCatalog(pageIndex, pageSize) {
        $.ajax({
            url: "/home",
            contentType : 'application/json',
            data:{
                "async":true,
                "pageIndex":pageIndex,
                "pageSize":pageSize,
                "catalog": catalogId
            },
            success: function(data){
                $("#articless").html(data);
                $("#articles").html(data);
            },
            error : function() {
                toastr.error("error!");
            }
        });
    }

    var catalogId;

    // 根据分类查询
    $(".catalog-content-container").on("click",".article-query-by-catalog", function () {
        catalogId = $(this).attr('catalogId');
        getArticlesByCatalog(0, _pageSize);
    });

});

