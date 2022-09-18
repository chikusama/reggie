/**
 * 后台管理：管理员登录接口
 * @param data
 * @returns {*}
 */
function loginApi(data) {
    return $axios({
        'url': '/employee/login',
        'method': 'post',
        data
    })
}

/**
 * 后台管理：管理员退出接口
 * @returns {*}
 */
function logoutApi() {
    return $axios({
        'url': '/employee/logout',
        'method': 'post',
    })
}
