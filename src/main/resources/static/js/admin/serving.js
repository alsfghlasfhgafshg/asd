$(function () {

    var _pageSize;

    $.tbpage("#mainContainer", function (pageIndex, pageSize) {
        console.log(pageIndex);
        // console.log(pageIndex, pageSize);
        _pageSize = pageSize;


        $.ajax({
            url: "/admins/serving",
            data: {
                "async": true,
                "pageIndex": pageIndex,
                "pageSize": pageSize,
            },
            success: function (data) {
                $("#mainContainer").html(data)
            },
            error: function () {
                toastr.error("error");
            }
        })


    });


    //获得catalog
    function getscatalog(scatalogid, scatalog2id) {
        $.ajax({
            url: "/admins/serving/getscatalog",
            success: function (data) {
                $("#catalogtagselect").empty()
                data.forEach(function (item) {
                    var tempoption = document.createElement("option")
                    tempoption.setAttribute("value", item.id)
                    tempoption.innerText = item.name
                    $("#catalogtagselect").prepend(tempoption);
                });
                if (scatalogid == null) {
                    var catalogid = $("#catalogtagselect").val();
                    getcatalog2(catalogid)
                } else {
                    $("#catalogtagselect").val(scatalogid);
                    getcatalog2(scatalogid, scatalog2id)
                }
            },
            error: function () {
                toastr.error("error");
            }
        })
    }

    //获得catalog2
    function getcatalog2(catalogid, scatalog2id) {

        $.ajax({
            url: "/admins/serving/getscatalog2",
            data: {
                scatalogid: catalogid
            },
            success: function (data) {
                $("#catalogtag2select").empty()
                data.forEach(function (item) {

                    var tempoption = document.createElement("option")
                    tempoption.setAttribute("value", item.id)
                    tempoption.innerText = item.name

                    $("#catalogtag2select").prepend(tempoption);
                })
                if (scatalog2id != null) {
                    $("#catalogtag2select").val(scatalog2id);
                }
            },
            error: function () {
                toastr.error("error");
            }
        })

    }

    //添加按钮
    $("#addbtn").click(function () {
        $("#exampleModalLongTitle").text("添加服务")
        $("#servingsubmitbtn").data("ischange", false)
        getscatalog()
    });


    $("#catalogtagselect").change(function () {
        var catalogid = $("#catalogtagselect").val()
        $("#catalogtag2select").empty()
        getcatalog2(catalogid)
    })

    //添加提交按钮
    $("#servingsubmitbtn").click(
        function submitserving() {

            var servingid = $("#servingid").val();
            var catalog2id = $("#catalogtag2select").val();
            var title = $("#titleinput").val();
            var subtitle = $("#subtitleinput").val();
            var pic = $("#picinput").val();
            var summary = $("#summaryinput").val();
            var price = $("#priceinput").val();


            var csrfToken = $("meta[name='_csrf']").attr("content");
            var csrfHeader = $("meta[name='_csrf_header']").attr("content");


            // console.log(catalog2id)
            // console.log(title)
            // console.log(subtitle)
            // console.log(pic)
            // console.log(summary)
            // console.log(price)


            var formdata = new FormData()
            formdata.append("catalog2id", catalog2id)
            formdata.append("title", title)
            formdata.append("subtitle", subtitle)
            formdata.append("summary", summary)
            formdata.append("price", price)
            var filename;
            if ($("#picinput")[0].files[0] != null) {
                filename = $("#picinput")[0].files[0].name
                formdata.append("pic", $("#picinput")[0].files[0], filename)
            }

            var url;
            var successmsg;

            if ($("#servingsubmitbtn").data()["ischange"] == false) {
                url = "/admins/serving/addserving";
                successmsg = "添加成功";

            } else if ($("#servingsubmitbtn").data()["ischange"] == true) {
                formdata.append("servingid", servingid);
                url = "/admins/serving/changeserving";
                successmsg = "修改成功";
            }

            $.ajax({
                url: url,
                type: 'post',
                data: formdata,
                processData: false,
                contentType: false,
                beforeSend: function (request) {
                    request.setRequestHeader(csrfHeader, csrfToken);
                },
                success: function (data) {
                    if (data.error == 0) {
                        showsuccess(successmsg, true)
                    }
                },
                error: function () {
                    toastr.error("error!");
                }
            });


        });

    //显示成功
    function showsuccess(successmsg, refreshpage) {
        clearinput()

        $("#succesmsg").text(successmsg);
        $("#delete").modal('hide');
        $("#addserving").modal('hide');

        $("#success").modal('show');
        if (refreshpage == true) {
            $("#closesuccess").data("refreshpage", true)
        }
    }

    //关闭成功按钮
    $("#closesuccess").click(function () {
        $("#success").modal('hide');

        //刷新页面
        if ($("#closesuccess").data()["refreshpage"] == true) {
            showright($("#titleid").data()["rightContainerurl"])

            //隐藏所有modal
            $('.modal').modal('hide') // closes all active pop ups.
            $('.modal-backdrop').remove()

        }
    });

    //修改按钮
    $(".changebtn").click(function (e) {
        console.log(e.currentTarget.dataset.servingid);
        $("#exampleModalLongTitle").text("修改服务")
        $("#servingsubmitbtn").data("ischange", true);
        getserving(e.currentTarget.dataset.servingid)
    });

    //确认删除
    $("#confirmdelete").click(function () {
        var servingid = $("#confirmdelete").data()["servingid"];


        var formdata = new FormData();
        formdata.append("servingid", servingid);


        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: "/admins/serving/delete",
            type: 'post',
            data: formdata,
            processData: false,
            contentType: false,
            beforeSend: function (request) {
                request.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {

                if (data.error == 0) {
                    showsuccess("删除成功")

                    var servinghtmlid = "#serving" + servingid
                    console.log(servinghtmlid)
                    $("#serving" + servingid).remove()
                }
            },
            error: function () {
                toastr.error("error!");
            }
        });


    });

    //删除按钮
    $(".deletebtn").click(function (e) {

        $("#confirmdelete").data("servingid", e.currentTarget.dataset.servingid)

    });


    //获得serving
    function getserving(servingid) {
        $.ajax({
            url: "/admins/serving/getserving",
            data: {
                servingid: servingid
            },
            success: function (data) {
                var json = JSON.parse(data);

                $("#servingid").val(json.id);
                $("#titleinput").val(json.title);
                $("#subtitleinput").val(json.subtitle);
                $("#summaryinput").val(json.summary);
                $("#priceinput").val(json.price);
                getscatalog(json.sCatalog2.sCatalog.id, json.sCatalog2.id);

            },
            error: function () {
                toastr.error("error");
            }
        })
    }

    function clearinput() {
        $("#catalogtag2select").empty()
        $("#catalogtagselect").empty()

        $("#servingid").val("");
        $("#titleinput").val("");
        $("#subtitleinput").val("");
        $("#summaryinput").val("");
        $("#priceinput").val("");
        $("#picinput").val(null);
    }
});

