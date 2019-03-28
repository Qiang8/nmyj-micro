G = G || {};

var initGridParam = { //用于记录表格初始化参数
    currentPage : 1,//记录表格页
    selectedRowIndex : 0,//记录表格索引位置
    gridSearchForm : null//记录表格查询条件
}

//app公共参数
App = App || {};




(function($){

    App['initSystem'] =  function () {
        console.log('initSystem');

        //设置当前菜单形式
        $('ul.nav-list a').click(function(){
            var href = $(this).attr('href');
            var title = $(this).data('title');
            if('#' != href)
            {
                localStorage.setItem("abrs-title",title);
            }
        });

        //局部刷新跳转用
        $('div.page-content').ace_ajax({

            'content_url': function (hash) {
                if (!hash.match(/^page\//)) return false;
                var path = document.location.pathname;

                var param = '';
                if (hash.indexOf('?') != -1) {
                    var arr = hash.split('?');
                    hash = arr[0];
                    param = "?" + arr[1];
                }

                if (path.match(/(\/)(index\.action)?/)) {
                    path = path.replace('/index\.action', '/' + hash.replace('page\/', '') + param);


                    return path;
                }

                return path + "?" + hash.replace(/\//, "=");
            }
        });

        //去掉浮动层
        G.globalAjaxCall();


        //调转地图首页
        G.pjaxLoadPageContentDiv("#page/index-main.action");

    };

    App['getAppPath'] = function (systemCode) {
        if(!systemCode)
        {
            return App.basePath;
        }
    }

})(jQuery);

