$(function () {

    function getscatalog() {
        $.ajax({
            url: "/admins/serving/getscatalog",
            success: function (data) {
                console.log(data)
                data.forEach(function (item) {
                    var tempoption = document.createElement("option")
                    tempoption.setAttribute("value", item.id)
                    tempoption.innerText = item.name
                    $("#catalogtagselect").prepend(tempoption);
                })

                var catalogid = $("#catalogtagselect").val()
                getcatalog2(catalogid)
            },
            error: function () {
                toastr.error("error");
            }
        })
    }

    function getcatalog2(catalogid) {

        $.ajax({
            url: "/admins/serving/getscatalog2",
            data: {
                scatalogid: catalogid
            },
            success: function (data) {
                console.log(data)
                data.forEach(function (item) {

                    var tempoption = document.createElement("option")
                    tempoption.setAttribute("value", item.id)
                    tempoption.innerText = item.name

                    $("#catalogtag2select").prepend(tempoption);
                })
            },
            error: function () {
                toastr.error("error");
            }
        })

    }

    $("#addbtn").click(function () {
        getscatalog()

    });

    $("#catalogtagselect").change(function () {
        var catalogid = $("#catalogtagselect").val()
        $("#catalogtag2select").empty()
        getcatalog2(catalogid)
    })

    $("#servingsubmitbtn").click(
        function submitserving() {

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
            formdata.append("pic", $("#picinput")[0].files[0], $("#picinput")[0].files[0].name)

            $.ajax({
                url: "/admins/serving/addserving",
                type: 'post',
                data: formdata,
                processData: false,
                contentType: false,
                beforeSend: function (request) {
                    request.setRequestHeader(csrfHeader, csrfToken);
                },
                success: function (data) {

                    if (data.error == 0) {
                        showsuccess()
                    }
                },
                error: function () {
                    toastr.error("error!");
                }
            });
        })


    function showsuccess() {
        $("#addserving").modal('hide');
        $("#success").modal('show');
    }

    $(".changebtn").click(function () {
        var servingid=$(this).attr('data-servingid');
        console.log(servingid);
        getserving(servingid)
    });


    function getserving(servingid) {
        $.ajax({
            url: "/admins/serving/getscatalogbyid",
            data:{
                servingid:servingid
            },
            success: function (data) {
                console.log(data)
            },
            error: function () {
                toastr.error("error");
            }
        })

    }



});

