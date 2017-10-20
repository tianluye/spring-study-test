var hrefs = location.href.split('/');
var baseHref = hrefs[0] + '//' + hrefs[2] + '/' + hrefs[3] + '/';
require.config({
    baseUrl: baseHref + 'resource/scripts',
    paths: {
        "jquery": "jquery",
        "underscore" : "underscore",
        "backbone" : "backbone",
        "bootstrap" : "bootstrap",
        "fish" : "fish",
        "layer" : "../plugins/layer/layer"
    },
    shim : {
        'underscore' : {
            exports: '_'
        },
        'backbone' : {
            deps: ['underscore', 'jquery'],
            exports: 'Backbone'
        },
        'bootstrap' : {
            deps: ['jquery'],
            exports: 'bootstrap'
        },
        'layer' : {
            deps : [ 'jquery' ],
            exports : 'layer'
        }
    },
    waitSeconds : 15
});
require([
    'jquery',
    'underscore',
    'backbone',
    '../view/modules/welcome/actions/welcomeAction',
    'i18n!../view/modules/welcome/i18n/welcome',
    'fish',
    'layer',
    'bootstrap'
], function ($, _, Backbone, action, I18N, fish, layer) {
    var tmp_loc = 0;
    var clear;
    var pageData = {
        topTexts : [I18N.TOP_TEXT_1, I18N.TOP_TEXT_2,I18N.TOP_TEXT_3, I18N.TOP_TEXT_4, I18N.TOP_TEXT_5],
        bottomTexts : [I18N.BOTTOM_TEXT_1, I18N.BOTTOM_TEXT_2, I18N.BOTTOM_TEXT_3, I18N.BOTTOM_TEXT_4, I18N.BOTTOM_TEXT_5],
        imgSrcs : [],
        login : I18N.LOGIN,
        register : I18N.REGISTER,
        showName : I18N.SHOW_NAME
    };
    initPageData = function () {
        var dtd = $.Deferred(); //在函数内部，新建一个Deferred对象
        action.getImgSrc(function (result) {
            pageData.imgSrcs = result;
            dtd.resolve(); // 改变Deferred对象的执行状态
        });
        return dtd.promise();
    };
    initPageData().then(function () {
        var ContentModel = Backbone.Model.extend({
            defaults : {
                top_text : "",
                bottom_text : "",
                img : ""
            },
            initialize : function(model, view) {
                this.on('change', view.render);
            }
        });
        var MyView = Backbone.View.extend({
            initialize : function() {
                this.contentModel = new ContentModel({
                    top_text : pageData.topTexts[0],
                    bottom_text : pageData.bottomTexts[0],
                    img : pageData.imgSrcs[0]
                }, this);
            },
            events : {
                'click .circle' : 'circleChangeModel',
                'click #content_left, #content_right' : 'imgTurnChangeModel',
                'click #denglu, #zhuce' : 'redirectPage',
                'click a' : 'changeLanguage'
            },
            circleChangeModel : function(e) {
                clearInterval(clear);
                var seq = ~~$(e.target).attr('id').split("_")[1];
                tmp_loc = seq - 1;
                this.contentModel.set({
                    top_text : pageData.topTexts[tmp_loc],
                    bottom_text : pageData.bottomTexts[tmp_loc],
                    img : pageData.imgSrcs[tmp_loc]
                });
                clear = setInterval(this.autoMove, 3000);
            },
            changeCircleBrg : function() {
                // 将所有的圆 的背景色清空
                var circles = $(".circle");
                for (var j=0; j<circles.length; j++){
                    circles[j].style.backgroundColor = "";
                }
                // 设置选中的点背景色为白色
                circles[tmp_loc].style.backgroundColor = "white";
            },
            imgTurnChangeModel : function(e) {
                clearInterval(clear);
                var direct = $(e.target).attr('alt') === 'left' ? -1 : 1;
                tmp_loc = tmp_loc + direct < 0 ? tmp_loc + direct + 5 :
                    tmp_loc + direct > 4 ? tmp_loc + direct - 5 : tmp_loc + direct;
                this.contentModel.set({
                    top_text : pageData.topTexts[tmp_loc],
                    bottom_text : pageData.bottomTexts[tmp_loc],
                    img : pageData.imgSrcs[tmp_loc]
                });
                clear = setInterval(this.autoMove, 3000);
            },
            autoMove : function() {
                // 判断 tmp_loc 当前值
                tmp_loc = tmp_loc + 1 > 4 ? tmp_loc + 1 - 5 : tmp_loc + 1;
                // 根据 tmp_loc 进行初始化
                myView.contentModel.set({
                    top_text : pageData.topTexts[tmp_loc],
                    bottom_text : pageData.bottomTexts[tmp_loc],
                    img : pageData.imgSrcs[tmp_loc]
                });
            },
            changeLanguage : function(e) {
                clearInterval(clear);
                var language = e.target.className;
                localStorage.setItem("language", language);
                var param = {
                    language : language
                }
                action.changeLocalInfo(param, function () {
                    // 重新加载页面，目的是为了改变加载的国际化资源文件
                    location.reload();
                });
            },
            redirectPage : function(e) {
                clearInterval(clear);
                // location.href = $(e.target).attr('id') === 'zhuce' ?
                //     '../../register/templates/registerTmpl.html' : '../../login/templates/loginTmpl.html';
                var status = $(e.target).attr('id');
                if (status === 'zhuce') {
                    layer.alert(I18N.ALERT_FRONT_INFO, {
                        icon: 1,
                        skin: 'layer-ext-seaing'
                    });
                } else {
                    action.sendErrorMsg(function (e) {
                        layer.alert('success', {
                            icon: 1,
                            skin: 'layer-ext-seaing'
                        });
                    }, {
                        error : function (xhr, status, error) {
                            if (xhr.responseJSON) {
                                var message = xhr.responseJSON.message;
                                layer.alert(message, {
                                    icon: 2,
                                    skin: 'layer-ext-seaing'
                                });
                            }
                        }
                    });
                }
            },
            render : function(context) {
                var template = _.template($('#welcome').html());
                $(this.el || myView.el).html(template(context.attributes || context));
                var windowH = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
                $("#body_content").css("height", windowH - 50);
                var that = this.changeCircleBrg ? this : myView;
                that.changeCircleBrg();
                if(!context.attributes) {
                    clear = setInterval(this.autoMove, 3000);
                }
            }
        });

        window.onresize = function() {
            var windowH = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
            $("#body_content").css("height", windowH - 50);
        };

        var myView = new MyView({el : $('#welcome_container')});
        myView.render($.extend(myView.contentModel.attributes, {
            login : pageData.login,
            register : pageData.register,
            language : localStorage.getItem("language") || 'en',
            showName : pageData.showName
        }));
    });

});