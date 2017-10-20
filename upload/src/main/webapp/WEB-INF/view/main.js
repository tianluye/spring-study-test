require.config({
    baseUrl: 'resource/scripts',
    paths: {
        "fish" : "fish"
    },
    waitSeconds : 15
});
define(['fish'], function(fish) {
    var lan = localStorage.getItem("language");
    if (!lan) {
        fish.get('locale', function (locale) {
            // 设置 localStorage
            localStorage.setItem("language", locale.language);
        }).then(function () {
            // 配置了 addResourceHandlers处理器，resource相当于 WEB-INF下的路径
            location.href = "resource/view/modules/welcome/templates/welcomeTmpl.html";
        });
    } else {
        // 配置了 addResourceHandlers处理器，resource相当于 WEB-INF下的路径
        location.href = "resource/view/modules/welcome/templates/welcomeTmpl.html";
    }
});