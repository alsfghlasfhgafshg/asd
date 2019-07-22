$(function () {
    $.tbpage("#mainContainer", function (pageIndex, pageSize) {
        console.log(pageIndex);
        // console.log(pageIndex, pageSize);
        _pageSize = pageSize;


        $.ajax({
            url: "/admins/courses",
            data: {
                "async": true,
                "pageIndex": pageIndex,
                "pageSize": pageSize,
            },
            success: function (data) {
                console.log(data)
                $("#mainContainer").html(data)
            },
            error: function () {
                toastr.error("error");
            }
        })


    });



    //添加按钮
    $("#addbtn").click(function () {
        $("#exampleModalLongTitle").text("添加课程")
        $("#servingsubmitbtn").data("ischange", false)
        getscatalog()
        getcatalog2()
    });

    //获得catalog
    function getscatalog(scatalogid) {
        $.ajax({
            url: "/admins/courses/getccatalog",
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
                } else {
                    $("#catalogtagselect").val(scatalogid);
                }
            },
            error: function () {
                toastr.error("error");
            }
        })
    }

    //获得catalog2
    function getcatalog2(scatalog2id) {
        $.ajax({
            url: "/admins/courses/getccatalog2",
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

    //添加提交按钮
    $("#servingsubmitbtn").click(
        function submitserving() {

            var courseid = $("#courseid").val();

            var catalogid = $("#catalogtagselect").val();
            var catalog2id = $("#catalogtag2select").val();
            var title = $("#titleinput").val();
            var teacher = $("#teacherinput").val();
            var pic = $("#picinput").val();
            var src = $("#srcinput").val();
            var summary = $("#summaryinput").val();

            var csrfToken = $("meta[name='_csrf']").attr("content");
            var csrfHeader = $("meta[name='_csrf_header']").attr("content");

            console.log(courseid)
            console.log(catalogid)
            console.log(catalog2id)
            console.log(title)
            console.log(teacher)
            console.log(pic)
            console.log(src)
            console.log(summary)


            var formdata = new FormData()
            formdata.append("courseid", courseid)
            formdata.append("catalogid", catalogid)
            formdata.append("catalog2id", catalog2id)
            formdata.append("title", title)
            formdata.append("teacher", teacher)
            formdata.append("summary", summary)

            var picfilename;
            if ($("#picinput")[0].files[0] != null) {
                picfilename = $("#picinput")[0].files[0].name
                formdata.append("pic", $("#picinput")[0].files[0], picfilename)
            }

            var srcfilename;
            if ($("#srcinput")[0].files[0] != null) {
                srcfilename = $("#srcinput")[0].files[0].name
                formdata.append("src", $("#srcinput")[0].files[0], srcfilename)
            }


            var url;
            var successmsg;

            if ($("#servingsubmitbtn").data()["ischange"] == false) {
                url = "/admins/courses/addcourse";
                successmsg = "添加成功";

            } else if ($("#servingsubmitbtn").data()["ischange"] == true) {
                formdata.append("courseid", courseid);

                url = "/admins/courses/changecourse";
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

    //删除按钮
    $(".deletebtn").click(function (e) {
        $("#confirmdelete").data("courseid", e.currentTarget.dataset.courseid)
    });

    //修改按钮
    $(".changebtn").click(function (e) {
        console.log(e.currentTarget.dataset.courseid);
        $("#exampleModalLongTitle").text("修改服务");
        clearinput();

        getscatalog()
        getcatalog2()

        $.ajax({
            url: "/admins/courses/getcourse",
            data: {
                courseid: e.currentTarget.dataset.courseid
            },
            success: function (data) {
                data=JSON.parse(data)

                $("#catalogtagselect").val(data.cCatalog.id);
                $("#catalogtag2select").val(data.cCatalog2.id);
                $("#courseid").val(data.id);
                $("#titleinput").val(data.title);
                $("#teacherinput").val(data.teacher);
                $("#teacherinput").val(data.teacher);
                $("#summaryinput").val(data.summary);
            },
            error: function () {
                toastr.error("error");
            }
        })

        $("#servingsubmitbtn").data("ischange", true);
        // getcour(e.currentTarget.dataset.courseid)
    });


    //确认删除
    $("#confirmdelete").click(function () {
        var courseid = $("#confirmdelete").data()["courseid"];


        var formdata = new FormData();
        formdata.append("courseid", courseid);


        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: "/admins/courses/delete",
            type: 'post',
            data: formdata,
            processData: false,
            contentType: false,
            beforeSend: function (request) {
                request.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {

                if (data.error == 0) {
                    showsuccess("删除成功", true)
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
        $("#addupdatecourses").modal('hide');

        $("#success").modal('show');
        if (refreshpage == true) {
            $("#closesuccess").data("refreshpage", true)
        }
    }

    function clearinput() {
        $("#catalogtagselect").empty()
        $("#catalogtag2select").empty()
        $("#titleinput").empty()
        $("#teacherinput").empty()
        $("#picinput").empty()
        $("#srcinput").empty()
        $("#summaryinput").empty()
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


});

