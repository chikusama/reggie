/**
 * 点击登录接口
 * @param data
 * @returns {*}
 */
function loginApi(data) {
    return $axios({
        'url': '/user/login',
        'method': 'post',
        data
    })
}

/**
 * 点击退出接口
 * @returns {*}
 */
function loginoutApi() {
    return $axios({
        'url': '/user/loginout',
        'method': 'post',
    })
}

/**
 * 点击发送验证码接口
 * @param data
 * @returns {*}
 */
function sendMsgApi(data) {
    return $axios({
        'url': '/user/sendMsg',
        'method': 'post',
        data
    })
}
