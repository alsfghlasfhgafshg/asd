$(function() {

    var _pageSize=10; // 存储用于搜索

    function getProductsByCatalog(pageIndex, pageSize) {
        $.ajax({
            url: "/productservice",
            contentType : 'application/json',
            data:{
                "async":true,
                "pageIndex":pageIndex,
                "pageSize":pageSize,
                "catalog": catalogId
            },
            success: function(data){
                $("#productContainer").html(data);
            },
            error : function() {
                toastr.error("error!");
            }
        });
    }

    var catalogId;

    // 根据分类查询
    $(".catalog-content-container").on("click",".product-query-by-catalog", function () {
        catalogId = $(this).attr('catalogId');
        getProductsByCatalog(0, _pageSize);
    });

});