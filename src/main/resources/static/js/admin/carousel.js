var vuecontent = new Vue({
    el: '#vuecontent',
    data: {
        items: {}
    }
})



$.ajax({
    url: "/admins/carousel/model",
    success: function (data) {
        vuecontent.items=data

    }
})

