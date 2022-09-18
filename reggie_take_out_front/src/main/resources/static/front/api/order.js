/**
 * 提交订单
 * @param data
 * @returns {*}
 */
function addOrderApi(data) {
    return $axios({
        'url': '/order/submit',
        'method': 'post',
        data
    })
}

/**
 * 查询所有订单
 * @returns {*}
 */
function orderListApi() {
    return $axios({
        'url': '/order/list',
        'method': 'get',
    })
}

/**
 * 分页查询订单
 * @param data
 * @returns {*}
 */
function orderPagingApi(data) {
    return $axios({
        'url': '/order/userPage',
        'method': 'get',
        params: {...data}
    })
}

/**
 * 再来一单
 * @param data
 * @returns {*}
 */
function orderAgainApi(data) {
    return $axios({
        'url': '/order/again',
        'method': 'post',
        data
    })
}