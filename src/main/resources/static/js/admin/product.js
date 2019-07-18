$(function () {
    var _pageSize;

    function getProducts(pageIndex, pageSize) {
        $.ajax({
            url: "/admins/products",
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
        getProducts(pageIndex, pageSize);
        _pageSize = pageSize;
    });

    $("#rightContainer").on("click", "#addProduct", function () {
        $.ajax({
            url: "/admins/products/add",
            success: function (data) {
                $("#productFormContainer").html(data);
            },
            error: function () {
                toastr.error("error!");
            }
        })
    });

    $("#rightContainer").on("click", ".blog-edit-product", function () {
        $.ajax({
            url: "/admins/products/edit/" + $(this).attr("productId"),
            success: function (data) {
                $("#productFormContainer").html(data);
            },
            error: function () {
                toastr.error("error!");
            }
        })
    });

    $("#submitEdit").click(function () {
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");

        var  id = $("#productId").val()
        var  name = $("#name").val();
        var  scale = $("#scale").val();
        var  startmoney = $("#startmoney").val();
        var  invetmentperiod = $("#invetmentperiod").val();
        var  performance = $("#performance").val();
        var  startDate = $("#startDate").val();
        var  pcatalogId = $("#catalog").val();
        var  type = $("#type").val();

        da = {"id":id,"name":name,"scale":scale,"startmoney":startmoney,"invetmentperiod":invetmentperiod,
        "performance":performance,"startDate":startDate,"pcatalogId":pcatalogId,"type":type};
        $.ajax({
            url: "/admins/products",
            type: 'post',
            beforeSend: function (request) {
                request.setRequestHeader(csrfHeader, csrfToken);
            },
            async:true,
            contentType:"application/json;charset=utf-8",
            data: JSON.stringify(da),
            success: function (data) {
                $('#productForm')[0].reset();
                if (data.success) {
                    getProducts(0, _pageSize);
                } else {
                    toastr.error(data.message);
                }
            }
        })
    });

    $("#rightContainer").on("click", ".blog-delete-product", function () {

        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: "/admins/products/" + $(this).attr("productId"),
            type: 'delete',
            beforeSend: function (request) {
                request.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                if (data.success) {
                    getProducts(0, _pageSize);
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