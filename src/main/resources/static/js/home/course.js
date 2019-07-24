$(function () {
    var page = 0;
    var ccatalog = 1;
    var ccatalog2 = 1;
    var coursecontentvue;

    var isloading = false;


    function getapage(pagenum, ccatalog, ccatalog2, m) {
        if (isloading == false) {
            isloading = true
            $.ajax({
                url: "/course/getapage",
                data: {
                    page: pagenum,
                    ccatalog: ccatalog,
                    ccatalog2: ccatalog2
                },
                success: function (data) {
                    console.log(data)
                    if (data.length == 0) {
                    }
                    page = page + 1

                    data.forEach(function (item) {
                        coursecontentvue.items.push(item)
                    });
                    isloading = false
                },
                error: function () {
                    toastr.error("error");
                }
            })
        }

    }

    function cleardata() {
        page = 0;
        var length = coursecontentvue.items.length
        for (i = 0; i < length; i++) {
            coursecontentvue.items.pop()
        }
    }

    function clearcontent() {
        $("#coursecontent").empty()
    }

    function activecccatalog(elementid) {
        $("#shipinkecheng").css("background", "");
        $("#yinpinkecheng").css("background", "");
        $("#wendangkecheng").css("background", "");
        $("#waibulianjie").css("background", "");
        $(elementid).css("background", "#007bff");
    }

    function activecccatalog2(ccatalog2id) {
        $(".ccatalog2btn").each(function (i) {
            $(this).css("background", "")
            if (this.dataset.ccata2logid == ccatalog2id) {
                $(this).css("background", "gray")
            }

        })
    }

    console.log($(".ccatalog1btn"))


    //点击ccatalog1
    $("body").on("click", ".ccatalog1btn", function () {
        if (this.id == "shipinkecheng") {
            ccatalog = 1;
        } else if (this.id == "yinpinkecheng") {
            ccatalog = 2;
        } else if (this.id == "wendangkecheng") {
            ccatalog = 3;
        } else if (this.id == "waibulianjie") {
            ccatalog = 4;
        }
        page=0;
        activecccatalog("#" + this.id)
        cleardata()
        getapage(page, ccatalog, ccatalog2)

    });


    //点击ccatalog2
    $("body").on("click", ".ccatalog2btn", function () {
        page=0;
        console.log(this.dataset)
        ccatalog2 = this.dataset.ccata2logid;
        cleardata()

        activecccatalog2(ccatalog2)
        getapage(page, ccatalog, ccatalog2)
    });


    function init() {
        coursecontentvue = new Vue({
            el: '#coursecontainer',
            data: {
                items: []
            }, methods: {
                tocourse: function (event) {
                    console.log(event)

                }
            }
        });
        activecccatalog("#shipinkecheng")
        activecccatalog2(1)
    }

    init();


    getapage(page, ccatalog, ccatalog2)

    $(window).scroll(function () {
        var scrollTop = $(this).scrollTop();
        var scrollHeight = $(document).height();
        var windowHeight = $(this).height();
        console.log(scrollTop + windowHeight + ":" + scrollHeight)
        if (scrollTop + windowHeight > scrollHeight - 50) {
            getapage(page, ccatalog, ccatalog2)
        }
    });

});

