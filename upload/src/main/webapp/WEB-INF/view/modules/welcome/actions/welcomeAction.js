var hrefs = location.href.split('/');
var baseHref = hrefs[0] + '//' + hrefs[2] + '/' + hrefs[3] + '/';
require.config({
    baseUrl: baseHref + 'resource/scripts',
    paths: {
        "fish" : "fish"
    },
    waitSeconds : 15
});
define(["fish"], function (fish) {
    return {
        getImgSrc : function (callback) {
            return fish.get("welcome/imgsrc", callback);
        },
        changeLocalInfo : function (param, callback) {
            return fish.post("locale", param, callback);
        },
        sendErrorMsg : function (callback, options) {
            return fish.get("welcome/error/msg", callback, options);
        }
    }
});