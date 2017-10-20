var hrefs = location.href.split('/');
var baseHref = hrefs[0] + '//' + hrefs[2] + '/' + hrefs[3] + '/';
require.config({
	baseUrl: baseHref + 'resource/scripts',
	paths: {
		"jquery": "jquery",
		"configItem" : "configItem"
	},
	waitSeconds : 15
});
define(['jquery', 'configItem'], function($, configItem) {

	// 根据cookie 名获取 cookie
	function cookieReader(name) {
		var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
		if (arr = document.cookie.match(reg))
			return decodeURI(arr[2]);
		else
			return null;
	}

	function cookieWriter() {
		function buildCookieString(name, value, options) {
			var path, expires;
			options = options || {};
			expires = options.expires;

			path = options.path ? options.path : '';
			if (value === undefined) {
				expires = 'Thu, 01 Jan 1970 00:00:00 GMT';
				value = '';
			}
			if (toString.call(expires) === '[object String]') {
				expires = new Date(expires);
			}
			var str = encodeURIComponent(name) + '=' + encodeURIComponent(value);
			str += path ? ';path=' + path : '';
			str += options.domain ? ';domain=' + options.domain : '';
			str += expires ? ';expires=' + expires.toUTCString() : '';
			str += options.secure ? ';secure' : '';

			var cookieLength = str.length + 1;
			if (cookieLength > 4096) {
				console.warn("Cookie '" + name +
					"' possibly not set or overflowed because it was too large (" +
					cookieLength + " > 4096 bytes)!");
			}
			return str;
		}
		return function(name, value, options) {
			document.cookie = buildCookieString(name, value, options);
		};
	}

	return {
		get : function(url, param, callback, options) {
			return this.callService("get", url, param, callback, options);
		},
		post : function(url, param, callback, options) {
			return this.callService("post", url, param, callback, options);
		},
		// dataType放在最后处理，不传默认为 undefined
		callService : function(method, url, param, callback, options) {
			// 处理param为空的情况
			if ($.isFunction(param)) {
				options = options || callback;
				callback = param;
				param = undefined;
			}
			// 在调用 ajax前，就清除this.isAsync，防止其他 方法误用
			var flag = this.isAsync;
			delete this.isAsync;
			return $.ajax({
				url : configItem.BASE_URL + url,
				type : method,
				contentType: configItem.CONTENT_TYPE,
				async : flag === undefined ? true : flag ? true : false,
				dataType : (options && options.dataType) ? options.dataType : configItem.dataType.JSON,
				data : JSON.stringify(param),
				// 如果回调 callback是函数的话，就只是成功回调。 callback = {success: function(){}, error: function(){}}
				success : callback,
				error : (options && options.error) ? options.error : function (e) {
					console.log(e);
				}
			});
		},
		/**
		 * 定义了链式操作，为当前对象新增一个 isAsync属性值，用于控制是否异步。
		 * 最后返回 this对象，保证调用该方法后可以继续调用 fish里面的方法
		 * 注意：
		 * 1、该方法一定要放在链式操作的第一步
		 * 2、还可以定义另一种方式：callService方法新增参数 options，$.ajax($.extends({}, options));
		 * @param async
		 * @returns {jquery}
		 */
		async : function(async) {
			this.isAsync = async;
			return this;
		},
		// 定义为返回值，方便 action中不必再次引入
		configItem : configItem,

		cookies : {
			/**
			 * 获取cookie记录
			 * @param {String} key 键值
			 * @return {Object} key对应的cookie记录
			 */
			get: function (key) {
				return cookieReader(key);
			},
			/**
			 * 设置cookie记录
			 * @param {String} key 键值
			 * @param {Object} value 键值内容
			 * @param {Object} options 配置项可以包含以下key。
			 * expires : 指定了coolie的生存期，默认情况下coolie是暂时存在的
			 */
			set: function (key, value, options) {
				cookieWriter()(key, value, options);
			},
			/**
			 * 使cookie立即失效
			 * @param {String} key
			 */
			remove: function (key, options) {
				this.set(key, undefined, options);
			}
		}

	};
});