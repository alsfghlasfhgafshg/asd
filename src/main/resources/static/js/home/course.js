$(function () {
    var page = 0;
    var ccatalog = 1;
    var ccatalog2 = 1;
    var coursecontentvue;

    var isloading = false;
    var mmui;


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
                    isloading = false
                    m.endPullupToRefresh()
                    if (data.length == 0) {
                        mui("#pullRefresh").pullRefresh().disablePullupToRefresh()
                    }
                    page = page + 1
                    console.log(data)
                    data.forEach(function (item) {
                        coursecontentvue.items.push(item)
                    });
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
        activecccatalog("#"+this.id)
        cleardata()
        mui('#pullRefresh').pullRefresh().refresh(true);
        getapage(page, ccatalog, ccatalog2, mmui)

    });


    //点击ccatalog2
    $("body").on("click", ".ccatalog2btn", function () {
        ccatalog2id = this.dataset.ccata2logid;
        mui('#pullRefresh').pullRefresh().refresh(true);
        activecccatalog2(ccatalog2id)
        getapage(page, ccatalog, ccatalog2, mmui)
    });


    function init() {
        coursecontentvue = new Vue({
            el: '#coursecontainer',
            data: {
                items: []
            }
        });
        activecccatalog("#shipinkecheng")
        activecccatalog2(1)
    }

    init();

    mui.init({
        pullRefresh: {
            container: "#pullRefresh",//待刷新区域标识，querySelector能定位的css选择器均可，比如：id、.class等
            up: {
                height: 50,//可选.默认50.触发上拉加载拖动距离
                auto: true,//可选,默认false.自动上拉加载一次
                contentrefresh: "正在加载...",//可选，正在加载状态时，上拉加载控件上显示的标题内容
                contentnomore: '没有更多数据了',//可选，请求完毕若没有更多数据时显示的提醒内容；

                callback: function () {
                    mmui = this;
                    getapage(page, ccatalog, ccatalog2, this)

                }
            }
        }
    });

});

